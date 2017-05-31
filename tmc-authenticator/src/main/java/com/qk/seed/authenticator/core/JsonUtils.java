package com.qk.seed.authenticator.core;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr353.JSR353Module;

public class JsonUtils {
	private static ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.registerModule(new JSR353Module());
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	public static <T> JsonObject convertToJsonObject(T val) {
		return mapper.convertValue(val, JsonObject.class);
	}
	public static JsonObjectBuilder createObjectBuilderFromString(String json) throws JsonParseException, JsonMappingException, IOException {
		JsonObject jsonObj = mapper.readValue(json, JsonObject.class);
		JsonObjectBuilder builder = Json.createObjectBuilder();
		for (String k: jsonObj.keySet()) {
			builder.add(k, jsonObj.get(k));
		}
		return builder;
	}
	public static <V, T> T convertToBean(V val, Class<T> clz) {
		return mapper.convertValue(val, clz);
	}
	public static <T> String convertToJson(T val) {
		try {
			return mapper.writeValueAsString(val);
		} catch (JsonProcessingException e) {
			return "{}";
		}
	}	
	
}
