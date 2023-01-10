package productShop.domain.dtos.user.wrappers;

import productShop.domain.dtos.user.UserAgeWithProductsDTO;

import javax.xml.bind.annotation.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithProductsWrapperDTO {

    @XmlAttribute(name = "count")
    private Integer usersCount;

    @XmlElement(name = "user")
    private List<UserAgeWithProductsDTO> users;

    public UserWithProductsWrapperDTO() {
    }

    public UserWithProductsWrapperDTO(List<UserAgeWithProductsDTO> users) {
        this.users = users.stream().sorted().collect(Collectors.toList());
        this.usersCount = users.size();
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

}
