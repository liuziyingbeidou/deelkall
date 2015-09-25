package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import utils.ReflectionUtils;

@MappedSuperclass
@SuppressWarnings({"serial"})
public class IdEntity implements Serializable{
	
	public static final String tablePrefix = "";
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer id;

	public Integer getId() {
	
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Transient boolean selected = false;
	@Transient Object userValue;
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	//¡ı÷“«Ï
	@Transient	
	private Map<Object, Object> condmap;
 	
 	public void setAttribute(Object key,Object value){
		if(condmap==null)condmap=new HashMap<Object,Object>();
		condmap.put(key, value);
	}
	
    public Object getAttribute(Object key){
    	if(condmap==null)condmap=new HashMap<Object,Object>();
    	return condmap.get(key);
    }
    
    /**
	 * @return the condmap
	 */
	public Map<Object, Object> getCondmap() {
		return condmap;
	}
	/**
	 * @param condmap the condmap to set
	 */
	public void setCondmap(Map<Object, Object> condmap) {
		this.condmap = condmap;
	}
	
	
	public boolean equals(Object object) {
		if (null == object)
			return false;
		if (!(object.getClass().getName().toString().equals(this.getClass().getName().toString())))
			return false;
		else {
			Integer object_id = (Integer) ReflectionUtils.getFieldValue(object,
					"id");
			if (null == this.getId() || null == object_id)
				return false;
			else
				return (this.getId().intValue() == object_id.intValue());
		}
	}
	@Transient
	public int hashCode = Integer.MIN_VALUE;

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":"
						+ this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString() {
		return super.toString();
	}

	public Object getUserValue() {
		return userValue;
	}

	public void setUserValue(Object userValue) {
		this.userValue = userValue;
	}
}
