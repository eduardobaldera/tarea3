package edu.pucmm.eict.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConection {

    private static edu.pucmm.eict.servicios.DBConection conn;
    private String URL = "jdbc:h2:tcp://localhost/~/tarea3";

    private DBConection() { registerDriver(); }

    public static edu.pucmm.eict.servicios.DBConection getInstance() {
        if(conn == null){
            conn = new edu.pucmm.eict.servicios.DBConection();
        }
        return conn;
    }

    private void registerDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }

    public void testConn() {
        try {
            getConn().close();
            System.out.println("Conexión exitosa!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}