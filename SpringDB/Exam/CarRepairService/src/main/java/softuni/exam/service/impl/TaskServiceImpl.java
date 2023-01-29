package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.wrappers.TaskSeedWrapperDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Task;
import softuni.exam.models.entity.enums.CarType;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.repository.PartRepository;
import softuni.exam.repository.TaskRepository;
import softuni.exam.service.TaskService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final String TASKS_XML_PATH = "src/main/resources/files/xml/tasks.xml";

    private final TaskRepository taskRepository;
    private final MechanicRepository mechanicRepository;
    private final PartRepository partRepository;
    private final CarRepository carRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;
    private final XmlParser xmlParser;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,
                           MechanicRepository mechanicRepository,
                           PartRepository partRepository,
                           CarRepository carRepository,
                           ValidationUtil validationUtil,
                           ModelMapper mapper, XmlParser xmlParser) {
        this.taskRepository = taskRepository;
        this.mechanicRepository = mechanicRepository;
        this.partRepository = partRepository;
        this.carRepository = carRepository;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.taskRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(Path.of(TASKS_XML_PATH));
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        final StringBuilder output = new StringBuilder();

        final TaskSeedWrapperDTO wrapper = this.xmlParser.fromXml(TASKS_XML_PATH, TaskSeedWrapperDTO.class);

        wrapper.getTasks().stream()
                .filter(taskDTO -> {
                    boolean isValid = this.validationUtil.isValid(taskDTO);

                    Optional<Car> car = this.carRepository.findById(taskDTO.getCar().getId());
                    Optional<Mechanic> mechanic = this.mechanicRepository.findByFirstName(taskDTO.getMechanic().getFirstName());

                    if (car.isEmpty() || mechanic.isEmpty()) {
                        isValid = false;
                    }

                    output.append(isValid ?
                            String.format("Successfully imported task %.2f", taskDTO.getPrice())
                            : "Invalid task")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(taskDTO -> {
                    Task task = this.mapper.map(taskDTO, Task.class);

                    task.setCar(this.carRepository.findById(taskDTO.getCar().getId()).get());
                    task.setPart(this.partRepository.findById(taskDTO.getPart().getId()).get());
                    task.setMechanic(this.mechanicRepository.findByFirstName(taskDTO.getMechanic().getFirstName()).get());

                    return task;
                })
                .forEach(taskRepository::save);

        return output.toString();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        final StringBuilder output = new StringBuilder();

        List<Task> tasks = this.taskRepository.findByCarCarTypeOrderByPriceDesc(CarType.coupe);

        tasks.forEach(t -> output.append(String.format(
                "Car %s %s with %dkm%n" +
                "-Mechanic: %s %s - task №%d:%n" +
                "--Engine: %.1f%n" +
                "---Price: %.2f$",
                t.getCar().getCarMake(), t.getCar().getCarModel(), t.getCar().getKilometers(),
                t.getMechanic().getFirstName(), t.getMechanic().getLastName(), t.getId(),
                t.getCar().getEngine(),
                t.getPrice())));

        return output.toString().trim();
    }
}
