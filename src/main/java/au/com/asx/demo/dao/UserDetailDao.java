package au.com.asx.demo.dao;

import au.com.asx.demo.dto.UserDetailDTO;

public interface UserDetailDao {

	UserDetailDTO findByUserId(int userId);
	
	void update(UserDetailDTO userDetailDTO);
}
