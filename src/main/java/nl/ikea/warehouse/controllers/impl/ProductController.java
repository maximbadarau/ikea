package nl.ikea.warehouse.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.ikea.warehouse.exceptions.ApplicationException;
import nl.ikea.warehouse.exceptions.ResourceNotFoundException;
import nl.ikea.warehouse.services.IProductService;
import nl.ikea.warehouse.controllers.IProductController;

import java.util.List;

import nl.ikea.warehouse.utils.FileReadUtils;
import nl.ikea.warehouse.views.impl.ProductView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;

/**
 * Product controller implementation.
 *
 * @see IProductController
 */
@Controller
@Validated
public class ProductController implements IProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final IProductService iProductService;
    private final ObjectMapper objectMapper;


    /**
     * Dependency injection default constructor
     *
     * @param iProductService {@link IProductService}
     */
    @Autowired
    public ProductController(IProductService iProductService, ObjectMapper objectMapper) {
        this.iProductService = iProductService;
        this.objectMapper = objectMapper;
    }

    /**
     * {@inheritDoc}
     *
     * @param model {@link Model}
     * @return {@link String} page representation
     */
    @Override
    public String render(Model model) {
        LOGGER.info("Rendering credit limit page.");
        model.addAttribute("products", this.iProductService.getProducts());
        return "products";
    }

    /**
     * {@inheritDoc}
     *
     * @param file    - {@link MultipartFile} uploaded file.
     * @param request - {@link HttpServletRequest} HTTP request.
     * @return
     */
    @Override
    public ModelAndView upload(MultipartFile file, HttpServletRequest request) {
        FileReadUtils.read(this.objectMapper, file, ProductView.class, this.iProductService::save);
        // As per HTTP 1.1 protocol reference, status codes 301 (Moved Permanently)
        // and 302 (Found) allow the request method to be changed from POST to GET.
        // The specification also defines the corresponding 307 (Temporary Redirect)
        // and 308 (Permanent Redirect) status codes that don’t allow the request method
        // to be changed from POST to GET.
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.MOVED_PERMANENTLY);
        return new ModelAndView("redirect:/products/render");
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link ResponseEntity} wrapping {@link Void} response
     */
    @Override
    public ResponseEntity<Void> delete() {
        LOGGER.info("Deleting product data.");
        this.iProductService.deleteAll();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * {@inheritDoc}
     *
     * @param productView {@link ProductView}
     * @return {@link ResponseEntity} wrapping {@link ProductView}
     */
    @Override
    public ResponseEntity<ProductView> save(ProductView productView) {
        LOGGER.info("Persisting product data.");
        return new ResponseEntity(
                this.iProductService
                        .save(productView)
                        .orElseThrow(ApplicationException::new),
                HttpStatus.CREATED);
    }

    /**
     * {@inheritDoc}
     *
     * @param filter - {@link String} representation for statuses filtering options
     * @return {@link ResponseEntity} wrapping {@link List} of {@link ProductView}
     */
    @Override
    public ResponseEntity<List<ProductView>> getProducts(String filter) {
        LOGGER.info("Retrieving product data with filter: {}.", filter);
        return new ResponseEntity<>(
                addHyperlinkToCollectionElements(this.iProductService.getProducts()),
                HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     *
     * @param id - {@link String} representation of {@link ProductView} unique
     *           identifier
     * @return {@link ResponseEntity} wrapping {@link ProductView}
     */
    @Override
    public ResponseEntity<ProductView> getProduct(String id) {
        LOGGER.info("Retrieving product data with id: {}.", id);
        return new ResponseEntity<>(
                this.iProductService
                        .getProduct(Long.valueOf(id))
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "MSG_IKEA_404", "There is no persisted entry with unique identifier")),
                HttpStatus.OK);
    }

}
