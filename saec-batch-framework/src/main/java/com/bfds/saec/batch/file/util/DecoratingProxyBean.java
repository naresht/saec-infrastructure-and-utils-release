package com.bfds.saec.batch.file.util;


import com.google.common.base.Preconditions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

public abstract class DecoratingProxyBean<T> implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    protected T target;

    @Autowired
    private ApplicationContextLookupUtil applicationContextLookupUtil;

    @Override
    public void afterPropertiesSet() throws Exception {
        if(target == null) {
            Preconditions.checkNotNull(applicationContext, "applicationContext is null");
            this.target = locateTarget();
        }

    }

    private  T locateTarget() {
        T ret = null;
        String beanName = getTargetBeanName();
        try {
            if(StringUtils.hasText(getDefaultTargetBeanName())) {
                ret = applicationContextLookupUtil.lookupWithDefaultFallback(beanName = getTargetBeanName(), getDefaultTargetBeanName());
            } else {
                ret =  (T) applicationContext.getBean(getTargetBeanName());
            }
        }catch(NoSuchBeanDefinitionException e) {
            //Noop
        }
        if(!allowNullTarget() && ret == null) {
            throw new IllegalStateException("no bean found with name " + beanName);
        }
        return ret;
    }

    protected abstract String getTargetBeanName();

    protected String getDefaultTargetBeanName(){
        return null;
    }

    protected boolean allowNullTarget(){
        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setTarget(T target) {
        this.target = target;
    }
}
