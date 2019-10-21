package indhu.com.model;

public class Message {
	private int messageId;
	private String message;

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	// Default Constructor
	public Message() {
	}

	// Overloaded Constructor
	public Message(int messageId, String message) {
		this.messageId = messageId;
		this.message = message;
	}
}
