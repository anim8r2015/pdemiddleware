package co.ke.predictiveanalytics;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
 
@Path("/ftocservice")
public class FtoCService {
 
	@GET
	@Produces("application/json")
	public Response convertFtoC() throws JSONException {
 
		JSONObject jsonObject = new JSONObject();
		Double fahrenheit = 98.24;
		Double celsius;
		celsius = (fahrenheit - 32) * 5 / 9;
		jsonObject.put("F Value", fahrenheit);
		jsonObject.put("C Value", celsius);
 
		String result = ""+jsonObject;
		return Response.status(200).entity(result).build();
		//return Response.status(200).entity(jsonObject).build();
	}
 
	@Path("{f}")
	@GET
	@Produces("application/json")
	public Response convertFtoCfromInput(@PathParam("f") float f) throws JSONException {
 
		JSONObject jsonObject = new JSONObject();
		float celsius;
		celsius = (f - 32) * 5 / 9;
		jsonObject.put("F Value", f);
		jsonObject.put("C Value", celsius);
 
		String result = "" + jsonObject;
		return Response.status(200).entity(result).build();
	}
}
