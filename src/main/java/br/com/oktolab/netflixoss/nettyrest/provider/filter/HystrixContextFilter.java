package br.com.oktolab.netflixoss.nettyrest.provider.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

@Provider
public class HystrixContextFilter implements ContainerRequestFilter, ContainerResponseFilter {

	@Override
	public void filter(final ContainerRequestContext requestContext) throws IOException {
//		HystrixRequestContext.initializeContext();
	}

	@Override
	public void filter(final ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		final HystrixRequestContext context = HystrixRequestContext.getContextForCurrentThread();
		if (context != null) {
//			context.shutdown();
		}
	}

}
