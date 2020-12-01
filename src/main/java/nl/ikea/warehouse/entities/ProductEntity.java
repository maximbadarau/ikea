package nl.ikea.warehouse.entities;

import nl.ikea.warehouse.annotations.CascadeSave;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.querydsl.core.annotations.QueryEntity;

/**
 * Product entity
 */
@QueryEntity
@Document
@CompoundIndexes({
        @CompoundIndex(
                name = "article_index",
                def = "{'articles.articleId' : 1}")})
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class ProductEntity {

    @Id
    private Long productId;
    @Indexed(direction = IndexDirection.ASCENDING)
    @Field(value = "name")
    private String name;

    @DBRef(lazy = true)
    @Field("articles")
    @CascadeSave
    private List<ArticleEntity> articles;

    /**
     * Entity persistence constructor.
     *
     * @param name      {@link String}
     */
    @PersistenceConstructor
    public ProductEntity(
            final String name) {
        this.name = name;
    }

    /**
     * Entity persistence constructor.
     *
     * @param name      {@link String}
     * @param articles  {@link List}
     */
    @PersistenceConstructor
    public ProductEntity(
            final String name,
            final List<ArticleEntity> articles) {
        this.name = name;
        this.articles = articles;
    }
}
