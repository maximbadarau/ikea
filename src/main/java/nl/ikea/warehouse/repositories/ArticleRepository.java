package nl.ikea.warehouse.repositories;

import nl.ikea.warehouse.entities.ArticleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

/**
 * Account repository
 */
public interface ArticleRepository
        extends MongoRepository<ArticleEntity, Long>, QuerydslPredicateExecutor<ArticleEntity> {

    /**
     * Get article {@link ArticleEntity}
     *
     * @param productId {@link Long} product unique identifier
     * @return {@link List} of {@link ArticleEntity}
     */
    @Query("{ 'productId' : ?0 }")
    List<ArticleEntity> getProductArticles(Long productId);

    /**
     * Get article {@link ArticleEntity}
     *
     * @param productId {@link Long} product unique identifier
     * @param articleId {@link Long}
     * @return {@link List} of {@link ArticleEntity}
     */
    @Query("{ 'productId' : ?0, 'articleId' : ?1  }")
    Optional<ArticleEntity> getProductArticle(Long productId, Long articleId);
}
