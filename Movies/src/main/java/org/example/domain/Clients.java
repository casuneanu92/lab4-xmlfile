package org.example.domain;

import java.util.Objects;

/**
 * @author Simona.
 */

public class Clients extends Entity<Long>{
    private String lastName;
    private String firstName;
    private int age;

    public Clients(String lastName, String firstName, int age) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
    }

    public Clients(Long idEntity, String lastName, String firstName, int age) {
        super(idEntity);
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clients clients = (Clients) o;
        return age == clients.age && Objects.equals(lastName, clients.lastName) && Objects.equals(firstName, clients.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, age);
    }

    @Override
    public String toString() {
        return "Clients{" +
                "id='" + getIdEntity() + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", age=" + age +
                '}';
    }
}


