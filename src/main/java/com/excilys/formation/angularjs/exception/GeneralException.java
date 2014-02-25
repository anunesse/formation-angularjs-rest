package com.excilys.formation.angularjs.exception;

import com.excilys.formation.angularjs.Tweet;

public class GeneralException extends RuntimeException {

    public static final GeneralException UNKNOWN_ERROR = new GeneralException(ErrorCode.UNKNOWN_ERROR);

    public static enum ErrorCode {
        NULL_TWEET(0, "Tweet is null."),
        MESSAGE_TOO_LONG(1, "The message is too long, it should be at most " + Tweet.MESSAGE_MAX_LENGTH + " character long."),
        MESSAGE_EMPTY(2, "The message can't be empty."),
        EMAIL_EMPTY(3, "The email can't be empty."),
        AUTHOR_EMPTY(4, "The author can't be empty."),
        AUTHOR_AROBAS(5, "The author name should start with an @."),
        NAME_TOO_LONG(6, "The author name is too long, it should be at most " + Tweet.NAME_MAX_LENGTH + " character long."),
        NAME_TOO_SHORT(7, "The author name is too short, it should be at least " + Tweet.NAME_MIN_LENGTH + " character long."),
        EMAIL_TOO_LONG(8, "The author email is too long, it should be at most " + Tweet.EMAIL_MAX_LENGTH + " character long."),
        EMAIL_INVALID(9, "The author email is not RFC822 compliant."),
        TWEET_NOT_FOUND(10, "The tweet doesn't exist."),
        UNKNOWN_ERROR(11, "Oops! An error happened.");

        int code;
        String message;

        ErrorCode(int code, String message) {
            this.message = message;
            this.code = code;
        }
    }

    public final ErrorCode errorCode;

    public GeneralException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
