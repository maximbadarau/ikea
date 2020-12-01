package nl.ikea.warehouse.views.impl;

import com.fasterxml.jackson.annotation.JsonAlias;
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
@Builder(builderClassName = "ArticleViewBuilder")
@JsonDeserialize(builder = ArticleView.ArticleViewBuilder.class)
public class ArticleView extends ResourceSupport implements Serializable, IView {

    private static final long serialVersionUID = -2041821262992260787L;

    @JsonProperty(value = "art_id", required = true)
    @JsonAlias(value = {"articleId", "article_id", "artId"})
    @NotNull(groups = DefaultValidationGroup.class)
    @ApiModelProperty(example = "1", notes = "Article ID")
    private final Long articleId;

    @JsonProperty(value = "amount_of", required = true)
    @JsonAlias(value = {"amount", "amountOf"})
    @NotNull(groups = DefaultValidationGroup.class)
    @ApiModelProperty(example = "4", notes = "Amount of current article")
    private final Long amount;

    /**
     * Builder pattern, used to provide immutability through application layers.
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class ArticleViewBuilder {
        // Lombok will add constructor, setters, build method
    }
}
