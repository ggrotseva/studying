package softuni.modelMapping.services;

import softuni.modelMapping.entities.dtos.gson.AddressDTO;
import softuni.modelMapping.entities.dtos.gson.CreateAddressDTO;

public interface AddressService {

    AddressDTO create(CreateAddressDTO data);
}
