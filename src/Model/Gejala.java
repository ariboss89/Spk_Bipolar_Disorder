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

/**
 *
 * @author User
 */
public class Gejala {

    private koneksi con;
    private Statement st;
    private String query;
    private ResultSet res;
    private String idGejala;
    private String nama;

    public String getIdGejala() {
        return idGejala;
    }

    public void setIdGejala(String IdGejala) {
        this.idGejala = IdGejala;
    }
    
    public void Save(String id, String nama) {
        con = new koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "insert into gejala(Id, nama)values('" + id + "','" + nama + "')";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Gejala di Tambahkan");
        } catch (SQLException e) {
        }
    }

    public void Update(String id, String nama) {
        con = new koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "update gejala set nama='" + nama + "' where Id = '" + id + "'";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Gejala di Update");
        } catch (SQLException e) {

        }
    }

    public void Delete(String id) {
        con = new koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "delete from gejala where Id = '" + id + "'";
            //st.executeQuery("insert into penjualan(nama,kategori)values('"+nama+"','"+kategori+"')");
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Gejala di Hapus");
        } catch (SQLException e) {

        }
    }

    public String[][] ShowData() {

        res = null;
        String[][] data = null;
        con = new koneksi();
        con.connect();
        int jumlahBaris = 0;
        try {
            st = con.conn.createStatement();
            query = "SELECT COUNT(Id) AS Jumlah FROM gejala;";
            res = st.executeQuery(query);
            if (res.next()) {
                jumlahBaris = res.getInt("Jumlah");
            }
            query = "select *from gejala order by Id asc";
            res = st.executeQuery(query);
            data = new String[jumlahBaris][2];
            int r = 0;
            while (res.next()) {
                data[r][0] = res.getString("Id");
                data[r][1] = res.getString("nama");
                r++;
            }
            int jmlBaris = r;
            String[][] tmpArray = data;
            data = new String[jmlBaris][2];
            for (r = 0; r < jmlBaris; r++) {
                for (int c = 0; c < 2; c++) {
                    data[r][c] = tmpArray[r][c];
                }
            }
            st.close();
            con.conn.close();
        } catch (SQLException e) {
            System.err.println("SQLException : " + e.getMessage());
        }
        return data;
    }

    public String[][] SearchData(String nama, String Id) {

        res = null;
        String[][] data = null;
        con = new koneksi();
        con.connect();
        int jumlahBaris = 0;
        try {
            st = con.conn.createStatement();
            query = "SELECT COUNT(Id) AS Jumlah FROM gejala";
            res = st.executeQuery(query);
            if (res.next()) {
                jumlahBaris = res.getInt("Jumlah");
            }
            query = "select *from gejala where nama like '%" + nama + "%' or Id like '%" + Id + "%'";
            res = st.executeQuery(query);
            data = new String[jumlahBaris][2];
            int r = 0;
            while (res.next()) {
                data[r][0] = res.getString("Id");
                data[r][1] = res.getString("nama");
                r++;
            }
            int jmlBaris = r;
            String[][] tmpArray = data;
            data = new String[jmlBaris][2];
            for (r = 0; r < jmlBaris; r++) {
                for (int c = 0; c < 2; c++) {
                    data[r][c] = tmpArray[r][c];
                }
            }
            st.close();
            con.conn.close();
        } catch (SQLException e) {
            System.err.println("SQLException : " + e.getMessage());
        }
        return data;
    }
}
