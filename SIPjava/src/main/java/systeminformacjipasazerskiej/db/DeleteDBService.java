package systeminformacjipasazerskiej.db;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import systeminformacjipasazerskiej.model.Kurs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Optional;

public class DeleteDBService {
    private Connection connection;

    public DeleteDBService(Connection connection) {
        this.connection = connection;
    }

    public void deleteStation(String s) {
        try {
            Statement statement = connection.createStatement();

            //get id_stacji
            int id_stacji;
            ResultSet resultSet = statement.executeQuery("SELECT id_stacji FROM stacje WHERE nazwa_stacji = '" + s + "';");
            if(resultSet.next()) id_stacji = resultSet.getInt("id_stacji");
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Nie znaleziono podanej stacji.");
                alert.showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdź wybór");
            alert.setHeaderText("Czy na pewno chcesz usunąć tę stację?");
            alert.setContentText("Usunięcie stacji spowoduje usunięcie wszystkich postójów, odcinków, tras i pociągów z nią związanych.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() != ButtonType.OK) {
                return;
            }


            //get id_odcinka
            resultSet = statement.executeQuery("SELECT id_odcinka FROM odcinki WHERE stacja_poczatkowa = " + id_stacji + " OR stacja_koncowa = " + id_stacji + "; ");
            HashSet<Integer> id_odcinka = new HashSet<>();
            while (resultSet.next()) {id_odcinka.add(resultSet.getInt("id_odcinka"));}

            //get id_trasy
            HashSet<Integer> id_trasy = new HashSet<>();
            for (Integer i: id_odcinka) {
                resultSet = statement.executeQuery( "SELECT  id_trasy FROM trasy_odcinki WHERE id_odcinka = " + i + ";");
                while (resultSet.next()) id_trasy.add(resultSet.getInt("id_trasy"));
            }

            //get id_pociagu
            HashSet<Integer> id_pociagu = new HashSet<>();
            for(Integer i: id_trasy) {
                resultSet = statement.executeQuery("SELECT id_pociagu FROM pociagi WHERE id_trasy = " + i + ";");
                while (resultSet.next()) id_pociagu.add(resultSet.getInt("id_pociagu"));
            }

            //get id_kursu
            HashSet<Integer> id_kursu = new HashSet<>();
            for(Integer i: id_pociagu) {
                resultSet = statement.executeQuery("SELECT id_kursu FROM rozklady WHERE id_pociagu = " + i + ";");
                while (resultSet.next()) id_kursu.add(resultSet.getInt("id_kursu"));
            }

            System.out.println("Stacja: " + id_stacji);
            System.out.println("Odcinki " + id_odcinka.toString());
            System.out.println("Trasy: " + id_trasy.toString());
            System.out.println("Pociagi: " + id_pociagu.toString());
            System.out.println("Kursy: " + id_kursu.toString());

            //DELETE time

            //DELETE postoje
            for(Integer i: id_kursu)
                statement.execute("DELETE FROM postoje WHERE id_kursu = " + i + ";");

            //DELETE rozklady
            for(Integer i: id_kursu)
                statement.execute("DELETE FROM rozklady WHERE id_kursu = " + i + ";");

            //DELETE pociagi
            for(Integer i: id_pociagu)
                statement.execute("DELETE FROM pociagi WHERE id_pociagu = " + i + ";");

            //DELETE trasy_odcinki
            for(Integer i: id_trasy)
                statement.execute("DELETE FROM trasy_odcinki WHERE id_trasy = " + i +";");

            //DELETE trasy
            for(Integer i: id_trasy)
                statement.execute("DELETE FROM trasy WHERE id_trasy = " + i +";");

            //DELETE odcinki
            statement.execute("DELETE FROM odcinki WHERE stacja_poczatkowa = " + id_stacji + " OR stacja_koncowa = " + id_stacji + "; ");

            //DELETE stacje
            statement.execute("DELETE FROM stacje WHERE id_stacji = " + id_stacji + ";");

            resultSet.close();
            statement.close();

            System.out.println("Deleted successfully: " + s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePociag(String nazwa_pociagu) {
        try {
            Statement statement = connection.createStatement();

            //get id_pociagu
            ResultSet resultSet = statement.executeQuery("SELECT id_pociagu FROM pociagi WHERE nazwa_pociagu = '" + nazwa_pociagu + "';");
            int id_pociagu;
            if(resultSet.next()) id_pociagu = resultSet.getInt("id_pociagu");
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Nie znaleziono podanego pociągu.");
                alert.showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdź wybór");
            alert.setHeaderText("Czy na pewno chcesz usunąć ten pociąg?");
            alert.setContentText("Usunięcie pociągu spowoduje usunięcie tras i postojów z nim związanych.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() != ButtonType.OK) {
                return;
            }

            //get id_kursu
            HashSet<Integer> id_kursu = new HashSet<>();
            resultSet = statement.executeQuery("SELECT id_kursu FROM rozklady WHERE id_pociagu = " + id_pociagu + ";");
            while (resultSet.next()) id_kursu.add(resultSet.getInt("id_kursu"));

            System.out.println("Pociag: " + id_pociagu);
            System.out.println("Kursy: " + id_kursu.toString());

            //delete postoje
            for(Integer i: id_kursu) statement.execute("DELETE FROM postoje WHERE id_kursu = " + i + ";");
            //delete rozklady
            statement.execute("DELETE FROM rozklady WHERE id_pociagu = " + id_pociagu + ";");
            //delete pociag
            statement.execute("DELETE FROM pociagi WHERE id_pociagu = " + id_pociagu + ";");

            statement.close();
            resultSet.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRide(Kurs kurs) {
        int id_kursu = kurs.getIdKursu();

        try {
            Statement statement = connection.createStatement();

            statement.execute("DELETE FROM postoje WHERE id_kursu = " + id_kursu + ";");
            statement.execute("DELETE FROM rozklady WHERE id_kursu = " + id_kursu + ";");

            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
