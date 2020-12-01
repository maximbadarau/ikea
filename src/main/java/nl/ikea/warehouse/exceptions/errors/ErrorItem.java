package nl.ikea.warehouse.exceptions.errors;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/** Error item representation contract. */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@ApiModel
public class ErrorItem {

  @ApiModelProperty(notes = "HTTP status code.")
  private final Integer status;

  @ApiModelProperty(notes = "Application internal error code.")
  private final String code;

  @ApiModelProperty(notes = "Error message.")
  private final String message;
}
