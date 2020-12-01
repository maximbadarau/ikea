package nl.ikea.warehouse.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.ikea.warehouse.views.IView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileReadUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileReadUtils.class);

    public static <T extends IView> void read(ObjectMapper objectMapper, MultipartFile file, Class<T> clazz, Consumer<T> consumer) {
        try {
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            CollectionType listType = typeFactory.constructCollectionType(ArrayList.class, clazz);
            InputStream inputStream = file.getInputStream();
            List<T> data = objectMapper.readValue(inputStream, listType);
            data.forEach(consumer);
        } catch (IOException e) {
            LOGGER.error("Error reading file.");
        }
    }
}
