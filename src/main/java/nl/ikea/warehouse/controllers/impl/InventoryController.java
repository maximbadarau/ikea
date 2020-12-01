package nl.ikea.warehouse.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.ikea.warehouse.controllers.IInventoryController;
import nl.ikea.warehouse.services.IArticleService;
import nl.ikea.warehouse.utils.FileReadUtils;
import nl.ikea.warehouse.views.impl.ArticleView;
import nl.ikea.warehouse.views.impl.InventoryView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.function.Consumer;

/**
 * Debit Card controller implementation.
 *
 * @see IInventoryController
 */
@Controller
public class InventoryController implements IInventoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);

    private final IArticleService iArticleService;
    private final ObjectMapper objectMapper;

    /**
     * Dependency injection default constructor
     *
     * @param iArticleService {@link IArticleService}
     */
    @Autowired
    public InventoryController(IArticleService iArticleService, ObjectMapper objectMapper) {
        this.iArticleService = iArticleService;
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
        model.addAttribute("inventories", this.iArticleService.getArticles());
        return "inventories";
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
        FileReadUtils.read(this.objectMapper, file, InventoryView.class, this.iArticleService::save);
        // As per HTTP 1.1 protocol reference, status codes 301 (Moved Permanently)
        // and 302 (Found) allow the request method to be changed from POST to GET.
        // The specification also defines the corresponding 307 (Temporary Redirect)
        // and 308 (Permanent Redirect) status codes that don’t allow the request method
        // to be changed from POST to GET.
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.MOVED_PERMANENTLY);
        return new ModelAndView("redirect:/inventories/render");
    }

    /**
     * {@inheritDoc}
     *
     * @param articleId - {@link String} representation of {@link ArticleView} unique identifier
     *                  identifier
     */
    @Override
    public ResponseEntity<Void> delete(String articleId) {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link ResponseEntity} wrapping {@link List} of {@link ArticleView}
     */
    @Override
    public ResponseEntity<List<InventoryView>> getArticles() {
        LOGGER.info("Get articles data.");
        return ResponseEntity.ok(this.iArticleService.getArticles());
    }

    /**
     * {@inheritDoc}
     *
     * @param inventoryView - {@link ArticleView} data
     * @return
     */
    @Override
    public ResponseEntity<InventoryView> saveArticle(InventoryView inventoryView) {
        LOGGER.info("Persist article data.");
        return ResponseEntity.ok(
                this.iArticleService.save(inventoryView).orElse(null));
    }

}
