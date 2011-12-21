package com.radioreference.api;

import com.radioreference.model.State;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class TestStates extends TestBase {
    @Test
    public void testStates() {
        List<State> states = mApi.getStates(1);
        assertNotNull(states);
        for (State state : states) {
            assertNotNull(state);
            assertNotNull(state.getCode());
            assertNotNull(state.getName());
            assertNotNull(state.getCode());
        }
    }
}
