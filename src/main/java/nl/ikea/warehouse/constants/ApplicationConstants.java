package nl.ikea.warehouse.constants;

import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** Application constants */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationConstants {

  public static final String POST_CODE_PATTERN = "";
  public static final String LOCAL_DATE_PATTERN = "yyyy/MM/dd";
  public static final String DOCUMENT_LOCAL_DATE_PATTERN = "dd/MM/yyyy";
  public static final DateTimeFormatter DATE_TIME_FORMATTER;

  static {
    DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DOCUMENT_LOCAL_DATE_PATTERN);
  }
}
