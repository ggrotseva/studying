package softuni.modelMapping.services;

import softuni.modelMapping.entities.dtos.address.AddressDTO;
import softuni.modelMapping.entities.dtos.address.CreateAddressDTO;

public interface AddressService {

    AddressDTO create(CreateAddressDTO data);
}
