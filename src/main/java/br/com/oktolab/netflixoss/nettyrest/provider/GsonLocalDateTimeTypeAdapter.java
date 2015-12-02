package br.com.oktolab.netflixoss.nettyrest.provider;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonLocalDateTimeTypeAdapter implements JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {

	private static final String DATE_PATTERN = "yyyy-MM-dd";
	private static final String DATE_WITH_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
	
	@Override
	public LocalDateTime deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		try { 
	        String jsonStr = json.getAsJsonPrimitive().getAsString();
	        return parseLocalDateTime(jsonStr);
	    } catch (ParseException e) { 
	        throw new JsonParseException(e.getMessage(), e);
	    } 
	}
	
	private LocalDateTime parseLocalDateTime(final String dateString) throws ParseException {
		DateTimeFormatter dateTimeFormat = null;
	    if (dateString != null && dateString.trim().length() > 0) {
	    	if (dateString.contains("T")) {
	    		dateTimeFormat = DateTimeFormatter.ofPattern(DATE_WITH_TIME_PATTERN);
	    	} else {
	    		dateTimeFormat = DateTimeFormatter.ofPattern(DATE_PATTERN);
	    	}
	    	return LocalDateTime.parse(dateString, dateTimeFormat);
	    } else { 
	        return null; 
	    } 
	}

	@Override
	public JsonElement serialize(final LocalDateTime src, final Type typeOfSrc,
			final JsonSerializationContext context) {
		final ZonedDateTime zonedDateTime = ZonedDateTime.of(src, ZoneId.systemDefault());
		final String strDateTime = DateTimeFormatter.ISO_OFFSET_DATE_TIME
									.withZone(ZoneId.of("America/Sao_Paulo"))
									.format(zonedDateTime);
		return new JsonPrimitive(strDateTime);
	}

}
