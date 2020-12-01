package nl.ikea.warehouse.controllers.impl;

import nl.ikea.warehouse.services.IArticleService;
import nl.ikea.warehouse.views.impl.ArticleView;
import nl.ikea.warehouse.views.impl.ProductView;
import nl.ikea.warehouse.controllers.IArticleController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Article controller implementation.
 *
 * @see IArticleController
 */
@Controller
public class ArticleController implements IArticleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    private final IArticleService iArticleService;

    /**
     * Dependency injection default constructor
     *
     * @param iArticleService {@link IArticleService}
     */
    @Autowired
    public ArticleController(IArticleService iArticleService) {
        this.iArticleService = iArticleService;
    }

    /**
     * {@inheritDoc}
     *
     * @param productId - {@link String} representation of {@link ProductView} unique identifier
     * @param articleId - {@link String} representation of {@link ProductView} unique identifier
     * @return
     */
    @Override
    public ResponseEntity<Void> delete(String productId, String articleId) {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @param productId - {@link String} representation of {@link ArticleView} unique identifier
     * @return {@link ResponseEntity} wrapping {@link List} of {@link ArticleView}
     */
    @Override
    public ResponseEntity<List<ArticleView>> getProductArticles(String productId) {
        LOGGER.info("Get product articles data.");
        return ResponseEntity.ok(this.iArticleService.getProductArticles(Long.valueOf(productId)));
    }

    /**
     * {@inheritDoc}
     *
     * @param productId - {@link String} representation of {@link ProductView} unique identifier
     * @param articleId - {@link String} representation of {@link ArticleView} unique identifier
     * @return
     */
    @Override
    public ResponseEntity<ArticleView> getProductArticle(String productId, String articleId) {
        LOGGER.info("Get product article data.");
        return ResponseEntity.ok(
                this.iArticleService.getProductArticle(Long.valueOf(productId), Long.valueOf(articleId)).orElse(null));
    }

    /**
     * {@inheritDoc}
     *
     * @param productId   - {@link String} representation of {@link ProductView} unique identifier
     * @param articleView - {@link ArticleView} data
     * @return {@link ResponseEntity} wrapping {@link ArticleView}
     */
    @Override
    public ResponseEntity<ArticleView> saveArticle(String productId, ArticleView articleView) {
        LOGGER.info("Persist product article data.");
        return ResponseEntity.ok(this.iArticleService.save(Long.valueOf(productId), articleView).orElse(null));
    }
}
