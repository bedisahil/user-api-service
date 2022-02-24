package au.com.asx.demo.mapper;

import au.com.asx.demo.dto.AddressDTO;
import au.com.asx.demo.dto.UserDetailDTO;
import au.com.asx.demo.entity.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressMapper {

    public AddressDTO toAddressDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity(address.getCity());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setState(address.getState());
        addressDTO.setPostcode(address.getPostcode());
        return addressDTO;
    }

    public Address toAddress(AddressDTO addressDTO, UserDetailDTO userDetailDTO) {
        Address address = new Address();
        address.setUserId(Integer.parseInt(userDetailDTO.getUserId()));
        address.setCity(addressDTO.getCity());
        address.setStreet(addressDTO.getStreet());
        address.setState(addressDTO.getState());
        address.setPostcode(addressDTO.getPostcode());
        return address;
    }
}
