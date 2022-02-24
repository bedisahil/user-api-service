package au.com.asx.demo.dao;

import au.com.asx.demo.dto.UserDetailDTO;
import au.com.asx.demo.entity.UserDetail;
import au.com.asx.demo.entity.Address;
import au.com.asx.demo.mapper.UserDetailMapper;
import au.com.asx.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailDaoImpl implements UserDetailDao {

	private static final Logger LOGGER= LoggerFactory.getLogger(UserDetailDaoImpl.class);
	
	private UserRepository userRepository;
	
	private UserDetailMapper userDetailMapper;

	@Override
	public UserDetailDTO findByUserId(int userId) {
		UserDetailDTO userDetailDTO = null;
		Optional<UserDetail> userDetail = userRepository.findById(userId);
		if(userDetail.isPresent()) {
			userDetailDTO = userDetailMapper.toUserDetailDTO(userDetail.get());

		}
		return userDetailDTO;
	}

	@Override
	@Transactional
	public void update(UserDetailDTO userDetailDTO) {
		UserDetail userDetail = userDetailMapper.toUserDetail(userDetailDTO);
		userRepository.save(userDetail);
	}


	@PostConstruct
	public void createDataInDatabase() {
		LOGGER.info("Creating 100 users during startup with user from 1 to 100.");
		for(int i=1; i<100; i++) {
			UserDetail userDetail = new UserDetail();
			Address address = new Address();
			userDetail.setUserId(i);
			userDetail.setFirstname("Sean" + i);
			userDetail.setLastname("Penn" + i);
			userDetail.setGender("Male");
			userDetail.setTitle("Mr");
			address.setUserId(i);
			address.setCity("Sydney");
			address.setPostcode("2000");
			address.setStreet(i + " George St");
			address.setState("NSW");
			userDetail.setAddress(address);
			address.setUserDetail(userDetail);
			userRepository.save(userDetail);
		}
		
	}
	
}
