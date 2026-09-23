package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

public abstract class FileExporter {
    private String fileToOpen;
    protected String fileToSave;
    private ArrayList<String> rawData = new ArrayList<>();
    private ArrayList<String> attendees = new ArrayList<>();
    protected ArrayList<Person> data = new ArrayList<>();
    protected boolean useTab;

    public FileExporter(String fileToOpen, String fileToSave) {
        this.fileToOpen = fileToOpen;
        this.fileToSave = fileToSave;
    }

    public FileExporter(String fileToOpen, String fileToSave, boolean useTab) {
        this.fileToOpen = fileToOpen;
        this.fileToSave = fileToSave;
        this.useTab = useTab;
    }

    /*
        1.Na początku wydobywam wszystkie dane z pliku.
        2.Następnie wyszukuję indeks początku i końca drugiej sekcji i ją wydobywam.
        3.W kolejnym kroku wydobywam konkretne dane z drugiej sekcji. Nie przejmuję się brakiem imienia lub nazwiska, ponieważ
        to nie moja wina, że programista zwalił swoją
        robotę i dopuścił do sytuacji, w której nie są one wymagane, więc polu nazwisko przypisuję null'a.
        4.Przekształcam również zapis czasu aktywności w stringu na duration, aby łatwo było mi sprawdzić, czy uczestnik przesiedział min godzinę na spotkaniu.
     */

    public void processData(){
        try(Scanner scan = new Scanner(new File(this.fileToOpen),StandardCharsets.UTF_16LE)){
            while(scan.hasNextLine()){
                rawData.add(scan.nextLine());
            }
            int min = 0;
            int max = rawData.size();

            for(int i=0;i<max;i++){
                String line = rawData.get(i).toLowerCase();
                if(line.contains("uczestnicy")){
                    min = i+2;
                } else if(line.contains("działania podczas spotkania")){
                    max = i-1;
                }
            }

            for(int i=min;i<max;i++){
                attendees.add(rawData.get(i));
            }

            for (String attendee : attendees) {
                data.add(createNewPerson(attendee.split("\t")));
            }

            data.sort(Comparator.comparing(Person::getSurname));

            createFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Person createNewPerson(String[] row){
        String[] partOfNames = row[0].trim().split("\\s+");
        String imie,nazwisko;
        if(partOfNames.length<3 && partOfNames[1].contains("(")){
            imie = partOfNames[0];
            nazwisko = "Null";
        } else {
            imie = partOfNames[0];
            nazwisko = partOfNames[1];
        }
        String timeAcitivity = row[3].trim();
        Duration duration = parseDuration(timeAcitivity.split(" "));
        Boolean certicicate = duration.compareTo(Duration.ofHours(1))>0;

        return new Person(imie,nazwisko,timeAcitivity,certicicate);
    }

    private Duration parseDuration(String[] text){
        long hours,minutes,seconds;

        if(text.length == 6){
            hours = Long.parseLong(text[0]);
            minutes = Long.parseLong(text[2]);
            seconds = Long.parseLong(text[4]);
        } else if(text.length == 4){
            hours = 0L;
            minutes = Long.parseLong(text[0]);
            seconds = Long.parseLong(text[2]);
        } else{
            hours = 0L;
            minutes = 0L;
            seconds = Long.parseLong(text[0]);
        }
        return Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
    }

    abstract void createFile();

}
