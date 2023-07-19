package io.pilp.blazeupdatebug.persistence;

import io.pilp.blazeupdatebug.domain.Country;
import io.pilp.blazeupdatebug.domain.CountryRepository;
import io.quarkus.test.junit.QuarkusTest;
import javax.inject.Inject;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CountryDAOTest {
    @Inject
    CountryRepository repository;

    @Test
    @Order(1)
    public void countries_should_be_created_in_db() {
        String[] countries = {"Germany", "France", "Spain", "Italy", "Croatia"};
        List<Country> results = new ArrayList<>();

        for (var country : countries) {
            results.add(repository.create(country));
        }

        Assertions.assertEquals(5, results.size());
    }

    @Test
    @Order(2)
    public void query_strategy__country_should_be_updated_in_db() {
        Country test = repository.getById(1L);
        var result = repository.editQueryStrategy(1L, "query_strategy_passed");

        Assertions.assertEquals(test.getId(), result.getId());
        Assertions.assertNotEquals(test.getName(), result.getName());
        Assertions.assertEquals("query_strategy_passed", result.getName());
    }

    @Test
    @Order(3)
    public void entity_strategy__country_should_be_updated_in_db() {
        Country test = repository.getById(2L);
        var result = repository.editEntityStrategy(2L, "entity_strategy_passed");

        Assertions.assertEquals(test.getId(), result.getId());
        Assertions.assertNotEquals(test.getName(), result.getName());
        Assertions.assertEquals("entity_strategy_passed", result.getName());
    }

}
