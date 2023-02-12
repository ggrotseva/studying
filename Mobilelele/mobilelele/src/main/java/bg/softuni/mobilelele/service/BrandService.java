package bg.softuni.mobilelele.service;

import bg.softuni.mobilelele.model.dto.BrandDTO;
import bg.softuni.mobilelele.repository.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService implements DatabaseInitService {

    private final BrandRepository brandRepository;
    private final ModelMapper mapper;

    public BrandService(BrandRepository brandRepository, ModelMapper mapper) {
        this.brandRepository = brandRepository;
        this.mapper = mapper;
    }

    @Override
    public void dbInit() {

    }

    @Override
    public boolean isDbInit() {
        return this.brandRepository.count() > 0;
    }

    public List<BrandDTO> getAllBrands() {
        return this.brandRepository.findAll().stream()
                .map(b -> mapper.map(b, BrandDTO.class))
                .collect(Collectors.toList());
    }
}
