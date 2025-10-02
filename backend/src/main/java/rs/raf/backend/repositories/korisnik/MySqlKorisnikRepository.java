package rs.raf.backend.repositories.korisnik;




import rs.raf.backend.entiteti.Korisnik;
import rs.raf.backend.repositories.MySqlAbstractRepository;

import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlKorisnikRepository extends MySqlAbstractRepository implements KorisnikRepository {
    @Override
    public Korisnik pronadjiKorisnikaPoEmailu(String email) {
        Korisnik korisnik = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM korisnici where email = ?");
            preparedStatement.setString(1,email);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                int korisnikId = resultSet.getInt("id");
                String emaill = resultSet.getString("email");
                String ime = resultSet.getString("ime");
                String prezime = resultSet.getString("prezime");
                String tip_korisnika = resultSet.getString("tip_korisnika");
                Boolean status = resultSet.getBoolean("status");
                String hesiranaLozinka = resultSet.getString("hesiranaLozinka");

                korisnik = new Korisnik(korisnikId,ime,prezime,emaill,tip_korisnika,status,hesiranaLozinka);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return korisnik;
    }

    @Override
    public Korisnik pronadjiKorisnikaPoId(Integer korisnik_id) {
        Korisnik korisnik = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM korisnici where id = ?");
            preparedStatement.setInt(1,korisnik_id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                int korisnikId = resultSet.getInt("id");
                String emaill = resultSet.getString("email");
                String ime = resultSet.getString("ime");
                String prezime = resultSet.getString("prezime");
                String tip_korisnika = resultSet.getString("tip_korisnika");
                Boolean status = resultSet.getBoolean("status");
                String hesiranaLozinka = resultSet.getString("hesiranaLozinka");

                korisnik = new Korisnik(korisnikId,ime,prezime,emaill,tip_korisnika,status,hesiranaLozinka);
                System.out.println("Pronađen korisnik: " + korisnik);

            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return korisnik;
    }

    @Override
    public List<Korisnik> sviKorisnici() {
        List<Korisnik> korisnici = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            connection = this.newConnection();
            String sqlQuery = "SELECT * from korisnici";
            statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);

            while(resultSet.next()){
                int korisnikId = resultSet.getInt("id");
                String emaill = resultSet.getString("email");
                String ime = resultSet.getString("ime");
                String prezime = resultSet.getString("prezime");
                String tip_korisnika = resultSet.getString("tip_korisnika");
                Boolean status = resultSet.getBoolean("status");

                korisnici.add(new Korisnik(korisnikId,ime,prezime,emaill,tip_korisnika,status));
            }
            resultSet.close();
            statement.close();
            connection.close();


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return  korisnici;
    }

    @Override
    public Response dodajKorisnika(Korisnik korisnik) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{

            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO korisnici(email,ime,prezime,tip_korisnika,hesiranaLozinka) VALUES(?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,korisnik.getEmail());
            preparedStatement.setString(2, korisnik.getIme());
            preparedStatement.setString(3, korisnik.getPrezime());
            preparedStatement.setString(4, korisnik.getTip_korisnika());
            preparedStatement.setString(5, korisnik.getHesiranaLozinka());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                korisnik.setId(resultSet.getInt(1));
            }

            return Response.ok(korisnik).build();

        }catch (SQLIntegrityConstraintViolationException e){
            return Response.status(Response.Status.CONFLICT)
                    .entity("Korisnik sa ovim emailom vec postoji.")
                    .build();
        }catch (SQLException e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Greska prilikom dodavanja korisnika")
                    .build();
        }finally {
            closeResources(resultSet,preparedStatement,connection);
        }
    }

    private void closeResources(ResultSet resultSet,PreparedStatement preparedStatement,Connection connection){
        if(resultSet != null){
            try{
                resultSet.close();
            }catch (SQLException e){
                System.err.println("Error closing ResultSet: " + e.getMessage());
            }
        }
        if(preparedStatement != null){
            try{
                preparedStatement.close();
            }catch (SQLException e){
                System.err.println("Error closing PreparedStatemnt: " + e.getMessage());
            }
        }
        if(connection != null){
            try{
                connection.close();
            }catch (SQLException e){
                System.err.println("Error closing Connection: " + e.getMessage());
            }
        }

    }

    @Override
    public void obrisiKorisnika(Integer korisnik_id) {

    }

    @Override
    public void promeniStatusKorisniku(Integer korisnik_id) {
        Korisnik korisnik = pronadjiKorisnikaPoId(korisnik_id);
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try{
            connection = this.newConnection();
            String sqlQuery = "UPDATE korisnici SET status = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);

            if(korisnik.isStatus()){
                preparedStatement.setBoolean(1,false);
            }else{
                preparedStatement.setBoolean(1,true);
            }
            preparedStatement.setInt(2,korisnik_id);
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public Korisnik izmeniKorisnika(Integer id, Korisnik izmenjenKorisnik) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Korisnik stari = pronadjiKorisnikaPoId(id);
        String hesiranaLozinka = stari.getHesiranaLozinka();


        try{
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE korisnici SET email = ?, ime = ?, prezime = ?, tip_korisnika = ?, hesiranaLozinka = ? WHERE id = ?");
            preparedStatement.setString(1, izmenjenKorisnik.getEmail());
            preparedStatement.setString(2, izmenjenKorisnik.getIme());
            preparedStatement.setString(3, izmenjenKorisnik.getPrezime());
            preparedStatement.setString(4, izmenjenKorisnik.getTip_korisnika());
            preparedStatement.setString(5, hesiranaLozinka);
            preparedStatement.setInt(6, id);
            preparedStatement.executeUpdate();
            preparedStatement.getGeneratedKeys();


            preparedStatement.close();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return  izmenjenKorisnik;
    }
}
