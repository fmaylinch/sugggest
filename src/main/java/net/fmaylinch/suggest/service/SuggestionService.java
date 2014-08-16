package net.fmaylinch.suggest.service;

import net.fmaylinch.suggest.model.Suggestion;

import java.util.List;

public interface SuggestionService
{
	public static final String FIELD_TEXT = "text";
	public static final String FIELD_FROM = "from";
	public static final String FIELD_TO = "to";

	/** Creates the suggestion and sets the generated id */
	void create(Suggestion suggestion);

	/** Get suggestions by "target" user, sorted by updated date (newest first). */
	List<Suggestion> findByUserTo(String userToId);

	/** Get suggestions by "target" user, sorted by updated date (newest first). */
	List<Suggestion> findByUserFrom(String userFromId);
}
