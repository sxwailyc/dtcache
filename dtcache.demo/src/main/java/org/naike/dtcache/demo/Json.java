package org.naike.dtcache.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

public class Json {

	private static ObjectMapper mapper = new ObjectMapper(); // can reuse, share
	private static ObjectMapper mapperIgnoreUnknownField = new ObjectMapper(); // 忽略不存在的字段.
	private static final Log logger = LogFactory.getLog(Json.class);

	static {
		mapperIgnoreUnknownField.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		return toJson(obj, mapper);
	}

	/**
	 * 
	 * @param obj
	 * @param mapper
	 * @return
	 */
	public static String toJson(Object obj, ObjectMapper mapper) {

		try {
			String json;
			if (obj == null) {
				json = null;
			} else {
				json = mapper.writeValueAsString(obj);
			}
			return json;
		} catch (Exception e) {
			logger.warn(e.getMessage());
			throw new RuntimeException();
		}
	}

	/**
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T toObject(String json, Class<T> clazz) {
		return toObject(json, clazz, false);
	}

	/**
	 * 
	 * @param json
	 * @param clazz
	 * @param ignoreUnknownField
	 * @return
	 */
	public static <T> T toObject(String json, Class<T> clazz, boolean ignoreUnknownField) {
		try {
			if (json == null || json.length() == 0) {
				return null;
			} else {
				if (ignoreUnknownField) {
					return mapperIgnoreUnknownField.readValue(json, clazz);
				} else {
					return mapper.readValue(json, clazz);
				}
			}
		} catch (Exception e) {
			logger.warn("message:" + e.getMessage() + " json:" + json);
			throw new RuntimeException();
		}

	}

	/**
	 * 
	 * @param writer
	 * @param obj
	 * @param fullMethodName
	 * @return
	 */
	protected static String toJson(ObjectWriter writer, Object obj, String fullMethodName) {
		try {
			if (obj == null) {
				return null;
			} else {
				return writer.writeValueAsString(obj);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
			throw new RuntimeException();
		}
	}
}
