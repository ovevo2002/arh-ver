package ru.arh.athletics.domain;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.util.ProxyUtils;

@MappedSuperclass
public abstract class AbstractDomain implements Persistable {

    @Transient // DATAJPA-622
    @Override
    public boolean isNew() {
        return null == getId();
    }
    
    @Override
    public String toString() {
        return String.format("Entity of type %s with id: %s", this.getClass().getName(), getId());
    }

    @Override
    public boolean equals(Object obj) {

        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(ProxyUtils.getUserClass(obj))) {
            return false;
        }

        AbstractPersistable<?> that = (AbstractPersistable<?>)obj;

        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {

        int hashCode = 17;

        hashCode += null == getId() ? 0 : getId().hashCode() * 31;

        return hashCode;
    }
}
