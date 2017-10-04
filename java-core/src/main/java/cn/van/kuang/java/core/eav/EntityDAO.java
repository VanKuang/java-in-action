package cn.van.kuang.java.core.eav;

import cn.van.kuang.java.core.utils.DatabaseUtils;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class EntityDAO {

    private final static String TABLE_ENTITY = "CREATE TABLE ENTITY (\n" +
            "            ID INT(10) NOT NULL AUTO_INCREMENT,\n" +
            "            CREATE_TIME TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP\n" +
            "        )";

    private final static String TABLE_ATTRIBUTE = "CREATE TABLE ATTRIBUTE (\n" +
            "            ID INT(10) NOT NULL AUTO_INCREMENT,\n" +
            "            NAME VARCHAR2(100) NOT NULL,\n" +
            "            CREATE_TIME TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP\n" +
            "        )";

    private final static String TABLE_VALUE = "CREATE TABLE ATTR_VALUE (\n" +
            "            ID INT(10) NOT NULL AUTO_INCREMENT,\n" +
            "            ENTITY_ID INT(10) NOT NULL,\n" +
            "            ATTR_ID INT(10) NOT NULL,\n" +
            "            VALUE VARCHAR2(255) NULL,\n" +
            "            CREATE_TIME TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP\n" +
            "        )";

    private final static String SQL_CREATE_ENTITY = "INSERT INTO ENTITY DEFAULT VALUES";

    private final static String SQL_CREATE_ATTR = "INSERT INTO ATTRIBUTE (NAME) VALUES (?)";

    private final static String SQL_ADD_VALUE = "INSERT INTO ATTR_VALUE (ENTITY_ID, ATTR_ID, VALUE) VALUES (?, ?, ?)";

    private final static String SQL_QUERY_ENTITY = "SELECT A.NAME, AV.VALUE FROM ATTR_VALUE AV LEFT JOIN ATTRIBUTE A ON AV.ATTR_ID = A.ID WHERE AV.ENTITY_ID = ?";

    public void initTables() throws SQLException, ClassNotFoundException {
        Connection connection = null;

        try {
            connection = DatabaseUtils.connection();
            Statement statement = connection.createStatement();

            statement.executeUpdate(TABLE_ENTITY);
            statement.executeUpdate(TABLE_ATTRIBUTE);
            statement.executeUpdate(TABLE_VALUE);

            connection.commit();
        } finally {
            DatabaseUtils.close(connection);
        }
    }

    public int createEntity() throws SQLException, ClassNotFoundException {
        Connection connection = null;

        try {
            connection = DatabaseUtils.connection();

            PreparedStatement statement = connection.prepareStatement(
                    SQL_CREATE_ENTITY,
                    Statement.RETURN_GENERATED_KEYS);

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();

            rs.next();

            return rs.getInt(1);
        } finally {
            DatabaseUtils.close(connection);
        }
    }

    public int createAttribute(String name) throws SQLException, ClassNotFoundException {
        Connection connection = null;

        try {
            connection = DatabaseUtils.connection();

            PreparedStatement statement = connection.prepareStatement(
                    SQL_CREATE_ATTR,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, name);

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();

            rs.next();

            return rs.getInt(1);
        } finally {
            DatabaseUtils.close(connection);
        }
    }

    public void addAttributeValue(int entityID, int attrID, String value) throws SQLException, ClassNotFoundException {
        Connection connection = null;

        try {
            connection = DatabaseUtils.connection();

            PreparedStatement statement = connection.prepareStatement(SQL_ADD_VALUE);

            statement.setInt(1, entityID);
            statement.setInt(2, attrID);
            statement.setString(3, value);

            statement.executeUpdate();
        } finally {
            DatabaseUtils.close(connection);
        }
    }

    public Entity getEntity(int entityID) throws SQLException, ClassNotFoundException {
        Connection connection = null;

        try {
            connection = DatabaseUtils.connection();

            PreparedStatement statement = connection.prepareStatement(SQL_QUERY_ENTITY);
            statement.setInt(1, entityID);

            ResultSet rs = statement.executeQuery();

            Map<String, String> attrs = new HashMap<>();
            while (rs.next()) {
                attrs.put(rs.getString(1), rs.getString(2));
            }

            return new Entity(entityID, attrs);
        } finally {
            DatabaseUtils.close(connection);
        }
    }
}
