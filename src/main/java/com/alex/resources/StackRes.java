package com.alex.resources;

import com.alex.api.Question;
import com.alex.api.stack.StackQuestionResponse;
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
    @Path("questions")
    public List<Question> searchQuestions(@QueryParam("tags") final String tags,
                                          @QueryParam("from") @DefaultValue("0") final int from,
                                          @QueryParam("size") @DefaultValue("19") final int size) {
        return stackService.searchQuestions(tags, from, size);
    }

    @POST
    @Path("questions/{questionIds}")
    public Boolean indexQuestions(@PathParam("questionIds") final String questionIds) {
        return stackService.indexQuestions(questionIds);
    }

    // DEBUG APIs
    @GET
    @Path("exchange/questions/{questionIds}")
    public StackQuestionResponse getStackExchangeQuestion(@PathParam("questionIds") final String questionIds) {
        return stackService.getStackExchangeQuestion(questionIds);
    }
}

