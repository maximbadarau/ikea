package nl.ikea.warehouse.rest;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import nl.ikea.warehouse.views.impl.ProductView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration tests definition.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@Rollback
public class ProductIntegrationTest extends DefaultIntegrationTest<ProductView> {

    public ProductIntegrationTest() {
        super("/products");
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testGetProducts() {
        ResponseEntity<List<ProductView>> response =
                collectionTypeHttpCall(
                        HttpStatus.OK, new ParameterizedTypeReference<List<ProductView>>() {
                        });
        assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteProducts() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        collectionTypeHttpCall(
                HttpMethod.DELETE,
                headers,
                HttpStatus.NO_CONTENT,
                new ParameterizedTypeReference<List<ProductView>>() {
                });
    }
}
