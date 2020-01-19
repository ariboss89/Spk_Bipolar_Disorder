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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import Controller.koneksi;

/**
 *
 * @author User
 */
public class Pengetahuan {

    private koneksi con;
    private Statement st;
    private String query;
    private ResultSet res;
    private String IdGejala;
    private String IdPenyakit;
    private String IdPengetahuan;
    private int Id;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getIdGejala() {
        return IdGejala;
    }

    public void setIdGejala(String idGejala) {
        this.IdGejala = idGejala;
    }

    public String getIdPenyakit() {
        return IdPenyakit;
    }

    public void setIdPenyakit(String idPenyakit) {
        this.IdPenyakit = idPenyakit;
    }

    public String getIdPengetahuan() {
        return IdPengetahuan;
    }

    public void setIdPengetahuan(String idPengetahuan) {
        this.IdPengetahuan = idPengetahuan;
    }
    
    public void Save(int myid, String idpengetahuan, String idpenyakit, String penyakit, String info, String solusi, String idgejala, String gejala, Double nilaicf) {
        con = new koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "insert into pengetahuan(myid, Id,idpenyakit, penyakit, info, solusi, idgejala, gejala, nilaicf)values('" + myid + "','" + idpengetahuan + "','" + idpenyakit + "','" + penyakit + "','" + info + "','" + solusi + "','" + idgejala + "','" + gejala + "','" + nilaicf + "')";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data di Simpan");
        } catch (SQLException e) {
        }
    }

    public void Delete(String idpengetahuan) {
        con = new koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "delete from pengetahuan where Id = '" + idpengetahuan + "'";
            //st.executeQuery("insert into penjualan(nama,kategori)values('"+nama+"','"+kategori+"')");
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data di Hapus");
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
            query = "SELECT COUNT(Id) AS Jumlah FROM pengetahuan";
            res = st.executeQuery(query);
            if (res.next()) {
                jumlahBaris = res.getInt("Jumlah");
            }
            query = "select *from pengetahuan order by Id asc";
            res = st.executeQuery(query);
            data = new String[jumlahBaris][8];
            int r = 0;
            while (res.next()) {
                data[r][0] = res.getString("Id");
                data[r][1] = res.getString("idpenyakit");
                data[r][2] = res.getString("penyakit");
                data[r][3] = res.getString("info");
                data[r][4] = res.getString("solusi");
                data[r][5] = res.getString("idgejala");
                data[r][6] = res.getString("gejala");
                data[r][7] = res.getString("nilaicf");
                r++;
            }
            int jmlBaris = r;
            String[][] tmpArray = data;
            data = new String[jmlBaris][8];
            for (r = 0; r < jmlBaris; r++) {
                for (int c = 0; c < 8; c++) {
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

    public String[][] SearchData(String nama) {

        res = null;
        String[][] data = null;
        con = new koneksi();
        con.connect();
        int jumlahBaris = 0;
        try {
            st = con.conn.createStatement();
            query = "SELECT COUNT(Id) AS pencocokan FROM Gejala";
            res = st.executeQuery(query);
            if (res.next()) {
                jumlahBaris = res.getInt("Jumlah");
            }
            query = "select *from pencocokan where nama like '%" + nama + "%'";
            res = st.executeQuery(query);
            data = new String[jumlahBaris][6];
            int r = 0;
            while (res.next()) {
                data[r][0] = res.getString("idgejala");
                data[r][1] = res.getString("gejala");
                data[r][2] = res.getString("nilai");
                data[r][3] = res.getString("penyakit");
                data[r][4] = res.getString("info");
                data[r][5] = res.getString("solusi");
                r++;
            }
            int jmlBaris = r;
            String[][] tmpArray = data;
            data = new String[jmlBaris][6];
            for (r = 0; r < jmlBaris; r++) {
                for (int c = 0; c < 6; c++) {
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
