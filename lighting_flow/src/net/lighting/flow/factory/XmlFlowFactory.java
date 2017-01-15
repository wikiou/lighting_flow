package net.lighting.flow.factory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.lighting.flow.adapter.NewInstanceBeanAdapter;
import net.lighting.flow.base.BeanAdapter;
import net.lighting.flow.base.FlowItem;
import net.lighting.flow.base.K;
import net.lighting.flow.controller.ListController;
import net.lighting.flow.controller.MapController;
import net.lighting.flow.controller.SleepController;
import net.lighting.flow.exception.FlowException;
import net.lighting.flow.util.FlowUtil;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlFlowFactory {

    private BeanAdapter beanAdapter = new NewInstanceBeanAdapter();
    private Map<String, FlowItem> flowMap = new HashMap<String, FlowItem>();
    
    public XmlFlowFactory() {
        
    }
    
    public BeanAdapter getBeanAdapter() {
        return beanAdapter;
    }

    public void setBeanAdapter(BeanAdapter beanAdapter) {
        this.beanAdapter = beanAdapter;
    }

    public Map<String, FlowItem> getFlowMap() {
        return flowMap;
    }

    public void setFlowMap(Map<String, FlowItem> flowMap) {
        this.flowMap = flowMap;
    }

    public void setCompositeXml(String cfgFile) throws FlowException {
        try {
            Document doc = readDocument(cfgFile);
            Element root = doc.getRootElement();
            parseBeans(root.element(K.beans));
            parseFlows(root.element(K.flows));
        } catch (Exception e) {
            throw new FlowException("Error in XmlFlowFactory.setCompositeXml()", 
                    K.err_setCompositeXml, e);
        }
    }
    
    public void setBeansXml(String cfgFile) throws Exception {
        Document doc = readDocument(cfgFile);
        parseFlows(doc.getRootElement().element(K.beans));
    }
    
    public void setFlowsXml(String cfgFile) throws Exception {
        Document doc = readDocument(cfgFile);
        parseFlows(doc.getRootElement().element(K.flows));
    }
    
    private Document readDocument(String res) throws Exception {
        Document doc = null;
        if (res.startsWith("classpath:")) {
            String file = res.substring(10);
            SAXReader saxReader = new SAXReader();
            InputStream is = null;
            is = XmlFlowFactory.class.getResourceAsStream(file);
            try {
            	doc = saxReader.read(is);
            } finally {
            	if (is != null) is.close();
            }
        } else {
            SAXReader saxReader = new SAXReader();
            doc = saxReader.read(res);
        }
        return doc;
    }
    
    private void parseBeans(Element beansElement) throws FlowException {
        Element e;
        String id, clazz;
        for (Object o: beansElement.elements()) {
            e = (Element) o;
            id = e.attributeValue(K.id);
            clazz = e.attributeValue(K.clazz);
            beanAdapter.prepare(id, clazz);
        }
    }
    
    private void parseFlows(Element flowsElement) {
        Element flowElement;
        String key;
        for (Object o : flowsElement.elements()) {
            flowElement = (Element) o;
            key = flowElement.attributeValue(K.id);
            flowMap.put(key, parseFlow(flowElement));
        }
    }
    
    private FlowItem parseFlow(Element flowElement) {
        ListController trunk = new ListController();
        String name, ref, value;
        Element item, when;
        for (Object o: flowElement.elements()) {
            item = (Element) o;
            name = item.getName();
            if (K.sleep.equals(name)) {
                String seconds = item.attributeValue(K.seconds);
                trunk.append(new SleepController(Integer.parseInt(seconds)));
            } else if (K.list.equals(name)) {
                ref = item.attributeValue(K.ref);
                trunk.append(toSimpleController(ref));
            } else if (K.map.equals(name)) {
                MapController condition = new MapController();
                condition.setKey(item.attributeValue(K.key));
                for (Object o1: item.elements()) {
                    when = (Element) o1;
                    value = when.attributeValue(K.value);
                    ref = when.attributeValue(K.ref);
                    if (FlowUtil.hasText(ref)) {
                        condition.append(value, toSimpleController(ref));
                    } else {
                        condition.append(value, parseFlow(when));
                    }
                }
                trunk.append(condition);
            }
        }
        return trunk;
    }
    
    private ListController toSimpleController(String ref) {
        ListController simple = new ListController();
        if (FlowUtil.hasText(ref)) {
            for (String part : ref.split(",")) {
                simple.append((FlowItem)beanAdapter.get(part));
            }
        }
        return simple;
    }
    
    
}
