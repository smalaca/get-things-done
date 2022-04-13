package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.idea;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollaboratorDtoTest {

    @Test
    void shouldFulfillEqualsContract() {
        EqualsVerifier.forClass(CollaboratorDto.class).verify();
    }
}