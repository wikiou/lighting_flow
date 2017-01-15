package net.lighting.flow.controller;

import java.util.HashMap;
import java.util.Map;

import net.lighting.flow.base.FlowItem;
import net.lighting.flow.base.K;
import net.lighting.flow.base.ValueAdapter;
import net.lighting.flow.exception.FlowException;

public class MapController implements FlowItem {

    private String key;
    private Map<String, FlowItem> itemMap = new HashMap<String, FlowItem>();
    
    public void setKey(String decisionKey) {
        this.key = decisionKey;
    }

    public void append(String value, FlowItem item) {
        itemMap.put(value, item);
    }
    
    @Override
    public void execute(ValueAdapter values) throws FlowException {
        String decisionValue = (String) values.get(key);
        FlowItem item = itemMap.get(decisionValue);
        if (item != null) {
        	item.execute(values);
        } else {
        	throw new FlowException("Key=" + key + ", decisionValue=" + decisionValue, K.err_item_not_found);
        }
    }


}
