package au.com.asx.demo.service;

import au.com.asx.demo.dao.UserDetailDao;
import au.com.asx.demo.dto.UserDetailDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER= LoggerFactory.getLogger(UserServiceImpl.class);

	private UserDetailDao userDetailDao;

	@Override
	@HystrixCommand(fallbackMethod = "getUserDetailsFallback",
				commandProperties = {
						@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
						@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value = "5"),
						@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value = "50"),
						@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value = "5000")
				})
	public UserDetailDTO getUserDetails(String userId) {
		return userDetailDao.findByUserId(Integer.parseInt(userId));
	}

	@Override
	public UserDetailDTO updateUserDetails(UserDetailDTO userDetailDTO) {
		UserDetailDTO existingUser = userDetailDao.findByUserId(Integer.parseInt(userDetailDTO.getUserId()));
		if(existingUser == null) {
			return null;
		}
		compareWithExistingUser(existingUser, userDetailDTO);
		userDetailDao.update(userDetailDTO);
		userDetailDTO = userDetailDao.findByUserId(Integer.parseInt(userDetailDTO.getUserId()));
		return userDetailDTO;
	}

	public UserDetailDTO getUserDetailsFallback(@PathVariable String id, Throwable e) {
		LOGGER.info("Executing callback due to exception: " + e.getMessage());
		return new UserDetailDTO();
	}

	private void compareWithExistingUser(UserDetailDTO existingUser, UserDetailDTO updatedUser) {
		if(updatedUser.getFirstname() == null) {
			updatedUser.setFirstname(existingUser.getFirstname());
		}
		if(updatedUser.getLastname() == null) {
			updatedUser.setLastname(existingUser.getLastname());
		}
		if(updatedUser.getTitle() == null) {
			updatedUser.setTitle(existingUser.getTitle());
		}
		if(updatedUser.getGender() == null) {
			updatedUser.setGender(existingUser.getGender());
		}
		if(updatedUser.getAddressDTO() != null) {
			if(updatedUser.getAddressDTO().getCity() == null) {
				updatedUser.getAddressDTO().setCity(existingUser.getAddressDTO().getCity());
			}
			if(updatedUser.getAddressDTO().getStreet() == null) {
				updatedUser.getAddressDTO().setStreet(existingUser.getAddressDTO().getStreet());
			}
			if(updatedUser.getAddressDTO().getState() == null) {
				updatedUser.getAddressDTO().setState(existingUser.getAddressDTO().getState());
			}
			if(updatedUser.getAddressDTO().getPostcode() == null) {
				updatedUser.getAddressDTO().setPostcode(existingUser.getAddressDTO().getPostcode());
			}
		} else {
			updatedUser.setAddressDTO(existingUser.getAddressDTO());
		}
	}
}
