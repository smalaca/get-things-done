package com.smalaca.gtd.projectmanagement.infrastructure.validation.constraints;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class AtLeastOneNotEmptyValidator implements ConstraintValidator<AtLeastOneNotEmpty, Object> {
    private String[] fields;

    @Override
    public void initialize(AtLeastOneNotEmpty annotation) {
        fields = annotation.fields();
    }

    @Override
    public boolean isValid(Object values, ConstraintValidatorContext context) {
        return nonEmptyValues(values) > 0;
    }

    private long nonEmptyValues(Object values) {
        BeanWrapperImpl wrapper = new BeanWrapperImpl(values);

        return Arrays.stream(fields)
                .map(wrapper::getPropertyValue)
                .filter(this::isNotEmpty)
                .count();
    }

    private boolean isNotEmpty(Object value) {
        return value != null && StringUtils.isNotBlank(value.toString());
    }
}
