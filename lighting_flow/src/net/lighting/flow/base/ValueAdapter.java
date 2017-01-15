package net.lighting.flow.base;

public interface ValueAdapter {

    Object get(String key);
    
    void set(String key, Object data);
    
}
