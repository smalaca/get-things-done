package com.smalaca.gtd.architecturetests.all;

import com.smalaca.gtd.tests.annotation.ArchitectureTest;
import org.junit.jupiter.api.Test;

import static com.smalaca.gtd.architecturetests.GtdClasses.allClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;

@ArchitectureTest
class GenericRulesTest {
    @Test
    void shouldNotUseFieldInjection() {
        NO_CLASSES_SHOULD_USE_FIELD_INJECTION.check(allClasses());
    }

    @Test
    void shouldNotThrowGenericException() {
        NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS.check(allClasses());
    }
}
