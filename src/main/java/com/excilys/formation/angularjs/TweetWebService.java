package com.excilys.formation.angularjs;

import com.excilys.formation.angularjs.exception.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
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

    @GET
    @Path("/paged")
    public List<Tweet> findAll(@QueryParam("page") int page, @QueryParam("size") int size, @QueryParam("search") String search) {
      if (search == null) search = "";
      if (size == 0) size = 20;
      return tweetDAO.findAllByPage(page, size, search);
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

    @POST
    @Path("/{tweet}/like")
    public Tweet like(@PathParam("tweet") Long tweet) {
      final Tweet parent = tweetDAO.findById(tweet);
      parent.like++;
      return tweetDAO.update(parent);
    }

    @POST
    @Path("/{tweet}/dislike")
    public Tweet dislike(@PathParam("tweet") Long tweet) {
      final Tweet parent = tweetDAO.findById(tweet);
      parent.like--;
      return tweetDAO.update(parent);
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
