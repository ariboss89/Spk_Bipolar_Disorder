/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.sun.jndi.ldap.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Controller.koneksi;
import View.FormLogin;
import View.FormAsGuest;

/**
 *
 * @author User
 */
public class Register{

    private static String nama;
    private static String alamat;

    public static String getNama() {
        return nama;
    }
    
    public static void setNama(String nama) {
        Register.nama = nama;
    }

    public static String getAlamat() {
        return alamat;
    }

    public static void setAlamat(String alamat) {
        Register.alamat = alamat;
    }
}
