package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class CsvExporter extends FileExporter {
    public CsvExporter(String fileToOpen, String fileToSave, boolean b) {
        super(fileToOpen, fileToSave,b);
    }

    @Override
    void createFile() {
        try(PrintWriter printWriter = new PrintWriter(this.fileToSave+".csv", StandardCharsets.UTF_8)){
            String[] columnNames = new String[]{"Nazwisko","Imię","Czas uczestnicwa","Statuś zaświadczenia"};
            String separator = useTab ? "\t" : ";";
            for (String columnName : columnNames) {
                printWriter.print(columnName + separator);
            }
            for (Person datum : data) {
                printWriter.println();
                printWriter.print(datum.getSurname() + separator);
                printWriter.print(datum.getName() + separator);
                printWriter.print(datum.getTimeActivity() + separator);
                printWriter.print(datum.getHasCertificate());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
