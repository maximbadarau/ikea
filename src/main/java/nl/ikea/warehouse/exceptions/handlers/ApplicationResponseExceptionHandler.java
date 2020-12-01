package nl.ikea.warehouse.exceptions.handlers;

import static nl.ikea.warehouse.exceptions.handlers.ExceptionHandlerHelper.generateErrorPayload;

import nl.ikea.warehouse.exceptions.BadRequestException;
import nl.ikea.warehouse.exceptions.PersistenceException;
import nl.ikea.warehouse.exceptions.ResourceNotFoundException;
import nl.ikea.warehouse.exceptions.ServiceException;
import nl.ikea.warehouse.exceptions.errors.ApplicationCustomError;
import nl.ikea.warehouse.exceptions.errors.ErrorItem;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/** Controller advice designed to handle exception mapping for HTTP status codes. */
@ControllerAdvice
public class ApplicationResponseExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Custom implementation of {@link ResponseEntityExceptionHandler#handleMethodArgumentNotValid}
   *
   * @param ex the exception
   * @param headers the headers to be written to the response
   * @param status the selected response status
   * @param request the current request
   * @return a {@code ResponseEntity} instance
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    if (!ex.getBindingResult().getGlobalErrors().isEmpty()) {
      final ApplicationCustomError applicationCustomError =
          generateErrorPayload(
              ex.getBindingResult().getGlobalErrors(),
              objectError ->
                  new ErrorItem(
                      HttpStatus.BAD_REQUEST.value(),
                      objectError.getCode(),
                      objectError.getDefaultMessage()));
      return handleExceptionInternal(
          ex, applicationCustomError, headers, HttpStatus.BAD_REQUEST, request);
    }
    final ApplicationCustomError applicationCustomError =
        generateErrorPayload(
            ex.getBindingResult().getFieldErrors(),
            objectError ->
                new ErrorItem(
                    HttpStatus.BAD_REQUEST.value(),
                    objectError.getCode(),
                    objectError.getDefaultMessage()));
    return handleExceptionInternal(
        ex, applicationCustomError, headers, HttpStatus.BAD_REQUEST, request);
  }

  /**
   * Custom implementation of {@link ResponseEntityExceptionHandler#handleHttpMessageNotReadable}.
   * Previously used: return super.handleHttpMessageNotReadable(ex, headers, status, request)
   *
   * @param ex the exception
   * @param headers the headers to be written to the response
   * @param status the selected response status
   * @param request the current request
   * @return a {@code ResponseEntity} instance
   */
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    return new ResponseEntity<>(
        new ApplicationCustomError(
            new ErrorItem(HttpStatus.BAD_REQUEST.value(), "MSG_IKEA_400", ex.getMessage())),
        headers,
        HttpStatus.BAD_REQUEST);
  }

  /**
   * Mapping exception to particular HTTP error status code.
   *
   * @param ex {@link ConstraintViolationException} handled exception
   * @param request {@link WebRequest} initialized request
   * @return {@link ResponseEntity} mapping HTTP error status code
   */
  @ExceptionHandler(value = {ConstraintViolationException.class})
  @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
  @ResponseBody
  public ResponseEntity<ApplicationCustomError> handleBadInput(
      ConstraintViolationException ex, WebRequest request) {
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    // To check if it's needed more detailed error message
    // Also applicable for regex: \\b((arg)(?=\\d|\\b))+\\d?\\b
    List<ConstraintViolation<?>> violations =
        ex.getConstraintViolations().stream()
            .filter(
                constraintViolation ->
                    constraintViolation.getPropertyPath() != null
                        && !constraintViolation
                            .getPropertyPath()
                            .toString()
                            .matches("^.*\\.arg\\d{1,3}$"))
            .collect(Collectors.toList());
    final ApplicationCustomError applicationCustomError =
        generateErrorPayload(violations, ExceptionHandlerHelper::getErrorItem);
    return new ResponseEntity<>(applicationCustomError, headers, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  /**
   * Mapping exception to particular HTTP error status code.
   *
   * @param ex {@link PersistenceException} handled exception
   * @param request {@link WebRequest} initialized request
   * @return {@link ResponseEntity} mapping HTTP error status code
   */
  @ExceptionHandler(value = {PersistenceException.class})
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ResponseEntity<ApplicationCustomError> handleBadInput(
      PersistenceException ex, WebRequest request) {
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    return new ResponseEntity<>(
        new ApplicationCustomError(
            new ErrorItem(HttpStatus.BAD_REQUEST.value(), ex.getErrorCode(), ex.getMessage())),
        headers,
        HttpStatus.BAD_REQUEST);
  }

  /**
   * Mapping exception to particular HTTP error status code.
   *
   * @param ex {@link BadRequestException} handled exception
   * @param request {@link WebRequest} initialized request
   * @return {@link ResponseEntity} mapping HTTP error status code
   */
  @ExceptionHandler(value = {BadRequestException.class})
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ResponseEntity<ApplicationCustomError> handleBadInput(
      BadRequestException ex, WebRequest request) {
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    return new ResponseEntity<>(
        new ApplicationCustomError(
            new ErrorItem(HttpStatus.BAD_REQUEST.value(), ex.getErrorCode(), ex.getMessage())),
        headers,
        HttpStatus.BAD_REQUEST);
  }

  /**
   * Mapping exception to particular HTTP error status code.
   *
   * @param ex {@link BadRequestException} handled exception
   * @param request {@link WebRequest} initialized request
   * @return {@link ResponseEntity} mapping HTTP error status code
   */
  @ExceptionHandler(value = {ServiceException.class})
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ResponseEntity<ApplicationCustomError> handleBadInput(
      ServiceException ex, WebRequest request) {
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    return new ResponseEntity<>(
        new ApplicationCustomError(
            new ErrorItem(HttpStatus.BAD_REQUEST.value(), ex.getErrorCode(), ex.getMessage())),
        headers,
        HttpStatus.BAD_REQUEST);
  }

  /**
   * Mapping NOT_FOUND resource/handler exception.
   *
   * @param ex {@link ResourceNotFoundException} handled exception
   * @param request {@link WebRequest} initialized request
   * @return {@link ResponseEntity} mapping HTTP error status code
   */
  @ExceptionHandler(value = {ResourceNotFoundException.class})
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ResponseBody
  public ResponseEntity<ApplicationCustomError> handleResourceNotFound(
      ResourceNotFoundException ex, WebRequest request) {
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    return new ResponseEntity<>(
        new ApplicationCustomError(
            new ErrorItem(HttpStatus.NOT_FOUND.value(), "MSG_IKEA_404", ex.getMessage())),
        headers,
        HttpStatus.NOT_FOUND);
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    final ApplicationCustomError applicationCustomError =
        new ApplicationCustomError(
            new ErrorItem(HttpStatus.NOT_FOUND.value(), "MSG_IKEA_404", ex.getMessage()));
    return handleExceptionInternal(
        ex, applicationCustomError, headers, HttpStatus.NOT_FOUND, request);
  }
}
