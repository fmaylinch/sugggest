package net.fmaylinch.suggest.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.fmaylinch.suggest.model.Suggestion;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

import java.util.List;

@Singleton
public class SuggestionServiceImpl implements SuggestionService {

	private final Datastore ds;

	@Inject
	public SuggestionServiceImpl(Datastore ds)
	{
		this.ds = ds;
	}

	@Override
	public void create(Suggestion suggestion)
	{
		final Key<Suggestion> key = ds.save(suggestion);

		suggestion.setId((String) key.getId());
	}

	@Override
	public List<Suggestion> findByUserTo(String userToId)
	{
		return ds.find(Suggestion.class).filter(FIELD_TO, userToId).order("-updated").asList();
	}

	@Override
	public List<Suggestion> findByUserFrom(String userFromId)
	{
		return ds.find(Suggestion.class).filter(FIELD_FROM, userFromId).order("-updated").asList();
	}

	@Override
	public List<Suggestion> findByUserFromOrTo(String userId) {

		final Query<Suggestion> query = ds.find(Suggestion.class);
		query.or(
				query.criteria(FIELD_FROM).equal(userId),
				query.criteria(FIELD_TO).equal(userId));
		return query.order("-updated").asList();
	}
}
