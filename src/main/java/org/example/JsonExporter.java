package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonExporter extends FileExporter{

    public JsonExporter(String fileToOpen, String fileToSave) {
        super(fileToOpen, fileToSave);
    }

    @Override
    void createFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(this.fileToSave+".json"),data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
