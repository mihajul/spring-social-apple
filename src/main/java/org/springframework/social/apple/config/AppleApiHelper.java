package org.springframework.social.apple.config;

import org.springframework.social.UserIdSource;
import org.springframework.social.apple.api.Apple;
import org.springframework.social.config.xml.ApiHelper;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;

public class AppleApiHelper implements ApiHelper<Apple> {

	private final UsersConnectionRepository usersConnectionRepository;

	private final UserIdSource userIdSource;
	
	public AppleApiHelper(UsersConnectionRepository usersConnectionRepository,UserIdSource userIdSource) {
		
		this.usersConnectionRepository = usersConnectionRepository;
		this.userIdSource = userIdSource;
	}
	
	@Override
	public Apple getApi() {
		
		Connection<Apple> connection = usersConnectionRepository.createConnectionRepository(userIdSource.getUserId()).
										findPrimaryConnection(Apple.class);
		
		return connection != null ? connection.getApi() : null;
	}

}
