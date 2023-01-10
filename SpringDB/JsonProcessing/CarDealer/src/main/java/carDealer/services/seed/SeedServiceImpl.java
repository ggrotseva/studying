package carDealer.services.seed;

import carDealer.domain.cars.Car;
import carDealer.domain.cars.CarNoIdDTO;
import carDealer.domain.customers.Customer;
import carDealer.domain.customers.CustomerImportDTO;
import carDealer.domain.parts.Part;
import carDealer.domain.parts.PartImportDTO;
import carDealer.domain.sales.Sale;
import carDealer.domain.suppliers.Supplier;
import carDealer.domain.suppliers.SupplierImportDTO;
import carDealer.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static carDealer.constants.Utils.GSON;

@Service
public class SeedServiceImpl implements SeedService {

    private static final String SUPPLIERS_PATH =
                    "CarDealer/src/main/resources/dbContent/suppliers.json";
    private static final String PARTS_PATH =
                    "CarDealer/src/main/resources/dbContent/parts.json";
    private static final String CARS_PATH =
                    "CarDealer/src/main/resources/dbContent/cars.json";
    private static final String CUSTOMERS_PATH =
                    "CarDealer/src/main/resources/dbContent/customers.json";

    private final SupplierRepository supplierRepository;
    private final PartRepository partRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;
    private final ModelMapper mapper;

    @Autowired
    public SeedServiceImpl(CustomerRepository customerRepository, SupplierRepository supplierRepository,
                           PartRepository partRepository, CarRepository carRepository,
                           SaleRepository saleRepository) {
        this.supplierRepository = supplierRepository;
        this.partRepository = partRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;

        this.mapper = new ModelMapper();
    }

    @Override
    public void seedSuppliers() throws IOException {
        if (this.supplierRepository.count() == 0) {
            FileReader supplierReader = new FileReader(SUPPLIERS_PATH);

            SupplierImportDTO[] supplierDtos = GSON.fromJson(supplierReader, SupplierImportDTO[].class);

            List<Supplier> suppliers = Arrays.stream(supplierDtos)
                    .map(sup -> mapper.map(sup, Supplier.class))
                    .collect(Collectors.toList());

            this.supplierRepository.saveAllAndFlush(suppliers);
            supplierReader.close();
        }
    }

    @Override
    public void seedParts() throws IOException {
        if (this.partRepository.count() == 0) {
            FileReader partsReader = new FileReader(PARTS_PATH);

            PartImportDTO[] partDtos = GSON.fromJson(partsReader, PartImportDTO[].class);

            List<Part> parts = Arrays.stream(partDtos)
                    .map(part -> mapper.map(part, Part.class))
                    .map(this::setRandomSupplier)
                    .collect(Collectors.toList());

            this.partRepository.saveAllAndFlush(parts);
            partsReader.close();
        }
    }

    @Override
    public void seedCars() throws IOException {
        if (this.carRepository.count() == 0) {
            FileReader carsReader = new FileReader(CARS_PATH);

            CarNoIdDTO[] carDtos = GSON.fromJson(carsReader, CarNoIdDTO[].class);

            List<Car> cars = Arrays.stream(carDtos)
                    .map(part -> mapper.map(part, Car.class))
                    .map(this::setRandomParts)
                    .collect(Collectors.toList());

            this.carRepository.saveAllAndFlush(cars);
            carsReader.close();
        }
    }

    @Override
    public void seedCustomers() throws IOException {
        if (this.customerRepository.count() == 0) {
            FileReader customerReader = new FileReader(CUSTOMERS_PATH);

            CustomerImportDTO[] customerDtos = GSON.fromJson(customerReader, CustomerImportDTO[].class);

            List<Customer> customers = Arrays.stream(customerDtos)
                    .map(customer -> mapper.map(customer, Customer.class))
                    .collect(Collectors.toList());

            this.customerRepository.saveAllAndFlush(customers);
            customerReader.close();
        }
    }

    @Override
    public void seedSales() {
        if (saleRepository.count() == 0) {

            List<Integer> discounts = List.of(0, 5, 10, 15, 20, 30, 40, 50);

            // getting the cars
            long customerCount = this.customerRepository.count();
            long carCount = this.carRepository.count();
            long randomSalesCount = new Random().nextLong(customerCount + 1, carCount);

            List<Car> cars = this.carRepository.getRandomCars(randomSalesCount);

            // seeding sales
            List<Sale> sales = new ArrayList<>();

            for (Car car : cars) {

                long randomCustomerId = new Random().nextLong(customerCount) + 1;
                Customer customer = this.customerRepository.findById(randomCustomerId).get();

                int randomDiscount = new Random().nextInt(discounts.size());
                int discountPercentage = discounts.get(randomDiscount);

                if (customer.isYoungDriver()) {
                    discountPercentage += 5;
                }

                Sale sale = new Sale(car, customer, discountPercentage);

                sales.add(sale);
            }

            this.saleRepository.saveAllAndFlush(sales);
        }
    }

    private Car setRandomParts(Car car) {
        int randomCount = new Random().nextInt(3, 6);
        long partsCount = this.partRepository.count();

        Set<Part> partsToAdd = new HashSet<>();

        while (partsToAdd.size() < randomCount) {
            long randomId = new Random().nextLong(partsCount) + 1;

            Part partById = this.partRepository.findById(randomId).get();

            partsToAdd.add(partById);
        }

        car.setParts(partsToAdd);

        return car;
    }

    private Part setRandomSupplier(Part part) {
        long suppliersCount = this.supplierRepository.count();

        long randomId = new Random().nextLong(suppliersCount) + 1;

        part.setSupplier(this.supplierRepository.findById(randomId).get());

        return part;
    }
}
