package br.com.oktolab.netflixoss.nettyrest.provider;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ExceptionMapperProvider implements ExceptionMapper<Throwable> {
	
	private static final Logger LOG = LoggerFactory.getLogger(ExceptionMapperProvider.class);
	
	@Override
	public Response toResponse(Throwable ex) {
		LOG.debug("Erro na requisição Rest.", ex);
		return Response.status(404).entity(ex.getMessage()).type("text/plain")
				.build();
	}

}
