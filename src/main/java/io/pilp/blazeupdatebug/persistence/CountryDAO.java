package io.pilp.blazeupdatebug.persistence;

import com.blazebit.persistence.view.Sorters;
import io.pilp.blazeupdatebug.domain.Country;
import io.pilp.blazeupdatebug.domain.CountryRepository;
import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CountryDAO implements CountryRepository {
    @Inject
    EntityManager em;
    @Inject
    CriteriaBuilderFactory cbf;
    @Inject
    EntityViewManager evm;

    @Override
    public List<? extends Country> list(int first, int size, String nameFilter) {
        CriteriaBuilder<CountryEntity> cb = cbf.create(em, CountryEntity.class);
        if (nameFilter != null) {
            cb = cb.where("libelle").like(false).expression("CONCAT('%',:name ,'%')").noEscape()
                    .setParameter("name", nameFilter);
        }

        EntityViewSetting<CountryViewUpdateEntity, ?> setting = EntityViewSetting.create(CountryViewUpdateEntity.class, first, size);
        setting.addAttributeSorter("libelle", Sorters.ascending());

        return evm.applySetting(setting, cb).getResultList();
    }

    @Override
    public Country getById(Long id) {
        return evm.find(em, CountryView.class, id);
    }

    @Transactional
    @Override
    public Country create(String name) {
        var view = evm.create(CountryViewUpdateEntity.class);
        view.setName(name);
        evm.save(em, view);

        return view;
    }

    @Transactional
    @Override
    public Country editEntityStrategy(Long id, String name) {
        var view = evm.find(em, CountryViewUpdateEntity.class, id);
        view.setName(name);
        evm.save(em, view);

        return getById(id);
    }

    @Transactional
    @Override
    public Country editQueryStrategy(Long id, String name) {
        var view = evm.find(em, CountryViewUpdateQuery.class, id);
        view.setName(name);
        evm.save(em, view);

        return getById(id);
    }
}
