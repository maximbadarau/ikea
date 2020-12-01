package nl.ikea.warehouse.converters.impl;

import com.google.common.collect.ImmutableList;
import nl.ikea.warehouse.converters.IDataConverter;
import nl.ikea.warehouse.entities.ArticleEntity;
import nl.ikea.warehouse.entities.ProductEntity;
import nl.ikea.warehouse.exceptions.ServiceException;
import nl.ikea.warehouse.views.impl.ProductView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Product converter implementation
 *
 * @see IDataConverter
 */
@Component
public class ProductConverter implements IDataConverter<ProductEntity, ProductView> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductConverter.class);

    private final ArticleConverter articleConverter;

    /**
     * Dependency injection default constructor
     *
     * @param articleConverter {@link ArticleConverter}
     */
    @Autowired
    public ProductConverter(ArticleConverter articleConverter) {
        this.articleConverter = articleConverter;
    }

    /**
     * {@inheritDoc}
     *
     * @param productView {@link ProductView}
     * @return {@link Optional} wrapping {@link ProductEntity}
     */
    @Override
    public Optional<ProductEntity> from(ProductView productView) {
        LOGGER.info("Converting {} model data to entity", productView);
        ProductEntity productEntity = null;
        if (productView != null) {
            productEntity = new ProductEntity();
            productEntity.setProductId(productView.getProductId());
            productEntity.setName(productView.getName());
            ImmutableList.Builder<ArticleEntity> builder = ImmutableList.builder();
            productView.getArticles().forEach(articleView -> builder.add(this.articleConverter.from(articleView)
                    .orElseThrow(() -> new ServiceException("Persistence exception."))));
            productEntity.setArticles(builder.build());
        }
        return Optional.ofNullable(productEntity);
    }

    /**
     * {@inheritDoc}
     *
     * @param productEntity {@link ProductEntity}
     * @return {@link Optional} wrapping {@link ProductView}
     */
    @Override
    public Optional<ProductView> from(ProductEntity productEntity) {
        LOGGER.info("Converting {} entity data to model", productEntity);
        ProductView productView = null;
        if (productEntity != null) {
            productView =
                    ProductView.builder()
                            .productId(productEntity.getProductId())
                            .name(productEntity.getName())
                            .articles(this.articleConverter.from(productEntity.getArticles()))
                            .build();
        }
        return Optional.ofNullable(productView);
    }
}
