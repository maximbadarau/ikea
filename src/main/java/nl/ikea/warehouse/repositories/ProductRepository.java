package nl.ikea.warehouse.repositories;

import nl.ikea.warehouse.entities.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * Product repository
 */
public interface ProductRepository extends MongoRepository<ProductEntity, Long>, QuerydslPredicateExecutor<ProductEntity> {
}
