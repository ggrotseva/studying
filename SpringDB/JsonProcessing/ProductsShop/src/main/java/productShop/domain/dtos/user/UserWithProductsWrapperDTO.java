package productShop.domain.dtos.user;

import java.util.List;

public class UserWithProductsWrapperDTO {

    private Integer usersCount;
    private List<UserAgeWithProductsDTO> users;

    public UserWithProductsWrapperDTO() {
    }

    public UserWithProductsWrapperDTO(List<UserAgeWithProductsDTO> users) {
        this.users = users;
        this.usersCount = users.size();
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public List<UserAgeWithProductsDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserAgeWithProductsDTO> users) {
        this.users = users;
    }
}
