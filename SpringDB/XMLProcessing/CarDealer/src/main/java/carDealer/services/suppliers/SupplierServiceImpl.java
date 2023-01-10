package carDealer.services.suppliers;

import carDealer.constants.OutputPaths;
import carDealer.constants.Utils;
import carDealer.domain.suppliers.SupplierPartsDTO;
import carDealer.domain.suppliers.wrappers.SupplierPartsWrapperDTO;
import carDealer.repositories.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ModelMapper mapper;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper mapper) {
        this.supplierRepository = supplierRepository;
        this.mapper = mapper;
    }

    @Override
    public List<SupplierPartsDTO> findAllByIsImporterFalse() throws JAXBException {

        final List<SupplierPartsDTO> supplierPartsDtos = this.supplierRepository.findAllByIsImporterFalse()
                .stream().map(supplier -> mapper.map(supplier, SupplierPartsDTO.class))
                .collect(Collectors.toList());

        final SupplierPartsWrapperDTO wrapperDTO = new SupplierPartsWrapperDTO(supplierPartsDtos);

        Utils.writeXmlIntoFile(wrapperDTO, OutputPaths.LOCAL_SUPPLIERS);

        return supplierPartsDtos;
    }
}
