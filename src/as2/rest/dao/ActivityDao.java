package as2.rest.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import as2.rest.model.Activity;

public enum ActivityDao {
	instance;
	
	private EntityManagerFactory emf;
	  
	  public EntityManager createEntityManager() {
	        try {
	            return emf.createEntityManager();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;    
	    }

	    public void closeConnections(EntityManager em) {
	        em.close();
	    }

	    public EntityTransaction getTransaction(EntityManager em) {
	        return em.getTransaction();
	    }

	    public EntityManagerFactory getEntityManagerFactory() {
	        return emf;
	    }  
	  
	  private Map<Long, Activity> contentProvider = new HashMap<Long, Activity>();
	  
	  public Map<Long, Activity> getDataProvider() {
	    return contentProvider;
	  }
	  
	   private ActivityDao() {
	     if(emf!=null)
	       emf.close();
	     emf = Persistence.createEntityManagerFactory("Server");
	     /*Connect();
	     contentProvider.put(john.getPersonId(), john);*/
	   }
	   
	   public void DropDB() {
	     Path p = Paths.get("./people.db");
	     try {
	      Files.delete(p);
	    } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	   }
	   
	   public Activity addActivity(Activity a) {
	     EntityManager em = ActivityDao.instance.createEntityManager();
	     EntityTransaction tx = em.getTransaction();
	     tx.begin();
	     em.persist(a);
	     tx.commit();
	     ActivityDao.instance.closeConnections(em);
	     return a;
	   }
	   
	   public List<Activity> getAll(){
	     EntityManager em = ActivityDao.instance.createEntityManager();
	     List<Activity> list = em.createNamedQuery("Activity.findAll", Activity.class).getResultList();
	     ActivityDao.instance.closeConnections(em);
	     return list;
	   }
	   
	   public Activity getActivityById(Long id) {
		   EntityManager em = ActivityDao.instance.createEntityManager();
		   Query query = em.createQuery("SELECT a FROM Activity a WHERE p.id=:arg1");
		   query.setParameter("arg1", id);
		   Activity a = (Activity) query.getSingleResult();
		   return a;
	   }
}