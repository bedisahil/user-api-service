package au.com.asx.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class Address {

    @Id
	private int userId;
    private String street;
    private String city;
    private String state;
    private String postcode;
    @OneToOne
    private UserDetail userDetail;

}
