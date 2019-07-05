package Form;

import Tool.ConfigDB;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrAkun extends javax.swing.JInternalFrame {
ConfigDB getCnn = new ConfigDB();
    Connection _Cnn;
    
    String sqlselect, sqlinsert, sqldelete;
    String vkd_perkiraan, vgolongan, vtipe, vnama;
    
    DefaultTableModel tblBarang;
    
    public IfrAkun() {
        initComponents();
        
        clearForm();
        disableForm();
        enableForm();
        setTabelAkun();  
        showDataAkun();
        
        btnHapus.setEnabled(false);
    }
    private void clearForm(){
        txtKdPerkiraan.setText("");
        txtNmPerkiraan.setText("");
        cmbGolongan.setSelectedIndex(0);
        cmbTipe.setSelectedIndex(0);
        
    }
      private void disableForm(){
        txtKdPerkiraan.setEnabled(false);
        txtNmPerkiraan.setEnabled(false);
        cmbGolongan.setSelectedIndex(0);
        cmbTipe.setSelectedIndex(0);
        btnHapus.setEnabled(false);
    }
      
       private void enableForm(){
        txtKdPerkiraan.setEnabled(true);
        txtNmPerkiraan.setEnabled(true);
        cmbGolongan.setSelectedIndex(0);
        cmbTipe.setSelectedIndex(0);
        btnHapus.setEnabled(true);
    }
      
       private void setTabelAkun(){
        String[]kolom1 = {"Kode Perkiraan", "Golongna", "Tipe Perkiraan", "Nama Perkiraan"};
        tblBarang= new DefaultTableModel(null,kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class
                    
            };
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            // agar tabel tidak bisa diedit
            public boolean isCellEditable(int row, int col) {
                int cola = tblDataAkun.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tblDataAkun.setModel(tblBarang);
        tblDataAkun.getColumnModel().getColumn(0).setPreferredWidth(75);
        tblDataAkun.getColumnModel().getColumn(1).setPreferredWidth(75);
        tblDataAkun.getColumnModel().getColumn(2).setPreferredWidth(75);
        tblDataAkun.getColumnModel().getColumn(3).setPreferredWidth(75);
       
    }
    private void clearTabelAkun(){
        int row = tblBarang.getRowCount();
        for (int i = 0;i < row;i++){
             tblBarang.removeRow(0);
        }
    }
    
    private void showDataAkun(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabelAkun();
            sqlselect =  "select * from perkiraan_akun order by kd_perkiraan";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vkd_perkiraan = res.getString("kd_perkiraan");
                vgolongan = res.getString("golongan");
                vtipe = res.getString("tipe_perkiraan");
                vnama = res.getString("nm_perkiraan");
                
                Object[]data = {vkd_perkiraan, vgolongan, vtipe, vnama };
                tblBarang.addRow(data); 
                btnTambah.setText("Tambah");
            }
            lblRecord.setText("Record : "+tblDataAkun.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdataSiswa : " + ex);
            }
         
 }
    
    private void aksiSimpan(){
          vkd_perkiraan = txtKdPerkiraan.getText();
          vnama = txtNmPerkiraan.getText();
          vgolongan = cmbGolongan.getSelectedItem().toString();
          vtipe = cmbTipe.getSelectedItem().toString();
          
           
            if(btnSimpan2.getText().equals("Simpan")){
            sqlinsert = "insert into perkiraan_akun values "
                    + " ('"+vkd_perkiraan+"', '"+vgolongan+"', '"+vtipe+"', '"+vnama+"') ";
            
             JOptionPane.showMessageDialog(null,"Data Berhasil Disimpan",
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
           }else{
               sqlinsert = "update perkiraan_akun set golongan ='"+vgolongan+"', tipe_perkiraan='"+vtipe+"', "
                       + " nm_perkiraan='"+vnama+"' where kd_perkiraan = '"+vkd_perkiraan+"'";
               InputSiswa.dispose();
               
                JOptionPane.showMessageDialog(null,"Data Berhasil DiUbah",
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
           try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            Statement state = _Cnn.createStatement();
            state.executeUpdate(sqlinsert);
            JOptionPane.showMessageDialog(this, "Data Berhasil disimpan");
            clearForm(); enableForm(); showDataAkun();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method aksiSimpan() : "+ex);
        } 
              
    }
    
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, 
                "Apakah anda yakin akan menghapus data ini ? ID BARANG : "+vkd_perkiraan,
                "Konfirmasi ",JOptionPane.YES_NO_OPTION);
        if(jawab== JOptionPane.YES_OPTION){
             try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqldelete = "delete from perkiraan_akun where kd_perkiraan= '"+vkd_perkiraan+"'"; 
            java.sql.Statement state = _Cnn.createStatement();
            state.executeUpdate(sqldelete);
           JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
           clearForm();disableForm();showDataAkun();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiHapus : " + ex);
        }
        
    }   
    }
    
    private void getData() {
             try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqlselect = "select * from perkiraan_akun "
                    + " where kd_perkiraan='"+vkd_perkiraan+"'"
                    + " order by kd_perkiraan asc ";        
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                if(res.first()){
                    txtKdPerkiraan.setText(res.getString("kd_perkiraan"));
                    cmbGolongan.setSelectedItem(res.getString("golongan"));
                    cmbTipe.setSelectedItem(res.getString("tipe_perkiraan"));
                    txtNmPerkiraan.setText(res.getString("nm_perkiraan"));
                   
                }   
                btnSimpan2.setText("Ubah Data");
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Eror Method GetDataPaien : " + ex);
            }
             
    }
    
    
    private void cariSiswa(){
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                clearTabelAkun();
                sqlselect ="select * from perkiraan_akun "
                    + " where nm_perkiraan like '%"+txtCari.getText()+"%' order by kd_perkiraan asc ";     
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                while(res.next()){
                    
                    vkd_perkiraan = res.getString("kd_perkiraan");
                    vgolongan =res.getString("golongan");
                    vtipe = res.getString("tipe_perkiraan");
                    vnama = res.getString("nm_perkiraan");
                    Object[]data = { vkd_perkiraan, vgolongan, vtipe, vnama};              
                    tblBarang.addRow(data);   
                }
                lblRecord.setText("Record : "+tblDataAkun.getRowCount());
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Eror Method showDataPasien : " + ex);
            }
    }
    String[] KeyKelas;
//    private void listKelas(){
//        try{
//            _Cnn = null;
//            _Cnn = getCnn.getConnection();
//            sqlselect = "SELECT * FROM dt_barang order by id_barang asc";
//            Statement stat = _Cnn.createStatement();
//            ResultSet res = stat.executeQuery(sqlselect);
//            cmbKelas.removeAllItems();
//            cmbKelas.repaint();
//            cmbKelas.addItem("-- PILIH KELAS --");
//            int i = 1;
//            while(res.next()){
//                cmbKelas.addItem(res.getString("nama_kelas"));
//                i++;
//            }
//            res.first();
//            KeyKelas = new String[i+1];
//            for(Integer x =1;x < i;x++){
//                KeyKelas[x] = res.getString(1);
//                res.next();
//            }
//        } catch (SQLException ex){
//            JOptionPane.showMessageDialog(this, "Error Method listKategori " +ex);
//        }
//    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        InputSiswa = new javax.swing.JFrame();
        jPanel4 = new javax.swing.JPanel();
        txtNmPerkiraan = new javax.swing.JTextField();
        txtKdPerkiraan = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbGolongan = new javax.swing.JComboBox<>();
        cmbTipe = new javax.swing.JComboBox<>();
        btnSimpan2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblRecord = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtCari = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblDataAkun = new javax.swing.JTable();
        jScrollBar1 = new javax.swing.JScrollBar();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Silahkan Input Data Perkiraan"));

        txtNmPerkiraan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNmPerkiraan.setOpaque(false);
        txtNmPerkiraan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNmPerkiraanKeyTyped(evt);
            }
        });

        txtKdPerkiraan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtKdPerkiraan.setOpaque(false);
        txtKdPerkiraan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKdPerkiraanKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setText("Kode Perkiraan");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setText("Golongan");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setText("Tipe Perkiraan");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setText("Nama Perkiraan");

        cmbGolongan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- PILIH GOLONGAN --", "Harta", "Hutang", "Aktiva" }));

        cmbTipe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- PILIH TIPE --", "Header", "Detail" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtKdPerkiraan, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                                    .addComponent(cmbGolongan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cmbTipe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                        .addComponent(txtNmPerkiraan, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtKdPerkiraan, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbGolongan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbTipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNmPerkiraan, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnSimpan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/save-black.png"))); // NOI18N
        btnSimpan2.setText("Simpan");
        btnSimpan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpan2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout InputSiswaLayout = new javax.swing.GroupLayout(InputSiswa.getContentPane());
        InputSiswa.getContentPane().setLayout(InputSiswaLayout);
        InputSiswaLayout.setHorizontalGroup(
            InputSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InputSiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InputSiswaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSimpan2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        InputSiswaLayout.setVerticalGroup(
            InputSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InputSiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSimpan2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("DATA PERKIRAAN");

        lblRecord.setText("Record 0");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Navigasi"));

        txtCari.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCariKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel1.setText("Silahkan Mencari");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(518, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCari, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jLabel1))
                .addGap(6, 6, 6))
        );

        jScrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblDataAkun.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblDataAkun.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Kode Perkiraan", "Golongan", "Tipe Perkiraan", "Nama Perkiraan"
            }
        ));
        tblDataAkun.setRowHeight(25);
        tblDataAkun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataAkunMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblDataAkun);

        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/insert.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/btn_delete.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lblRecord)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 848, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRecord)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        
        if(btnTambah.getText().equals("Tambah")){
        InputSiswa.setVisible(true);
        InputSiswa.setSize(500, 450);
        InputSiswa.setLocationRelativeTo(this);
        enableForm();
        txtKdPerkiraan.requestFocus(true);
        btnSimpan2.setText("Simpan");
        }else{
            btnSimpan2.setText("Ubah Data");
            InputSiswa.setVisible(true);
        InputSiswa.setSize(500, 450);
        InputSiswa.setLocationRelativeTo(this);
        enableForm();
        txtKdPerkiraan.requestFocus(true);
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpan2ActionPerformed
        if(txtKdPerkiraan.getText().equals("")){
            JOptionPane.showMessageDialog(this, "KD Perkiraan harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE); 
        }else if(txtNmPerkiraan.getText().equals("    ")){
            JOptionPane.showMessageDialog(this, "Nama Perkiraan harus diisi",  
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            aksiSimpan();
        }
    }//GEN-LAST:event_btnSimpan2ActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
         if(txtKdPerkiraan.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Belum ada data yang dipilih ! ",
            "Informasi",JOptionPane.INFORMATION_MESSAGE );  
         
        }else{
            aksiHapus();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tblDataAkunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataAkunMouseClicked
    if(evt.getClickCount()==1){
            vkd_perkiraan = tblDataAkun.getValueAt(tblDataAkun.getSelectedRow(), 0).toString();
            btnHapus.setEnabled(true);
            btnTambah.setText("Ubah");
            getData();
            enableForm();
             
           }
    }//GEN-LAST:event_tblDataAkunMouseClicked

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        cariSiswa();
    }//GEN-LAST:event_txtCariKeyTyped

    private void txtNmPerkiraanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNmPerkiraanKeyTyped
        if(txtNmPerkiraan.getText().length() == 35){
            evt.consume();
        }
    }//GEN-LAST:event_txtNmPerkiraanKeyTyped

    private void txtKdPerkiraanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKdPerkiraanKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKdPerkiraanKeyTyped

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame InputSiswa;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan2;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbGolongan;
    private javax.swing.JComboBox<String> cmbTipe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tblDataAkun;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtKdPerkiraan;
    private javax.swing.JTextField txtNmPerkiraan;
    // End of variables declaration//GEN-END:variables
}
