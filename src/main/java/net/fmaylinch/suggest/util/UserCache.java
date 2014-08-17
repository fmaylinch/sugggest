package net.fmaylinch.suggest.util;

import com.google.common.collect.Maps;
import net.fmaylinch.suggest.model.User;
import net.fmaylinch.suggest.service.UserService;

import java.util.Map;

/**
 * Utility to load different users that probably repeat
 */
public class UserCache
{
	private final UserService userService;
	private final Map<String, User> loadedUsers;

	public UserCache(UserService userService)
	{
		this.userService = userService;
		loadedUsers = Maps.newHashMap();
	}

	public User findById(String userId)
	{
		if (!loadedUsers.containsKey(userId)) {
			loadedUsers.put(userId, userService.findById(userId));
		}

		return loadedUsers.get(userId);
	}
}
