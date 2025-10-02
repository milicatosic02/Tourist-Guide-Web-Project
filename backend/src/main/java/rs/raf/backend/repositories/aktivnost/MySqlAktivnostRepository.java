package rs.raf.backend.repositories.aktivnost;



import rs.raf.backend.entiteti.Aktivnost;
import rs.raf.backend.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlAktivnostRepository extends MySqlAbstractRepository implements AktivnostRepository {

    @Override
    public Aktivnost pronadjiAktivnost(Integer aktivnost_id) {

        Aktivnost aktivnost = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM aktivnosti where id = ?");
            preparedStatement.setInt(1, aktivnost_id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int aktivnostId = resultSet.getInt("id");
                String naziv = resultSet.getString("naziv");

                aktivnost = new Aktivnost(aktivnostId, naziv);

            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return aktivnost;
    }

    @Override
    public List<Aktivnost> pronadjiAktivnostiZaClanak(Integer clanak_id) {
        List<Aktivnost> aktivnosti = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = this.newConnection();
            String sqlQuery = "SELECT * FROM clanak_aktivnosti WHERE clanak_id = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, clanak_id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int aktivnostId = resultSet.getInt("aktivnost_id");
                Aktivnost aktivnost = pronadjiAktivnost(aktivnostId);
                if (aktivnost != null) {
                    aktivnosti.add(aktivnost);
                }
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return aktivnosti;
    }

    @Override
    public void dodajAktivnost(Aktivnost aktivnost) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO aktivnosti (naziv) VALUES(?)", generatedColumns);
            preparedStatement.setString(1, aktivnost.getNaziv());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                aktivnost.setId(resultSet.getInt(1));
            }

            connection.close();
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

    }

    @Override
    public List<Aktivnost> sveAktivnosti() {
        List<Aktivnost> aktivnosti  = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = this.newConnection();

            String sqlQuery = "SELECT * from aktivnosti";
            statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()){
                int aktivnost_id = resultSet.getInt("id");
                String kljucneReci = resultSet.getString("naziv");

                aktivnosti.add(new Aktivnost(aktivnost_id, kljucneReci));
            }

            connection.close();
            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnection(connection);
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
        }

        return aktivnosti;
    }



}

