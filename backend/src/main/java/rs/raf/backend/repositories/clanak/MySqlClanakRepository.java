package rs.raf.backend.repositories.clanak;



import rs.raf.backend.entiteti.Aktivnost;
import rs.raf.backend.entiteti.Clanak;
import rs.raf.backend.entiteti.Komentar;
import rs.raf.backend.repositories.MySqlAbstractRepository;
import rs.raf.backend.repositories.aktivnost.AktivnostRepository;
import rs.raf.backend.repositories.komentar.KomentarRepository;

import javax.inject.Inject;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MySqlClanakRepository extends MySqlAbstractRepository implements ClanakRepositroy {
    @Inject
    private AktivnostRepository aktivnostRepository;
    @Inject
    private KomentarRepository komentarRepository;
    @Override
    public Clanak dodajClanak(Integer destinacija_id, Clanak clanak) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementClanakAktivnost = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            connection.setAutoCommit(false);

            String[] generatedColumns = {"id"};
            LocalDate date = LocalDate.now();
            String dateString = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            preparedStatement = connection.prepareStatement("INSERT INTO clanci (naslov, tekst, autor_id, vreme_kreiranja, destinacija_id) VALUES(?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, clanak.getNaslov());
            preparedStatement.setString(2, clanak.getTekst());
            preparedStatement.setString(3, clanak.getAutor_id());
            preparedStatement.setString(4, dateString);
            preparedStatement.setInt(5, destinacija_id);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                clanak.setId(resultSet.getInt(1));

                String sqlClanakAktivnost = "INSERT INTO clanak_aktivnosti (clanak_id, aktivnost_id) VALUES (?, ?)";
                preparedStatementClanakAktivnost = connection.prepareStatement(sqlClanakAktivnost);
                System.out.println("CLANAK" + clanak.getAktivnosti());

                    for (Aktivnost aktivnost : clanak.getAktivnosti()) {
                        System.out.println("aktivnost" + aktivnost);
                        int clanakId = resultSet.getInt(1);
                        int aktivnostId = aktivnost.getId();
                        preparedStatementClanakAktivnost.setInt(1, clanakId);
                        preparedStatementClanakAktivnost.setInt(2, aktivnostId);
                        preparedStatementClanakAktivnost.executeUpdate();
                    }


            }

            connection.commit(); // Potvrdi transakciju

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Poništi transakciju u slučaju greške
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
            this.closeStatement(preparedStatementClanakAktivnost);
        }

        return clanak;
    }

    @Override
    public List<Clanak> sviClanci() {
        List<Clanak> clanci = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            // Napravi SQL upit koji kombinuje podatke iz tabele clanci sa podacima iz tabele korisnik
            String sql = "SELECT c.id, c.naslov, c.tekst, k.ime, k.prezime, c.vreme_kreiranja, c.destinacija_id " +
                    "FROM clanci c " +
                    "INNER JOIN korisnici k ON c.autor_id = k.id " +
                    "ORDER BY c.vreme_kreiranja DESC";

            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery(sql);

            // Iteriraj kroz rezultat i napravi objekte tipa Clanak
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String naslov = resultSet.getString("naslov");
                String tekst = resultSet.getString("tekst");
                String autor = resultSet.getString("ime") + " " + resultSet.getString("prezime");
                String vreme_kreiranja = resultSet.getString("vreme_kreiranja");
                Integer destinacija_id =  resultSet.getInt("destinacija_id");


                List<Komentar> komentari = komentarRepository.pronadjiKomentareZaClanak(id);


                List<Aktivnost> aktivnosti = aktivnostRepository.pronadjiAktivnostiZaClanak(id);


                // Kreiraj novi Clanak objekat i dodaj ga u listu clanci
                Clanak noviClanak = new Clanak(id, naslov, tekst, autor, vreme_kreiranja, destinacija_id);
                noviClanak.setKomentari(komentari);
                noviClanak.setAktivnosti(aktivnosti);
                clanci.add(noviClanak);

            }

            connection.close();
            statement.close();
            resultSet.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return clanci;
    }

    @Override
    public Clanak pronadjiClanak(Integer id) {
        Clanak clanak = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String upit = "SELECT c.id, c.naslov, c.tekst, c.broj_poseta, k.ime, k.prezime, c.vreme_kreiranja, c.destinacija_id " +
                    "FROM clanci c " +
                    "INNER JOIN korisnici k ON c.autor_id = k.id " +
                    "WHERE c.id = ?";


            preparedStatement = connection.prepareStatement(upit);

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int clanakId = resultSet.getInt("id");
                String naslov = resultSet.getString("naslov");
                String tekst = resultSet.getString("tekst");
                String autor = resultSet.getString("ime") + " " + resultSet.getString("prezime");
                String vreme_kreiranja = resultSet.getString("vreme_kreiranja");
                Integer destinacija_id = resultSet.getInt("destinacija_id");

                povecajBrojPoseta(clanakId);




                clanak = new Clanak(clanakId, naslov, tekst, autor, vreme_kreiranja, destinacija_id);

                // Dohvat komentara
                List<Komentar> komentari = komentarRepository.pronadjiKomentareZaClanak(clanakId);
                clanak.setKomentari(komentari);

                //   Dohvat aktivnosti
                List<Aktivnost> aktivnosti = aktivnostRepository.pronadjiAktivnostiZaClanak(clanakId);
                clanak.setAktivnosti(aktivnosti);
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

        return clanak;
    }

    @Override
    public void obrisiClanak(Integer id) {
        Connection connection = null;
        PreparedStatement deleteClanakAktivnostiStmt = null;
        PreparedStatement deleteKomentariStmt = null;
        PreparedStatement deleteClanakStmt = null;

        try {
            connection = this.newConnection();

            // Počinje transakcija
            connection.setAutoCommit(false);

            // Brisanje povezanih redova iz tabele clanak_aktivnosti
            String deleteClanakAktivnostiSQL = "DELETE FROM clanak_aktivnosti WHERE clanak_id = ?";
            deleteClanakAktivnostiStmt = connection.prepareStatement(deleteClanakAktivnostiSQL);
            deleteClanakAktivnostiStmt.setInt(1, id);
            deleteClanakAktivnostiStmt.executeUpdate();

            // Brisanje povezanih redova iz tabele komentari
            String deleteKomentariSQL = "DELETE FROM komentari WHERE clanak_id = ?";
            deleteKomentariStmt = connection.prepareStatement(deleteKomentariSQL);
            deleteKomentariStmt.setInt(1, id);
            deleteKomentariStmt.executeUpdate();

            // Brisanje članka iz tabele clanci
            String deleteClanakSQL = "DELETE FROM clanci WHERE id = ?";
            deleteClanakStmt = connection.prepareStatement(deleteClanakSQL);
            deleteClanakStmt.setInt(1, id);
            deleteClanakStmt.executeUpdate();

            // Završava transakciju
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    // Poništava transakciju u slučaju greške
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            // Zatvara resurse
            this.closeStatement(deleteClanakAktivnostiStmt);
            this.closeStatement(deleteKomentariStmt);
            this.closeStatement(deleteClanakStmt);
            this.closeConnection(connection);
        }
    }

    @Override
    public void povecajBrojPoseta(Integer clanakId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            String sqlQuery = "UPDATE clanci SET broj_poseta = broj_poseta + 1 WHERE id = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, clanakId);
            preparedStatement.executeUpdate();


            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public List<Clanak> filtrirajPoDestinaciji(Integer destinacija_id) {
        List<Clanak> clanci = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            // Napravi SQL upit koji kombinuje podatke iz tabele clanci sa podacima iz tabele korisnik
            String sql = "SELECT c.id, c.naslov, c.tekst, c.broj_poseta, k.ime, k.prezime, c.vreme_kreiranja, c.destinacija_id " +
                    "FROM clanci c " +
                    "INNER JOIN korisnici k ON c.autor_id = k.id " +
                    "WHERE c.destinacija_id = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, destinacija_id);
            resultSet = preparedStatement.executeQuery();

            // Iteriraj kroz rezultat i napravi objekte tipa Clanak
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String naslov = resultSet.getString("naslov");
                String tekst = resultSet.getString("tekst");
                String autor = resultSet.getString("ime") + " " + resultSet.getString("prezime");
                String vreme_kreiranja = resultSet.getString("vreme_kreiranja");
                Integer destinacijaa_id = resultSet.getInt("destinacija_id");


                List<Komentar> komentari = komentarRepository.pronadjiKomentareZaClanak(id);


                List<Aktivnost> aktivnosti = aktivnostRepository.pronadjiAktivnostiZaClanak(id);


                // Kreiraj novi Clanak objekat i dodaj ga u listu clanci
                Clanak noviClanak = new Clanak(id, naslov, tekst, autor, vreme_kreiranja, destinacijaa_id);
                noviClanak.setKomentari(komentari);
                noviClanak.setAktivnosti(aktivnosti);
                clanci.add(noviClanak);

            }

            connection.close();
            preparedStatement.close();
            resultSet.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return clanci;
    }

    @Override
    public List<Clanak> najcitanijiClanci() {
        List<Clanak> najcitanije = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            String sqlQuery = " SELECT c.id, c.naslov, c.tekst, k.ime, k.prezime, c.vreme_kreiranja, c.broj_poseta, c.destinacija_id " +
                    " FROM clanci c " +
                    " INNER JOIN korisnici k ON c.autor_id = k.id " +
                    " WHERE c.vreme_kreiranja >= DATE_SUB(NOW(), INTERVAL 365 DAY) " +
                    " ORDER BY c.broj_poseta DESC " +
                    " LIMIT 10";

            connection = this.newConnection();
            statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String naslov = resultSet.getString("naslov");
                String tekst = resultSet.getString("tekst");
                String autor = resultSet.getString("ime") + " " + resultSet.getString("prezime");
                String vreme_kreiranja = resultSet.getString("vreme_kreiranja");
                Integer destinacija_id  = resultSet.getInt("destinacija_id");

                List<Komentar> komentari = komentarRepository.pronadjiKomentareZaClanak(id);
                List<Aktivnost> aktivnosti = aktivnostRepository.pronadjiAktivnostiZaClanak(id);


                Clanak noviClanak = new Clanak(id, naslov, tekst, autor, vreme_kreiranja, destinacija_id);
                noviClanak.setKomentari(komentari);
                noviClanak.setAktivnosti(aktivnosti);
                najcitanije.add(noviClanak);
            }

            connection.close();
            statement.close();
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return najcitanije;
    }

    @Override
    public List<Clanak> filtrirajPoAktivnostima(Integer aktivnost_id) {
        List<Clanak> clanciPoAktivnostima = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sqlQuery = "SELECT c.id, c.naslov, c.tekst, k.ime, k.prezime, c.vreme_kreiranja, c.destinacija_id\n" +
                    "FROM clanci c\n" +
                    "INNER JOIN clanak_aktivnosti ca ON c.id = ca.clanak_id\n" +
                    "INNER JOIN aktivnosti a ON ca.aktivnost_id = a.id\n" +
                    "INNER JOIN korisnici k ON c.autor_id = k.id\n" +
                    "WHERE a.id = ?";

            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, aktivnost_id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String naslov = resultSet.getString("naslov");
                String tekst = resultSet.getString("tekst");
                String autor = resultSet.getString("ime") + " " + resultSet.getString("prezime");
                String vreme_kreiranja = resultSet.getString("vreme_kreiranja");
                Integer destinacija_id = resultSet.getInt("destinacija_id");

                List<Komentar> komentari = komentarRepository.pronadjiKomentareZaClanak(id);
                List<Aktivnost> aktivnosti = aktivnostRepository.pronadjiAktivnostiZaClanak(id);



                Clanak noviClanak = new Clanak(id, naslov, tekst, autor, vreme_kreiranja, destinacija_id);
                noviClanak.setKomentari(komentari);
                noviClanak.setAktivnosti(aktivnosti);
                clanciPoAktivnostima.add(noviClanak);
            }

            connection.close();
            preparedStatement.close();
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return clanciPoAktivnostima;
    }

    @Override
    public Clanak izmeniClanak(Integer id, Clanak izmenjeniClanak) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementClanakAktivnost = null;


        try {
            connection = this.newConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("UPDATE clanci SET naslov = ?, tekst = ?,  destinacija_id = ? WHERE id = ?");
            preparedStatement.setString(1, izmenjeniClanak.getNaslov());
            preparedStatement.setString(2, izmenjeniClanak.getTekst());
            preparedStatement.setInt(3, izmenjeniClanak.getDestinacija_id());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();

            // Brisanje starih aktivnosti
            preparedStatement = connection.prepareStatement("DELETE FROM clanak_aktivnosti WHERE clanak_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            // Dodavanje novih aktivnosti
            String sqlClanakAktivnost = "INSERT INTO clanak_aktivnosti (clanak_id, aktivnost_id) VALUES (?, ?)";
            preparedStatementClanakAktivnost = connection.prepareStatement(sqlClanakAktivnost);

            for (Aktivnost aktivnost : izmenjeniClanak.getAktivnosti()) {
                int aktivnostId = aktivnost.getId();
                preparedStatementClanakAktivnost.setInt(1, id); // koristimo `id` direktno
                preparedStatementClanakAktivnost.setInt(2, aktivnostId);
                preparedStatementClanakAktivnost.executeUpdate();
            }

            connection.commit(); // Potvrdi transakciju

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Poništi transakciju u slučaju greške
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeStatement(preparedStatementClanakAktivnost);
            this.closeConnection(connection);
        }

        return izmenjeniClanak;
    }
}
