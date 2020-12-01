package nl.ikea.warehouse.repositories;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import nl.ikea.warehouse.configurations.PersistenceConfiguration;

import java.util.Arrays;
import java.util.List;

import nl.ikea.warehouse.entities.ArticleEntity;
import nl.ikea.warehouse.entities.ProductEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This test requires: * mongodb instance running on the environment
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfiguration.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MongoOperations mongoOps;

    @Before
    public void testSetup() {
        if (!this.mongoOps.collectionExists(ProductEntity.class)) {
            this.mongoOps.createCollection(ProductEntity.class);
        }
        if (!this.mongoOps.collectionExists(ArticleEntity.class)) {
            this.mongoOps.createCollection(ArticleEntity.class);
        }
    }

    @After
    public void tearDown() {
        if (this.mongoOps.collectionExists(ArticleEntity.class)) {
            this.mongoOps.dropCollection(ArticleEntity.class);
        }
        if (this.mongoOps.collectionExists(ProductEntity.class)) {
            this.mongoOps.dropCollection(ProductEntity.class);
        }
    }

    @Test
    public void whenInsertingProduct_thenProductIsInserted() {
        final String name = "BED";
        final ProductEntity productEntity =
                prepareData(1L, name, Arrays.asList(prepareData(1L, "article_1", 20L, 1L)));
        this.productRepository.insert(productEntity);
        assertThat(
                this.mongoOps
                        .findOne(Query.query(Criteria.where("productId").is(1L)), ProductEntity.class)
                        .getName(),
                is(name));
    }

    @Test
    public void whenSavingNewProduct_thenProductIsInserted() {
        final String name = "Table";
        final ProductEntity productEntity =
                prepareData(1L, name,
                        Arrays.asList(prepareData(1L, "article_2", 30L, 2L)));
        this.productRepository.save(productEntity);
        assertThat(
                this.mongoOps
                        .findOne(
                                Query.query(Criteria.where("name").is(name)), ProductEntity.class)
                        .getName(),
                is(name));
    }

    @Test
    public void givenProductExists_whenSavingExistProduct_thenProductIsUpdated() {
        final String name = "Chair";
        ProductEntity productEntity =
                prepareData(1L, name,
                        Arrays.asList(prepareData(1L, "article_3", 30L, 1L)));
        this.mongoOps.insert(productEntity);
        productEntity =
                this.mongoOps.findOne(
                        Query.query(Criteria.where("name").is(name)),
                        ProductEntity.class);
        this.productRepository.save(productEntity);
        assertThat(this.mongoOps.findAll(ProductEntity.class).size(), is(1));
    }

    @Test
    public void givenProductExists_whenDeletingProduct_thenProductIsDeleted() {
        final String name = "Some product";
        final ProductEntity productEntity =
                prepareData(1L, name,
                        Arrays.asList(prepareData(1L, "article_4", 20L, 1L)));
        this.mongoOps.insert(productEntity);

        this.productRepository.delete(productEntity);

        assertThat(
                this.mongoOps
                        .find(
                                Query.query(Criteria.where("name").is(name)), ProductEntity.class)
                        .size(),
                is(0));
    }

    @Test
    public void givenProductExists_whenFindingProduct_thenProductIsFound() {
        final String name = "Some other product";
        ProductEntity productEntity =
                prepareData(1L, name,
                        Arrays.asList(prepareData(1L, "article_4", 60L, 1L)));
        this.mongoOps.insert(productEntity);
        productEntity =
                this.mongoOps.findOne(
                        Query.query(Criteria.where("productId").is(1L)), ProductEntity.class);
        final ProductEntity foundUser =
                this.productRepository.findById(productEntity.getProductId()).get();
        assertThat(productEntity.getName(), is(foundUser.getName()));
    }

    @Test
    public void givenProductExists_whenCheckingDoesProductExist_thenProductIsExist() {
        final String name = "Random product";
        ProductEntity productEntity =
                prepareData(1L, name,
                        Arrays.asList(prepareData(1L, "article_5", 90L, 1L)));
        this.mongoOps.insert(productEntity);

        productEntity =
                this.mongoOps.findOne(
                        Query.query(Criteria.where("productId").is(1L)), ProductEntity.class);
        assertNotNull(productEntity);
    }

    @Test
    public void givenProductExist_whenFindingAllProductsWithSorting_thenProductsAreFoundAndSorted() {
        final String name = "Product";
        ProductEntity productEntity =
                prepareData(1L, name,
                        Arrays.asList(prepareData(1L, "article_5", 90L, 1L)));
        this.mongoOps.insert(productEntity);

        productEntity =
                prepareData(2L, name,
                        Arrays.asList(prepareData(2L, "article_6", 10L, 2L)));
        this.mongoOps.insert(productEntity);

        final List<ProductEntity> products =
                this.productRepository.findAll(new Sort(Sort.Direction.ASC, "name"));

        assertThat(products.size(), is(2));
        assertThat(products.get(0).getProductId(), is(1L));
        assertThat(products.get(1).getProductId(), is(2L));
    }

    @Test
    public void
    givenProductsExist_whenFindingAllProductsWithPagination_thenProductsAreFoundAndOrderedOnPage() {
        final String name = "Product";
        ProductEntity productEntity =
                prepareData(1L, name,
                        Arrays.asList(prepareData(1L, "article_7", 50L, 1L)));
        this.mongoOps.insert(productEntity);
        productEntity =
                prepareData(2L, name,
                        Arrays.asList(prepareData(2L, "article_8", 50L, 2L)));
        this.mongoOps.insert(productEntity);

        final Pageable pageableRequest = PageRequest.of(0, 1);

        final Page<ProductEntity> page =
                this.productRepository.findAll(pageableRequest);
        List<ProductEntity> product = page.getContent();

        assertThat(product.size(), is(1));
        assertThat(page.getTotalPages(), is(2));
    }

    private ProductEntity prepareData(
            Long productId,
            String name,
            List<ArticleEntity> articles) {
        final ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId(productId);
        productEntity.setName(name);
        productEntity.setArticles(articles);
        return productEntity;
    }

    private ArticleEntity prepareData(
            Long articleId,
            String name,
            Long amount,
            Long productId) {
        final ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setArticleId(articleId);
        articleEntity.setName(name);
        articleEntity.setAmount(amount);
        articleEntity.setProductId(productId);
        return articleEntity;
    }
}
