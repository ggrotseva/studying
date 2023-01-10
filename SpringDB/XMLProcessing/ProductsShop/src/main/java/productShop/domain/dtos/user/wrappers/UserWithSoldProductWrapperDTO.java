package productShop.domain.dtos.user.wrappers;

import productShop.domain.dtos.user.UserWithSoldProductDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithSoldProductWrapperDTO {

    @XmlElement(name = "user")
    private List<UserWithSoldProductDTO> users;

    public UserWithSoldProductWrapperDTO() {
    }

    public UserWithSoldProductWrapperDTO(List<UserWithSoldProductDTO> users) {
        this.users = users;
    }
}
