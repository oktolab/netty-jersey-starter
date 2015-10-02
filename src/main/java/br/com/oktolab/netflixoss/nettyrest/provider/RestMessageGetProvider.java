package br.com.oktolab.netflixoss.nettyrest.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.oktolab.netflixoss.nettyrest.provider.annotation.BeanParam;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.sun.jersey.api.model.Parameter;
//import com.sun.jersey.core.spi.component.ComponentContext;
//import com.sun.jersey.core.spi.component.ComponentScope;
//import com.sun.jersey.spi.container.WebApplication;
//import com.sun.jersey.spi.inject.Injectable;
//import com.sun.jersey.spi.inject.InjectableProvider;

//@Provider
public class RestMessageGetProvider {} /*implements InjectableProvider<BeanParam, Parameter> {
	
	private static final String MSG_ERROR_QUERY_PARAMS = "Erro ao tentar converter QueryParameters na classe '%s'. Params: '%s'.";

	private static final Logger LOG = LoggerFactory.getLogger(RestMessageGetProvider.class);

	@javax.ws.rs.core.Context
    private UriInfo uriInfo;
	WebApplication
	
	@Override
	public ComponentScope getScope() {
		return ComponentScope.PerRequest;
	}
	
	@Override
	public Injectable<Object> getInjectable(ComponentContext ic, BeanParam annotation, Parameter param) {
        return new QueryParamsInjectable(uriInfo, param);
	}
	
	static class QueryParamsInjectable implements Injectable<Object> {
		
		private static Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		
		private UriInfo uriInfo;
		private Parameter param;
		
		public QueryParamsInjectable(UriInfo uriInfo, Parameter param) {
			this.uriInfo = uriInfo;
			this.param = param;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Object getValue() {
        	Object instance = null;
        	Class<?> parameterClass = param.getParameterClass();
        	MultivaluedMap<String, String> params = uriInfo.getQueryParameters();
        	Map<String, Object> mapToJson = new HashMap<String, Object>();
        	try {
        		for (Entry<String, List<String>> param : params.entrySet()) {
        			String key = param.getKey();
        			Object value = param.getValue().iterator().next();
        			if (mapToJson.containsKey(key)) {
        				Object valueAtMap = mapToJson.get(key);
        				if (valueAtMap instanceof List) {
        					((List)valueAtMap).add(value);
        				} else {
        					ArrayList<Object> listToJson = new ArrayList<Object>();
        					listToJson.add(mapToJson.get(key));
        					listToJson.add(value);
        					mapToJson.put(key, listToJson);
        				}
        			} else {
        				mapToJson.put(key, value);
        			}
        		}
        		String json = gson.toJson(mapToJson);
        		instance = gson.fromJson(json, parameterClass);
        	} catch (Exception e) {
        		RestMessageGetProvider.LOG.error(String.format(MSG_ERROR_QUERY_PARAMS, parameterClass, params));
        	}
            return instance;
        }
	}

}
*/