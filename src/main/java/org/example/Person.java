package org.example;

public class Person {
    private String name;
    private String surname;
    private String timeActivity;
    private Boolean hasCertificate;

    public Person(String name, String surname, String timeActivity, Boolean hasCertificate) {
        this.name = name;
        this.surname = surname;
        this.timeActivity = timeActivity;
        this.hasCertificate = hasCertificate;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getTimeActivity() {
        return timeActivity;
    }

    public Boolean getHasCertificate() {
        return hasCertificate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", timeActivity='" + timeActivity + '\'' +
                ", hasCertificate=" + hasCertificate +
                '}';
    }
}
