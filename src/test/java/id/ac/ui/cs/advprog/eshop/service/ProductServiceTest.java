package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @Test
    void testCreate() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        when(productRepository.create(product)).thenReturn(product);

        Product result = productService.create(product);
        assertEquals(product.getProductName(), result.getProductName());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        productList.add(product);

        Iterator<Product> iterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setProductId("eb558e9f");
        product.setProductName("Sampo Cap Bambang");

        when(productRepository.findById("eb558e9f")).thenReturn(product);

        Product result = productService.findById("eb558e9f");
        assertNotNull(result);
        assertEquals("Sampo Cap Bambang", result.getProductName());
        verify(productRepository, times(1)).findById("eb558e9f");
    }

    @Test
    void testEditSuccess() {
        Product existingProduct = new Product();
        existingProduct.setProductId("eb558e9f");
        existingProduct.setProductName("Sampo Lama");
        existingProduct.setProductQuantity(10);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f");
        updatedProduct.setProductName("Sampo Baru");
        updatedProduct.setProductQuantity(20);

        when(productRepository.findById("eb558e9f")).thenReturn(existingProduct);

        productService.edit(updatedProduct);

        assertEquals("Sampo Baru", existingProduct.getProductName());
        assertEquals(20, existingProduct.getProductQuantity());
        verify(productRepository, times(1)).findById("eb558e9f");
    }

    @Test
    void testEditNotFound() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("id-palsu");

        when(productRepository.findById("id-palsu")).thenReturn(null);

        productService.edit(updatedProduct);

        verify(productRepository, times(1)).findById("id-palsu");
    }

    @Test
    void testDelete() {
        String productId = "eb558e9f";

        productService.delete(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }
}