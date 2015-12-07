package shared.model.dao;

import org.hibernate.Session;

import java.util.HashSet;
import java.util.Set;

public abstract class DAO<T> {
    protected Session session;
    protected Class<T> typeClass;

    public DAO(Session session, Class<T> typeClass) {
        this.session = session;
        this.typeClass = typeClass;
    }

    public void save(T object) {
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
    }

    public void update(T object) {
        session.beginTransaction();
        session.update(object);
        session.getTransaction().commit();
    }

    @SuppressWarnings("unchecked")
    public Set<T> getAll() {
        session.beginTransaction();
        Set set = new HashSet(session.createCriteria(typeClass).list());
        session.getTransaction().commit();
        return set;
    }
}
