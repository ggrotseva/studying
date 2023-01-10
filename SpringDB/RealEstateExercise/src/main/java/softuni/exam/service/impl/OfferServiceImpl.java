package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferImportDTO;
import softuni.exam.models.dto.wrappers.OfferImportWrapperDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
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
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final AgentRepository agentRepository;
    private final ApartmentRepository apartmentRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;
    private final XmlParser xmlParser;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository,
                            AgentRepository agentRepository,
                            ApartmentRepository apartmentRepository,
                            ValidationUtil validationUtil,
                            ModelMapper mapper, XmlParser xmlParser) {
        this.offerRepository = offerRepository;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFERS_XML_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        final StringBuilder outputBuilder = new StringBuilder();

        final OfferImportWrapperDTO wrapperDTO = this.xmlParser.parseXml(OFFERS_XML_PATH, OfferImportWrapperDTO.class);

        final List<OfferImportDTO> offers = wrapperDTO.getOffers();

        for (OfferImportDTO offer : offers) {
            if (!validationUtil.isValid(offer) || !doesAgentExist(offer)) {
                outputBuilder.append(INVALID_OFFER_MESSAGE).append(System.lineSeparator());
                continue;
            }

            final Offer offerToAdd = setupOfferEntity(offer);
            this.offerRepository.save(offerToAdd);

            outputBuilder.append(String.format(SUCCESSFULLY_IMPORTED_OFFER_FORMAT, offer.getPrice()))
                    .append(System.lineSeparator());
        }

        return outputBuilder.toString();
    }

    @Override
    public String exportOffers() {
        return null;
    }

    private Offer setupOfferEntity(OfferImportDTO offerDTO) {
        Offer offer = this.mapper.map(offerDTO, Offer.class);

        long id = offerDTO.getApartment().getId();
        Apartment apartment = this.apartmentRepository.findById(id).get();
        offer.setApartment(apartment);

        Agent agent = this.agentRepository.findByFirstName(offerDTO.getAgent().getFirstName()).get();
        offer.setAgent(agent);

        return offer;
    }

    private boolean doesAgentExist(OfferImportDTO offer) {
        return this.agentRepository.findByFirstName(offer.getAgent().getFirstName()).isPresent();
    }
}
