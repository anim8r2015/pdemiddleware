package co.ke.predictiveanalytics.model;

import java.sql.Date;
import java.util.List;

public class Data {
	
	
	private String errorMessage;
	private String responseId;
	private boolean success;
	private List<DataModel> allData;
	
	
	public List<DataModel> getAllData() {
		return allData;
	}
	public void setAllData(List<DataModel> allData) {
		this.allData = allData;
	}
	public String getResponseId() {
		return responseId;
	}
	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
		
}
