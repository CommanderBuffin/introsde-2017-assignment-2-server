package as2.rest.resources;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
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
public class ActivityCollectionResource {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	String activity_type;
	Long personId;
	
	
	public ActivityCollectionResource(UriInfo uriInfo, Request request, Long personId, String activity_type) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.personId = personId;
		this.activity_type = activity_type;
	}
	
	@GET
	@Consumes({"application/json","application/xml"})
	@Produces({"application/json","application/xml"})
	public List<Activity> getPersonActivitiesByType(){
		MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
		if(uriInfo.getQueryParameters().containsKey("before")&&uriInfo.getQueryParameters().containsKey("after"))
			return getPersonActivitiesByDateRange(queryParams.getFirst("before"),queryParams.getFirst("after"));
		/*List<Activity> list = ActivityDao.instance.getPersonActivitiesByType(personId, activity_type);
		return Response.status(Status.OK).entity(list).build();*/
		return getListActivity();
	}
	
	@Consumes({"application/json","application/xml"})
	@Produces({"application/json","application/xml"})
	private List<Activity> getListActivity() {
		List<Activity> list = ActivityDao.instance.getPersonActivitiesByType(personId, activity_type);
		return list;
		//return Response.status(Status.OK).entity(p.getActivities()).build();
	}
	
	@Path("{activityId}")
	public ActivityResource getActivity(@PathParam("activityId") Long id) {
		return new ActivityResource(uriInfo,request,personId,activity_type,id);
	}
	
	@POST
	@Consumes({"application/json","application/xml"})
	@Produces({"application/json","application/xml"})
	public Response addActivityToPerson(Activity a)
	{
		if(a.getId()==null)
			a.setId(ActivityDao.instance.getNewId());
		//if(ActivityDao.instance.getActivityById(a.getId())!=null)
			//return Response.noContent().status(Status.BAD_REQUEST).build();
		if(a.getName()==null||a.getDescription()==null||a.getPlace()==null||a.getStartdate()==null)
			return Response.noContent().status(Status.BAD_REQUEST).build();
		if(ActivityTypeDao.instance.getActivityTypeByName(activity_type)==null)
			return Response.noContent().status(Status.BAD_REQUEST).build();
		if(PersonDao.instance.getPersonById(personId)==null)
			return Response.noContent().status(Status.BAD_REQUEST).build();
		
		a.setType(ActivityTypeDao.instance.getActivityTypeByName(activity_type));
		Person p = PersonDao.instance.getPersonById(personId);
		a.setPerson(p);
		Activity ra = ActivityDao.instance.addActivity(a);
		p.addActivity(ra);
		PersonDao.instance.updatePerson(p);
		return Response.status(Status.CREATED).entity(a).build();
	}
	

	@Consumes({"application/json","application/xml"})
	@Produces({"application/json","application/xml"})
	public List<Activity> getPersonActivitiesByDateRange(String before, String after) {
		List<Activity> activities = PersonDao.instance.getPersonById(personId).getActivities();
		List<Activity> aa = new ArrayList<Activity>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.s");
		Date beforeDate;
		Date afterDate;
		try {
			beforeDate = formatter.parse(before);
			afterDate = formatter.parse(after);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return aa;//Response.noContent().status(Status.BAD_REQUEST).build();
		}
		for(Activity a : activities)
		{
			Date a_date;
			try{
				a_date = formatter.parse(a.getStartdate());
				if(a_date.before(beforeDate)&&a_date.after(afterDate))
					aa.add(a);
			}
			catch(ParseException e) {
				e.printStackTrace();
				return new ArrayList<Activity>();//Response.status(Status.BAD_REQUEST).entity("Activity date parse error").build();
			}
		}
		return aa;//Response.status(Status.OK).entity(aa).build();
	}
	
}
