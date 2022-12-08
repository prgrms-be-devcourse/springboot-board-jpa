package org.prgrms.board.error;

public abstract class ServiceRuntimeException extends RuntimeException {

	private final String messageKey;
	private final String detailsKey;
	private final Object[] params;

	public ServiceRuntimeException(String messageKey, String detailsKey, Object[] params) {
		this.messageKey = messageKey;
		this.detailsKey = detailsKey;
		this.params = params;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public String getDetailsKey() {
		return detailsKey;
	}

	public Object[] getParams() {
		return params;
	}
}
