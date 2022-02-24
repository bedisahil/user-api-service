package au.com.asx.demo.mapper;

import au.com.asx.demo.dto.UserDetailDTO;
import au.com.asx.demo.entity.UserDetail;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailMapper {

    private AddressMapper addressMapper;

    public UserDetailDTO toUserDetailDTO(UserDetail userDetail) {
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setUserId(Integer.toString(userDetail.getUserId()));
        userDetailDTO.setTitle(userDetail.getTitle());
        userDetailDTO.setFirstname(userDetail.getFirstname());
        userDetailDTO.setLastname(userDetail.getLastname());
        userDetailDTO.setGender(userDetail.getGender());
        userDetailDTO.setAddressDTO(addressMapper.toAddressDTO(userDetail.getAddress()));
        return userDetailDTO;
    }

    public UserDetail toUserDetail(UserDetailDTO userDetailDTO) {
        UserDetail userDetail = new UserDetail();
        userDetail.setUserId(Integer.parseInt(userDetailDTO.getUserId()));
        userDetail.setTitle(userDetailDTO.getTitle());
        userDetail.setFirstname(userDetailDTO.getFirstname());
        userDetail.setLastname(userDetailDTO.getLastname());
        userDetail.setGender(userDetailDTO.getGender());
        userDetail.setAddress(addressMapper.toAddress(userDetailDTO.getAddressDTO(), userDetailDTO));
        return userDetail;
    }
}
