package nl.ikea.warehouse.views.impl;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import nl.ikea.warehouse.views.IView;
import nl.ikea.warehouse.validators.groups.DefaultValidationGroup;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

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
@Builder(builderClassName = "ProductViewBuilder")
@JsonDeserialize(builder = ProductView.ProductViewBuilder.class)
public class ProductView extends ResourceSupport implements Serializable, IView {

    private static final long serialVersionUID = 6443742697019018124L;

    @JsonProperty(value = "productId", required = true)
    @JsonAlias(value = {"product_id", "prod_id"})
    @NotNull(groups = DefaultValidationGroup.class)
    @ApiModelProperty(example = "1", notes = "Product Id")
    private final Long productId;

    @JsonProperty(value = "name", required = true)
    @JsonAlias(value = {"productName", "product_name"})
    @NotNull(groups = DefaultValidationGroup.class)
    @ApiModelProperty(example = "Ikea Round Table", notes = "Product name")
    private final String name;

    @JsonProperty(value = "contain_articles", required = true)
    @JsonAlias(value = {"containArticles", "articles"})
    @NotNull(groups = DefaultValidationGroup.class)
    @ApiModelProperty(notes = "List of available articles")
    private final List<ArticleView> articles;

    /**
     * Builder pattern, used to provide immutability through application layers.
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class ProductViewBuilder {
        // Lombok will add constructor, setters, build method
    }
}
