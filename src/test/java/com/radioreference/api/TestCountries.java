package com.radioreference.api;

import com.radioreference.model.Country;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class TestCountries extends TestBase {
    @Test
    public void testCountries() {
        List<Country> countries = mApi.getCountries();
        assertNotNull(countries);
        assertFalse(countries.isEmpty());
        for (Country country : countries) {
            assertNotNull(country);
            assertNotNull(country.getId());
            assertNotNull(country.getName());
            assertNotNull(country.getCode());
        }
    }
}
