package demo;

import net.lighting.flow.adapter.SimpleValueAdapter;
import net.lighting.flow.engine.FlowEngine;
import net.lighting.flow.factory.XmlFlowFactory;
import net.lighting.flow.util.LogUtil;

public class Launcher {

	public static void main(String[] args) {
		try {
			SimpleValueAdapter valueAdapter = new SimpleValueAdapter();
			valueAdapter.set("valueKey1", "value2");
			XmlFlowFactory factory = new XmlFlowFactory();
			factory.setCompositeXml("classpath:/composite1.xml");
			FlowEngine engine = new FlowEngine(factory.getFlowMap(), valueAdapter);
			engine.execute("flow1");
		} catch (Exception e) {
			LogUtil.error("", e);
		}
	}
	
}
