package bg.softuni.mobilelele.service.brand;

import bg.softuni.mobilelele.repository.BrandRepository;
import bg.softuni.mobilelele.service.DatabaseInitService;
import org.springframework.stereotype.Service;

@Service
public class BrandService implements DatabaseInitService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public void dbInit() {

    }

    @Override
    public boolean isDbInit() {
        return this.brandRepository.count() > 0;
    }
}
