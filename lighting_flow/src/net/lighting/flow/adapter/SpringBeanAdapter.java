package net.lighting.flow.adapter;

import java.util.HashMap;
import java.util.Map;

import net.lighting.flow.base.BeanAdapter;
import net.lighting.flow.base.K;
import net.lighting.flow.exception.FlowException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanAdapter implements ApplicationContextAware, BeanAdapter {

    private static ApplicationContext appContext;
    
    private Map<String, Object> beanMap = new HashMap<String, Object>();
    
    @Override
    public void setApplicationContext(ApplicationContext ctx)
            throws BeansException {
        appContext = ctx;
    }

    @Override
    public Object get(String key) {
        return beanMap.get(key);
    }

    @Override
    public void prepare(String key, String clazz) throws FlowException {
        try {
            beanMap.put(key, getBean(Class.forName(clazz)));
        } catch (Exception e) {
            throw new FlowException("", K.err_SpringBeanAdapter, e);
        }
    }

    public static Object getBean(String id) {
        return appContext.getBean(id);
    }
    
    public <T> T getBean(Class<T> clazz) {
        return appContext.getBean(clazz);
    }
    
}
