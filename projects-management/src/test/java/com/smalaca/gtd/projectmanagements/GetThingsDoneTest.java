package com.smalaca.gtd.projectmanagements;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GetThingsDoneTest {
    @Test
    void shouldRecognizeIfThingsAreDone() {
        boolean actual = new GetThingsDone().isEverythingDone();

        assertThat(actual).isTrue();
    }
}