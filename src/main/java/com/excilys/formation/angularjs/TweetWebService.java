package com.excilys.formation.angularjs;

import com.excilys.formation.angularjs.exception.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

import static com.excilys.formation.angularjs.exception.GeneralException.ErrorCode.NULL_TWEET;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Service
@Path("/tweets")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class TweetWebService {

    @Autowired
    private TweetDAO tweetDAO;

    @GET
    @Path("/")
    public List<Tweet> findAll() {
        return tweetDAO.findAll();
    }

    @POST
    @Path("/")
    public Tweet save(Tweet tweet) {
        if (tweet == null) throw new GeneralException(NULL_TWEET);
        return tweetDAO.save(tweet);
    }

    @POST
    @Path("/{tweet}/answers")
    public Tweet answer(@PathParam("tweet") Long tweet, Tweet answer) {
        if (answer == null) throw new GeneralException(NULL_TWEET);
        final Tweet parent = tweetDAO.findById(tweet);
        return tweetDAO.save(answer, parent);
    }

    /**
     * Return the list of tweet in the conversation, no matter which answer
     * is given in the URL.
     * @param tweetId The id of any tweet in the conversation.
     * @return The list of tweets in the conversation.
     */
    @GET
    @Path("/{tweetId}/answers")
    public List<Tweet> findAnswers(@PathParam("tweetId") Long tweetId) {
        Tweet topParent = tweetDAO.findById(tweetId).getTopParent();
        List<Tweet> result = new ArrayList<Tweet>(topParent.answers);
        result.add(topParent);
        return result;
    }
}
