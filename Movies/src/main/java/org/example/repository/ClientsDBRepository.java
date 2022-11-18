package org.example.repository;

import org.example.domain.Clients;
import org.example.domain.validators.Validator;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientsDBRepository extends InMemoryRepository<Long, Clients> {

        private String url;
        private String user;
        private String password;

        public ClientsDBRepository(Validator<Clients> validator, String url, String user, String password) {
            super(validator);
            this.url = url;
            this.user = user;
            this.password = password;
        }

        public List<Clients> findAll() {
            var clients = new ArrayList<Clients>();
            var sql = "select * from clients";

            try (var connection = DriverManager.getConnection(url, user, password);
                 var preparedStatement = connection.prepareStatement(sql);
                 var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    var id = resultSet.getLong("id");
                    var lastName = resultSet.getString("lastName");
                    var firstName = resultSet.getString("firstName");
                    var age = resultSet.getInt("age");

                    Clients clients1 = new Clients(id,lastName,firstName,age);

                    clients.add(clients1);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            return clients;
        }
        public Optional<Clients> save(Clients clients){
            Optional<Clients> optional = super.save(clients);
            if (optional.isPresent()) {
                return optional;
            }
            addClients(clients);
            return Optional.empty();
        }
        public void addClients(Clients clients) {

            var sql = "insert into clients (id, firstName, lastName, age) values (?, ?, ?, ?)";

            try (var connection = DriverManager.getConnection(url, user, password);
                 var preparedStatement = connection.prepareStatement(sql);
            ) {
                preparedStatement.setInt(1,clients.getIdEntity().intValue());
                preparedStatement.setString(2, clients.getLastName());
                preparedStatement.setString(3, clients.getFirstName());
                preparedStatement.setInt(4, clients.getAge());
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    public Optional<Clients> update(Clients clients){
        Optional<Clients> optional = super.save(clients);
        if (optional.isPresent()) {
            return optional;
        }
        updateClients(clients);
        return Optional.empty();
    }
        public void updateClients(Clients clients) {

            var sql = " update doctors set lastName = ?, firstName = ?, age =? where id = ?";
            try (var connection = DriverManager.getConnection(url, user, password);
                 var preparedStatement = connection.prepareStatement(sql);) {

                preparedStatement.setString(1, clients.getLastName());
                preparedStatement.setString(2, clients.getFirstName());
                preparedStatement.setInt(3, clients.getAge());
                preparedStatement.setLong(4, clients.getIdEntity());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

    public void delete(Clients clients) {
        var sql = " delete from clients where id = " + clients.getIdEntity();
        try (var connection = DriverManager.getConnection(url, user, password);
             var preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
