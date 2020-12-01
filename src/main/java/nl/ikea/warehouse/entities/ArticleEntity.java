package nl.ikea.warehouse.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Article entity.
 */
@Document
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ArticleEntity {

    @Id
    private Long articleId;
    @Indexed(direction = IndexDirection.ASCENDING)
    @Field(value = "name")
    private String name;
    private Long amount;
    private Long productId;

    @PersistenceConstructor
    public ArticleEntity(Long articleId, String name, Long amount, Long productId) {
        this.articleId = articleId;
        this.name = name;
        this.amount = amount;
        this.productId = productId;
    }

    @PersistenceConstructor
    public ArticleEntity(Long articleId, String name, Long amount) {
        this.articleId = articleId;
        this.name = name;
        this.amount = amount;
    }

}
