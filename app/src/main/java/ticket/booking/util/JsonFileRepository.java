package ticket.booking.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonFileRepository<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String filePath;
    private final TypeReference<List<T>> typeReference;

    public JsonFileRepository(String filePath, TypeReference<List<T>> typeReference) {
        this.filePath = filePath;
        this.typeReference = typeReference;
    }

    public List<T> loadAll() throws IOException {
        File file = new File(filePath);
        return objectMapper.readValue(file, typeReference);
    }

    public void saveAll(List<T> entities) throws IOException {
        File file = new File(filePath);
        objectMapper.writeValue(file, entities);
    }
}
