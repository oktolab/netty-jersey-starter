package br.com.oktolab.netflixoss.nettyrest;

import netflix.karyon.Karyon;
import netflix.karyon.KaryonServer;
import netflix.karyon.ShutdownModule;
import netflix.karyon.servo.KaryonServoModule;

import org.apache.commons.lang.ArrayUtils;

import br.com.oktolab.netflixoss.nettyrest.module.KaryonJerseyModuleImpl;

import com.google.inject.Module;
import com.netflix.config.DynamicPropertyFactory;

public class ApplicationBootstrap {
	
	public static KaryonServer run(final Class<?> applicationClazz) {
		return run(applicationClazz, new Module[]{});
	}
	
	public static KaryonServer run(final Class<?> applicationClazz, final Module... modules) {
		System.setProperty(DynamicPropertyFactory.ENABLE_JMX, "true");
		final Module[] modulesConcat = getModules(modules);
		KaryonServer karyonServer = Karyon.forApplication(applicationClazz, modulesConcat);
		karyonServer.start();
		return karyonServer;
	}
	
	public static void runAndWaitTillShutdown(final Class<?> applicationClazz) {
		run(applicationClazz, new Module[]{}).waitTillShutdown();
	}
	
	public static void runAndWaitTillShutdown(final Class<?> applicationClazz, final Module... modules) {
		run(applicationClazz, modules).waitTillShutdown();
	}

	private static Module[] getModules(final Module[] modules) {
		final Module[] defaultModules = defaultModules();
		final Module[] modulesConcat;
		if (modules == null) {
			modulesConcat = defaultModules;
		} else {
			modulesConcat = (Module[]) ArrayUtils.addAll(modules, defaultModules);
		}
		return modulesConcat;
	}
	
	/**
	 * Other options:
	 *  new KaryonWebAdminModule(), new KaryonEurekaModule()
	 * @return
	 */
	protected static Module[] defaultModules() {
		return new Module[]{
				new ShutdownModule(),
				new KaryonJerseyModuleImpl(),
		        new KaryonServoModule()};
	}
	
}
