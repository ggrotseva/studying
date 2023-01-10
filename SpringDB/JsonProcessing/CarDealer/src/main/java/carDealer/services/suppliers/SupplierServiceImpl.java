package carDealer.services.suppliers;

import carDealer.constants.OutputPaths;
import carDealer.constants.Utils;
import carDealer.domain.suppliers.SupplierPartsDTO;
import carDealer.repositories.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public List<SupplierPartsDTO> findAllByIsImporterFalse() throws IOException {

        List<SupplierPartsDTO> supplierPartsDtos = this.supplierRepository.findAllByIsImporterFalse()
                .stream().map(supplier -> mapper.map(supplier, SupplierPartsDTO.class))
                .collect(Collectors.toList());

        Utils.writeJsonIntoFile(supplierPartsDtos, OutputPaths.LOCAL_SUPPLIERS);

        return supplierPartsDtos;
    }
}
