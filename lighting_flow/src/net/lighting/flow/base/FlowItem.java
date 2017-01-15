package net.lighting.flow.base;

import net.lighting.flow.exception.FlowException;

public interface FlowItem {

    void execute(ValueAdapter values) throws FlowException;
    
}
