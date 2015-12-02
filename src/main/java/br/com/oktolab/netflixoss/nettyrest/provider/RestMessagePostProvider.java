package br.com.oktolab.netflixoss.nettyrest.provider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Produces(MediaType.APPLICATION_JSON)
@Provider
public class RestMessagePostProvider implements MessageBodyWriter<Object>, MessageBodyReader<Object> {

//    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
	private Gson gson = new GsonBuilder()
								.setDateFormat(DATE_FORMAT)
								.registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeTypeAdapter())
								.create();

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
        String json = gson.toJson(t);
        entityStream.write(json.getBytes());
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    // javascript -> encodeURIComponent(JSON.stringify(object_to_be_serialised))
    @Override
    public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        String json = IOUtils.toString(entityStream);
        if (StringUtils.isNotBlank(json)) {
        	return gson.fromJson(json, type);
        }
        return null;
    }

}