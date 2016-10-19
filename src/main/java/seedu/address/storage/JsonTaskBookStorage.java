package seedu.address.storage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.TaskBook;

public class JsonTaskBookStorage implements TaskBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTaskBookStorage.class);

    private final SimpleStringProperty filePath;

    private final ObjectMapper objectMapper;

    public JsonTaskBookStorage(String filePath, ObjectMapper objectMapper) {
        assert !CollectionUtil.isAnyNull(filePath, objectMapper);
        this.filePath = new SimpleStringProperty(filePath);
        this.objectMapper = objectMapper;
    }

    public JsonTaskBookStorage(ObservableStringValue filePath, ObjectMapper objectMapper) {
        this(filePath.getValue(), objectMapper);
        this.filePath.bind(filePath);
    }

    public JsonTaskBookStorage(String filePath) {
        this(filePath, initDefaultObjectMapper());
    }

    public JsonTaskBookStorage(ObservableStringValue filePath) {
        this(filePath, initDefaultObjectMapper());
    }

    private static ObjectMapper initDefaultObjectMapper() {
        return new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .registerModule(new JavaTimeModule())
            .registerModule(new JsonStorageModule());
    }

    @Override
    public String getTaskBookFilePath() {
        return filePath.get();
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException {
        return readTaskBook(filePath.get());
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook(String filePath) throws DataConversionException, IOException {
        assert filePath != null;
        final File taskBookFile = new File(filePath);
        if (!taskBookFile.exists()) {
            logger.info("TaskBook file " + taskBookFile + " not found");
            return Optional.empty();
        }
        try {
            return Optional.of(objectMapper.readValue(taskBookFile, TaskBook.class));
        } catch (JsonProcessingException e) {
            throw new DataConversionException(e);
        }
    }

    @Override
    public void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException {
        saveTaskBook(taskBook, filePath.get());
    }

    @Override
    public void saveTaskBook(ReadOnlyTaskBook taskBook, String filePath) throws IOException {
        assert !CollectionUtil.isAnyNull(taskBook, filePath);
        final File file = new File(filePath);
        FileUtil.createIfMissing(file);
        objectMapper.writeValue(file, taskBook);
    }

}
