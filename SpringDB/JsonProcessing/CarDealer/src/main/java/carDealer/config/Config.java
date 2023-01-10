package carDealer.config;

import carDealer.domain.parts.Part;
import carDealer.domain.suppliers.Supplier;
import carDealer.domain.suppliers.SupplierPartsDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Configuration
@Component
public class Config {

    private class CollectionToSizeConverter extends AbstractConverter<Collection<Part>, Integer> {
        @Override
        protected Integer convert(Collection<Part> source) {
            if (source == null) {
                return 0;
            }
            return source.size();
        }
    }

    @Bean
    public ModelMapper createModelMapper() {
        ModelMapper mapper = new ModelMapper();

        PropertyMap<Supplier, SupplierPartsDTO> supplierMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                using(new CollectionToSizeConverter()).map(source.getParts(), destination.getPartsCount());
            }
        };

        mapper.addMappings(supplierMap);

        return mapper;
    }

}
