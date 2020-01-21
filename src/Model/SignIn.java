/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import Controller.koneksi;
import View.FormDiagnosa;
import View.FormMaster;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class SignIn {
    private koneksi con;
    private Statement st;
    private String query;
    private ResultSet res;
    private static String username;
    private String nama;
    private String alamat;
    private String password;
    private static String status;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String Username) {
        username = Username;
    }
    
    public String GetNama(){
        return nama;
    }
    
    public void SetNama(String Nama){
        nama = Nama;
    }
    
    public String GetAlamat(){
        return alamat;
    }
    
    public void SetAlamat(String Alamat){
        alamat = Alamat;
    }
    
    public String GetPassword(){
        return password;
    }
    
    public void SetPassword(String Password){
        password = Password;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String Status) {
       status = Status;
    }
    
    public void Login(String username, String password) {
        con = new koneksi();
        con.connect();
        try {
            st = con.connect().createStatement();
            res = st.executeQuery("select *from user where username ='" + username + "' And password = '" + password + "'");
            if (res.next()) {
                JOptionPane.showMessageDialog(null, "Welcome " + username);
                new FormMaster().setVisible(true);
               
            } 
            else{
                JOptionPane.showMessageDialog(null, "Username or Password is Wrong");
            }
        } catch (SQLException e) {

        }
    } 
}
