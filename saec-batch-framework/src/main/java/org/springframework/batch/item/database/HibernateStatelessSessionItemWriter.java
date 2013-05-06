package org.springframework.batch.item.database;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * Uses a Hibernate {@link StatelessSession} to insert entities.
 */
public class HibernateStatelessSessionItemWriter<T> implements ItemWriter<T>, InitializingBean {

    protected static final Log logger = LogFactory.getLog(JpaItemWriter.class);

    private SessionFactory sessionFactory;

    private StatelessSession session;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Check mandatory properties - there must be an entityManagerFactory.
     */
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(sessionFactory, "A sessionFactory is required");
    }

    /**
     * Merge all provided items that aren't already in the persistence context
     * and then flush the entity manager.
     *
     * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
     */
    public final void write(List<? extends T> items) {
        if(session == null) {
            session = sessionFactory.openStatelessSession();
        }
        doWrite(session, items);
    }

    protected void doWrite(StatelessSession session, List<? extends T> items) {
        if (logger.isDebugEnabled()) {
            logger.debug("Writing to Hibernate Stateless Session with " + items.size() + " items.");
        }

        if (!items.isEmpty()) {
            long persistCount = 0;
            for (T item : items) {
                session.insert(item);
                persistCount++;
            }
            if (logger.isDebugEnabled()) {
                logger.debug(persistCount + " entities persisted.");
            }
        }

    }

}
