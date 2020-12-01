package nl.ikea.warehouse.services;


import nl.ikea.warehouse.views.impl.ProductView;

import java.util.List;
import java.util.Optional;

/**
 * Product service layer contract definition.
 */
public interface IProductService {

    /**
     * {@inheritDoc}
     *
     * @param productView {@link ProductView}
     * @return {@link Optional} of {@link ProductView}
     */
    Optional<ProductView> save(ProductView productView);

    /**
     * Retrieve persisted {@link List} of {@link ProductView}
     *
     * @return {@link List} of {@link ProductView}
     */
    List<ProductView> getProducts();

    /**
     * Retrieve persisted {@link ProductView} by unique identifier.
     *
     * @param productId {@link Long}
     * @return {@link Optional} of {@link ProductView}
     */
    Optional<ProductView> getProduct(Long productId);

    /**
     * Delete all entries.
     */
    void deleteAll();
}
