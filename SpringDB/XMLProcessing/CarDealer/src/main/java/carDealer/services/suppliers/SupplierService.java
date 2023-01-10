package carDealer.services.suppliers;

import carDealer.domain.suppliers.SupplierPartsDTO;

import javax.xml.bind.JAXBException;
import java.util.List;

public interface SupplierService {

    List<SupplierPartsDTO> findAllByIsImporterFalse() throws JAXBException;
}
