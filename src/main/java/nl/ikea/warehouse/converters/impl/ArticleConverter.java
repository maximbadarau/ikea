package nl.ikea.warehouse.converters.impl;

import nl.ikea.warehouse.converters.IDataConverter;
import nl.ikea.warehouse.entities.ArticleEntity;
import nl.ikea.warehouse.views.impl.ArticleView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Article converter implementation
 *
 * @see IDataConverter
 */
@Component
public class ArticleConverter implements IDataConverter<ArticleEntity, ArticleView> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleConverter.class);

    /**
     * {@inheritDoc}
     *
     * @param articleView {@link ArticleView}
     * @return {@link Optional} wrapping {@link ArticleEntity}
     */
    @Override
    public Optional<ArticleEntity> from(ArticleView articleView) {
        LOGGER.info("Converting {} model data to entity", articleView);
        ArticleEntity articleEntity = null;
        if (articleView != null) {
            articleEntity = new ArticleEntity();
            articleEntity.setArticleId(articleView.getArticleId());
            articleEntity.setAmount(articleView.getAmount());
        }
        return Optional.ofNullable(articleEntity);
    }

    /**
     * Assign {@link ArticleEntity} to an existing {@link String} product ID.
     *
     * @param productId   {@link String}
     * @param articleView {@link ArticleView}
     * @return {@link Optional} wrapping {@link ArticleEntity}
     */
    public Optional<ArticleEntity> from(Long productId, ArticleView articleView) {
        LOGGER.info("Converting {} model data to entity", articleView);
        ArticleEntity articleEntity = null;
        if (articleView != null && productId != null) {
            articleEntity = new ArticleEntity();
            articleEntity.setArticleId(articleView.getArticleId());
            articleEntity.setAmount(articleView.getAmount());
            articleEntity.setProductId(productId);
        }
        return Optional.ofNullable(articleEntity);
    }

    /**
     * {@inheritDoc}
     *
     * @param articleEntity {@link ArticleEntity}
     * @return {@link Optional} wrapping {@link ArticleView}
     */
    @Override
    public Optional<ArticleView> from(ArticleEntity articleEntity) {
        LOGGER.info("Converting {} entity data to model", articleEntity);
        ArticleView articleView = null;
        if (articleEntity != null) {
            articleView =
                    ArticleView.builder()
                            .articleId(articleEntity.getArticleId())
                            .amount(articleEntity.getAmount())
                            .build();
        }
        return Optional.ofNullable(articleView);
    }

}
