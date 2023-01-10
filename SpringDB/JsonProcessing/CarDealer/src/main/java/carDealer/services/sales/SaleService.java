package carDealer.services.sales;

import carDealer.domain.sales.SaleWithDiscountDTO;

import java.io.IOException;
import java.util.List;

public interface SaleService {

    List<SaleWithDiscountDTO> getAllWithDiscounts() throws IOException;
}
