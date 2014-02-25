package com.excilys.formation.angularjs.exception;

public class GeneralExceptionDTO {

    public final int code;
    public final String key;
    public final String message;

    public GeneralExceptionDTO(GeneralException generalException) {
        this.code = generalException.errorCode.code;
        this.message = generalException.errorCode.message;
        this.key = generalException.errorCode.name();
    }
}
