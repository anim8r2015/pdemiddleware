package co.ke.predictiveanalytics;

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

import co.ke.predictiveanalytics.helpers.DataBaseHelper;
import co.ke.predictiveanalytics.model.Data;
 
@Path("/service")
public class DataService {
	
	@Path("save/{b}/{c}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveData(@PathParam("b") String b, @PathParam("c") String c) throws JSONException {
 
		JSONObject jsonObject = new JSONObject();
		int x = (int)Math.random();
		try {
			new DataBaseHelper().insertUpdateData("A",x,b,c);
			jsonObject.put("success", "true");
			jsonObject.put("message", "data saved succesfully");
		} catch (Exception e) {
			jsonObject.put("success", "false");
			jsonObject.put("message", "error saving data");
		}
		
		String result = "" + jsonObject;
		return Response.status(200).entity(result).build();
	}
	
	@Path("/update")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateData(String data) {
		
		JSONObject jsonObject = new JSONObject();
		try {
			//new DataBaseHelper().
			//insertUpdateData("U",data.getDataId(),data.getDataName(),data.getDataDate().toString());
			jsonObject.put("data", data);
			jsonObject.put("success", "true");
			jsonObject.put("message", "data updated succesfully");
			
		} catch (Exception e) {
			jsonObject.put("success", "false");
			jsonObject.put("message", "error updating data");
		}
		String result = "" + jsonObject;
		return Response.status(200).entity(result).build();
	}

}
