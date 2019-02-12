package co.ke.pdeanalytics.services;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;

@Path("data/")
public class PDEWebServices {
	@Context
    private UriInfo context;
	
	@GET
    @Path("years")
    @Produces(MediaType.TEXT_HTML)
    public String getSchemeYears(@PathParam("policyNumber") String policyNumber) {
        //TODO return proper representation object
        return "<html><body><h1>Hello member " + policyNumber + "!</body></h1></html>"; 
    }

	@PUT
	@Consumes(MediaType.TEXT_HTML)
    public void putHtml(String content) {
    }
}
