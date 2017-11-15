package as2.rest.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

//@XmlRootElement(name="activity")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name ="Activities")
@NamedQuery(name="Activity.findAll", query="SELECT a FROM Activity a")
public class Activity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	@Column(name="activity_id")
	@XmlAttribute
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private String startdate;
	
	@Column
	private String place;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="person_id")
	@XmlTransient
	private Person person;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="activitytype_id")
	@XmlElement(name="activity_type")
	private ActivityType type;
	
	public Activity() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
		if(!person.getActivities().contains(this)) {
			person.getActivities().add(this);
		}
	}

	public ActivityType getType() {
		return type;
	}

	public void setType(ActivityType type) {
		this.type = type;
		if(!type.getActivities().contains(this)) {
			type.getActivities().add(this);
		}
	}
	/*
	public void setOwner(Employee employee) {
        this.owner = employee;
        if (!employee.getPhones().contains(this)) { // warning this may cause performance issues if you have a large data set since this operation is O(n)
            employee.getPhones().add(this);
        }
    }*/
}
