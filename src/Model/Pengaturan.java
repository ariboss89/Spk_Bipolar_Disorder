/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import Controller.koneksi;

/**
 *
 * @author User
 */
public class Pengaturan {

    private String nama;
    private String alamat;
    private String username;
    private String password;
    private String konfirmasi;
    private koneksi con;
    private Statement st;
    private String query;
    private ResultSet res;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKonfirmasi() {
        return konfirmasi;
    }

    public void setKonfirmasi(String konfirmasi) {
        this.konfirmasi = konfirmasi;
    }

    public void Update(String nama, String alamat,String username, String password, String konfirmasi) {
        con = new koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "update user set nama='" + nama + "',alamat='" + alamat + "',password= md5 '" + password + "',konfirmasi= md5 '" + konfirmasi + "' where username = '" + username + "'";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data Pengguna di Update");
        } catch (SQLException e) {

        }
    }
}
