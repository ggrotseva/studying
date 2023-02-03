package bg.softuni.mobilelele.service;

import bg.softuni.mobilelele.repository.ModelRepository;
import bg.softuni.mobilelele.service.DatabaseInitService;
import org.springframework.stereotype.Service;

@Service
public class ModelService implements DatabaseInitService {

    private final ModelRepository modelRepository;

    public ModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public void dbInit() {

    }

    @Override
    public boolean isDbInit() {
        return this.modelRepository.count() > 0;
    }
}
