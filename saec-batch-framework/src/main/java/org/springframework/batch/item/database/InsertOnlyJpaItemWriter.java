package org.springframework.batch.item.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.util.Assert;

/**
 * Same as {@link JpaItemWriter} with only one difference.
 *
 * {@link JpaItemWriter} uses entityManager.merge(item)
 *
 * this class uses entityManager.persist(item)
 *
 * So this class can be used only for inserting records and not updating them.
 *
 * {@linkplain} http://spitballer.blogspot.in/2010/04/jpa-persisting-vs-merging-entites.html
 * {@linkplain}  http://stackoverflow.com/questions/1069992/jpa-entitymanager-why-use-persist-over-merge
 */
public class InsertOnlyJpaItemWriter<T> implements ItemWriter<T>, InitializingBean {

    protected static final Log logger = LogFactory.getLog(JpaItemWriter.class);

    private EntityManagerFactory entityManagerFactory;

    /**
     * Set the EntityManager to be used internally.
     *
     * @param entityManagerFactory the entityManagerFactory to set
     */
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Check mandatory properties - there must be an entityManagerFactory.
     */
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(entityManagerFactory, "An EntityManagerFactory is required");
    }

    /**
     * Merge all provided items that aren't already in the persistence context
     * and then flush the entity manager.
     *
     * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
     */
    public final void write(List<? extends T> items) {
        EntityManager entityManager = EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory);
        if (entityManager == null) {
            throw new DataAccessResourceFailureException("Unable to obtain a transactional EntityManager");
        }
        doWrite(entityManager, items);
        entityManager.flush();
    }

    /**
     * Do perform the actual write operation. This can be overridden in a
     * subclass if necessary.
     *
     * @param entityManager the EntityManager to use for the operation
     * @param items the list of items to use for the write
     */
    protected void doWrite(EntityManager entityManager, List<? extends T> items) {

        if (logger.isDebugEnabled()) {
            logger.debug("Writing to JPA with " + items.size() + " items.");
        }

        if (!items.isEmpty()) {
            long persistCount = 0;
            for (T item : items) {
                entityManager.persist(item);
                persistCount++;
            }
            if (logger.isDebugEnabled()) {
                logger.debug(persistCount + " entities persisted.");
            }
        }

    }

}
