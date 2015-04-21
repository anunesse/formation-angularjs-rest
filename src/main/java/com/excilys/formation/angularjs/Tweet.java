package com.excilys.formation.angularjs;

import com.excilys.formation.angularjs.exception.GeneralException;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.excilys.formation.angularjs.exception.GeneralException.ErrorCode.*;
import static com.excilys.formation.angularjs.utils.ValidationUtils.isEmailValid;
import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.hash.Hashing.md5;

public class Tweet {

    public static final int MESSAGE_MAX_LENGTH = 140;
    public static final int NAME_MAX_LENGTH = 30;
    public static final int NAME_MIN_LENGTH = 2;
    public static final int EMAIL_MAX_LENGTH = 60;

    @JsonProperty
    private Long id;

    @JsonProperty
    public final String authorName, message;

    @JsonIgnore
    public final String authorEmail;

    @JsonProperty
    public final String authorEmailHash;

    @JsonProperty
    public final Date date;

    @JsonProperty
    public Integer like;

    @JsonIgnore
    public final List<Tweet> answers;

    @JsonIgnore
    private Tweet parent;

    @JsonCreator
    public Tweet(
            @JsonProperty("authorName") String authorName,
            @JsonProperty("authorEmail") String authorEmail,
            @JsonProperty("message") String message) {
        if (authorName != null && !authorName.startsWith("@")) authorName = "@" + authorName;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.authorEmailHash = isNullOrEmpty(authorEmail) ? ""
                : md5().hashString(authorEmail, UTF_8).toString();
        this.message = message;
        this.date = new Date();
        this.like = 0;
        this.answers = new ArrayList<Tweet>();
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public void setParent(Tweet parent) {
        this.parent = parent;
    }

    @JsonIgnore
    public void addAnswer(Tweet tweet) {
        answers.add(tweet);
    }

    public void validate() throws IllegalArgumentException {

        // Message checks
        if (isNullOrEmpty(message)) throw new GeneralException(MESSAGE_EMPTY);
        if (message.length() > MESSAGE_MAX_LENGTH) throw new GeneralException(MESSAGE_TOO_LONG);

        // Author name checks
        if (isNullOrEmpty(authorName)) throw new GeneralException(AUTHOR_EMPTY);
        if (authorName.length() > NAME_MAX_LENGTH) throw new GeneralException(NAME_TOO_LONG);
        if (authorName.length() < NAME_MIN_LENGTH) throw new GeneralException(NAME_TOO_SHORT);
        if (!authorName.startsWith("@")) throw new GeneralException(AUTHOR_AROBAS);

        // Author email checks
        if (isNullOrEmpty(authorEmail)) throw new GeneralException(EMAIL_EMPTY);
        if (authorEmail.length() > EMAIL_MAX_LENGTH) throw new GeneralException(EMAIL_TOO_LONG);
        if (!isEmailValid(authorEmail)) throw new GeneralException(EMAIL_INVALID);

        // Like checks
        if (like>Integer.MAX_VALUE || like<Integer.MIN_VALUE) throw new GeneralException(TWEET_LIKE_INVALID);
    }

    @JsonIgnore
    /** If this tweet is an answer, return the original tweet */
    public Tweet getTopParent() {
        if (parent != null) return parent.getTopParent();
        else return this;
    }

    @JsonProperty
    public boolean isAnswer() {
        return parent != null;
    }

    @JsonProperty
    public long getAnswers() {
        return answers.size();
    }
}
