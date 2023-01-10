package carDealer.services.sales;

import carDealer.domain.sales.SaleWithDiscountDTO;

import javax.xml.bind.JAXBException;
import java.util.List;

public interface SaleService {

    List<SaleWithDiscountDTO> getAllWithDiscounts() throws JAXBException;
}
