package nl.ikea.warehouse.controllers;

import io.swagger.annotations.*;
import nl.ikea.warehouse.exceptions.errors.ApplicationCustomError;
import nl.ikea.warehouse.validators.groups.PersistValidationGroup;
import nl.ikea.warehouse.views.impl.ArticleView;
import nl.ikea.warehouse.views.impl.ProductView;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Article Controller contract definition.
 */
@Validated
@RequestMapping(value = "/products/{productId}")
@Api(value = "Product Articles Controller")
public interface IArticleController {

    /**
     * Delete all entries of {@link nl.ikea.warehouse.views.impl.ArticleView}
     *
     * @param productId - {@link String} representation of {@link ProductView} unique identifier
     * @param articleId - {@link String} representation of {@link nl.ikea.warehouse.views.impl.ArticleView} unique identifier
     * @return {@link String} page representation
     */
    @ApiOperation(
            value = "Delete all entries from database.",
            notes = "Recursively deleting entries from database.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 204, message = "Deleted."),
                    @ApiResponse(code = 404, message = "Not found."),
                    @ApiResponse(code = 415, message = "The content type is unsupported"),
                    @ApiResponse(
                            code = 500,
                            message =
                                    "An unexpected error has occurred. The error has been logged and is being investigated.")
            })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/articles/{articleId}")
    ResponseEntity<Void> delete(
            @ApiParam(value = "Product unique identifier.") @PathVariable(value = "productId")
            @Pattern(regexp = "^(0|[1-9][0-9]*)$") String productId,
            @ApiParam(value = "Article unique identifier.") @PathVariable(value = "articleId")
            @Pattern(regexp = "^(0|[1-9][0-9]*)$") String articleId);

    /**
     * Retrieve {@link ArticleView}, identified uniquely by {@link ProductView} {@link String}.
     *
     * @param productId - {@link String} representation of {@link ProductView} unique identifier
     * @return {@link ResponseEntity} with wrapped status code and {@link List} of {@link ArticleView}
     * payload with HAL.
     */
    @ApiOperation(
            value = "Retrieve collection of product articles.",
            notes = "See the data model parameter for more information about data types.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "Product articles data collection.",
                            examples =
                            @Example(
                                    value =
                                    @ExampleProperty(
                                            mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                                            value = "")),
                            response = ArticleView.class),
                    @ApiResponse(
                            code = 400,
                            message = "Invalid or malformed path parameters.",
                            examples =
                            @Example(
                                    value =
                                    @ExampleProperty(
                                            mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                                            value = "")),
                            response = ApplicationCustomError.class),
                    @ApiResponse(code = 404, message = "Not found."),
                    @ApiResponse(code = 415, message = "The content type is unsupported"),
                    @ApiResponse(
                            code = 500,
                            message =
                                    "An unexpected error has occurred. The error has been logged and is being investigated.")
            })
    @GetMapping(value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    ResponseEntity<List<ArticleView>> getProductArticles(
            @ApiParam(value = "Product unique identifier.") @PathVariable(value = "productId")
            @Pattern(regexp = "^(0|[1-9][0-9]*)$") String productId);

    /**
     * Retrieve {@link ProductView}, identified uniquely by {@link java.util.UUID}.
     *
     * @param productId - {@link String} representation of {@link ProductView} unique identifier
     * @param articleId - {@link String} representation of {@link ArticleView} unique identifier
     * @return {@link ResponseEntity} with wrapped status code {@link
     * ArticleView} payload with HAL.
     */
    @ApiOperation(
            value = "Retrieve uniquely identified product article.",
            notes = "See the data model parameter for more information about data types.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "Product article data uniquely identified.",
                            examples =
                            @Example(
                                    value =
                                    @ExampleProperty(
                                            mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                                            value = "")),
                            response = ArticleView.class),
                    @ApiResponse(
                            code = 400,
                            message = "Invalid or malformed path parameters.",
                            examples =
                            @Example(
                                    value =
                                    @ExampleProperty(
                                            mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                                            value = "")),
                            response = ApplicationCustomError.class),
                    @ApiResponse(code = 404, message = "Not found."),
                    @ApiResponse(code = 415, message = "The content type is unsupported"),
                    @ApiResponse(
                            code = 500,
                            message =
                                    "An unexpected error has occurred. The error has been logged and is being investigated.")
            })
    @GetMapping(value = "/articles/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    ResponseEntity<ArticleView> getProductArticle(
            @ApiParam(value = "Product unique identifier.") @PathVariable(value = "productId")
            @Pattern(regexp = "^(0|[1-9][0-9]*)$") String productId,
            @ApiParam(value = "Article unique identifier.") @PathVariable(value = "articleId")
            @Pattern(regexp = "^(0|[1-9][0-9]*)$") String articleId);

    /**
     * Persist {@link ProductView}.
     *
     * @param productId   - {@link String} representation of {@link ProductView} unique identifier
     * @param articleView - {@link ArticleView} representation of data
     * @return {@link ResponseEntity} with wrapped status code and {@link List} of {@link
     * ArticleView} payload with HAL.
     */
    @ApiOperation(
            value = "Persist uniquely identified product article.",
            notes = "See the data model parameter for more information about data types.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "Product article data persisted.",
                            examples =
                            @Example(
                                    value =
                                    @ExampleProperty(
                                            mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                                            value = "")),
                            response = ArticleView.class),
                    @ApiResponse(
                            code = 400,
                            message = "Invalid or malformed path parameters.",
                            examples =
                            @Example(
                                    value =
                                    @ExampleProperty(
                                            mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                                            value = "")),
                            response = ApplicationCustomError.class),
                    @ApiResponse(
                            code = 422,
                            message = "Malformed data.",
                            examples =
                            @Example(
                                    value =
                                    @ExampleProperty(
                                            mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                                            value = "")),
                            response = ApplicationCustomError.class),
                    @ApiResponse(code = 404, message = "Not found."),
                    @ApiResponse(code = 415, message = "The content type is unsupported"),
                    @ApiResponse(
                            code = 500,
                            message =
                                    "An unexpected error has occurred. The error has been logged and is being investigated.")
            })
    @PostMapping(value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    ResponseEntity<ArticleView> saveArticle(
            @ApiParam(value = "Product unique identifier.") @PathVariable(value = "productId")
            @Pattern(regexp = "^(0|[1-9][0-9]*)$") String productId,
            @ApiParam(value = "Article data.")
            @Validated(value = {PersistValidationGroup.class})
            @RequestBody ArticleView articleView);
}
