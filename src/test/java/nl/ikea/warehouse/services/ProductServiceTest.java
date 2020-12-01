package nl.ikea.warehouse.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import nl.ikea.warehouse.entities.ArticleEntity;
import nl.ikea.warehouse.entities.ProductEntity;
import nl.ikea.warehouse.repositories.ProductRepository;

import java.util.Arrays;
import java.util.List;

import nl.ikea.warehouse.views.impl.ProductView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Service test definition
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private IProductService iProductService;

    @Test
    public void testGetProducts() {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setName("test");
        articleEntity.setAmount(20L);
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("testName");
        productEntity.setArticles(Arrays.asList(articleEntity));
        Mockito.when(this.productRepository.findAll())
                .thenReturn(Arrays.asList(productEntity));
        List<ProductView> products = this.iProductService.getProducts();
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals(productEntity.getProductId(), products.get(0).getProductId());
        assertEquals(productEntity.getName(), products.get(0).getName());
        assertEquals(productEntity.getArticles().size(), products.get(0).getArticles().size());
    }
}
