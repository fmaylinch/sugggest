package net.fmaylinch.suggest.auth;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import net.fmaylinch.suggest.model.User;
import net.fmaylinch.suggest.service.UserService;

public class UserAuthenticator implements Authenticator<BasicCredentials, User>
{
	private final UserService userService;

	public UserAuthenticator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {

		User user = userService.findByEmailAndPwd(credentials.getUsername(), credentials.getPassword());

		if (user != null) {
			return Optional.of(user);
		} else {
			return Optional.absent();
		}
	}
}
