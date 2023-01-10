package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ApartmentImportDTO;
import softuni.exam.models.dto.wrappers.ApartmentImportWrapperDTO;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.enums.ApartmentType;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static softuni.exam.constants.FilePaths.*;
import static softuni.exam.constants.ValidationMessages.*;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;
    private final XmlParser xmlParser;

    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository,
                                TownRepository townRepository,
                                ValidationUtil validationUtil,
                                ModelMapper mapper, XmlParser xmlParser) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(APARTMENTS_XML_PATH));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        final StringBuilder outputBuilder = new StringBuilder();

        final ApartmentImportWrapperDTO wrapperDTO =
                xmlParser.parseXml(APARTMENTS_XML_PATH, ApartmentImportWrapperDTO.class);

        final List<ApartmentImportDTO> apartments = wrapperDTO.getApartments();

        for (ApartmentImportDTO apartment : apartments) {
            if (!validationUtil.isValid(apartment) || doesApartmentExist(apartment)) {
                outputBuilder.append(INVALID_APARTMENT_MESSAGE).append(System.lineSeparator());
                continue;
            }

            final Apartment apartmentToAdd = this.mapper.map(apartment, Apartment.class);
            apartmentToAdd.setTown(this.townRepository.findByTownName(apartment.getTown()).get());
            apartmentToAdd.setApartmentType(ApartmentType.valueOf(apartment.getApartmentType().toUpperCase()));

            this.apartmentRepository.save(apartmentToAdd);

            outputBuilder.append(String.format(SUCCESSFULLY_IMPORTED_APARTMENT_FORMAT, apartment.getApartmentType(), apartment.getArea()))
                    .append(System.lineSeparator());
        }

        return outputBuilder.toString();
    }

    private boolean doesApartmentExist(ApartmentImportDTO apartment) {
        return this.apartmentRepository.findByAreaAndTownTownName(apartment.getArea(), apartment.getTown()).isPresent();
    }
}
