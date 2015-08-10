package br.com.oktolab.netflixoss.nettyrest.module;

import static com.netflix.config.ConfigurationManager.getConfigInstance;
import netflix.karyon.jersey.blocking.KaryonJerseyModule;
import br.com.oktolab.netflixoss.nettyrest.ConfigurationConstants;

public class KaryonJerseyModuleImpl extends KaryonJerseyModule {
	
//	private static final Logger LOG = LoggerFactory.getLogger(KaryonJerseyModuleImpl.class);
	
	@Override
	protected void configureServer() {
		final int jerseyPort = getConfigInstance().getInt(ConfigurationConstants.KEY_JERSEY_PORT, 8082);
		final int jerseyPoolSize = getConfigInstance().getInt(ConfigurationConstants.KEY_JERSEY_THREAD_POOL_SIZE, 100);
		HttpServerConfigBuilder serverConfig = server();
		serverConfig.port(jerseyPort).threadPoolSize(jerseyPoolSize);
	}
	
}
