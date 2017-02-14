package org.testrelay.http;

import org.springframework.http.HttpMethod;


public enum Method {

	GET("GET"), POST("POST");

	private String method;

	Method(String httpMethod) {
		this.method = httpMethod;
	}

	@Override
	public String toString() {
		return method;
	}

	public static HttpMethod getHttpMethod(String httpMethodString) {
		switch (getEnum(httpMethodString)) {
		case GET:
			return HttpMethod.GET;
		case POST:
			return HttpMethod.POST;
		default:
			throw new IllegalArgumentException();
		}
	}

	private static Method getEnum(String httpMethodString) {
		if (httpMethodString == null) {
			throw new IllegalArgumentException();
		}
		for (Method httpMethod : values()) {
			if (httpMethod.toString().equalsIgnoreCase(httpMethodString)) {
				return httpMethod;
			}
		}
		throw new IllegalArgumentException();
	}

}