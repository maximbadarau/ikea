package nl.ikea.warehouse.rest;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class DefaultIntegrationTest<T> {

  static final String BASE_URL = "http://localhost:";

  String endpointUrl;

  @Autowired
  protected TestRestTemplate restTemplate;

  @LocalServerPort
  protected int localPort;

  DefaultIntegrationTest(String endpointUrl) {
    this.endpointUrl = endpointUrl;
  }

  ResponseEntity<T> defaultHttpPostCall(HttpStatus status,
    ParameterizedTypeReference<T> typeReference, T model) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    ResponseEntity<T> response = defaultHttpCall(BASE_URL + this.localPort + this.endpointUrl,
      HttpMethod.POST, headers, status, typeReference, model);
    return response;
  }

  ResponseEntity<T> defaultHttpPostCall(HttpHeaders headers, HttpStatus status,
    ParameterizedTypeReference<T> typeReference, T model) {
    ResponseEntity<T> response = defaultHttpCall(BASE_URL + this.localPort + this.endpointUrl,
      HttpMethod.POST, headers, status, typeReference, model);
    return response;
  }

  ResponseEntity<T> defaultHttpCall(HttpStatus status, HttpMethod httpMethod,
    ParameterizedTypeReference<T> typeReference, T model) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    ResponseEntity<T> response = defaultHttpCall(BASE_URL + this.localPort + this.endpointUrl,
      httpMethod, headers, status, typeReference, model);
    return response;
  }

  ResponseEntity<T> defaultHttpCall(HttpHeaders headers, HttpMethod httpMethod,
    HttpStatus status, ParameterizedTypeReference<T> typeReference, T model) {
    ResponseEntity<T> response = defaultHttpCall(BASE_URL + this.localPort + this.endpointUrl,
      httpMethod, headers, status, typeReference, model);
    return response;
  }

  ResponseEntity<T> defaultHttpCall(String uri, HttpMethod httpMethod,
    HttpHeaders headers, HttpStatus status,
    ParameterizedTypeReference<T> typeReference, T model) {
    HttpEntity<?> entity = new HttpEntity<>(model, headers);
    ResponseEntity<T> response = this.restTemplate
      .exchange(URI.create(uri), httpMethod, entity, typeReference);
    assertEquals(status, response.getStatusCode());
    return response;
  }

  ResponseEntity<List<T>> collectionTypeHttpCall(String uri, HttpMethod httpMethod,
    HttpHeaders headers, HttpStatus status, ParameterizedTypeReference<List<T>> typeReference) {
    HttpEntity<?> entity = new HttpEntity<>(null, headers);
    ResponseEntity<List<T>> response = this.restTemplate
      .exchange(URI.create(uri), httpMethod, entity, typeReference);
    assertEquals(status, response.getStatusCode());
    return response;
  }

  ResponseEntity<List<T>> collectionTypeHttpCall(HttpMethod httpMethod, HttpHeaders headers,
    HttpStatus status, ParameterizedTypeReference<List<T>> typeReference) {
    ResponseEntity<List<T>> response = collectionTypeHttpCall(
      BASE_URL + this.localPort + this.endpointUrl, httpMethod, headers, status, typeReference);
    assertEquals(status, response.getStatusCode());
    return response;
  }

  ResponseEntity<List<T>> collectionTypeHttpCall(HttpStatus status,
    ParameterizedTypeReference<List<T>> typeReference) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    ResponseEntity<List<T>> response = collectionTypeHttpCall(
      BASE_URL + this.localPort + this.endpointUrl, HttpMethod.GET, headers, status, typeReference);
    assertEquals(status, response.getStatusCode());
    return response;
  }

}
