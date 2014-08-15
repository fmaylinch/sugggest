package net.fmaylinch.suggest.service;

import net.fmaylinch.suggest.model.User;

import java.util.List;

public interface UserService
{
	User findById(String id);

	User findByEmail(String email);

	String save(User user);

	User findByEmailAndPwd(String email, String password);

	List<User> findFriendsOfUserId(String userId);

	/**
	 * Adds the friend and returns the friend user, or returns null if user or friend don't exist
	 */
	User addFriendByEmail(String userId, String friendEmail);
}
