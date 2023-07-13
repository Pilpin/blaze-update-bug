package io.pilp.blazeupdatebug.persistence;

import com.blazebit.persistence.view.*;
import io.pilp.blazeupdatebug.domain.Country;

@EntityView(CountryEntity.class)
@CreatableEntityView
@UpdatableEntityView(strategy = FlushStrategy.ENTITY)
public interface CountryViewUpdateEntity extends Country {
    @IdMapping
    Long getId();
    String getName();
    void setName(String name);
}
