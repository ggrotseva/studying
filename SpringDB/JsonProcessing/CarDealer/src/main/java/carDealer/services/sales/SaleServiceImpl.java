package carDealer.services.sales;

import carDealer.constants.OutputPaths;
import carDealer.constants.Utils;
import carDealer.domain.sales.SaleWithDiscountDTO;
import carDealer.repositories.SaleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public List<SaleWithDiscountDTO> getAllWithDiscounts() throws IOException {
        List<SaleWithDiscountDTO> salesWithDiscounts = this.saleRepository.getAllWithDiscounts();

        Utils.writeJsonIntoFile(salesWithDiscounts, OutputPaths.SALES_DISCOUNT);

        return salesWithDiscounts;
    }
}
