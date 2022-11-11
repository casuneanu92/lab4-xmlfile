package org.example.domain;
/**
        * @author Sergiu.
        */
public class Movies extends BaseEntity<Long> {

    private String name;

    private int year;

    private int duration;

    public Movies(String name, int year, int duration) {
        this.name = name;
        this.year = year;
        this.duration = duration;
    }

    public Movies(Long id, String name, int year, int duration) {
        super(id);
        this.name = name;
        this.year = year;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movies movies= (Movies) o;
        if (year != movies.year) return false;
        if (duration != movies.duration) return false;
        return name.equals(movies.name);

    }

    @Override
    public int hashCode() {
        int result = duration;
        result = 31 * result + name.hashCode();
        result = 31 * result + year;
        return result;
    }
    @Override
    public String toString() {
        return "Movies{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", year=" + year +
                ", duration=" + duration +
                '}' + super.toString();
    }
}
