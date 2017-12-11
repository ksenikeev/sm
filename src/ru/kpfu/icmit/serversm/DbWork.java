package ru.kpfu.icmit.serversm;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Класс реализует создание подключения к БД.
 *
 * Запуск метода main генерирует базу данных sm мессенджера, располагающуюся в домашней директории пользователя
 * @author Нафиков
 */
public class DbWork {
    public static Connection connection;

    public static Connection initDb() {
        if (connection == null) {
            try {
                Class.forName("org.h2.Driver").newInstance();
                connection = DriverManager.getConnection("jdbc:h2:~/sm", "sa", "");

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
            return connection;


    }


    public static  void createDB(){
        try {
            initDb();
            Statement st = connection.createStatement();
            st.execute("CREATE SEQUENCE if not exists users_seq;");
            st.execute("CREATE SEQUENCE if not exists msg_seq;");
            st.execute("CREATE TABLE if not exists users(    id integer NOT NULL DEFAULT nextval('users_seq') PRIMARY KEY,    username character varying(255) NOT NULL,     login character varying(255)  NOT NULL,     password character varying(255) NOT NULL,  seans_id uuid )");
        st.execute("CREATE TABLE if not exists messages (     id integer NOT NULL DEFAULT nextval('msg_seq') PRIMARY KEY,  fromuser integer NOT NULL, touser integer NOT NULL, content text NOT NULL DEFAULT ''::text,     datemsg timestamp  NOT NULL DEFAULT now(),     readed boolean NOT NULL DEFAULT false )");
       st.execute("insert into users (username, login, password) values ('User1', 'user1', 'user1')");
       st.execute("insert into users (username, login, password) values ('User2', 'user2', 'user2')");
        st.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public static void main(String[] args) {
        createDB();
        Statement st = null;
        try {
            st = initDb().createStatement();
            ResultSet rs = st.executeQuery(
                    "select * from users");
            while (rs.next()) {
                System.out.println(rs.getString("login") + "-" + rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}


