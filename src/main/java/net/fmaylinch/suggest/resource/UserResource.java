package net.fmaylinch.suggest.resource;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.auth.Auth;
import net.fmaylinch.suggest.model.User;
import net.fmaylinch.suggest.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class UserResource {

	private final UserService userService;

	@Inject
	public UserResource(UserService userService)
	{
		this.userService = userService;
	}

	/**
	 * Registers a new user.
	 *
	 * @param user   provided in the body, must have name, email and password
	 *
	 * @return {@link User} that was created,
	 *                      or {@link Response.Status#FORBIDDEN} if email is already used,
	 *                      or {@link Response.Status#PRECONDITION_FAILED} if some property is missing or is invalid.
	 */
	@Path("signup")
	@POST
	public Response signUp(User user)
	{
		if (validateNewUser(user))
		{
			final User existingUser = userService.findByEmail(user.getEmail());

			if (existingUser == null)
			{
				userService.save(user);
				final User userCreated = userService.findByEmail(user.getEmail());
				return Response.ok(userCreated).build();
			} else {
				return Response.status(Response.Status.FORBIDDEN).build();
			}
		} else {
			return Response.status(Response.Status.PRECONDITION_FAILED).build();
		}
	}

	/**
	 * Checks email and password.
	 *
	 * @param user   provided in the body, must have email and password
	 *
	 * @return {@link User} that matches those credentials,
	 *                      or {@link Response.Status#UNAUTHORIZED} if credentials are wrong.
	 */
	@Path("signin")
	@POST
	public Response signIn(User user)
	{
		final User userFound = userService.findByEmailAndPwd(user.getEmail(), user.getPassword());

		if (userFound != null) {
			return Response.ok(userFound).build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	/**
	 * Retrieves the friends of a user.
	 *
	 * @param user   provided with basic auth
	 *
	 * @return {@link User} friends
	 */
	@Path("friends")
	@GET
	public Response friends(@Auth User user)
	{
		return Response.ok(userService.findFriendsOfUserId(user.getId())).build();
	}

	/**
	 * Adds a friendship relation from user to friend
	 *
	 * @param user   provided with basic auth
	 * @param friend provided in the body, only needs the email
	 *
	 * @return {@link User} friend,
	 *         or {@link Response.Status#NOT_FOUND} if there's no user with the friend email
	 */
	@Path("friends")
	@POST
	public Response addFriend(@Auth User user, User friend)
	{
		User friendFound = userService.addFriendByEmail(user.getId(), friend.getEmail());
		if (friendFound != null) {
			return Response.ok(friendFound).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	private boolean validateNewUser(User user)
	{
		return StringUtils.isNotEmpty(user.getName())
				&& StringUtils.isNotEmpty(user.getPassword())
				&& EmailValidator.getInstance().isValid(user.getEmail());
	}
}
