package br.com.oktolab.netflixoss.nettyrest.provider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import br.com.oktolab.gson.GSON;

@Produces(MediaType.APPLICATION_JSON)
@Provider
public class RestMessagePostProvider implements MessageBodyWriter<Object>, MessageBodyReader<Object> {

    @Override
    public long getSize(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        // deprecated by JAX-RS 2.0 and ignored by Jersey runtime
        return 0;
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        String json = GSON.getGson().toJson(t);
        entityStream.write(json.getBytes());
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
    	Charset charset = null;
    	if (mediaType != null && mediaType.getParameters() != null
    			&& mediaType.getParameters().containsKey("charset")) {
    		charset = Charsets.toCharset(mediaType.getParameters().get("charset"));
    	} else {
    		charset = Charsets.UTF_8;
    	}
        String json = IOUtils.toString(entityStream, charset);
        if (StringUtils.isNotBlank(json)) {
        	return GSON.getGson().fromJson(json, type);
        }
        return null;
    }

}