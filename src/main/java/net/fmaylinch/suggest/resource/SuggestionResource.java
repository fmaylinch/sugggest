package net.fmaylinch.suggest.resource;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.auth.Auth;
import net.fmaylinch.suggest.model.Suggestion;
import net.fmaylinch.suggest.model.SuggestionDTO;
import net.fmaylinch.suggest.model.User;
import net.fmaylinch.suggest.service.SuggestionService;
import net.fmaylinch.suggest.service.UserService;
import net.fmaylinch.suggest.util.UserCache;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("suggestions")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class SuggestionResource {

	private final SuggestionService suggestionService;
	private final UserService userService;

	@Inject
	public SuggestionResource(SuggestionService suggestionService, UserService userService)
	{
		this.suggestionService = suggestionService;
		this.userService = userService;
	}

	@GET
	public Response findByUserFromOrTo(@Auth User user) {

		final List<Suggestion> suggestions = suggestionService.findByUserFromOrTo(user.getId());

		return Response.ok(convertToDTOs(suggestions)).build();
	}

	@Path("to")
	@GET
	public Response findByUserTo(@Auth User user) {

		final List<Suggestion> suggestions = suggestionService.findByUserTo(user.getId());

		return Response.ok(convertToDTOs(suggestions)).build();
	}

	@Path("from")
	@GET
	public Response findByUserFrom(@Auth User user) {

		final List<Suggestion> suggestions = suggestionService.findByUserFrom(user.getId());

		return Response.ok(convertToDTOs(suggestions)).build();
	}

	@POST
	public Response create(@Auth User user, Suggestion suggestion)
	{
		// TODO: Move these checks to the service

		if (!user.getId().equals(suggestion.getFrom())) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("'from' user is not equal to authenticated user").build();
		}

		if (userService.findById(suggestion.getTo()) == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("'to' user not found").build();
		}

		if (StringUtils.isEmpty(suggestion.getText())) {
			return Response.status(Response.Status.PRECONDITION_FAILED).entity("'text' is empty").build();
		}

		suggestionService.create(suggestion);

		return Response.ok(suggestion).build();
	}


	private List<SuggestionDTO> convertToDTOs(List<Suggestion> suggestions)
	{
		final UserCache userCache = new UserCache(userService);

		final List<SuggestionDTO> suggestionDtos = new ArrayList<SuggestionDTO>();

		for (Suggestion suggestion : suggestions)
		{
			SuggestionDTO dto = convertToDto(suggestion, userCache);

			suggestionDtos.add(dto);
		}
		return suggestionDtos;
	}

	private SuggestionDTO convertToDto(Suggestion suggestion, UserCache userCache)
	{
		SuggestionDTO dto = new SuggestionDTO();
		dto.setText(suggestion.getText());

		dto.setFromId(suggestion.getFrom());
		dto.setFromName(userCache.findById(suggestion.getFrom()).getName());
		dto.setToId(suggestion.getId());
		dto.setToName(userCache.findById(suggestion.getTo()).getName());

		return dto;
	}
}
