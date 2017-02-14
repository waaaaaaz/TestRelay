package org.testrelay.http;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.testrelay.common.Constants;
import org.testrelay.domain.TestCase;

public class HttpRequest implements RequestInterface{

	private TestCase testcase;
	private HttpMethod method;
	
	public HttpRequest(TestCase testcase) {
		this.testcase = testcase;
		this.method = Method.getHttpMethod(testcase.getInput().getMethod());
	}

	private HttpHeaders getRequestHeader() {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "*/*");
		headers.add("Content-Type", "application/json; charset=UTF-8");
		return headers;

	}

	private String getRequestBody() {

		if (testcase.getInput().getType().equalsIgnoreCase("json")) {

			File bodyFile = new File(Constants.TEST_CASE_BASE_LOCATION + "/"
					+ testcase.getInput().getLocation());
			ObjectMapper objMapper = new ObjectMapper();
			JsonNode rootNode = null;
			try {
				rootNode = objMapper.readTree(bodyFile);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return rootNode.toString();
		} else {
			return null;
		}
	}
	
	public HttpEntity<String> getRequestEntity() {
		switch(method) {
		case GET:
			return new HttpEntity<String>(null, getRequestHeader());
		case POST:
			return new HttpEntity<String>(getRequestBody(), getRequestHeader());
		default:
			throw new IllegalArgumentException();
		}
	}
	
	public ResponseEntity<String> execute() {
		String url = testcase.getInput().getUrl();
		RestTemplate restTemplate = new RestTemplate();
		SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) restTemplate
				.getRequestFactory();
		factory.setConnectTimeout(Constants.HTTP_REQUEST_CONNECTION_TIMEOUT);
		factory.setReadTimeout(Constants.HTTP_REQUEST_READ_TIMEOUT);
		restTemplate.getMessageConverters().add(0,
				new StringHttpMessageConverter(Charset.forName("UTF-8")));
		HttpEntity<String> httpEntity = getRequestEntity();
		return restTemplate.exchange(url, method, httpEntity, String.class);
	}
	
	}
