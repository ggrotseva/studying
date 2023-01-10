package carDealer.services.suppliers;

import carDealer.domain.suppliers.SupplierPartsDTO;

import java.io.IOException;
import java.util.List;

public interface SupplierService {

    List<SupplierPartsDTO> findAllByIsImporterFalse() throws IOException;
}
