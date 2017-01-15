package net.lighting.flow.base;

import net.lighting.flow.exception.FlowException;

public interface BeanAdapter {

    Object get(String key);
    
    void prepare(String key, String clazz) throws FlowException;
    
}
