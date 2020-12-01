package nl.ikea.warehouse.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Custom validator for checking if equals with provided value.
 */
public class MatchValidator implements ConstraintValidator<Match, String> {

    private String value;
    private Boolean isNullable;

    /**
     * {@inheritDoc}
     *
     * @param constraint {@link Match} annotation context scope.
     */
    @Override
    public void initialize(Match constraint) {
        this.value = constraint.value();
        this.isNullable = constraint.isNullable();
    }

    /**
     * {@inheritDoc}
     *
     * @param value                      {@link String} expected value
     * @param constraintValidatorContext {@link ConstraintValidatorContext}
     * @return {@link Boolean}
     */
    @Override
    public boolean isValid(
            String value, ConstraintValidatorContext constraintValidatorContext) {
        if (this.isNullable && value == null) {
            return true;
        }
        return this.value.equals(value);
    }
}
