package org.example.repository;

import org.example.domain.Movies;
import org.example.domain.validators.Validator;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MoviesDBRepository extends InMemoryRepository<Long, Movies>{

        private String url;
        private String user;
        private String password;

        public MoviesDBRepository(Validator<Movies> validator, String url, String user, String password) {
            super(validator);
            this.url = url;
            this.user = user;
            this.password = password;
    }

        public List<Movies> findAll() {
        var movies = new ArrayList<Movies>();
        var sql = "select * from movies";

        try (var connection = DriverManager.getConnection(url, user, password);
             var preparedStatement = connection.prepareStatement(sql);
             var resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("name");
                var year = resultSet.getInt("year");
                var duration = resultSet.getInt("duration");


                Movies movies1 = new Movies(id,name, year, duration);

                movies.add(movies1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return movies;
    }
    public Optional<Movies> save(Movies movies){
        Optional<Movies> optional = super.save(movies);
        if (optional.isPresent()) {
            return optional;
        }
        addMovies(movies);
        return Optional.empty();
    }
        public void addMovies(Movies movies) {

        var sql = "insert into movies (id ,name, year, duration) values (?, ?, ?, ?, ?)";

        try (var connection = DriverManager.getConnection(url, user, password);
             var preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, movies.getIdEntity().intValue());
            preparedStatement.setString(2, movies.getName());
            preparedStatement.setInt(3, movies.getYear());
            preparedStatement.setInt(4,movies.getDuration());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public Optional<Movies> update(Movies movies){
        Optional<Movies> optional = super.save(movies);
        if (optional.isPresent()) {
            return optional;
        }
        updatePatient(movies);
        return Optional.empty();
    }
        public void updatePatient(Movies movies) {

        var sql = " update movies set name = ?, year =?, duration=? where id = ?";
        try (var connection = DriverManager.getConnection(url, user, password);
             var preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setString(1, movies.getName());
            preparedStatement.setInt(2, movies.getYear());
            preparedStatement.setInt(3,movies.getDuration());
            preparedStatement.setLong(4, movies.getIdEntity());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public Optional<Movies> delete(Long id) throws IllegalAccessException {
        if (findOne(id).isEmpty()) {
        throw new IllegalAccessException ("id is null");
        }
        var sql = " delete from movies where id = " + id;
        try (var connection = DriverManager.getConnection(url, user, password);
             var preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.executeUpdate();
            return findOne(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Movies> findOne(Long id) {

        var sql = " select * from movies where id = " + id;
        try (var connection = DriverManager.getConnection(url, user, password);
             var preparedStatement = connection.prepareStatement(sql);
             var resultSet = preparedStatement.executeQuery()) {
            var idEntity = resultSet.getLong("id");
            var name = resultSet.getString("name");
            var year = resultSet.getInt("year");
            var duration = resultSet.getInt("duration");
            Movies movies = new Movies(idEntity,name,year, duration);
            return findOne(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}

