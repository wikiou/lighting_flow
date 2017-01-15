package net.lighting.flow.controller;

import java.util.ArrayList;
import java.util.List;

import net.lighting.flow.base.FlowItem;
import net.lighting.flow.base.ValueAdapter;
import net.lighting.flow.exception.FlowException;

public class ListController implements FlowItem {

    private List<FlowItem> items = new ArrayList<FlowItem>();
    
    public void append(FlowItem item) {
        items.add(item);
    }
    
    @Override
    public void execute(ValueAdapter values) throws FlowException {
        for (FlowItem item : items) {
            item.execute(values);
        }
    }


}
