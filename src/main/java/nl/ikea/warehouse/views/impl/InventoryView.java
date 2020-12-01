package nl.ikea.warehouse.views.impl;


import com.fasterxml.jackson.annotation.*;
import nl.ikea.warehouse.views.IView;
import nl.ikea.warehouse.views.RenderGroupView;
import nl.ikea.warehouse.validators.groups.DefaultValidationGroup;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.hateoas.ResourceSupport;

/**
 * Data model describing objects server records listing. Implementation of {@link IView} for
 * definition standards. Extending {@link ResourceSupport} for HAL.
 *
 * @see IView
 * @see ResourceSupport
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "InventoryViewBuilder")
@JsonDeserialize(builder = InventoryView.InventoryViewBuilder.class)
public class InventoryView extends ResourceSupport implements Serializable, IView {

  private static final long serialVersionUID = 9063659380754834377L;

  @JsonProperty(value = "art_id", required = true)
  @JsonAlias(value = {"articleId", "article_id", "artId"})
  @NotNull(groups = DefaultValidationGroup.class)
  @ApiModelProperty(example = "1", notes = "Article ID")
  @JsonView(value = {RenderGroupView.Public.class, RenderGroupView.Restricted.class})
  private final Long articleId;

  @JsonProperty(value = "name", required = true)
  @JsonAlias(value = {"articleName", "article_name"})
  @NotNull(groups = DefaultValidationGroup.class)
  @ApiModelProperty(example = "Leg", notes = "Article name")
  private final String name;

  @JsonProperty(value = "stock", required = true)
  @JsonAlias(value = {"articleStock", "article_stock"})
  @NotNull(groups = DefaultValidationGroup.class)
  @ApiModelProperty(example = "12", notes = "Article stock")
  @JsonView(value = {RenderGroupView.Public.class, RenderGroupView.Restricted.class})
  private final Long stock;

  /** Builder pattern, used to provide immutability through application layers. */
  @JsonPOJOBuilder(withPrefix = "")
  public static class InventoryViewBuilder {
    // Lombok will add constructor, setters, build method

  }
}
