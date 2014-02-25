package com.excilys.formation.angularjs.exception;

import org.jboss.resteasy.spi.DefaultOptionsMethodException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static com.excilys.formation.angularjs.exception.GeneralException.UNKNOWN_ERROR;

@Component
@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger logger = LoggerFactory.getLogger(GeneralExceptionMapper.class);

    @Override
    public Response toResponse(Exception e) {
        if (e instanceof GeneralException)
            return Response.status(400)
                    .entity(new GeneralExceptionDTO((GeneralException) e))
                    .build();

        if (e instanceof DefaultOptionsMethodException) {
            logger.debug("Return 200 on OPTION");
            return Response.status(200).build();
        }

        logger.error("", e);
        return Response.status(500)
                .entity(new GeneralExceptionDTO(UNKNOWN_ERROR))
                .build();
    }
}
