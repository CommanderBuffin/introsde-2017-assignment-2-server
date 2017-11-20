package as2.rest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import as2.rest.dao.ActivityDao;
import as2.rest.dao.ActivityTypeDao;
import as2.rest.dao.PersonDao;
import as2.rest.model.Activity;
import as2.rest.model.ActivityType;
import as2.rest.model.Person;

@Path("activity")
public class ActivityCollectionResource {
	
	@GET
	@Path("ok")
	public String asd() {
		return "ok";
	}
	
	@GET
	@Consumes({"application/json","application/xml"})
	@Produces({"application/json","application/xml"})
	public List<Activity> getActivities(){
		List<Activity> list = ActivityDao.instance.getAll();
		return list;
		
	}
	
	@DELETE
	@Path("drop")
	@Consumes({"application/json","application/xml"})
	@Produces({"application/json","application/xml"})
	public String drop() {
		PersonDao.instance.DropDB();
		return "Dropped";
	}
	
	@GET
	@Path("new")
	@Consumes({"application/json","application/xml"})
	@Produces({"application/json","application/xml"})
	public Person addPerson() {
		Person p = new Person();
		p.setFirstname("Mattia");
		p.setLastname("Buffa");
		p.setBirthdate("24/02/1992");
		p.setId(new Long(1));
		List<Activity> a = new ArrayList<Activity>();
		p.setActivities(a);
		
		Person r = PersonDao.instance.addPerson(p);
		return r;
	}
	
	@GET
	@Path("{id}/new")
	@Consumes({"application/json","application/xml"})
	@Produces({"application/json","application/xml"})
	public Person addActivityToPerson(@PathParam("id") Long id) {
		Person p = PersonDao.instance.getPersonById(id);
		Activity a = new Activity();
		a.setId(new Long(4));
		a.setName("Play PUBG");
		a.setDescription("Play PUBG Test Server");
		a.setPlace("Home");
		a.setStartdate("14/11/2017");
		a.setPerson(p);
		
		ActivityType at = new ActivityType();
		at.setName("Media");
		//at.addActivity(a);
		
		ActivityType rat = ActivityTypeDao.instance.addActivityType(at);
		
		a.setType(rat);
		
		Activity ra = ActivityDao.instance.addActivity(a);
		
		return PersonDao.instance.getPersonById(id);
	}
	
	@GET
	@Path("{id}")
	@Consumes({"application/json","application/xml"})
	@Produces({"application/json","application/xml"})
	public Person getPersonById(@PathParam("id") Long id){
		System.out.println(id);
		return PersonDao.instance.getPersonById(id);
	}
}
