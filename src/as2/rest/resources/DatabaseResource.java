package as2.rest.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import as2.rest.dao.DatabaseDao;

@Path("/db")
public class DatabaseResource {
	
	@GET
	public String Init() {
		DatabaseDao.instance.Init();
		return "Init";
	}
	
	@DELETE
	public String Drop() {
		DatabaseDao.instance.DropDB();
		return "Drop";
	}
}
