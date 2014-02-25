package com.excilys.formation.angularjs;

import com.excilys.formation.angularjs.exception.GeneralException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.excilys.formation.angularjs.exception.GeneralException.ErrorCode.TWEET_NOT_FOUND;

@Service
public class TweetDAO {

    private final List<Tweet> tweets = new ArrayList<Tweet>();

    private static long count = 0;

    public List<Tweet> findAll() {
        return tweets;
    }

    /**
     * Save the given tweet, set its id first. Thread-safe.
     * @return The created tweet.
     */
    public Tweet save(Tweet tweet) {

        // Validate the tweet
        tweet.validate();

        // Set its id
        synchronized (this) {
            tweet.setId(count++);
        }

        // Save it
        tweets.add(tweet);
        return tweet;
    }

    /**
     * Save the given tweet as an answer.
     * @param tweet  The new tweet.
     * @param parent The tweet that has been answered.
     * @return The new tweet with an id.
     */
    public Tweet save(Tweet tweet, Tweet parent) {

        // Validate the tweet
        tweet.validate();

        // Make sure it's not answering an answer
        // by setting parent to the top parent
        Tweet topParent = parent.getTopParent();
        tweet.setParent(topParent);

        // Set its id
        synchronized (this) {
            tweet.setId(count++);
        }

        // Add it in the list of tweets and in answer list
        tweets.add(tweet);
        topParent.addAnswer(tweet);

        return tweet;
    }

    /**
     * Find a tweet by its id.
     * @throws GeneralException if the tweet doesn't exist.
     */
    public Tweet findById(Long tweetId) {
        for (Tweet tweet : tweets)
            if (tweet.getId().equals(tweetId))
                return tweet;
        throw new GeneralException(TWEET_NOT_FOUND);
    }
}
