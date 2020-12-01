package nl.ikea.warehouse.services.impl;

import nl.ikea.warehouse.converters.impl.ProductConverter;
import nl.ikea.warehouse.repositories.ProductRepository;
import nl.ikea.warehouse.services.IProductService;

import java.util.List;
import java.util.Optional;

import nl.ikea.warehouse.views.impl.ProductView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @see IProductService
 */
@Component
public class ProductService implements IProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    /**
     * Dependency injection default constructor
     *
     * @param productRepository {@link ProductRepository}
     * @param productConverter  {@link ProductConverter}
     */
    @Autowired
    public ProductService(
            ProductRepository productRepository,
            ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
    }

    /**
     * {@inheritDoc}
     *
     * @param productView {@link ProductView}
     * @return {@link Optional} of {@link ProductView}
     */
    @Override
    public Optional<ProductView> save(ProductView productView) {
        return this.productConverter.from(
                this.productConverter
                        .from(productView)
                        .map(this.productRepository::save)
                        .orElse(null));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link List} of {@link ProductView}
     */
    @Override
    public List<ProductView> getProducts() {
        LOGGER.info("Entered getProducts() method.");
        return this.productConverter.from(this.productRepository.findAll());
    }

    /**
     * {@inheritDoc}
     *
     * @param productId {@link Long}
     * @return {@link Optional} of {@link ProductView}
     */
    @Override
    public Optional<ProductView> getProduct(Long productId) {
        LOGGER.info("Entered getProduct(String) with productId: {}.", productId);
        return this.productRepository
                .findById(productId)
                .flatMap(this.productConverter::from);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAll() {
        LOGGER.info("Entered deleteAll() method.");
        this.productRepository.deleteAll(this.productRepository.findAll());
    }
}
