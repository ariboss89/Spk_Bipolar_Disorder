/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.koneksi;
import Model.CertaintyFactor;
import Model.Deteksi;
import Model.ModelTabel;
import Model.SignIn;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author User
 */
public class FormDiagnosa extends javax.swing.JFrame {

    ModelTabel model = new ModelTabel();
    Deteksi deteksi = new Deteksi();
    SignIn login = new SignIn();
    CertaintyFactor cf = new CertaintyFactor();
    private String[][] rs;
    String[] namaKolom = {"Nama Penyakit", "Nama Gejala", "Nilai CF", "CF Old"};
    int jmlKolom = namaKolom.length;
    int[] lebar = {200, 200, 300, 300};

    /**
     * Creates new form FormLogin
     */
    public FormDiagnosa() {
        initComponents();
        setLocationRelativeTo(this);
        Refresh();
        cbJawaban.setEnabled(true);
        btnConfirm.setEnabled(true);
        btnSelesai.setEnabled(true);
        lblExit.setEnabled(true);
    }

    private void IdRiwayat() {
        java.sql.Connection conn = new koneksi().connect();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from history order by idriwayat DESC");
            if (rs.first() == false) {
                deteksi.setIdriwayat("R0001");
            } else {
                rs.first();
                System.out.println("COT: " + rs.getString("idriwayat").substring(4, 5));
                int nokirim = Integer.valueOf(rs.getString("idriwayat").substring(3, 5)) + 1;
                System.out.println(nokirim);
                if (nokirim < 10) {
                    deteksi.setIdriwayat("R000" + nokirim);
                } else if (nokirim >= 10 && nokirim < 100) {
                    deteksi.setIdriwayat("R00" + nokirim);
                } else if (nokirim >= 100 && nokirim < 1000) {
                    deteksi.setIdriwayat("R0" + nokirim);
                } else if (nokirim >= 1000 && nokirim < 10000) {
                    deteksi.setIdriwayat("R" + nokirim);
                }
            }
            rs.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
        }
    }

    private void Refresh() {
        IdRiwayat();
        Show();
        String id = deteksi.GetId();
        ShowTable();
        txtKesimpulan.setText("");
        ShowGejala(id);
        cbJawaban.setSelectedItem("PILIH JAWABAN");
    }

    private void ShowTable() {
        String idriwayat = deteksi.getIdriwayat();
        rs = deteksi.ShowData(idriwayat);
        model.SetTabel(jTable1, rs, namaKolom, jmlKolom, lebar);
    }

    private void ShowTableByPenyakit() {
        String idriwayat = deteksi.getIdriwayat();
        String penyakit = deteksi.getPenyakit();
        rs = deteksi.ShowDataByPenyakit(idriwayat, penyakit);
        model.SetTabel(jTable1, rs, namaKolom, jmlKolom, lebar);
    }

    private void Show() {
        java.sql.Connection conn = new koneksi().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select *from pengetahuan order by Id ASC");
            while (res.next()) {
                int nokirim = Integer.valueOf(res.getString("Id").substring(2, 4));
                String a = String.valueOf(nokirim);
                deteksi.SetId(a);
                String Idriwayat = deteksi.getIdriwayat();
            }
        } catch (SQLException ex) {

        }
    }

    private void ShowGejala(String id) {

        id = deteksi.GetId();
        java.sql.Connection conn = new koneksi().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select *from pengetahuan where myid = '" + id + "'");
            while (res.next()) {
                txtPertanyaan.setText("Apakah anda " + res.getString("gejala") + " ?");
                deteksi.setIdpenyakit(res.getString("idpenyakit"));
                deteksi.setPenyakit(res.getString("penyakit"));
                deteksi.setIdgejala(res.getString("idgejala"));
                deteksi.setGejala(res.getString("gejala"));
                cf.setNilaiCF(res.getDouble("nilaicf"));
            }
        } catch (SQLException ex) {

        }
    }

    private void Rumus1() {
        java.sql.Connection conn = new koneksi().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select *from detailhistory order by Id ASC");
            while (res.next()) {
                cf.setCf1(res.getDouble("nilaicf"));
                deteksi.setPenyakit2(res.getString("penyakit"));
            }
        } catch (SQLException ex) {

        }
    }

    private void Rumus2() {
        java.sql.Connection conn = new koneksi().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select *from detailhistory order by Id ASC");
            while (res.next()) {
                cf.setCfOld(res.getDouble("cfold"));
                deteksi.setPenyakit2(res.getString("penyakit"));
            }
        } catch (SQLException ex) {

        }
    }

    private void ShowPertanyaan() {
        int a = Integer.parseInt(deteksi.GetId());
        int b = a - 1;
        String c = String.valueOf(b);
        ShowGejala(c);
        deteksi.SetId(c);
        ShowGejala(c);

        if (b <= 0) {
            btnConfirm.setEnabled(false);
        }
    }

    private void CountAndMAx() {
        java.sql.Connection conn = new koneksi().connect();
        try {
            String sql = "SELECT max(cfold) as maximumValue FROM detailhistory where idriwayat = '" + deteksi.getIdriwayat() + "' ";
            java.sql.Statement stmt = conn.createStatement();
            //java.sql.ResultSet res = stmt.executeQuery(sql);
            java.sql.ResultSet res = stmt.executeQuery("SELECT max(cfold) as maximumValue, penyakit as penyakit FROM detailhistory where idriwayat = '" + deteksi.getIdriwayat() + "'");
            if (res.next()) {
                cf.setMaxValue(res.getDouble("maximumValue"));
                deteksi.setPenyakit(res.getString("penyakit"));

            }
        } catch (SQLException ex) {

        }
    }

    private void Count() {
        java.sql.Connection conn = new koneksi().connect();
        try {
            String sql = "SELECT count(idriwayat) as jumlah FROM detailhistory where idriwayat = '" + deteksi.getIdriwayat() + "' ";
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery(sql);
            //java.sql.ResultSet res = stmt.executeQuery("SELECT max(cfold) as maximumValue, penyakit as penyakit FROM detailhistory where idriwayat = '" + deteksi.getIdriwayat() + "'");
            if (res.next()) {
                deteksi.setJumlah(res.getInt("jumlah"));
            }
        } catch (SQLException ex) {

        }
    }

    private void ShowHasilAkhir() {
        java.sql.Connection conn = new koneksi().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select *from penyakit where nama = '" + deteksi.getPenyakit() + "'");
            while (res.next()) {
                deteksi.setInfo(res.getString("info"));
                deteksi.setSolusi(res.getString("solusi"));

                Double a = cf.getMaxValue();
                Double hitung = a * 100;
                String convert = String.format("%.2f", hitung);
                Double todouble = Double.parseDouble(convert);

                if (todouble > 50) {
                    String kesimpulan = "Anda Terkena Bipolar dengan jenis " + deteksi.getPenyakit() + " dengan presentase sebesar " + convert + " %, dan solusinya ialah " + deteksi.getSolusi();
                    deteksi.setKesimpulan(kesimpulan);
                    txtKesimpulan.setText(kesimpulan);
                } else {
                    String kesimpulan = "Anda tidak mendiagnosa jenis Bipolar apapun";
                    deteksi.setInfo("Anda tidak terkena Bipolar apapun");
                    deteksi.setSolusi("Tidak Ada Solusi dikarenakan Anda Tidak Mendiagnosa Bipolar Jenis Apapun");
                    deteksi.setKesimpulan(kesimpulan);
                    txtKesimpulan.setText(deteksi.getKesimpulan());
                }
            }
        } catch (SQLException ex) {

        }
    }

    private void cetak() {
        ShowAlamat();
        java.sql.Connection conn = new koneksi().connect();

        try {
            HashMap parameter = new HashMap();
            File file = new File("src/Report/HasilDiagnosa.jasper");
            String nama = login.GetNama();
            String alamat = login.GetAlamat();
            String idriwayat = deteksi.getIdriwayat();
            parameter.put("nama", nama);
            parameter.put("alamat", alamat);
            parameter.put("idriwayat", idriwayat);
            JasperReport jp = (JasperReport) JRLoader.loadObject(file);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jp, parameter, conn);
            JasperViewer.viewReport(jasperPrint, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
            Refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void ShowAlamat() {
        java.sql.Connection conn = new koneksi().connect();
        try {
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery("select *from user where username = '" + SignIn.getUsername() + "'");
            if (res.next()) {
                login.SetNama(res.getString("nama"));
                login.SetAlamat(res.getString("alamat"));
            }
        } catch (SQLException ex) {

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblExit = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtPertanyaan = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        cbJawaban = new javax.swing.JComboBox();
        btnConfirm = new javax.swing.JButton();
        btnSelesai = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtKesimpulan = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        btnCetak = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(102, 255, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        lblExit.setBackground(new java.awt.Color(153, 255, 204));
        lblExit.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblExit.setForeground(new java.awt.Color(102, 255, 204));
        lblExit.setText("X");
        lblExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExitMouseClicked(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(153, 255, 204));
        jLabel4.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 255, 204));
        jLabel4.setText("FORM PENYAKIT");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblExit))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblExit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(153, 255, 204));
        jPanel3.setForeground(new java.awt.Color(102, 255, 204));

        jTable1.setBackground(new java.awt.Color(102, 255, 204));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel7.setBackground(new java.awt.Color(172, 255, 112));
        jLabel7.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel7.setText("Silahkan Jawab Pertanyaan di Bawah ini");

        txtPertanyaan.setEditable(false);
        txtPertanyaan.setBackground(new java.awt.Color(153, 255, 204));
        txtPertanyaan.setColumns(20);
        txtPertanyaan.setLineWrap(true);
        txtPertanyaan.setRows(5);
        jScrollPane3.setViewportView(txtPertanyaan);

        jPanel5.setBackground(new java.awt.Color(153, 255, 204));
        jPanel5.setForeground(new java.awt.Color(102, 255, 204));

        cbJawaban.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbJawaban.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PILIH JAWABAN", "SANGAT YAKIN", "YAKIN", "CUKUP YAKIN", "SEDIKIT YAKIN", "TIDAK TAHU", "TIDAK" }));
        cbJawaban.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbJawabanItemStateChanged(evt);
            }
        });

        btnConfirm.setBackground(new java.awt.Color(51, 0, 204));
        btnConfirm.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        btnConfirm.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirm.setText("CONFIRM");
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });

        btnSelesai.setBackground(new java.awt.Color(0, 255, 102));
        btnSelesai.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        btnSelesai.setForeground(new java.awt.Color(255, 255, 255));
        btnSelesai.setText("SELESAI");
        btnSelesai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelesaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbJawaban, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnConfirm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSelesai)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbJawaban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirm)
                    .addComponent(btnSelesai))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        txtKesimpulan.setEditable(false);
        txtKesimpulan.setBackground(new java.awt.Color(153, 255, 204));
        txtKesimpulan.setColumns(20);
        txtKesimpulan.setLineWrap(true);
        txtKesimpulan.setRows(5);
        jScrollPane4.setViewportView(txtKesimpulan);

        jLabel8.setBackground(new java.awt.Color(172, 255, 112));
        jLabel8.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel8.setText("KESIMPULAN");

        btnCetak.setBackground(new java.awt.Color(255, 51, 51));
        btnCetak.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        btnCetak.setForeground(new java.awt.Color(255, 255, 255));
        btnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/printer.png"))); // NOI18N
        btnCetak.setText("CETAK HASIL DIAGNOSA");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane4)
                            .addComponent(btnCetak, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 962, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCetak))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_lblExitMouseClicked

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmActionPerformed
        // TODO add your handling code here:
        if (cbJawaban.getSelectedItem().equals("PILIH JAWABAN")) {
            JOptionPane.showMessageDialog(null, "SIlahkan Pilih Jawaban Terlebih Dahulu");
            cbJawaban.requestFocus();
        } else {
            int rowCount = jTable1.getRowCount();
            String Idriwayat = deteksi.getIdriwayat();
            String penyakit = deteksi.getPenyakit();
            String penyakit2 = deteksi.getPenyakit2();
            String gejala = deteksi.getGejala();
            String jawaban = deteksi.getJawaban();
            Double nilaicfxpakar = cf.getCfxPakar();
            Double cfold = cf.getCfOld();

            if (rowCount == 0) {
                deteksi.Save(Idriwayat, penyakit, gejala, jawaban, nilaicfxpakar, 0.0);
                ShowTableByPenyakit();
                cbJawaban.setSelectedIndex(0);
                ShowPertanyaan();
                Rumus1();
                Rumus2();
            } else if (rowCount == 1) {
                Double cf1 = cf.getCf1();
                Double cf2 = cf.getCfxPakar();
                cf.getHitungCfOld(cf1, cf2);
                deteksi.Save(Idriwayat, penyakit, gejala, jawaban, nilaicfxpakar, cf.getCfOld());
                ShowTableByPenyakit();
                cbJawaban.setSelectedIndex(0);
                ShowPertanyaan();
                Rumus1();
                Rumus2();
            } else if (rowCount == 2) {
                Double cfold1 = cf.getCfOld();
                Double cf2 = cf.getCfxPakar();
                cf.getHitungCfOld(cfold1, cf2);
                deteksi.Save(Idriwayat, penyakit, gejala, jawaban, nilaicfxpakar, cf.getCfOld());
                ShowTableByPenyakit();
                cbJawaban.setSelectedIndex(0);
                ShowPertanyaan();
                Rumus1();
                Rumus2();
            } else if (rowCount > 2 && penyakit.equals(penyakit2)) {
                Double cf3 = cf.getCfxPakar();
                cf.getHitungCfOld2(cf3);
                deteksi.Save(Idriwayat, penyakit, gejala, jawaban, cf3, cf.getCfOld2());
                ShowTableByPenyakit();
                cbJawaban.setSelectedIndex(0);
                ShowPertanyaan();
                Rumus1();
                Rumus2();
            } else if (rowCount > 2 && !penyakit.equals(penyakit2)) {
                deteksi.Save(Idriwayat, penyakit, gejala, jawaban, nilaicfxpakar, 0.0);
                ShowTableByPenyakit();
                cbJawaban.setSelectedIndex(0);
                ShowPertanyaan();
                Rumus1();
                Rumus2();
            } else if (rowCount > 2 && penyakit.equals(penyakit2) && cf.getCfOld() == 0.0) {
                Rumus1();
                Double cf1 = cf.getCf1();
                Double cf2 = cf.getCfxPakar();
                cf.getHitungCfOld(cf1, cf2);
                deteksi.Save(Idriwayat, penyakit, gejala, jawaban, nilaicfxpakar, cf.getCfOld());
                ShowTableByPenyakit();
                cbJawaban.setSelectedIndex(0);
                ShowPertanyaan();
                Rumus1();
                Rumus2();
            }
        }
    }//GEN-LAST:event_btnConfirmActionPerformed

    private void btnSelesaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelesaiActionPerformed
        // TODO add your handling code here:
        int count = jTable1.getRowCount();
        if (count == 1) {
            JOptionPane.showMessageDialog(null, "Minimal Gejala 2");
            cbJawaban.setSelectedIndex(0);
        } else {
            Count();
            CountAndMAx();
            ShowHasilAkhir();
            String idriwayat = deteksi.getIdriwayat();
            String penyakit = deteksi.getPenyakit();
            int jumlah = deteksi.getJumlah();
            Double a = cf.getMaxValue();
            Double hitung = a * 100;
            String convert = String.format("%.2f", hitung);
            Double nilai = Double.parseDouble(convert);
            String kesimpulan = deteksi.getKesimpulan();
            String info = deteksi.getInfo();
            String solusi = deteksi.getSolusi();
            txtKesimpulan.setText(deteksi.getKesimpulan());

            deteksi.SaveRiwayat(idriwayat, penyakit, jumlah, nilai, kesimpulan, info, solusi);
            cbJawaban.setEnabled(false);
            btnConfirm.setEnabled(false);
            btnSelesai.setEnabled(false);
            lblExit.setEnabled(false);
        }
    }//GEN-LAST:event_btnSelesaiActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        // TODO add your handling code here:
        cbJawaban.setEnabled(true);
        btnConfirm.setEnabled(true);
        btnSelesai.setEnabled(true);
        lblExit.setEnabled(true);
        cetak();
    }//GEN-LAST:event_btnCetakActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void cbJawabanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbJawabanItemStateChanged
        // TODO add your handling code here:
        Double nilaicf = cf.getNilaiCF();
        String jawaban = cbJawaban.getSelectedItem().toString();

        if (cbJawaban.getSelectedItem().equals("SANGAT YAKIN")) {
            Double cfpakar = 1.0;
            Double a = cf.PerhitunganCFxPakar(nilaicf, cfpakar);
            cf.setCfxPakar(a);
            deteksi.setJawaban(jawaban);
        } else if (cbJawaban.getSelectedItem().equals("YAKIN")) {
            Double cfpakar = 0.8;
            Double a = cf.PerhitunganCFxPakar(nilaicf, cfpakar);
            cf.setCfxPakar(a);
            deteksi.setJawaban(jawaban);
        } else if (cbJawaban.getSelectedItem().equals("SEDIKIT YAKIN")) {
            Double cfpakar = 0.6;
            Double a = cf.PerhitunganCFxPakar(nilaicf, cfpakar);
            cf.setCfxPakar(a);
            deteksi.setJawaban(jawaban);
        } else if (cbJawaban.getSelectedItem().equals("CUKUP YAKIN")) {
            Double cfpakar = 0.4;
            Double a = cf.PerhitunganCFxPakar(nilaicf, cfpakar);
            cf.setCfxPakar(a);
            deteksi.setJawaban(jawaban);
        } else if (cbJawaban.getSelectedItem().equals("TIDAK TAHU")) {
            Double cfpakar = 0.2;
            Double a = cf.PerhitunganCFxPakar(nilaicf, cfpakar);
            cf.setCfxPakar(a);
            deteksi.setJawaban(jawaban);
        } else if (cbJawaban.getSelectedItem().equals("TIDAK")) {
            Double cfpakar = 0.0;
            Double a = cf.PerhitunganCFxPakar(nilaicf, cfpakar);
            cf.setCfxPakar(a);
            deteksi.setJawaban(jawaban);
        }
    }//GEN-LAST:event_cbJawabanItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormDiagnosa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormDiagnosa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormDiagnosa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormDiagnosa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormDiagnosa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JButton btnSelesai;
    private javax.swing.JComboBox cbJawaban;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblExit;
    private javax.swing.JTextArea txtKesimpulan;
    private javax.swing.JTextArea txtPertanyaan;
    // End of variables declaration//GEN-END:variables
}
