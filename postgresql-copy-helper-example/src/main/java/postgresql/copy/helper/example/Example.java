package postgresql.copy.helper.example;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.postgresql.core.BaseConnection;

import com.github.onozaty.postgresql.copy.CopyHelper;
import com.github.onozaty.postgresql.copy.bean.Column;
import com.github.onozaty.postgresql.copy.bean.Table;

public class Example {

    private static final String DATABASE_URL = "jdbc:postgresql://192.168.33.10:5432/testdb";
    private static final String DATABASE_USER = "user1";
    private static final String DATABASE_PASSWORD = "pass1";

    public static void main(String[] args) throws SQLException, IOException, IntrospectionException {

        try (BaseConnection connection = getConnection()) {

            createTable(connection);

            List<Item> items = generateItems(100000);

            {
                long startTime = System.currentTimeMillis();

                System.out.println("[insert] Start");

                insert(connection, items);

                System.out.println(
                        String.format(
                                "[insert] End (%,d msec)",
                                System.currentTimeMillis() - startTime));
            }
            {
                long startTime = System.currentTimeMillis();

                System.out.println("[insert batch] Start");

                insertByBatch(connection, items);

                System.out.println(
                        String.format(
                                "[insert batch] End (%,d msec)",
                                System.currentTimeMillis() - startTime));
            }
            {
                long startTime = System.currentTimeMillis();

                System.out.println("[insert copy] Start");

                insertByCopy(connection, items);

                System.out.println(
                        String.format(
                                "[insert copy] End (%,d msec)",
                                System.currentTimeMillis() - startTime));
            }
        }
    }

    private static void insert(BaseConnection connection, List<Item> items) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO items VALUES (?, ?)");

        for (Item item : items) {
            preparedStatement.setInt(1, item.getId());
            preparedStatement.setString(2, item.getName());
            preparedStatement.executeUpdate();
        }
    }

    private static void insertByBatch(BaseConnection connection, List<Item> items) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO items VALUES (?, ?)");

        for (Item item : items) {
            preparedStatement.setInt(1, item.getId());
            preparedStatement.setString(2, item.getName());
            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();
    }

    private static void insertByCopy(BaseConnection connection, List<Item> items)
            throws SQLException, IOException, IntrospectionException {

        CopyHelper.copyFrom(connection, items, Item.class);
    }

    private static BaseConnection getConnection() throws SQLException {

        return (BaseConnection) DriverManager.getConnection(
                DATABASE_URL,
                DATABASE_USER,
                DATABASE_PASSWORD);
    }

    private static void createTable(BaseConnection connection) throws SQLException {

        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate("DROP TABLE IF EXISTS items;"
                    + "CREATE TEMPORARY TABLE items ("
                    + "id integer, "
                    + "name text)");
        }
    }

    private static List<Item> generateItems(int count) {

        return IntStream.rangeClosed(1, count)
                .mapToObj(x -> new Item(x, "name" + x))
                .collect(Collectors.toList());
    }

    @Table("items")
    public static class Item {

        @Column("id")
        private final int id;

        @Column("name")
        private final String name;

        public Item(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
