package com.radioreference.api;

import com.radioreference.model.County;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class TestCounties extends TestBase {
    @Test
    public void testCounties() {
        List<County> counties = mApi.getCounties(48);
        assertNotNull(counties);
        assertFalse(counties.isEmpty());

        for (County county : counties) {
            assertNotNull(county);
            assertNotNull(county.getId());
            assertNotNull(county.getName());
            assertNotNull(county.getType());
        }
    }
}
