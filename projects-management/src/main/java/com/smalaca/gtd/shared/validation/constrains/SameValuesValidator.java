package com.smalaca.gtd.shared.validation.constrains;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class SameValuesValidator implements ConstraintValidator<SameValues, Object> {
    private String[] fields;

    @Override
    public void initialize(SameValues annotation) {
        fields = annotation.fields();
    }

    @Override
    public boolean isValid(Object values, ConstraintValidatorContext context) {
        BeanWrapperImpl wrapper = new BeanWrapperImpl(values);

        return Arrays.stream(fields)
                .map(wrapper::getPropertyValue)
                .distinct().count() == 1;
    }
}
