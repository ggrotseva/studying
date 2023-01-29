package GameStore.services;

public interface OrderService {

    String addItem(String gameTitle);

    String removeItem(String gameTitle);

    String buyItem();

    void setOrderToNull();
}
