package nl.ikea.warehouse.converters.impl;

import nl.ikea.warehouse.converters.IDataConverter;
import nl.ikea.warehouse.entities.ArticleEntity;
import nl.ikea.warehouse.views.impl.InventoryView;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Inventory converter implementation
 *
 * @see IDataConverter
 */
@Component
public class InventoryConverter
        implements IDataConverter<ArticleEntity, InventoryView> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryConverter.class);

    /**
     * {@inheritDoc}
     *
     * @param inventoryView {@link InventoryView}
     * @return {@link Optional} wrapping {@link ArticleEntity}
     */
    @Override
    public Optional<ArticleEntity> from(InventoryView inventoryView) {
        LOGGER.info("Converting {} model data to entity", inventoryView);
        ArticleEntity articleEntity = null;
        if (inventoryView != null) {
            articleEntity = new ArticleEntity();
            articleEntity.setArticleId(inventoryView.getArticleId());
            articleEntity.setName(inventoryView.getName());
            articleEntity.setAmount(inventoryView.getStock());
        }
        return Optional.ofNullable(articleEntity);
    }

    /**
     * {@inheritDoc}
     *
     * @param articleEntity {@link ArticleEntity}
     * @return {@link Optional} wrapping {@link InventoryView}
     */
    @Override
    public Optional<InventoryView> from(ArticleEntity articleEntity) {
        LOGGER.info("Converting {} entity data to model", articleEntity);
        InventoryView inventoryView = null;
        if (articleEntity != null) {
            inventoryView =
                    InventoryView.builder()
                            .articleId(articleEntity.getArticleId())
                            .name(articleEntity.getName())
                            .stock(articleEntity.getAmount())
                            .build();
        }
        return Optional.ofNullable(inventoryView);
    }
}
