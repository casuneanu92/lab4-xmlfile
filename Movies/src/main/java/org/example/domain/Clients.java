package org.example.domain;
/**
 * @author Simona.
 */

public class Clients extends BaseEntity<Long>{
    private String cnp;
    private String firstName;
    private String lastName;
    private int age;

    public Clients(String cnp, String firstName, String lastName, int age) {

        this.cnp = cnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }


    public Clients() {

    }
    public Clients (Long id, String cnp, String firstName, String lastName, int age) {
        super(id);
        this.cnp = cnp;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
    }
    public String getCnp() {
        return  cnp;
    }
    public void setCNP (String cnp) {
        this.cnp = cnp;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
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

        Clients clients= (Clients) o;

        if (age != clients.age) return false;
        if (!cnp.equals(clients.cnp)) return false;
        return firstName.equals(clients.firstName);

    }

    @Override
    public int hashCode() {
        int result = cnp.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + age;
        return result;
    }
    @Override
    public String toString() {
        return "Clients{" +
                "id='" + getId() + '\'' +
                "cnp='" + cnp + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}' + super.toString();
    }
}




