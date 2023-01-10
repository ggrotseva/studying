package productShop.domain.dtos.user.wrappers;

import productShop.domain.dtos.user.UserImportDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserImportWrapperDTO {

    @XmlElement(name = "user")
    private List<UserImportDTO> users;

    public UserImportWrapperDTO() {
    }

    public List<UserImportDTO> getUsers() {
        return users;
    }
}
