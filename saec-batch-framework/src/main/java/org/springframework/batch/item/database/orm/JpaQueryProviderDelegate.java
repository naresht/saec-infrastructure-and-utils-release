package org.springframework.batch.item.database.orm;


import com.bfds.saec.batch.file.util.FileJobUtils;
import com.bfds.saec.batch.file.util.SetpScopedDecoratingProxyBean;
import org.springframework.batch.item.database.orm.JpaQueryProvider;

import javax.persistence.EntityManager;
import javax.persistence.Query;


public class JpaQueryProviderDelegate extends SetpScopedDecoratingProxyBean<JpaQueryProvider> implements JpaQueryProvider {

    @Override
    public Query createQuery() {
        return target.createQuery();
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        target.setEntityManager(entityManager);
    }

    @Override
    protected String getTargetBeanName() {
        return FileJobUtils.getFileRecordClassName(stepExecution, this) + "QueryProvider";
    }

    protected String getDefaultTargetBeanName(){
        return "fileExportQueryProvider";
    }

}
