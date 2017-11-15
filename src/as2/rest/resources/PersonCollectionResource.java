package as2.rest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
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

@Path("person")
public class PersonCollectionResource {
	
	@GET
	@Path("ok")
	public String asd() {
		return "ok";
	}
	
	@GET
	@Consumes({"application/json","application/xml"})
	@Produces({"application/json","application/xml"})
	public List<Person> getPeople(){
		/*List<Person> list = new ArrayList<Person>();
		Person p = new Person();
		Activity a = new Activity();
		a.setId(new Long(1));
		a.setName("Play PUBG");
		a.setDescription("Play FPP Squad PUBG");
		a.setPlace("Home");
		a.setStartdate("14/11/2017");
		
		p.setFirstname("Mattia");
		p.setLastname("Buffa");
		p.setBirthdate("24/02/1992");
		p.setId(new Long(1));
		

		ActivityType at = new ActivityType();
		at.setName("Game");
		List<Activity> aat = new ArrayList<Activity>();
		aat.add(a);
		at.setActivities(aat);
		
		a.setType(at);
		
		a.setPerson(p);
		ArrayList<Activity> aa = new ArrayList<Activity>();
		aa.add(a);
		p.setActivities(aa);
		
		list.add(p);*/
		
		List<Person> list = PersonDao.instance.getAll();
		return list;
		/*JAXBContext context;
		try {
			context = JAXBContext.newInstance(Person.class);
		
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(p, sw);  
			r = sw.toString();
		}
		 catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				r=e.getMessage();
			}
		return Response.accepted().entity(r).build();*/
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
