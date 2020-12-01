package nl.ikea.warehouse.converters;

import com.google.common.collect.ImmutableList;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Data models converter contract definition. Used to separate data by layer and ensure
 * immutability. Transforming back and forth object in between {@link F} and {@link T}
 *
 * @param <F> parametrized data type. Mainly used to transform data 'from'
 * @param <T> parametrized data type. Mainly used to transform data 'to'
 */
public interface IDataConverter<F, T extends Serializable> {

  /**
   * Convert from {@link T} into {@link F} data type.
   *
   * @param obj - {@link T} parameter to transform from
   * @return {@link Optional} of type {@link F}F
   */
  Optional<F> from(T obj);

  /**
   * Convert from {@link F} into {@link T} data type.
   *
   * @param obj - {@link F} parameter to transform from
   * @return {@link Optional} of type {@link T}
   */
  Optional<T> from(F obj);

  /**
   * Default implementation used to convert from collection of {@link F} into collection of {@link
   * T} Under the hood used {@link #from(F) from} method.
   *
   * @param collection - of {@link F} objects
   * @return {@link List} empty or containing transformed objects of {@link T}
   */
  default List<T> from(List<F> collection) {
    ImmutableList.Builder<T> builder = ImmutableList.builder();
    if (collection != null) {
      builder.addAll(
          collection.stream()
              .filter(Objects::nonNull)
              .map(this::from)
              .filter(Optional::isPresent)
              .map(Optional::get)
              .collect(Collectors.toList()));
    }
    return builder.build();
  }
}
