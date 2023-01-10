package productShop.services;

import productShop.domain.dtos.user.UserAgeWithProductsDTO;
import productShop.domain.dtos.user.UserWithSoldProductDTO;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<UserWithSoldProductDTO> findAllUsersWithAtLeastOneSoldProduct() throws IOException;

    List<UserAgeWithProductsDTO> findAllWithSoldProductsOrderByCount() throws IOException;
}
