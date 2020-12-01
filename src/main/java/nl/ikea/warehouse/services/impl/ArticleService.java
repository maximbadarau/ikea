package nl.ikea.warehouse.services.impl;

import nl.ikea.warehouse.converters.impl.ArticleConverter;
import nl.ikea.warehouse.converters.impl.InventoryConverter;
import nl.ikea.warehouse.repositories.ArticleRepository;
import nl.ikea.warehouse.services.IArticleService;
import nl.ikea.warehouse.views.impl.ArticleView;
import nl.ikea.warehouse.views.impl.InventoryView;
import nl.ikea.warehouse.views.impl.ProductView;
import nl.ikea.warehouse.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * {@inheritDoc}
 */
@Component
public class ArticleService implements IArticleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleService.class);

    private final ArticleConverter articleConverter;
    private final InventoryConverter inventoryConverter;
    private final ArticleRepository articleRepository;

    /**
     * Dependency injection default constructor
     *
     * @param articleRepository  {@link ArticleRepository}
     * @param inventoryConverter {@link InventoryConverter}
     * @param articleConverter   {@link ArticleConverter}
     */
    @Autowired
    public ArticleService(ArticleRepository articleRepository, InventoryConverter inventoryConverter, ArticleConverter articleConverter) {
        this.articleConverter = articleConverter;
        this.inventoryConverter = inventoryConverter;
        this.articleRepository = articleRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @param productId   - {@link Long} representation of {@link ProductView}
     *                    unique identifier.
     * @param articleView - {@link ArticleView} representation data.
     */
    @Override
    public Optional<ArticleView> save(Long productId, ArticleView articleView) {
        LOGGER.info("Entered save() method with: {}", articleView);
        return this.articleConverter.from(
                this.articleConverter
                        .from(productId, articleView)
                        .map(this.articleRepository::save)
                        .orElse(null));
    }

    /**
     * {@inheritDoc}
     *
     * @param articleView - {@link ArticleView} representation data.
     * @return {@link Optional} of {@link ArticleView}
     */
    @Override
    public Optional<ArticleView> save(ArticleView articleView) {
        LOGGER.info("Entered save() method with: {}", articleView);
        return this.articleConverter.from(
                this.articleConverter
                        .from(articleView)
                        .map(this.articleRepository::save)
                        .orElse(null));
    }

    /**
     * {@inheritDoc}
     *
     * @param inventoryView - {@link InventoryView} representation data.
     * @return {@link Optional} of {@link InventoryView}
     */
    @Override
    public Optional<InventoryView> save(InventoryView inventoryView) {
        LOGGER.info("Entered save() method with: {}", inventoryView);
        return this.inventoryConverter.from(
                this.inventoryConverter
                        .from(inventoryView)
                        .map(this.articleRepository::save)
                        .orElse(null));
    }

    /**
     * {@inheritDoc}
     *
     * @param productId - {@link Long} representation of {@link ProductView}
     *                  unique identifier.
     * @param articleId {@link Long} representation of {@link ArticleView} unique identifier.
     * @return {@link Optional} of {@link ArticleView}
     */
    @Override
    public Optional<ArticleView> getProductArticle(Long productId, Long articleId) {
        LOGGER.info(
                "Entered getProductArticle() method with productId: {} and articleId: {}",
                productId,
                articleId);
        return this.articleConverter.from(
                this.articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new));
    }

    /**
     * {@inheritDoc}
     *
     * @param productId - {@link Long} representation of {@link ProductView}
     *                  unique identifier.
     * @return {@link List} of {@link ArticleView}
     */
    @Override
    public List<ArticleView> getProductArticles(Long productId) {
        LOGGER.info(
                "Entered getProductArticles() method with productId: {}",
                productId);
        return this.articleConverter.from(
                this.articleRepository.getProductArticles(productId));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link List} of {@link ArticleView}
     */
    @Override
    public List<InventoryView> getArticles() {
        LOGGER.info("Entered getArticles()");
        return this.inventoryConverter.from(
                this.articleRepository.findAll());
    }
}
