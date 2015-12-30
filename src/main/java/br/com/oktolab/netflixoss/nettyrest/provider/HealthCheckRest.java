package br.com.oktolab.netflixoss.nettyrest.provider;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/health-check")
public class HealthCheckRest {

	@GET
	public String healthCheck() {
		return "OK";
	}
}
