package bg.softuni.mobilelele.service;

import bg.softuni.mobilelele.model.dto.OfferAddDTO;
import bg.softuni.mobilelele.model.entities.Model;
import bg.softuni.mobilelele.model.entities.Offer;
import bg.softuni.mobilelele.model.entities.User;
import bg.softuni.mobilelele.repository.ModelRepository;
import bg.softuni.mobilelele.repository.OfferRepository;
import bg.softuni.mobilelele.repository.UserRepository;
import bg.softuni.mobilelele.user.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class OfferService implements DatabaseInitService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final ModelRepository modelRepository;
    private final CurrentUser currentUser;
    private final ModelMapper mapper;

    public OfferService(OfferRepository offerRepository,
                        UserRepository userRepository,
                        ModelRepository modelRepository,
                        CurrentUser currentUser,
                        ModelMapper mapper) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.modelRepository = modelRepository;
        this.currentUser = currentUser;
        this.mapper = mapper;
    }

    @Override
    public void dbInit() {

    }

    @Override
    public boolean isDbInit() {
        return this.offerRepository.count() > 0;
    }

    public void addOffer(OfferAddDTO addOfferDTO) {
        Offer newOffer = mapper.map(addOfferDTO, Offer.class);

        // TODO: check if current user is logged

        User user = this.userRepository.findById(currentUser.getId())
                .orElseThrow(NoSuchElementException::new);

        Model model = this.modelRepository.findById(addOfferDTO.getModelId())
                .orElseThrow(NoSuchElementException::new);

        newOffer
                .setId(null)
                .setSeller(user)
                .setModel(model)
                .setCreated(LocalDateTime.now());

        System.out.println(newOffer);

        this.offerRepository.save(newOffer);
    }
}
