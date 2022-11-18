package org.example.domain;

import java.util.Objects;

/**
        * @author Sergiu.
        */
public class Movies extends Entity<Long> {

    private String name;

    private int year;

    private int duration;

    public Movies(String name, int year, int duration) {
        this.name = name;
        this.year = year;
        this.duration = duration;
    }

    public Movies(Long idEntity, String name, int year, int duration) {
        super(idEntity);
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
        Movies movies = (Movies) o;
        return year == movies.year && Objects.equals(name, movies.name) && Objects.equals(duration, movies.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, duration);
    }

    @Override
    public String toString() {
        return "Movies{" +
                "id='" + getIdEntity() + '\'' +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", duration='" + duration + '\'' +
                '}';
    }
}
