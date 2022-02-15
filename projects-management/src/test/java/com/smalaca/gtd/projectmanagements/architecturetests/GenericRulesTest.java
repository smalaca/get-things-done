package com.smalaca.gtd.projectmanagements.architecturetests;

import org.junit.jupiter.api.Test;

import static com.smalaca.gtd.projectmanagements.architecturetests.GetThingsDoneClasses.projectClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;

public class GenericRulesTest {
    @Test
    void shouldNotUseFieldInjection() {
        NO_CLASSES_SHOULD_USE_FIELD_INJECTION.check(projectClasses());
    }

    @Test
    void shouldNotThrowGenericException() {
        NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS.check(projectClasses());
    }
}
