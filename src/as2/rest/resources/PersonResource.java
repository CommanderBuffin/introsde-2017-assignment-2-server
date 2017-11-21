package as2.rest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.UriInfo;

import as2.rest.dao.ActivityDao;
import as2.rest.dao.ActivityTypeDao;
import as2.rest.dao.PersonDao;
import as2.rest.model.Activity;
import as2.rest.model.ActivityType;
import as2.rest.model.Person;

@Stateless
@LocalBean
public class PersonResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	Long id;
	
	public PersonResource(UriInfo uriInfo, Request request, Long id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	@GET
	@Consumes({"application/json","application/xml"})
	@Produces({"application/json","application/xml"})
	public Response getPerson() {
		Person p = PersonDao.instance.getPersonById(id);
		if(p==null)
			Response.noContent().status(Status.NOT_FOUND).build();
		return Response.status(Status.OK).entity(p).build();
	}
	/*@GET
	@Consumes({"application/json","application/xml"})
	@Produces({"application/json","application/xml"})
	public Person getPerson() {
		Person p = PersonDao.instance.getPersonById(id);
		return p;
	}*/
	
	@PUT
	@Consumes({"application/json","application/xml"})
	@Produces({"application/json","application/xml"})
	public Response updatePerson(Person p) {
		Person r = PersonDao.instance.getPersonById(id);
		if(r==null)
		{
			//abort
			Response.noContent().status(Status.NOT_FOUND).build();
		}
		else {
			if(p.getFirstname()!=null)
				r.setFirstname(p.getFirstname());
			if(p.getLastname()!=null)
				r.setLastname(p.getLastname());
			if(p.getBirthdate()!=null)
				r.setBirthdate(p.getBirthdate());
		}

		PersonDao.instance.updatePerson(r);
		return Response.status(Status.OK).entity(r).build();
	}
	
	@DELETE
	public Response deletePerson() {
		PersonDao.instance.removePerson(id);
		return Response.noContent().status(Status.OK).build();
	}
	
	@Path("{activity_type}")
	public ActivityCollectionResource getPersonActivitiesByType(@PathParam("activity_type") String activity_type) {
		return new ActivityCollectionResource(uriInfo,request,id,activity_type);
	}
}
