package nl.ikea.warehouse.exceptions.errors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.ImmutableList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/** Custom representation for error data information. */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@EqualsAndHashCode
@ToString
@ApiModel()
public class ApplicationCustomError {

  @ApiModelProperty(notes = "List of application errors.")
  private List<ErrorItem> errors;

  /**
   * Constructor
   *
   * @param errors {{@link ErrorItem}}
   */
  public ApplicationCustomError(ErrorItem... errors) {
    this.errors =
        populateErrorList(
            Arrays.stream(errors).filter(Objects::nonNull).collect(Collectors.toList()));
  }

  /**
   * Constructor
   *
   * @param errors {{@link ErrorItem}}
   */
  public ApplicationCustomError(List<ErrorItem> errors) {
    this.errors = populateErrorList(errors);
  }

  private List<ErrorItem> populateErrorList(List<ErrorItem> errorItems) {
    if (errorItems == null || errorItems.isEmpty()) {
      return ImmutableList.<ErrorItem>builder().build();
    }
    return ImmutableList.<ErrorItem>builder()
        .addAll(errorItems.stream().filter(Objects::nonNull).collect(Collectors.toList()))
        .build();
  }
}
