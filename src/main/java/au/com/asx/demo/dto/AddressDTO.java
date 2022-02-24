package au.com.asx.demo.dto;

import lombok.Data;

@Data
public class AddressDTO {

    private String street;
    private String city;
    private String state;
    private String postcode;

}
