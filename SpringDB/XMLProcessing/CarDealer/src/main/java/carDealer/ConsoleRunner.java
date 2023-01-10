package carDealer;

import carDealer.services.cars.CarService;
import carDealer.services.customers.CustomerService;
import carDealer.services.sales.SaleService;
import carDealer.services.seed.SeedService;
import carDealer.services.suppliers.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final CustomerService customerService;
    private final CarService carService;
    private final SupplierService supplierService;
    private final SaleService saleService;

    @Autowired
    public ConsoleRunner(SeedService seedService, CustomerService customerService,
                         CarService carService, SupplierService supplierService, SaleService saleService) {
        this.seedService = seedService;
        this.customerService = customerService;
        this.carService = carService;
        this.supplierService = supplierService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.seedService.seedAllData();


        // 1
//        this.customerService.findAllByOrderByBirthDateIsYoungDriver();


        // 2
//        this.carService.findAllByMakeToyotaOrderByTravelledDistance();


        // 3
//        this.supplierService.findAllByIsImporterFalse();


        // 4
//        this.carService.getAllCarsWithParts();


        // 5
//        this.customerService.getAllWithSales();


        // 6
//        this.saleService.getAllWithDiscounts();
    }
}
