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
public class Penyakit {

    private koneksi con;
    private Statement st;
    private String query;
    private ResultSet res;
    private String idPenyakit;

    public String getIdPenyakit() {
        return idPenyakit;
    }

    public void setIdPenyakit(String IdPenyakit) {
        this.idPenyakit = IdPenyakit;
    }
    
    public void CekPenyakit(String nama){
        con = new koneksi();
        con.connect();        
        try{
            Statement st = con.conn.createStatement();
            res = st.executeQuery("select *from penyakit where nama = '"+nama+"' "); 
            while(res.next()){
                JOptionPane.showMessageDialog(null, "Disease Already Exist !!");
            }
            
        }catch(SQLException ex){
            
        }
    }
    
    public void Save(String id, String nama, String info, String solusi) {
        con = new koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "insert into penyakit(Id, nama, info, solusi)values('" + id + "','" + nama + "','" + info + "','" + solusi + "')";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data Penyakit di Tambahkan");
        } catch (SQLException e) {
        }
    }

    public void Update(String id, String nama, String info, String solusi) {
        con = new koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "update penyakit set nama='" + nama + "',info='" + info + "',solusi='" + solusi + "'  where Id = '" + id + "'";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data Penyakit di Update");
        } catch (SQLException e) {

        }
    }

    public void Delete(String id) {
        con = new koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "delete from penyakit where Id = '" + id + "'";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data Penyakit di Hapus");
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
            query = "SELECT COUNT(Id) AS Jumlah FROM penyakit";
            res = st.executeQuery(query);
            if (res.next()) {
                jumlahBaris = res.getInt("Jumlah");
            }
            query = "select *from penyakit order by Id asc";
            res = st.executeQuery(query);
            data = new String[jumlahBaris][4];
            int r = 0;
            while (res.next()) {
                data[r][0] = res.getString("Id");
                data[r][1] = res.getString("nama");
                data[r][2] = res.getString("info");
                data[r][3] = res.getString("solusi");
                r++;
            }
            int jmlBaris = r;
            String[][] tmpArray = data;
            data = new String[jmlBaris][4];
            for (r = 0; r < jmlBaris; r++) {
                for (int c = 0; c < 4; c++) {
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
            query = "SELECT COUNT(Id) AS Jumlah FROM penyakit";
            res = st.executeQuery(query);
            if (res.next()) {
                jumlahBaris = res.getInt("Jumlah");
            }
            query = "select *from penyakit where nama like '%" + nama + "%' or Id like '%" + Id + "%'";
            res = st.executeQuery(query);
            data = new String[jumlahBaris][4];
            int r = 0;
            while (res.next()) {
                data[r][0] = res.getString("Id");
                data[r][1] = res.getString("nama");
                data[r][2] = res.getString("info");
                data[r][3] = res.getString("solusi");
                r++;
            }
            int jmlBaris = r;
            String[][] tmpArray = data;
            data = new String[jmlBaris][4];
            for (r = 0; r < jmlBaris; r++) {
                for (int c = 0; c < 4; c++) {
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
