package rs.raf.backend.repositories.komentar;


import rs.raf.backend.entiteti.Komentar;
import rs.raf.backend.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MySqlKomentarRepository extends MySqlAbstractRepository implements KomentarRepository {
    @Override
    public List<Komentar> pronadjiKomentareZaClanak(Integer clanak_id) {
        List<Komentar> komentari = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = this.newConnection();
            String sqlQuery = "SELECT * FROM komentari WHERE clanak_id = ? ORDER BY datum_kreiranja DESC";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1,clanak_id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int komentarId = resultSet.getInt("id");
                String autorKomentara = resultSet.getString("autor_komentara");
                String sadrzajKomentara = resultSet.getString("tekst");
                String datumKomentara = resultSet.getString("datum_kreiranja");
                Komentar komentar = new Komentar(komentarId,autorKomentara,sadrzajKomentara,datumKomentara);
                komentari.add(komentar);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return komentari;
    }

    @Override
    public void dodajKomentar(Integer id, Komentar komentar) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = this.newConnection();
            String [] generatedColumns = {"id"};
            LocalDateTime vremeKreiranja = LocalDateTime.now();
            String datum_kreiranja = vremeKreiranja.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String sqlQuery = "INSERT INTO komentari (autor_komentara, tekst, datum_kreiranja, clanak_id) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlQuery,generatedColumns);
            preparedStatement.setString(1,komentar.getAutor_komentara());
            preparedStatement.setString(2, komentar.getTekst());
            preparedStatement.setString(3,datum_kreiranja);
            preparedStatement.setInt(4,id);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                komentar.setId(resultSet.getInt(1));
            }


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

    }

    @Override
    public void obrisiKomentar(Integer clanakId, Integer komentarId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = this.newConnection();
            String sqlQuery = "DELETE from komentari WHERE id = ? AND clanak_id = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1,komentarId);
            preparedStatement.setInt(2, clanakId);
            preparedStatement.executeUpdate();

            connection.close();
            preparedStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.closeConnection(connection);
            this.closeStatement(preparedStatement);
        }
    }
}
