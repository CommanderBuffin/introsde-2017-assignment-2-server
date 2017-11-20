package as2.rest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import as2.rest.dao.ActivityDao;
import as2.rest.dao.ActivityTypeDao;
import as2.rest.dao.PersonDao;
import as2.rest.model.Activity;
import as2.rest.model.ActivityType;
import as2.rest.model.Person;

@Stateless
@LocalBean
@Path("person")
public class PersonCollectionResource {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@GET
	@Path("ok")
	public String asd() {
		return "ok";
	}
	
	@GET
	@Consumes({"application/json","application/xml"})
	@Produces({"application/json","application/xml"})
	public List<Person> getPeople(){
		List<Person> list = PersonDao.instance.getAll();
		return list;
	}
	
	@POST
	@Consumes({"application/json","application/xml"})
	@Produces({"application/json","application/xml"})
	public Response createPerson(Person p) {
		if(p.getId()!=null) {
			if(PersonDao.instance.getPersonById(p.getId())!=null)
				return Response.noContent().status(Status.BAD_REQUEST).build();
		}
		else {
			Long p_id=PersonDao.instance.getNewId();
			p.setId(p_id);
		}
		

		List<Activity> activities = new ArrayList<Activity>();
		if(p.getActivities()!=null)
			activities.addAll(p.getActivities());
		
		if(p.getFirstname()==null || p.getLastname()==null || p.getBirthdate()==null)
			return Response.noContent().status(Status.BAD_REQUEST).build();
		
		p.setActivities(new ArrayList<Activity>());
		PersonDao.instance.addPerson(p);

		for(Activity a : activities) {
			ActivityType at = ActivityTypeDao.instance.getActivityTypeByName(a.getType().getName());
			if(at==null) {
				at = a.getType();
				at = ActivityTypeDao.instance.addActivityType(at);
			}
			a.setType(at);
			Long a_id = ActivityDao.instance.getNewId();
			a.setId(a_id);
			a.setPerson(p);
			Activity ra = ActivityDao.instance.addActivity(a);
			p.addActivity(ra);
		}
		Person rp = PersonDao.instance.updatePerson(p);
		return Response.status(Status.CREATED).entity(rp).build();
	}
	
	@Path("{personId}")
	public PersonResource getPerson(@PathParam("personId") Long id) {
		return new PersonResource(uriInfo,request,id);
	}
}
