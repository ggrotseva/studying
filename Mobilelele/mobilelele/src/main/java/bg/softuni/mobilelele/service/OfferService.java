package bg.softuni.mobilelele.service;

import bg.softuni.mobilelele.model.dto.OfferAddDTO;
import bg.softuni.mobilelele.model.dto.OfferDTO;
import bg.softuni.mobilelele.model.dto.OfferDetailsDTO;
import bg.softuni.mobilelele.model.dto.OfferUpdateDTO;
import bg.softuni.mobilelele.model.entities.Model;
import bg.softuni.mobilelele.model.entities.Offer;
import bg.softuni.mobilelele.model.entities.UserEntity;
import bg.softuni.mobilelele.repository.ModelRepository;
import bg.softuni.mobilelele.repository.OfferRepository;
import bg.softuni.mobilelele.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class OfferService implements DatabaseInitService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final ModelRepository modelRepository;
    private final ModelMapper mapper;

    public OfferService(OfferRepository offerRepository,
                        UserRepository userRepository,
                        ModelRepository modelRepository,
                        ModelMapper mapper) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.modelRepository = modelRepository;
        this.mapper = mapper;
    }

    @Override
    public void dbInit() {

    }

    @Override
    public boolean isDbInit() {
        return this.offerRepository.count() > 0;
    }

    public void addOffer(OfferAddDTO addOfferDTO, String username) {
        Offer newOffer = mapper.map(addOfferDTO, Offer.class);

        UserEntity user = this.userRepository.findByUsername(username)
                .orElseThrow(NoSuchElementException::new);

        Model model = this.modelRepository.findById(addOfferDTO.getModelId())
                .orElseThrow(NoSuchElementException::new);

        newOffer
                .setId(null)
                .setSeller(user)
                .setModel(model)
                .setCreated(LocalDateTime.now())
                .setModified(LocalDateTime.now());

        System.out.println(newOffer);

        this.offerRepository.save(newOffer);
    }

    public List<OfferDTO> getAllOffers() {
        return this.offerRepository.findAll()
                .stream()
                .map(offer -> this.mapper.map(offer, OfferDTO.class))
                .collect(Collectors.toList());
    }

    public OfferDetailsDTO getDetailedOfferById(Long id) {
        return this.mapper.map(
                this.offerRepository.findById(id).orElseThrow(), OfferDetailsDTO.class);
    }

    public void deleteById(Long id) {
        this.offerRepository.deleteById(id);
    }

    public OfferUpdateDTO getOfferUpdateDTO(Long id) {
        return this.mapper.map(
                this.offerRepository.findById(id).orElseThrow(), OfferUpdateDTO.class);
    }

    public void updateOffer(OfferUpdateDTO offerUpdateDTO) {
        Offer offer = this.mapper.map(offerUpdateDTO, Offer.class);

        offer.setCreated(this.offerRepository.findById(offerUpdateDTO.getId()).orElseThrow().getCreated());
        offer.setModified(LocalDateTime.now());

        this.offerRepository.save(offer);
    }
}
