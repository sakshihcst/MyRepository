package com.searshc.mpuwebservice.util;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestClientException;

public class RestTemplate  extends org.springframework.web.client.RestTemplate {

	private static Logger logger = Logger.getLogger(RestTemplate.class.getName());
	// DELETE

	/**
	 * @param <T>
	 * @param url
	 * @param request
	 * @param responseType
	 * @return
	 * @throws RestClientException
	 */
	public <T> T deleteForObject(String url, Object request, Class<T> responseType) throws RestClientException {
		HttpEntityRequestCallback requestCallback = new HttpEntityRequestCallback(
				request, responseType);
		HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<T>(
				responseType, getMessageConverters());
		return execute(url, HttpMethod.DELETE, requestCallback, responseExtractor);
	}
	
	/**
	 * @param <T>
	 * @param url
	 * @param request
	 * @param responseType
	 * @param urlVariables
	 * @return
	 * @throws RestClientException
	 */
	public <T> T delete(String url, Object request, Class<T> responseType, Object... urlVariables) throws RestClientException {
		HttpEntityRequestCallback requestCallback = new HttpEntityRequestCallback(
				request, responseType);
		HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<T>(
				responseType, getMessageConverters());
		return execute(url, HttpMethod.DELETE, requestCallback, responseExtractor, urlVariables);
	}

	/**
	 * @param <T>
	 * @param url
	 * @param request
	 * @param responseType
	 * @param urlVariables
	 * @return
	 * @throws RestClientException
	 */
	public <T> T delete(String url, Object request, Class<T> responseType, Map<String, ?> urlVariables) throws RestClientException {
		HttpEntityRequestCallback requestCallback = new HttpEntityRequestCallback(
				request, responseType);
		HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<T>(
				responseType, getMessageConverters());
		return execute(url, HttpMethod.DELETE, requestCallback, responseExtractor, urlVariables);
	}

	/**
	 * @param <T>
	 * @param url
	 * @param request
	 * @param responseType
	 * @return
	 * @throws RestClientException
	 */
	public <T> T delete(URI url, Object request, Class<T> responseType) throws RestClientException {
		HttpEntityRequestCallback requestCallback = new HttpEntityRequestCallback(
				request, responseType);
		HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<T>(
				responseType, getMessageConverters());
		return execute(url, HttpMethod.DELETE, requestCallback, responseExtractor);
	}
	
	
	// PUT
	/**
	 * @param <T>
	 * @param url
	 * @param request
	 * @param responseType
	 * @param urlVariables
	 * @return
	 * @throws RestClientException
	 */
	public <T> T put(String url, Object request, Class<T> responseType, Object... urlVariables) throws RestClientException {
		HttpEntityRequestCallback requestCallback = new HttpEntityRequestCallback(request);
		HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<T>(
				responseType, getMessageConverters());
		return execute(url, HttpMethod.PUT, requestCallback, responseExtractor, urlVariables);
	}

	/**
	 * @param <T>
	 * @param url
	 * @param request
	 * @param responseType
	 * @param urlVariables
	 * @return
	 * @throws RestClientException
	 */
	public <T> T put(String url, Object request, Class<T> responseType,  Map<String, ?> urlVariables) throws RestClientException {
		HttpEntityRequestCallback requestCallback = new HttpEntityRequestCallback(request);
		HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<T>(
				responseType, getMessageConverters());
		return execute(url, HttpMethod.PUT, requestCallback, responseExtractor, urlVariables);
	}

	/**
	 * @param <T>
	 * @param url
	 * @param request
	 * @param responseType
	 * @return
	 * @throws RestClientException
	 */
	public <T> T put(URI url, Object request, Class<T> responseType) throws RestClientException {
		HttpEntityRequestCallback requestCallback = new HttpEntityRequestCallback(request);
		HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<T>(
				responseType, getMessageConverters());
		return execute(url, HttpMethod.PUT, requestCallback, responseExtractor);
	}
	
	
	/**
	 * Request callback implementation that prepares the request's accept
	 * headers.
	 * 
	 * MJH - This method is private in the parent class so we needed to copy
	 * the code here
	 */
	protected class AcceptHeaderRequestCallback implements RequestCallback {

		private final Class<?> responseType;

		private AcceptHeaderRequestCallback(Class<?> responseType) {
			this.responseType = responseType;
		}

		public void doWithRequest(ClientHttpRequest request) throws IOException {
			if (responseType != null) {
				List<MediaType> allSupportedMediaTypes = new ArrayList<MediaType>();
				for (HttpMessageConverter<?> messageConverter : getMessageConverters()) {
					if (messageConverter.canRead(responseType, null)) {
						List<MediaType> supportedMediaTypes = messageConverter
								.getSupportedMediaTypes();
						for (MediaType supportedMediaType : supportedMediaTypes) {
							if (supportedMediaType.getCharSet() != null) {
								supportedMediaType = new MediaType(
										supportedMediaType.getType(),
										supportedMediaType.getSubtype());
							}
							allSupportedMediaTypes.add(supportedMediaType);
						}
					}
				}
				if (!allSupportedMediaTypes.isEmpty()) {
					MediaType.sortBySpecificity(allSupportedMediaTypes);
					if (logger.isDebugEnabled()) {
						logger.debug("Setting request Accept header to "
								+ allSupportedMediaTypes);
					}
					request.getHeaders().setAccept(allSupportedMediaTypes);
				}
			}
		}
	}

	/**
	 * Request callback implementation that writes the given object to the
	 * request stream.
	 * 
	 * MJH - This method is private in the parent class so we needed to copy
	 * the code here
	 */
	protected class HttpEntityRequestCallback extends AcceptHeaderRequestCallback {

		@SuppressWarnings("unchecked")
		private final HttpEntity requestEntity;

		private HttpEntityRequestCallback(Object requestBody) {
			this(requestBody, null);
		}

		@SuppressWarnings("unchecked")
		private HttpEntityRequestCallback(Object requestBody,
				Class<?> responseType) {
			super(responseType);
			if (requestBody instanceof HttpEntity) {
				this.requestEntity = (HttpEntity) requestBody;
			} else if (requestBody != null) {
				this.requestEntity = new HttpEntity(requestBody);
			} else {
				this.requestEntity = HttpEntity.EMPTY;
			}
		}

		@Override
		@SuppressWarnings("unchecked")
		public void doWithRequest(ClientHttpRequest httpRequest)
				throws IOException {
			super.doWithRequest(httpRequest);
			if (!requestEntity.hasBody()) {
				HttpHeaders httpHeaders = httpRequest.getHeaders();
				HttpHeaders requestHeaders = requestEntity.getHeaders();
				if (!requestHeaders.isEmpty()) {
					httpHeaders.putAll(requestHeaders);
				}
				if (httpHeaders.getContentLength() == -1) {
					httpHeaders.setContentLength(0L);
				}
			} else {
				Object requestBody = requestEntity.getBody();
				Class<?> requestType = requestBody.getClass();
				HttpHeaders requestHeaders = requestEntity.getHeaders();
				MediaType requestContentType = requestHeaders.getContentType();
				for (HttpMessageConverter messageConverter : getMessageConverters()) {
					if (messageConverter.canWrite(requestType,
							requestContentType)) {
						if (!requestHeaders.isEmpty()) {
							httpRequest.getHeaders().putAll(requestHeaders);
						}
						if (logger.isDebugEnabled()) {
							if (requestContentType != null) {
								logger
										.debug("Writing [" + requestBody
												+ "] as \""
												+ requestContentType
												+ "\" using ["
												+ messageConverter + "]");
							} else {
								logger.debug("Writing [" + requestBody
										+ "] using [" + messageConverter + "]");
							}

						}
						messageConverter.write(requestBody, requestContentType,
								httpRequest);
						return;
					}
				}
				String message = "Could not write request: no suitable HttpMessageConverter found for request type ["
						+ requestType.getName() + "]";
				if (requestContentType != null) {
					message += " and content type [" + requestContentType + "]";
				}
				throw new RestClientException(message);
			}
		}
	}
}
