package softuni.modelMapping.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.modelMapping.entities.Address;
import softuni.modelMapping.entities.dtos.gson.AddressDTO;
import softuni.modelMapping.entities.dtos.gson.CreateAddressDTO;
import softuni.modelMapping.repositories.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;
    private final ModelMapper mapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper mapper) {
        this.addressRepository = addressRepository;
        this.mapper = mapper;
    }

    @Override
    public AddressDTO create(CreateAddressDTO data) {
        Address address = mapper.map(data, Address.class);

        Address saved = this.addressRepository.save(address);

        return this.mapper.map(saved, AddressDTO.class);
    }
}
