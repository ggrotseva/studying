package productShop.services;

import productShop.domain.dtos.user.UserAgeWithProductsDTO;
import productShop.domain.dtos.user.UserWithSoldProductDTO;

import javax.xml.bind.JAXBException;
import java.util.List;

public interface UserService {

    List<UserWithSoldProductDTO> findAllUsersWithAtLeastOneSoldProduct() throws JAXBException;

    List<UserAgeWithProductsDTO> findAllWithSoldProductsOrderByCount() throws JAXBException;
}
