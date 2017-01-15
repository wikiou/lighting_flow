package net.lighting.flow.controller;

import net.lighting.flow.base.FlowItem;
import net.lighting.flow.base.ValueAdapter;
import net.lighting.flow.exception.FlowException;

public class SleepController implements FlowItem {

	private long seconds = 1;
	
	public SleepController(long seconds) {
		this.seconds = seconds;
	}

	@Override
	public void execute(ValueAdapter values) throws FlowException {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
