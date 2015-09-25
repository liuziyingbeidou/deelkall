package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private int pk_user;
	private String  usercode;
	private String password;
	private String username;
	private int  sex;
	private int  level;//level
	private String pk_shop;
	private int userlock;
	
	public int getUserlock() {
		return userlock;
	}
	public void setUserlock(int userlock) {
		this.userlock = userlock;
	}
	
	public String getPk_shop() {
		return pk_shop;
	}
	public void setPk_shop(String pk_shop) {
		this.pk_shop = pk_shop;
	}
	@Id
	@GeneratedValue
	public int getPk_user() {
		return pk_user;
	}
	public void setPk_user(int pkUser) {
		pk_user = pkUser;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}
