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
import co.ke.predictiveanalytics.helpers.OpenShiftDataBaseHelper;
import co.ke.predictiveanalytics.model.Data;

@Path("/service")
public class DataService {

	@Path("save/{b}/{c}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveData(@PathParam("b") String b, @PathParam("c") String c) throws JSONException {
		List<Data> resultData = new ArrayList<>();
		JSONObject jsonObject = new JSONObject();
		int x = (int) (Math.random() * 1000);
		try {
			resultData = new OpenShiftDataBaseHelper().insertUpdateData("A", x, b, c);
			JSONArray jobj = new JSONArray(resultData);
			for (Data d : resultData) {
				if (!d.getErrorMessage().isEmpty()) {
					jsonObject.put("success", "false");
					jsonObject.put("message", "An error occurred: " + d.getErrorMessage());
					break;
				}
			}
			jsonObject.put("result", jobj);
			jsonObject.put("status", "completed");
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

	@Path("/retrieve")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieve(String data) {
		List<Data> resultData = new ArrayList<>();
		JSONObject jsonObject = new JSONObject();

		try {
			resultData = new OpenShiftDataBaseHelper()
			//resultData = new DataBaseHelper()
					.getTableData();
			JSONArray jobj = new JSONArray(resultData);

			for (Data d : resultData) {
				if (!d.getErrorMessage().isEmpty()) {
					jsonObject.put("success", "false");
					jsonObject.put("message", "An error occurred: " + d.getErrorMessage());
					break;
				}
			}
			jsonObject.put("result", jobj);
			jsonObject.put("status", "completed");
			jsonObject.put("data", data);

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
	@Path("/dml")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doDML(String data) {
		boolean status = true;
		String errorMessage = null;
		List<Data> resultData = new ArrayList<>();
		JSONObject jsonObject = new JSONObject();
		JSONObject dataPosted = new JSONObject(data);
		String queryString = dataPosted.getString("data");
		try {
			resultData = new OpenShiftDataBaseHelper().doDML(queryString.trim());
			for (Data d : resultData) {
				if (!d.getErrorMessage().isEmpty()) {
					status = false;
					errorMessage = d.getErrorMessage();
					break;
				}
			}

			if (status) {
				jsonObject.put("success", "true");
				jsonObject.put("message", "Operation Completed succesfully");
			} else {
				jsonObject.put("success", "false");
				jsonObject.put("message", "An error occurred: " + errorMessage);
			}
			jsonObject.put("status", "completed");
			jsonObject.put("data", queryString);

		} catch (Exception e) {
			jsonObject.put("success", "false");
			jsonObject.put("message", "Error occured when doing operation: " + e.getMessage());
			jsonObject.put("status", "aborted");
		}
		String result = "" + jsonObject;
		return Response.status(200).entity(result).build();
	}

}
