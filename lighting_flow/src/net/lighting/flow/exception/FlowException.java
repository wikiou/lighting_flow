package net.lighting.flow.exception;

public class FlowException extends Exception {

	private static final long serialVersionUID = 1L;

	private String key;
	
	private Object ref;
	
	public FlowException(String message, String key, Exception e) {
		super(message, e);
		this.key = key;
	}

	public FlowException(String message, String key) {
		super(message);
		this.key = key;
	}

	public FlowException(String message, String key, Object ref) {
		super(message);
		this.key = key;
		this.ref = ref;
	}

	public String getKey() {
		return key;
	}

	public Object getRef() {
		return ref;
	}

}
