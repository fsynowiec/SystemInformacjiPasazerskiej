package systeminformacjipasazerskiej.db;

import java.sql.*;
import java.util.ArrayList;

public class DeleteDBService {
    private Connection connection;

    public DeleteDBService(Connection connection) {
        this.connection = connection;
    }

    public void deleteStation(String s) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT id_stacji FROM stacje WHERE nazwa_stacji = '" + s + "';"
            );
            if(resultSet.next());
            int id_stacji = resultSet.getInt("id_stacji");


            statement.execute("DELETE FROM postoje WHERE id_stacji = " + id_stacji + ";");

            resultSet = statement.executeQuery("SELECT id_odcinka FROM odcinki WHERE stacja_poczatkowa = " + id_stacji + " OR stacja_koncowa = " + id_stacji + "; ");

            ArrayList<Integer> id_odcinka = new ArrayList<>();

            while (resultSet.next()) {id_odcinka.add(resultSet.getInt("id_odcinka"));}

            for (Integer i: id_odcinka) {
                statement.execute( "DELETE FROM trasy_odcinki WHERE id_odcinka = " + i + ";");
            }

            statement.execute("DELETE FROM odcinki WHERE stacja_poczatkowa = " + id_stacji + " OR stacja_koncowa = " + id_stacji + ";");

            statement.execute("DELETE FROM stacje WHERE id_stacji = " + id_stacji + ";");

            System.out.println("Deleted successfully: " + id_stacji);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
