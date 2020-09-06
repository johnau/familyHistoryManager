package tech.jmcs.fhm.ejb.facade;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author John
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    private final Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    protected abstract Boolean okToCreate(T newEntity);

    protected void log(String s) {
        // no implementation (for optional Override)
    }

    public void create(T entity) {
        if (!okToCreate(entity)) {
            return;
        }
        try {
            getEntityManager().persist(entity);
            getEntityManager().flush();
        } catch (OptimisticLockException opex) {
            log(String.format("Optimistic Lock Exception, cant merge this entity: '%s' (Message: '%s')", opex.getEntity(), opex.getMessage()));

        }
    }

    public void edit(T entity) {
        try {
            getEntityManager().merge(entity);
            getEntityManager().flush();
        } catch (OptimisticLockException opex) {
            log(String.format("Optimistic Lock Exception, cant merge this entity: '%s' (Message: '%s')", opex.getEntity(), opex.getMessage()));
            getEntityManager().refresh(entity);
        }
    }

    public void refresh(T entity) {
        getEntityManager().refresh(entity);
    }

    public void remove(T entity) {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
            getEntityManager().flush();
        } catch (OptimisticLockException opex) {
            log(String.format("Optimistic Lock Exception, cant merge this entity: '%s'", opex.getMessage(), opex.getEntity()));
        }
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
