package demo;

import net.lighting.flow.base.FlowItem;
import net.lighting.flow.base.ValueAdapter;

public class Item1 implements FlowItem {

	@Override
	public void execute(ValueAdapter values) {
		System.out.println("execute: " + getClass().getName());
	}

}
