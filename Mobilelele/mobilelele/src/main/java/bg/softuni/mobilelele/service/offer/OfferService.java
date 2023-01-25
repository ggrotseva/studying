package bg.softuni.mobilelele.service.offer;

import bg.softuni.mobilelele.repository.OfferRepository;
import bg.softuni.mobilelele.service.DatabaseInitService;
import org.springframework.stereotype.Service;

@Service
public class OfferService implements DatabaseInitService {

    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public void dbInit() {

    }

    @Override
    public boolean isDbInit() {
        return this.offerRepository.count() > 0;
    }
}
