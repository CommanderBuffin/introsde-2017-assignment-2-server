package as2.rest.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="ActivityTypes")
@NamedQuery(name="ActivityType.findAll", query="SELECT at FROM ActivityType at")
public class ActivityType {
	@Id
	@Column(name="activitytype_id")
	private String name;

	@OneToMany(mappedBy="type", cascade = CascadeType.ALL)
	@XmlTransient
	private List<Activity> activities;
	
	public ActivityType() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
	public void addActivity(Activity activity) {
        this.activities.add(activity);
        if (activity.getType() != this) {
            activity.setType(this);
        }
    }
}
