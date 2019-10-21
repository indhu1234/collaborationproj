package indhu.com.errors;

public class ErrorDetail {
	private String fieldName, errorMessage;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String toString() {
		return "fieldName: " + fieldName + "\nerrorMessage: " + errorMessage;
	}

}
