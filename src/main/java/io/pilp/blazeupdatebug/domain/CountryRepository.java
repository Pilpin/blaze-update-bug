package io.pilp.blazeupdatebug.domain;

import java.util.List;

public interface CountryRepository {
    List<? extends Country> list(int first, int size, String nameFilter);

    Country getById(Long id);

    Country create(String name);

    Country editEntityStrategy(Long id, String name);

    Country editQueryStrategy(Long id, String name);
}
