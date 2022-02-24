package au.com.asx.demo.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class UserDetail {

	@Id
	private int userId;
	private String title;
	private String firstname;
	private String lastname;
	private String gender;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Address address;

}
