package nl.ikea.warehouse.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.fasterxml.jackson.annotation.JsonView;
import nl.ikea.warehouse.exceptions.errors.ApplicationCustomError;
import nl.ikea.warehouse.validators.groups.PersistValidationGroup;
import nl.ikea.warehouse.views.RenderGroupView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;

import java.util.List;

import nl.ikea.warehouse.views.impl.ProductView;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;

/**
 * Product controller contract definition.
 */
@Validated
@RequestMapping(value = "/products")
@Api(value = "Product Controller")
public interface IProductController {

    /**
     * Render products page
     *
     * @param model {@link Model}
     * @return {@link String} page representation
     */
    @ApiOperation(
            value = "Render products page.",
            notes = "HTML page provided as a response.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Page rendered."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 415, message = "The content type is unsupported"),
            @ApiResponse(code = 500,
                    message = "An unexpected error has occurred. The error has been logged and is being investigated.")})
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/render")
    String render(Model model);

    /**
     * Upload file.
     *
     * @param file    - {@link MultipartFile} uploaded file.
     * @param request - {@link HttpServletRequest} HTTP request.
     * @return {@link ModelAndView} with redirect command.
     */
    @ApiOperation(
            value = "Import a product JSON in the system.",
            notes = "For any existing data assigned to particular provided unique identifier, data will be overridden")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Products been successfully imported."),
            @ApiResponse(code = 400, message = "Invalid or malformed products JSON."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 415, message = "The content type is unsupported"),
            @ApiResponse(code = 500,
                    message = "An unexpected error has occurred. The error has been logged and is being investigated.")})
    @PostMapping(value = "/upload")
    ModelAndView upload(@RequestParam(value = "file") @ApiParam(
            value = "A document value representing products list to be imported. An example of the expected schema can be found.") MultipartFile file,
                        HttpServletRequest request);

    /**
     * Delete all entries of {@link ProductView}
     *
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
    @DeleteMapping()
    ResponseEntity<Void> delete();

    /**
     * Persist entry of {@link ProductView}
     *
     * @param productView - {@link ProductView}
     * @return {@link String} page representation
     */
    @ApiOperation(value = "Persist entry in the database.", notes = "Persist entry in the database")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Created."),
                    @ApiResponse(code = 404, message = "Not found."),
                    @ApiResponse(code = 415, message = "The content type is unsupported"),
                    @ApiResponse(
                            code = 500,
                            message =
                                    "An unexpected error has occurred. The error has been logged and is being investigated.")
            })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping()
    @JsonView(value = {RenderGroupView.Public.class})
    ResponseEntity<ProductView> save(
            @Validated(value = PersistValidationGroup.class) @RequestBody
                    ProductView productView);

    /**
     * Retrieve {@link List} of {@link ProductView}
     *
     * @param filter - {@link String} representation for statuses filtering options
     * @return {@link ResponseEntity} with wrapped status code and {@link List} of {@link
     * ProductView} payload with HAL.
     */
    @ApiOperation(
            value = "Retrieve persisted products.",
            notes = "See the data model parameter for more information about data types.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "Retrieve products data.",
                            examples =
                            @Example(
                                    value =
                                    @ExampleProperty(
                                            mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                                            value = "")),
                            response = Object.class),
                    @ApiResponse(
                            code = 400,
                            message = "Invalid or malformed path parameters.",
                            examples =
                            @Example(
                                    value =
                                    @ExampleProperty(
                                            mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                                            value = "")),
                            response = Object.class),
                    @ApiResponse(code = 404, message = "Not found."),
                    @ApiResponse(code = 415, message = "The content type is unsupported"),
                    @ApiResponse(
                            code = 500,
                            message =
                                    "An unexpected error has occurred. The error has been logged and is being investigated.")
            })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @JsonView(value = {RenderGroupView.Restricted.class})
    ResponseEntity<List<ProductView>> getProducts(
            @RequestParam(name = "filter", defaultValue = "active", required = false) String filter);

    /**
     * Retrieve {@link ProductView}, identified uniquely by {@link Long}.
     *
     * @param id - {@link Long} representation of {@link ProductView} unique identifier
     * @return {@link ResponseEntity} with wrapped status code and {@link List} of {@link
     * ProductView} payload with HAL.
     */
    @ApiOperation(
            value = "Retrieve uniquely identified product.",
            notes = "See the data model parameter for more information about data types.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "Product data uniquely identified.",
                            examples =
                            @Example(
                                    value =
                                    @ExampleProperty(
                                            mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                                            value = "")),
                            response = ProductView.class),
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
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @JsonView(value = {RenderGroupView.Restricted.class})
    ResponseEntity<ProductView> getProduct(@Pattern(regexp = "^(0|[1-9][0-9]*)$")
                                           @ApiParam(value = "Product unique identifier.") @PathVariable(value = "id")
                                                   String id);

    /**
     * Apply HAL on collection elements.
     *
     * @param models {@link List} of {@link ProductView} POJOs
     * @return {@link List} of {@link ProductView}
     */
    default List<ProductView> addHyperlinkToCollectionElements(
            List<ProductView> models) {
        models.forEach(
                view ->
                        view.add(
                                linkTo(
                                        methodOn(IProductController.class)
                                                .getProduct(String.valueOf(view.getProductId())))
                                        .withSelfRel()));
        return models;
    }
}
