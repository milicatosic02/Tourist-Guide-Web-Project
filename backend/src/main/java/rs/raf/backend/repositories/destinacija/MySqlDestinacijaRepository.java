package rs.raf.backend.repositories.destinacija;



import rs.raf.backend.entiteti.Clanak;
import rs.raf.backend.entiteti.Destinacija;
import rs.raf.backend.repositories.MySqlAbstractRepository;
import rs.raf.backend.repositories.clanak.ClanakRepositroy;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlDestinacijaRepository extends MySqlAbstractRepository implements DestinacijaRepository {

    @Inject
    ClanakRepositroy clanakRepositroy;

    @Override
    public Response dodajDestinaciju(Destinacija destinacija) {
        Connection connection= null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO destinacije (ime,opis ) VALUES(?,?)",generatedColumns);
            preparedStatement.setString(1, destinacija.getIme());
            preparedStatement.setString(2, destinacija.getOpis());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();


            if (resultSet.next()) {
                destinacija.setId(resultSet.getInt(1));
            }

            return Response.ok(destinacija).build();
        }catch (SQLIntegrityConstraintViolationException e){
            System.err.println("SQLIntegrityCOntrainyViolationExcpetion: "+e.getMessage());
            return Response.status(Response.Status.CONFLICT)
                    .entity("Destinacija sa ovim imenom već postoji.")
                    .build();
        }catch (SQLException e){

            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Greška prilikom dodavanja destinacije.")
                    .build();
        }finally {
            closeResources(resultSet,preparedStatement,connection);
        }
    }

    private void closeResources(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.err.println("Error closing ResultSet: " + e.getMessage());
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                System.err.println("Error closing PreparedStatement: " + e.getMessage());
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing Connection: " + e.getMessage());
            }
        }
    }

        @Override
        public Response obrisiDestinaciju (Integer destinacija_id){
            Connection connection = null;
            PreparedStatement preparedStatement = null;

            List<Clanak> clanci = clanakRepositroy.filtrirajPoDestinaciji(destinacija_id);

            if(clanci.isEmpty()){

                try {
                    connection = this.newConnection();

                    String sqlQuery = "DELETE from destinacije WHERE id = ?";
                    preparedStatement = connection.prepareStatement(sqlQuery);
                    preparedStatement.setInt(1, destinacija_id);
                    preparedStatement.executeUpdate();

                    connection.close();
                    preparedStatement.close();

                    return Response.ok("Destinacija uspešno obrisana.").build();

                }catch (SQLException e){
                    e.printStackTrace();
                }finally {
                    this.closeConnection(connection);
                    this.closeStatement(preparedStatement);
                }

            }

            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Destinacija ne može biti obrisana jer postoje povezani članci.")
                    .build();
        }

        @Override
        public List<Destinacija> sveDestinacije () {
            List<Destinacija> destinacije  = new ArrayList<>();

            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;

            try {

                connection = this.newConnection();

                String sqlQuery = "SELECT * from destinacije";
                statement = connection.prepareStatement(sqlQuery);
                resultSet = statement.executeQuery(sqlQuery);

                while (resultSet.next()){
                    int destinacija_id = resultSet.getInt("id");
                    String ime = resultSet.getString("ime");
                    String opis = resultSet.getString("opis");

                    destinacije.add(new Destinacija(destinacija_id, ime, opis));
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

            return destinacije;
        }

        @Override
        public Destinacija izmeniDestinaciju (Integer id, Destinacija izmenjenaDestinacija){
            Connection connection = null;
            PreparedStatement preparedStatement = null;


            try {
                connection = this.newConnection();

                preparedStatement = connection.prepareStatement("UPDATE destinacije SET ime = ?, opis = ? WHERE id = ?");
                preparedStatement.setString(1, izmenjenaDestinacija.getIme());
                preparedStatement.setString(2, izmenjenaDestinacija.getOpis());
                preparedStatement.setInt(3, id);

                preparedStatement.executeUpdate();
                preparedStatement.getGeneratedKeys();

            } catch (SQLException e) {
                e.printStackTrace();

            } finally {
                this.closeConnection(connection);
                this.closeStatement(preparedStatement);
            }
            return izmenjenaDestinacija;
        }

        @Override
        public Destinacija pronadjiDestinaciju (Integer destinacija_id){
             Destinacija destinacija = null;
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                connection = this.newConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM destinacije where id = ?");
                preparedStatement.setInt(1,destinacija_id);
                resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    int destinacijaID = resultSet.getInt("id");
                    String ime = resultSet.getString("ime");
                    String opis = resultSet.getString("opis");

                    destinacija = new Destinacija(destinacijaID,ime,opis);
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
            return destinacija;
        }


}
