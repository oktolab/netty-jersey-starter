package br.com.oktolab.netflixoss.nettyrest.provider;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import br.com.oktolab.netflixoss.nettyrest.exception.BusinessException;

@Provider
public class ExceptionMapperProvider implements ExceptionMapper<Throwable> {
	
	private static final int UNPROCESSABLE_ENTITY = 422;
	
	private static final Logger LOG = LoggerFactory.getLogger(ExceptionMapperProvider.class);
	
	private static final String RESPONSE_ERROR_TYPE = "text/plain";
	private static final String MSG_INTERNAL_SERVER_ERROR = "Internal server error.";
	
	private static final Gson gson = new Gson();
	
	@Override
	public Response toResponse(final Throwable ex) {
		final Throwable realCause = getRealCause(ex);
		LOG.debug(MSG_INTERNAL_SERVER_ERROR, realCause);
		final int status = buildStatusCode(realCause);
		final String message = realCause.getMessage();
		return Response.status(status).entity(gson.toJson(new Message(message)))
					.type(RESPONSE_ERROR_TYPE).build();
	}

	private int buildStatusCode(final Throwable realCause) {
		if (realCause instanceof NotAuthorizedException) {
			return Response.Status.UNAUTHORIZED.getStatusCode();
		} else if (realCause instanceof BusinessException) {
			return UNPROCESSABLE_ENTITY;
		}
		return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
	}

	private Throwable getRealCause(Throwable ex) {
		return ex.getCause() != null ? getRealCause(ex.getCause()) : ex;
	}
	
	class Message {
		String message;
		
		Message(String message) {
			this.message = message;
		}
	}

}
