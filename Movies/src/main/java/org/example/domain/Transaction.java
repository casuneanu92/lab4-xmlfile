package org.example.domain;

import java.util.Objects;

public class Transaction extends Entity<Long>{
    private long idClients;
    private long idMovies;
    private long price;

    public Transaction(Long idEntity, long idClients, long idMovies, long price) {
        super(idEntity);
        this.idClients = idClients;
        this.idMovies = idMovies;
        this.price = price;
    }

    public Transaction(Long idClients, Long idMovies, Long price) {
    }

    public long getIdClients() {
        return idClients;
    }

    public void setIdClients(long idClients) {
        this.idClients = idClients;
    }

    public long getIdMovies() {
        return idMovies;
    }

    public void setIdMovies(long idMovies) {
        this.idMovies = idMovies;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return idClients == that.idMovies && idMovies == that.idMovies && price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClients, idMovies, price);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + getIdEntity() + '\'' +
                ", idClients=" + idClients +
                ", idMovies=" + idMovies +
                ", price=" + price +
                '}';
    }
}
