package com.smalaca.gtd.projectmanagement.application.idea;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class ShareIdeaCommandTest {
    @Test
    void shouldFulfillEqualsContract() {
        EqualsVerifier.forClass(ShareIdeaCommand.class).verify();
    }
}