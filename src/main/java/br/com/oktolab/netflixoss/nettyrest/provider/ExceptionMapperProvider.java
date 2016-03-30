package br.com.oktolab.netflixoss.nettyrest.provider;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.oktolab.netflixoss.nettyrest.exception.BusinessException;

import com.google.gson.Gson;

@Provider
public class ExceptionMapperProvider implements ExceptionMapper<Throwable> {
	
	private static final String APPLICATION_RESPONSE_TYPE = "application/json;charset=UTF-8";
	private static final int UNPROCESSABLE_ENTITY = 422;
	private static final Logger LOG = LoggerFactory.getLogger(ExceptionMapperProvider.class);
	private static final String MSG_INTERNAL_SERVER_ERROR = "Internal server error. Message: '%s'";
	private static final Gson gson = new Gson();
	
	@Override
	public Response toResponse(final Throwable ex) {
		final Throwable realCause = getRealCause(ex);
		final int status = buildStatusCode(realCause);
		final String message = buildMessage(realCause);
		logByCause(realCause, message);
		return Response.status(status)
						.entity(gson.toJson(new Message(message)))
						.type(APPLICATION_RESPONSE_TYPE).build();
	}

	private void logByCause(final Throwable realCause, String message) {
		if (realCause instanceof BusinessException
				|| realCause instanceof NotAuthorizedException
				|| realCause instanceof ForbiddenException) {
			LOG.debug(String.format(MSG_INTERNAL_SERVER_ERROR, message), realCause);
		} else {
			LOG.error(String.format(MSG_INTERNAL_SERVER_ERROR, message), realCause);
		}
	}

	private String buildMessage(final Throwable exception) {
		if (exception == null) {
			return null;
		}
//		if (realCause instanceof BusinessException) {
			// TODO
//		}
		return exception.getMessage();
	}

	private int buildStatusCode(final Throwable realCause) {
		if (realCause instanceof NotAuthorizedException) {
			return Response.Status.UNAUTHORIZED.getStatusCode();
		} else if (realCause instanceof ForbiddenException) {
			return Response.Status.FORBIDDEN.getStatusCode();
		} else if (realCause instanceof BusinessException) {
			return UNPROCESSABLE_ENTITY;
		}
		return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
	}

	private Throwable getRealCause(Throwable ex) {
		Throwable parentEx = ex.getCause();
		if (parentEx != null) {
			if (ex == parentEx) {
				return ex;
			}
			return getRealCause(parentEx);
		}
		return ex;
	}
	
	class Message {
		String message;
		
		Message(String message) {
			this.message = message;
		}
	}

}
