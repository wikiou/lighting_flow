package net.lighting.flow.adapter;

import java.util.HashMap;
import java.util.Map;

import net.lighting.flow.base.BeanAdapter;
import net.lighting.flow.base.K;
import net.lighting.flow.exception.FlowException;

public class NewInstanceBeanAdapter implements BeanAdapter {

    Map<String, Object> beanMap = new HashMap<String, Object>();
    
    @Override
    public Object get(String key) {
        return beanMap.get(key);
    }

    @Override
    public void prepare(String key, String clazz) throws FlowException {
        try {
            beanMap.put(key, Class.forName(clazz).newInstance());
        } catch (Exception e) {
            throw new FlowException("", K.err_SimpleBeanAdapter, e);
        }
    }

}
