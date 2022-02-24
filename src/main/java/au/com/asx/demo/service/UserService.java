package au.com.asx.demo.service;

import au.com.asx.demo.dto.UserDetailDTO;

public interface UserService {
	
	UserDetailDTO getUserDetails(String userId);
	
	UserDetailDTO updateUserDetails(UserDetailDTO userDetailDTO);

}