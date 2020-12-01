package nl.ikea.warehouse.factories;

import nl.ikea.warehouse.factories.types.ITypeService;

/**
 * Factory method for {@link ITypeService} beans to be injected at runtime. Bean injection
 * depends on enumeration mapping.
 */
public interface IServiceFactoryBean {

  /**
   * Get implementation of {@link ITypeService} describing generic behaviour for card type
   * service bean.
   *
   * @param typeId {@link String} representing card type id, as a bean is singleton
   * @return {@link ITypeService} implementation
   */
  ITypeService getService(Integer typeId);
}
