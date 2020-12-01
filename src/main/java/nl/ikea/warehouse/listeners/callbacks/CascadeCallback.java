package nl.ikea.warehouse.listeners.callbacks;

import nl.ikea.warehouse.annotations.CascadeSave;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.ReflectionUtils;

/**
 * Cascade persistence trigger callback implementation
 *
 * @see ReflectionUtils.FieldCallback
 */
@Setter(value = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CascadeCallback implements ReflectionUtils.FieldCallback {

    private Object source;
    private MongoOperations mongoOperations;

    /**
     * {@inheritDoc}
     *
     * @param field {@link Field}
     */
    @Override
    public void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
        ReflectionUtils.makeAccessible(field);
        if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
            final Object fieldValue = field.get(getSource());
            if (fieldValue != null) {
                final FieldCallback callback = new FieldCallback();
                ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
                if (fieldValue instanceof Collection) {
                    ((Collection) fieldValue).forEach(o -> getMongoOperations().save(o));
                }
            }
        }
    }
}
