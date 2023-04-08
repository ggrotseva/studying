package softuni.expirationManager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import softuni.expirationManager.model.dtos.product.ProductAddDTO;
import softuni.expirationManager.model.dtos.product.ProductViewDTO;
import softuni.expirationManager.model.entities.CategoryEntity;
import softuni.expirationManager.model.entities.ProductEntity;
import softuni.expirationManager.repository.CategoryRepository;
import softuni.expirationManager.repository.ProductRepository;
import softuni.expirationManager.utils.DateTimeProvider;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository mockProductRepository;

    @Mock
    private CategoryRepository mockCategoryRepository;

    @Mock
    private DateTimeProvider mockDateTimeProvider;

    @Mock
    private ModelMapper mockMapper;

    @Captor
    private ArgumentCaptor<ProductEntity> productArgumentCaptor;

    private ProductService testProductService;

    @BeforeEach
    void setUp() {
        testProductService = new ProductService(mockProductRepository, mockCategoryRepository, mockDateTimeProvider, mockMapper);
    }

    @Test
    void testProductAdd() {
        ProductAddDTO testProductAddDto = new ProductAddDTO();
        ProductEntity testProduct = new ProductEntity().setId(2L);
        CategoryEntity testCategory = new CategoryEntity().setId(1L);

        when(mockMapper.map(testProductAddDto, ProductEntity.class))
                .thenReturn(testProduct);

        when(mockCategoryRepository.findById(testCategory.getId())).thenReturn(Optional.of(testCategory));
        when(mockProductRepository.saveAndFlush(testProduct)).thenReturn(testProduct);

        Long returnedProductId = testProductService.addProduct(testProductAddDto, testCategory.getId());

        verify(mockProductRepository).saveAndFlush(productArgumentCaptor.capture());

        ProductEntity savedProduct = productArgumentCaptor.getValue();

        assertEquals(testProduct.getId(), returnedProductId);
        assertEquals(testCategory.getId(), savedProduct.getCategory().getId());
    }

    @Test
    void testProductAdd_NoCategoryFound_Throws() {
        ProductAddDTO testProductAddDto = new ProductAddDTO();
        ProductEntity testProduct = new ProductEntity().setId(2L);

        when(mockMapper.map(testProductAddDto, ProductEntity.class))
                .thenReturn(testProduct);

        assertThrows(NoSuchElementException.class, () -> testProductService.addProduct(testProductAddDto, 2L));
    }

    @Test
    void testDeleteById_NoSuchProduct_Throws() {
        assertThrows(NoSuchElementException.class, () -> testProductService.deleteById(2L));
    }

    @Test
    void testDeleteById_ReturnsProductViewDTO() {

        ProductEntity testProduct = new ProductEntity().setId(2L);
        ProductViewDTO testProductViewDto = new ProductViewDTO().setId(2L);

        when(mockProductRepository.findById(testProduct.getId())).thenReturn(Optional.of(testProduct));
        when(mockMapper.map(testProduct, ProductViewDTO.class))
                .thenReturn(testProductViewDto);

        ProductViewDTO actualProductViewDto = testProductService.deleteById(testProduct.getId());

        verify(mockProductRepository).deleteById(testProduct.getId());

        assertEquals(testProductViewDto.getId(), actualProductViewDto.getId());
    }

    @Test
    void testFindById_NoSuchProduct_Throws() {
        assertThrows(NoSuchElementException.class, () -> testProductService.findById(2L));
    }

}
