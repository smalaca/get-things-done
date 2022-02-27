package com.smalaca.gtd.shared.validation.constrains;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class SameValuesValidator implements ConstraintValidator<SameValues, Object> {
    private String fieldOne;
    private String fieldTwo;

    @Override
    public void initialize(SameValues annotation) {
        fieldOne = annotation.fieldOne();
        fieldTwo = annotation.fieldTwo();
    }

    @Override
    public boolean isValid(Object values, ConstraintValidatorContext context) {
        BeanWrapperImpl wrapper = new BeanWrapperImpl(values);

        return Objects.equals(
                wrapper.getPropertyValue(fieldOne),
                wrapper.getPropertyValue(fieldTwo));
    }
}
