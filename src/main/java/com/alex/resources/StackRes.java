package com.alex.resources;

import com.alex.api.Question;
import com.alex.service.StackService;
import lombok.AllArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("stack")
@AllArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
public class StackRes {

    private StackService stackService;

    @GET
    public List<Question> search(@QueryParam("tags") final String tags,
                                 @QueryParam("from") @DefaultValue("0") final int from,
                                 @QueryParam("size") @DefaultValue("19") final int size) {
        return stackService.search(tags, from, size);
    }

    @GET
    @Path("{questionId}")
    public Question getQuestion(@PathParam("transactionId") final Integer questionId) {
        return stackService.getQuestion(questionId);
    }
}

