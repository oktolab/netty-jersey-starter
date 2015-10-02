package br.com.oktolab.netflixoss.nettyrest.provider;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ExceptionMapperProvider implements ExceptionMapper<Throwable> {
	
	private static final Logger LOG = LoggerFactory.getLogger(ExceptionMapperProvider.class);
	private static final String RESPONSE_ERROR_TYPE = "text/plain";
	private static final String MSG_INTERNAL_SERVER_ERROR = "Internal server error.";
	
	@Override
	public Response toResponse(final Throwable ex) {
		final Throwable realCause = getRealCause(ex);
		LOG.debug(MSG_INTERNAL_SERVER_ERROR, realCause);
		if (realCause instanceof NotAuthorizedException) {
			return Response.status(401).entity(realCause.getMessage()).type(RESPONSE_ERROR_TYPE)
					.build();
		}
		return Response.status(500).entity(realCause.getMessage()).type(RESPONSE_ERROR_TYPE)
				.build();
	}

	private Throwable getRealCause(Throwable ex) {
		return ex.getCause() != null ? getRealCause(ex.getCause()) : ex;
	}

}
