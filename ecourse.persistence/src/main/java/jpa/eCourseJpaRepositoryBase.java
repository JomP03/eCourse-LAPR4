package jpa;

import appsettings.*;
import eapli.framework.infrastructure.repositories.impl.jpa.*;

public class eCourseJpaRepositoryBase<T, K, I> extends JpaTransactionalRepository<T, K, I> {

    public eCourseJpaRepositoryBase(final String persistenceUnitName, final String identityFieldName) {
        super(persistenceUnitName, Application.settings().extendedPersistenceProperties(),
                identityFieldName);
    }

    public eCourseJpaRepositoryBase(String identityFieldName) {
        super(Application.settings().persistenceUnitName(),
                Application.settings().extendedPersistenceProperties(), identityFieldName);
    }
}
