package productShop.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import productShop.constants.OutputPaths;
import productShop.constants.Utils;
import productShop.domain.dtos.user.UserAgeWithProductsDTO;
import productShop.domain.dtos.user.UserDTO;
import productShop.domain.dtos.user.wrappers.UserWithProductsWrapperDTO;
import productShop.domain.dtos.user.UserWithSoldProductDTO;
import productShop.domain.dtos.user.wrappers.UserWithSoldProductWrapperDTO;
import productShop.domain.entities.User;
import productShop.repositories.UserRepository;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public List<UserWithSoldProductDTO> findAllUsersWithAtLeastOneSoldProduct() throws JAXBException {
        List<UserWithSoldProductDTO> users = this.userRepository.findAllWithSoldProducts()
                .orElseThrow(NoSuchElementException::new)
                .stream().map(user -> mapper.map(user, UserWithSoldProductDTO.class))
                .collect(Collectors.toList());

        final UserWithSoldProductWrapperDTO usersWrapper = new UserWithSoldProductWrapperDTO(users);

        Utils.writeXmlIntoFile(usersWrapper, OutputPaths.USERS_WITH_SOLD_PRODUCT_XML);

        return users;
    }

    @Override
    @Transactional
    public List<UserAgeWithProductsDTO> findAllWithSoldProductsOrderByCount() throws JAXBException {

        List<User> users = this.userRepository.findAllWithSoldProductsOrderByCount()
                .orElseThrow(NoSuchElementException::new);

        List<UserAgeWithProductsDTO> userWithProducts = users.stream()
                .map(user -> mapper.map(user, UserDTO.class))
                .map(UserDTO::toUserAgeWithProductsDTO)
                .collect(Collectors.toList());

        final UserWithProductsWrapperDTO userWithProductsWrapperDTO = new UserWithProductsWrapperDTO(userWithProducts);

        Utils.writeXmlIntoFile(userWithProductsWrapperDTO, OutputPaths.USERS_AND_PRODUCTS_XML);

        return userWithProducts;
    }
}
