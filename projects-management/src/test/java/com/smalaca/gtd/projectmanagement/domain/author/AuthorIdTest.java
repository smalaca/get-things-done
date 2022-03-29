package com.smalaca.gtd.projectmanagement.domain.author;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class AuthorIdTest {
    @Test
    void shouldFulfillEqualsContract() {
        EqualsVerifier.forClass(AuthorId.class).verify();
    }
}