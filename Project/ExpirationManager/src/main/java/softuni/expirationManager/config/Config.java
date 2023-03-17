package softuni.expirationManager.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.expirationManager.model.dtos.category.CategoryViewDTO;
import softuni.expirationManager.model.entities.CategoryEntity;
import softuni.expirationManager.model.entities.ProductEntity;

import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Configuration
public class Config {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper mapper = new ModelMapper();

        mapper.addMappings(categoryViewDtoPropertyMap());

        return mapper;
    }

    private PropertyMap<CategoryEntity, CategoryViewDTO> categoryViewDtoPropertyMap() {

        Converter<List<ProductEntity>, Integer> collectionToCount = new AbstractConverter<>() {
            protected Integer convert(List<ProductEntity> products) {
                return products.size();
            }
        };

        Converter<byte[], String> encodeToBase64 = new AbstractConverter<>() {
            protected String convert(byte[] icon) {
                if (Objects.requireNonNull(icon).length > 0) {
                    return Base64.getEncoder().encodeToString(icon);
                }
                return null;
            }
        };

        return new PropertyMap<>() {
            @Override
            protected void configure() {
                using(encodeToBase64).map(source.getIcon(), destination.getIcon());
                using(collectionToCount).map(source.getProducts(), destination.getProductsCount());
            }
        };
    }
}
