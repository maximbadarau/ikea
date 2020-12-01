package nl.ikea.warehouse.services;

import nl.ikea.warehouse.views.impl.ArticleView;
import nl.ikea.warehouse.views.impl.InventoryView;
import nl.ikea.warehouse.views.impl.ProductView;

import java.util.List;
import java.util.Optional;

/**
 * Article service contract definition
 */
public interface IArticleService {
    /**
     * Persist {@link ArticleView}
     *
     * @param productId   - {@link Long} representation of {@link ProductView}
     *                    unique identifier.
     * @param articleView - {@link ArticleView} representation data.
     * @return {@link Optional} of {@link ArticleView}
     */
    Optional<ArticleView> save(Long productId, ArticleView articleView);

    /**
     * Persist {@link ArticleView}
     *
     * @param articleView - {@link ArticleView} representation data.
     * @return {@link Optional} of {@link ArticleView}
     */
    Optional<ArticleView> save(ArticleView articleView);

    /**
     * Persist {@link InventoryView}
     *
     * @param inventoryView - {@link InventoryView} representation data.
     * @return {@link Optional} of {@link ArticleView}
     */
    Optional<InventoryView> save(InventoryView inventoryView);

    /**
     * Get {@link ArticleView}
     *
     * @param productId - {@link Long} representation of {@link ProductView}
     *                  unique identifier.
     * @param articleId {@link Long} representation of {@link ArticleView} unique identifier.
     * @return {@link Optional} of {@link ArticleView}
     */
    Optional<ArticleView> getProductArticle(Long productId, Long articleId);

    /**
     * Get {@link List} of {@link ArticleView}
     *
     * @param productId - {@link Long} representation of {@link ProductView}
     *                  unique identifier.
     * @return {@link List} of {@link ArticleView}
     */
    List<ArticleView> getProductArticles(Long productId);

    /**
     * Get {@link List} of {@link InventoryView}
     *
     * @return {@link List} of {@link InventoryView}
     */
    List<InventoryView> getArticles();
}
