package co.ke.predictiveanalytics;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


import co.ke.predictiveanalytics.helpers.DataBaseHelper;
import co.ke.predictiveanalytics.helpers.JSONHelper;
import co.ke.predictiveanalytics.helpers.OpenShiftDataBaseHelper;
import co.ke.predictiveanalytics.model.Data;
import co.ke.predictiveanalytics.model.DataModel;

@Path("/service")
public class DataService {


	//save batch string
	@Path("/savebatchstr")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveDataString(String obj) throws JSONException {
		List<Data> resultData = new ArrayList<>();
		JSONObject jsonObject = new JSONObject();
		JSONObject receivedData = new JSONObject(obj);
		JSONHelper jh = new JSONHelper();
		DataModel data = jh.convertJSONToModel(receivedData);
		String responseId = "0";
		
		int x = (int) (Math.random() * 1000);
		try {
			resultData = new OpenShiftDataBaseHelper().insertUpdateDataObject(data);
			if(resultData.isEmpty()) {
				jsonObject.put("success", false);
				jsonObject.put("message", "No results found.");
				jsonObject.put("dataid", "0");
			} else {
				for (Data d : resultData) {
					responseId = d.getResponseId();
					
					if (!d.getErrorMessage().isEmpty()) {
						jsonObject.put("success", false);
						jsonObject.put("message", "An error occurred: " + d.getErrorMessage());
						jsonObject.put("dataid", responseId);
						break;
					} else {
						jsonObject.put("success", true);
						jsonObject.put("message", "Data saved succesfully");
						jsonObject.put("dataid", responseId);
					}
				}
			}
						
		} catch (JSONException j) {
			jsonObject.put("success", false);
			jsonObject.put("message", "JSON error: " + j.getMessage());
			jsonObject.put("dataid", responseId);
			
			j.printStackTrace();
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", "Error updating data. " + e.getMessage());
			jsonObject.put("dataid", responseId);
		}

		String result = "" + jsonObject;
		return Response.status(200).entity(result).build();
	}
		
	@Path("/retrieve")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieve(String data) {
		List<Data> resultData = new ArrayList<>();
		JSONObject jsonObject = new JSONObject();
		//JSONObject dataPosted = new JSONObject(data);
		//String queryString = dataPosted.getString("data");
		try {
			resultData = new OpenShiftDataBaseHelper()
					.getTableData();
			
			for (Data d : resultData) {
				if (!d.getErrorMessage().isEmpty()) {
					jsonObject.put("success", "false");
					jsonObject.put("message", "An error occurred: " + d.getErrorMessage());
					break;
				} else {
					jsonObject.put("success", "true");
				}
			}
			
			jsonObject.put("status", "completed");
			jsonObject.put("data", resultData);

		} catch (JSONException j) {
			jsonObject.put("success", "false");
			jsonObject.put("message", "JSON error: " + j.getMessage());
			jsonObject.put("status", "aborted");
			j.printStackTrace();

		} catch (Exception e) {
			jsonObject.put("success", "false");
			jsonObject.put("message", "Error updating data. " + e.getMessage());
			jsonObject.put("status", "aborted");
			e.printStackTrace();
		}
		String result = "" + jsonObject;
		return Response.status(200).entity(result).build();
	}

	// service to do database operations e.g create, alter or drop table; add, drop
	// or modify column on OpenShift
	@Path("/ddl")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doDDL(String data) {
		boolean status = true;
		String errorMessage = null;
		List<Data> resultData = new ArrayList<>();
		JSONObject jsonObject = new JSONObject();
		JSONObject dataPosted = new JSONObject(data);
		String queryString = dataPosted.getString("data");
		try {
			resultData = new OpenShiftDataBaseHelper().doDDL(queryString.trim());
			for (Data d : resultData) {
				if (!d.getErrorMessage().isEmpty()) {
					status = false;
					errorMessage = d.getErrorMessage();
					break;
				}
			}

			if (status) {
				jsonObject.put("success", true);
				jsonObject.put("message", "Operation Completed succesfully");
			} else {
				jsonObject.put("success", false);
				jsonObject.put("message", "An error occurred: " + errorMessage);
			}
			jsonObject.put("status", "completed");
			jsonObject.put("data", queryString);

		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", "Error occured when doing operation: " + e.getMessage());
			jsonObject.put("status", "aborted");
		}
		String result = "" + jsonObject;
		return Response.status(200).entity(result).build();
	}
	
	//save batch string
	@Path("/savebatchlocal")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveDataStringlocal(String obj) throws JSONException {
		List<Data> resultData = new ArrayList<>();
		JSONObject jsonObject = new JSONObject();
		JSONObject receivedData = new JSONObject(obj);
		JSONHelper jh = new JSONHelper();
		DataModel data = jh.convertJSONToModel(receivedData);
		String responseId = "0";
		
		int x = (int) (Math.random() * 1000);
		try {
			resultData = new DataBaseHelper().insertUpdateDataObject(data);
			if(resultData.isEmpty()) {
				jsonObject.put("success", false);
				jsonObject.put("message", "No results found.");
				jsonObject.put("dataid", "0");
			} else {
				for (Data d : resultData) {
					responseId = d.getResponseId();
					
					if (!d.getErrorMessage().isEmpty()) {
						jsonObject.put("success", false);
						jsonObject.put("message", "An error occurred: " + d.getErrorMessage());
						jsonObject.put("dataid", responseId);
						break;
					} else {
						jsonObject.put("success", true);
						jsonObject.put("message", "Data saved succesfully");
						jsonObject.put("dataid", responseId);
					}
				}
			}
						
		} catch (JSONException j) {
			jsonObject.put("success", false);
			jsonObject.put("message", "JSON error: " + j.getMessage());
			jsonObject.put("dataid", responseId);
			
			j.printStackTrace();
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", "Error saving data. " + e.getMessage());
			jsonObject.put("dataid", responseId);
		}

		String result = "" + jsonObject;
		return Response.status(200).entity(result).build();
	}
	
	//retrieve local
	@Path("/retrievelocal")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveLocal(String data) {
		List<Data> resultData = new ArrayList<>();
		JSONObject jsonObject = new JSONObject();
		//JSONObject dataPosted = new JSONObject(data);
		//String queryString = dataPosted.getString("data");
		try {
			resultData = new DataBaseHelper()
					.getTableData();
			
			for (Data d : resultData) {
				if (!d.getErrorMessage().isEmpty()) {
					jsonObject.put("success", "false");
					jsonObject.put("message", "An error occurred: " + d.getErrorMessage());
					break;
				}
			}
			
			jsonObject.put("status", "completed");
			jsonObject.put("data", resultData);

		} catch (JSONException j) {
			jsonObject.put("success", "false");
			jsonObject.put("message", "JSON error: " + j.getMessage());
			jsonObject.put("status", "aborted");
			j.printStackTrace();

		} catch (Exception e) {
			jsonObject.put("success", "false");
			jsonObject.put("message", "Error updating data. " + e.getMessage());
			jsonObject.put("status", "aborted");
			e.printStackTrace();
		}
		String result = "" + jsonObject;
		return Response.status(200).entity(result).build();
	}
		
}
