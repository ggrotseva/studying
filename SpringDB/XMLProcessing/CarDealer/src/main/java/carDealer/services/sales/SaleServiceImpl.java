package carDealer.services.sales;

import carDealer.constants.OutputPaths;
import carDealer.constants.Utils;
import carDealer.domain.sales.SaleWithDiscountDTO;
import carDealer.domain.sales.SaleWithDiscountWrapperDTO;
import carDealer.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public List<SaleWithDiscountDTO> getAllWithDiscounts() throws JAXBException {
        final List<SaleWithDiscountDTO> salesWithDiscounts = this.saleRepository.getAllWithDiscounts();

        final SaleWithDiscountWrapperDTO wrapperDTO = new SaleWithDiscountWrapperDTO(salesWithDiscounts);

        Utils.writeXmlIntoFile(wrapperDTO, OutputPaths.SALES_DISCOUNT);

        return salesWithDiscounts;
    }
}
