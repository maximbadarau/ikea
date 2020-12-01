package nl.ikea.warehouse.controllers;

import io.swagger.annotations.*;
import nl.ikea.warehouse.exceptions.errors.ApplicationCustomError;
import nl.ikea.warehouse.validators.groups.PersistValidationGroup;
import nl.ikea.warehouse.views.impl.InventoryView;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Article Controller contract definition.
 */
@Validated
@RequestMapping(value = "/inventories")
@Api(value = "Inventory Controller")
public interface IInventoryController {

    /**
     * Render inventories page
     *
     * @param model {@link Model}
     * @return {@link String} page representation
     */
    @ApiOperation(
            value = "Render inventories page.",
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
            value = "Import a inventory JSON in the system.",
            notes = "For any existing data assigned to particular provided unique identifier, data will be overridden")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Inventory been successfully imported."),
            @ApiResponse(code = 400, message = "Invalid or malformed inventory JSON."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 415, message = "The content type is unsupported"),
            @ApiResponse(code = 500,
                    message = "An unexpected error has occurred. The error has been logged and is being investigated.")})
    @PostMapping(value = "/upload")
    ModelAndView upload(@RequestParam(value = "file") @ApiParam(
            value = "A document value representing inventory list to be imported. An example of the expected schema can be found.") MultipartFile file,
                        HttpServletRequest request);

    /**
     * Delete all entries of {@link InventoryView}
     *
     * @param articleId - {@link String} representation of debit {@link InventoryView} unique
     *                  identifier
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
    @DeleteMapping(path = "/{articleId}")
    ResponseEntity<Void> delete(
            @ApiParam(value = "Article unique identifier.") @PathVariable(value = "articleId")
                    String articleId);

    /**
     * Retrieve all {@link InventoryView}.
     *
     * @return {@link ResponseEntity} with wrapped status code and {@link List} of {@link InventoryView}
     * payload with HAL.
     */
    @ApiOperation(
            value = "Retrieve articles.",
            notes = "See the data model parameter for more information about data types.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "Retrieve all articles.",
                            examples =
                            @Example(
                                    value =
                                    @ExampleProperty(
                                            mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                                            value = "")),
                            response = InventoryView.class),
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
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    ResponseEntity<List<InventoryView>> getArticles();

    /**
     * Persist {@link InventoryView}.
     *
     * @param inventoryView - {@link InventoryView} data
     * @return {@link ResponseEntity} with wrapped status code and {@link List} of {@link
     * InventoryView} payload with HAL.
     */
    @ApiOperation(
            value = "Persist uniquely identified article.",
            notes = "See the data model parameter for more information about data types.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "Article data persisted.",
                            examples =
                            @Example(
                                    value =
                                    @ExampleProperty(
                                            mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                                            value = "")),
                            response = InventoryView.class),
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
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    ResponseEntity<InventoryView> saveArticle(@ApiParam(value = "Article data.")
                                              @Validated(
                                                      value = {
                                                              PersistValidationGroup.class
                                                      })
                                              @RequestBody
                                                      InventoryView inventoryView);
}
