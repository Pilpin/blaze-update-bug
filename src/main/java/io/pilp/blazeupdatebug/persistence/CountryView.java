package io.pilp.blazeupdatebug.persistence;

import com.blazebit.persistence.view.*;
import io.pilp.blazeupdatebug.domain.Country;

@EntityView(CountryEntity.class)
public interface CountryView extends Country {
    @IdMapping
    Long getId();
    String getName();
}
