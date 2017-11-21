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
public class ActivityResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	Long personId;
	String activity_type;
	Long id;
	
	public ActivityResource(UriInfo uriInfo, Request request,Long personId, String activity_type, Long id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.personId = personId;
		this.activity_type = activity_type;
		this.id = id;
	}
	
	@GET
	@Consumes({"application/json","application/xml"})
	@Produces({"application/json","application/xml"})
	public Response getActivityById() {
		Activity a = ActivityDao.instance.getPersonActivitiyWithTypeById(personId, activity_type, id);
		return Response.status(Status.OK).entity(a).build();
	}
	
	@PUT
	@Consumes({"application/json","application/xml"})
	@Produces({"application/json","application/xml"})
	public Response updateActivityById(Activity a) {
		Activity ra = ActivityDao.instance.getPersonActivitiyWithTypeById(personId, activity_type, id);
		if(a.getName()!=null)
			ra.setName(a.getName());
		if(a.getDescription()!=null)
			ra.setDescription(a.getDescription());
		if(a.getPlace()!=null)
			ra.setPlace(a.getPlace());
		if(a.getStartdate()!=null)
			ra.setStartdate(a.getStartdate());
		if(a.getType()!=null)
		{
			ActivityType at = null;
			try {
				at = ActivityTypeDao.instance.getActivityTypeByName(a.getType().getName());
			}
			catch(Exception e){
				at = null;
			}
			if(at==null)
			{
				at = new ActivityType();
				at.setName(a.getType().getName());
				ActivityTypeDao.instance.addActivityType(at);
			}
			ra.setType(at);
		}
		
		
		ra = ActivityDao.instance.updateActivity(ra);
		return Response.status(Status.OK).entity(ra).build();
	}
}
