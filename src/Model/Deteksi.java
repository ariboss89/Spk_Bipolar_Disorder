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
public class Deteksi {

    private koneksi con;
    private Statement st;
    private String query;
    private ResultSet res;
    private String idgejala;
    private String gejala;
    private String idpenyakit;
    private String penyakit;
    private String penyakit2;
    private String info;
    private String solusi;
    private String id;
    private String idriwayat;
    private String jawaban;
    private int rowcount;
    private int jumlah;
    private String kesimpulan;
    
    public String GetId(){
        return id;
    }
    
    public void SetId(String Id){
        id = Id;
    }

    public String getIdriwayat() {
        return idriwayat;
    }

    public void setIdriwayat(String Idriwayat) {
        this.idriwayat = Idriwayat;
    }

    public String getIdpenyakit() {
        return idpenyakit;
    }

    public void setIdpenyakit(String Idpenyakit) {
        this.idpenyakit = Idpenyakit;
    }

    public String getIdgejala() {
        return idgejala;
    }

    public void setIdgejala(String Idgejala) {
        this.idgejala = Idgejala;
    }
    
    public String getGejala() {
        return gejala;
    }
    
    public void setGejala(String aGejala) {
        gejala = aGejala;
    }
    
    public String getPenyakit() {
        return penyakit;
    }
    
    public void setPenyakit(String aPenyakit) {
        penyakit = aPenyakit;
    }

    public String getPenyakit2() {
        return penyakit2;
    }

    public void setPenyakit2(String Penyakit2) {
        this.penyakit2 = Penyakit2;
    }
    
    public String getInfo() {
        return info;
    }
    
    public void setInfo(String aInfo) {
        info = aInfo;
    }
    
    public String getSolusi() {
        return solusi;
    }
    
    public void setSolusi(String aSolusi) {
        solusi = aSolusi;
    }
    
    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String Jawaban) {
        this.jawaban = Jawaban;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int Jumlah) {
        this.jumlah = Jumlah;
    }

    public String getKesimpulan() {
        return kesimpulan;
    }

    public void setKesimpulan(String Kesimpulan) {
        this.kesimpulan = Kesimpulan;
    }
    
    public void Save(String idriwayat, String penyakit, String gejala, String jawaban, Double nilaicf, Double cfold) {
        con = new koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "insert into detailhistory(idriwayat, penyakit, gejala, jawaban, nilaicf, cfold)values('"+idriwayat+"','"+penyakit+"','" + gejala + "','" + jawaban + "','" + nilaicf + "','" + cfold + "')";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Menambahkan Data "+gejala);
        } catch (SQLException e) {
        }
    }
    
    public void SaveRiwayat(String idRiwayat, String penyakit, int jumlahgejala, Double nilai,  String kesimpulan, String info, String solusi) {
        con = new koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "insert into history(idriwayat, penyakit, jumlah, nilai, kesimpulan, info, solusi)values('"+idRiwayat+"', '"+penyakit+"','" + jumlahgejala + "', '" + nilai + "', '" + kesimpulan + "','" + info + "','"+solusi+"')";
            st.executeUpdate(query);
            st.close();
            con.conn.close();
        } catch (SQLException e) {
        }
    }

    public void Delete(String idriwayat) {
        con = new koneksi();
        con.connect();
        try {
            st = con.conn.createStatement();
            query = "delete from history where idriwayat = '" + idriwayat + "'";
            //st.executeQuery("insert into penjualan(nama,kategori)values('"+nama+"','"+kategori+"')");
            st.executeUpdate(query);
            st.close();
            con.conn.close();
            JOptionPane.showMessageDialog(null, "Data di Hapus");
        } catch (SQLException e) {

        }
    }

    public String[][] ShowData(String idriwayat) {

        res = null;
        String[][] data = null;
        con = new koneksi();
        con.connect();
        int jumlahBaris = 0;
        try {
            st = con.conn.createStatement();
            query = "SELECT COUNT(Id) AS Jumlah FROM detailhistory;";
            res = st.executeQuery(query);
            if (res.next()) {
                jumlahBaris = res.getInt("Jumlah");
            }
            query = "select *from detailhistory where idriwayat = '"+idriwayat+"' order by Id asc";
            res = st.executeQuery(query);
            data = new String[jumlahBaris][4];
            int r = 0;
            while (res.next()) {
                data[r][0] = res.getString("penyakit");
                data[r][1] = res.getString("gejala");
                data[r][2] = res.getString("nilaicf");
                data[r][3] = res.getString("cfold");
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
    
    public String[][] ShowDataByPenyakit(String idriwayat, String penyakit) {

        res = null;
        String[][] data = null;
        con = new koneksi();
        con.connect();
        int jumlahBaris = 0;
        try {
            st = con.conn.createStatement();
            query = "SELECT COUNT(Id) AS Jumlah FROM detailhistory;";
            res = st.executeQuery(query);
            if (res.next()) {
                jumlahBaris = res.getInt("Jumlah");
            }
            query = "select *from detailhistory where penyakit = '"+penyakit+"' and idriwayat = '"+idriwayat+"' order by Id asc";
            res = st.executeQuery(query);
            data = new String[jumlahBaris][4];
            int r = 0;
            while (res.next()) {
                data[r][0] = res.getString("penyakit");
                data[r][1] = res.getString("gejala");
                data[r][2] = res.getString("nilaicf");
                data[r][3] = res.getString("cfold");
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
