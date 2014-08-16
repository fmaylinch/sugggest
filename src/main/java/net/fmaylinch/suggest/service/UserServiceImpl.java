package net.fmaylinch.suggest.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.fmaylinch.suggest.model.Friendship;
import net.fmaylinch.suggest.model.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class UserServiceImpl implements UserService {

	public static final String FIELD_ID = "_id";
	public static final String FIELD_EMAIL = "email";
	public static final String FIELD_PASSWORD = "password";
	public static final String FIELD_FROM = "from";
	public static final String FIELD_TO = "to";


	private final Datastore ds;

	@Inject
	public UserServiceImpl(Datastore ds) {
		this.ds = ds;
	}

	@Override
	public User findById(String id)
	{
		return ds.find(User.class).filter(FIELD_ID, new ObjectId(id)).get();
	}

	@Override
	public User findByEmail(String email)
	{
		return ds.find(User.class).filter(FIELD_EMAIL, email.toLowerCase()).get();
	}

	@Override
	public String save(User user)
	{
		user.setEmail(user.getEmail().toLowerCase());
		final Key<User> key = ds.save(user);
		return (String) key.getId();
	}

	@Override
	public User findByEmailAndPwd(String email, String password) {

		return ds.find(User.class)
				.filter(FIELD_EMAIL, email.toLowerCase())
				.filter(FIELD_PASSWORD, password).get();
	}

	@Override
	public List<User> findFriendsOfUserId(String userId)
	{
		final List<Friendship> friendships = ds.find(Friendship.class).filter(FIELD_FROM, userId).asList();
		return getToUsers(friendships);
	}

	@Override
	public User addFriendByEmail(String userId, String friendEmail) {

		final User user = findById(userId);

		friendEmail = friendEmail.toLowerCase();

		if (user != null && !user.getEmail().equals(friendEmail))
		{
			final User friend = findByEmail(friendEmail);

			if (friend != null)
			{
				if (!userHasFriend(user.getId(), friend.getId())) {
					ds.save(new Friendship(user.getId(), friend.getId()));
				}

				return friend;
			}
		}

		return null;
	}

	private boolean userHasFriend(String userId, String friendId) {

		return ds.find(Friendship.class)
				.filter(FIELD_FROM, userId)
				.filter(FIELD_TO, friendId)
				.countAll() > 0;
	}

	/** Returns the "to" users of these friendships */
	private List<User> getToUsers(List<Friendship> friendships)
	{
		List<User> friends = new ArrayList<User>();

		for (Friendship friendship : friendships) {
			final User user = findById(friendship.getTo());
			friends.add(user);
		}

		return friends;
	}
}
