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
    String vkd_perkiraan, vgolongan, vtipe, vnama, mid;
    String visi_awal;
    
    DefaultTableModel tblAkun;
    
    public IfrAkun() {
        initComponents();
//        Id();
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
        String[]kolom1 = {"Kode Perkiraan", "Golongan", "Tipe Perkiraan", "Nama Perkiraan"};
        tblAkun= new DefaultTableModel(null,kolom1){
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
        tblDataAkun.setModel(tblAkun);
        tblDataAkun.getColumnModel().getColumn(0).setPreferredWidth(75);
        tblDataAkun.getColumnModel().getColumn(1).setPreferredWidth(75);
        tblDataAkun.getColumnModel().getColumn(2).setPreferredWidth(75);
        tblDataAkun.getColumnModel().getColumn(3).setPreferredWidth(75);
       
    }
    private void clearTabelAkun(){
        int row = tblAkun.getRowCount();
        for (int i = 0;i < row;i++){
             tblAkun.removeRow(0);
        }
    }
    
    private void showDataAkun(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
          clearTabelAkun();
            sqlselect =  "select * from perkiraan_akun order by id_akun";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vkd_perkiraan = res.getString("id_akun");
                vgolongan = res.getString("golongan");
                vtipe = res.getString("tipe_perkiraan");
                vnama = res.getString("nm_perkiraan");
                
                Object[]data = {vkd_perkiraan, vgolongan, vtipe, vnama };
              tblAkun.addRow(data); 
                btnTambah.setText("Tambah");
            }
            //Id();
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
                       + " nm_perkiraan='"+vnama+"' where id_akun = '"+vkd_perkiraan+"'";
               InputAkun.dispose();
               
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
            //Id();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method aksiSimpan() : "+ex);
        } 
              
    }
    
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, 
                "Apakah anda yakin akan menghapus data ini ? ID : "+vkd_perkiraan,
                "Konfirmasi ",JOptionPane.YES_NO_OPTION);
        if(jawab== JOptionPane.YES_OPTION){
             try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqldelete = "delete from perkiraan_akun where id_akun= '"+vkd_perkiraan+"'"; 
            java.sql.Statement state = _Cnn.createStatement();
            state.executeUpdate(sqldelete);
           JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
           clearForm();disableForm();showDataAkun();
           //Id();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiHapus : " + ex);
        }
        
    }   
    }
  /*  private void Id(){
        //kode jenis
        if(btnSimpan2.getText().equals("Simpan")){
            try{
                _Cnn = getCnn.getConnection();
                String id = "select max(id_akun) as id_akun from perkiraan_akun";
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(id);
               while(res.next()){
                    if(res.first() == false){
                        mid = " ";
                    } else{
                        res.last();
                        int noID = res.getInt(1) + 1;
                        String no = String.valueOf(noID);
                mid= no;
                        txtKdPerkiraan.setText(mid);
                        }
                   
                }
            } catch(SQLException ex){
                System.out.println("Error Method Id : " + ex);
            }
        }
        //kode jenis
    }*/
   
    private void getData() {
             try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqlselect = "select * from perkiraan_akun "
                    + " where id_akun='"+vkd_perkiraan+"'"
                    + " order by id_akun asc ";        
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                if(res.first()){
                     visi_awal = res.getString("nm_perkiraan");
                    txtKdPerkiraan.setText(res.getString("id_akun"));
                    cmbGolongan.setSelectedItem(res.getString("golongan"));
                    cmbTipe.setSelectedItem(res.getString("tipe_perkiraan"));
                    txtNmPerkiraan.setText(res.getString("nm_perkiraan"));
                   
                }   
                btnSimpan2.setText("Ubah Data");
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Eror Method GetDataPaien : " + ex);
            }
             
    }
    
    
    private void cariAkun(){
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                clearTabelAkun();
                sqlselect ="select * from perkiraan_akun "
                    + " where nm_perkiraan like '%"+txtCari.getText()+"%' order by id_akun asc ";     
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                while(res.next()){
                    
                    vkd_perkiraan = res.getString("id_akun");
                    vgolongan =res.getString("golongan");
                    vtipe = res.getString("tipe_perkiraan");
                    vnama = res.getString("nm_perkiraan");
                    Object[]data = { vkd_perkiraan, vgolongan, vtipe, vnama};              
                    tblAkun.addRow(data);   
                }
                lblRecord.setText("Record : "+tblDataAkun.getRowCount());
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Eror Method showDataPasien : " + ex);
            }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        InputAkun = new javax.swing.JFrame();
        jPanel4 = new javax.swing.JPanel();
        txtNmPerkiraan = new javax.swing.JTextField();
        txtKdPerkiraan = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbGolongan = new javax.swing.JComboBox<String>();
        cmbTipe = new javax.swing.JComboBox<String>();
        btnSimpan2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblRecord = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtCari = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblDataAkun = new javax.swing.JTable();
        jScrollBar1 = new javax.swing.JScrollBar();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        InputAkun.setBackground(new java.awt.Color(51, 102, 255));
        InputAkun.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
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

        cmbGolongan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- PILIH GOLONGAN --", "Harta", "Hutang", "Modal", "Pendapatan", "HPP", "Biaya", "Pendapatan lain-lain", "Biaya Lain-lain" }));

        cmbTipe.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- PILIH TIPE --", "Header", "Detail" }));

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
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbTipe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbGolongan, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtKdPerkiraan, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtNmPerkiraan, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 92, Short.MAX_VALUE)))
                .addContainerGap())
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
                .addContainerGap(38, Short.MAX_VALUE))
        );

        InputAkun.getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        btnSimpan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/save-black.png"))); // NOI18N
        btnSimpan2.setText("Simpan");
        btnSimpan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpan2ActionPerformed(evt);
            }
        });
        InputAkun.getContentPane().add(btnSimpan2, new org.netbeans.lib.awtextra.AbsoluteConstraints(349, 223, 99, 30));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/akun.jpg"))); // NOI18N
        jLabel8.setText("jLabel8");
        InputAkun.getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(-6, -6, 460, 290));

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("DATA PERKIRAAN");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 227, -1));

        lblRecord.setText("Record 0");
        getContentPane().add(lblRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 440, -1, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Navigasi"));
        jPanel1.setOpaque(false);

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

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel1.setText("Silahkan Mencari");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh.png"))); // NOI18N
        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(351, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(34, 34, 34))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addGap(6, 10, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtCari)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, -1, -1));

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
        tblDataAkun.setOpaque(false);
        tblDataAkun.setRowHeight(25);
        tblDataAkun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataAkunMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblDataAkun);

        getContentPane().add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 137, 848, 297));
        getContentPane().add(jScrollBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 62, -1, 407));

        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/insert.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });
        getContentPane().add(btnTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 445, 109, 30));

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/btn_delete.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });
        getContentPane().add(btnHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(765, 445, 93, 30));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/akuntansi (1).jpg"))); // NOI18N
        jLabel7.setText("jLabel7");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 510));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        
        if(btnTambah.getText().equals("Tambah")){
        InputAkun.setVisible(true);
        InputAkun.setSize(500, 450);
        InputAkun.setLocationRelativeTo(this);
        enableForm();
//        Id();
        txtKdPerkiraan.requestFocus(true);
        btnSimpan2.setText("Simpan");
        }else{
            btnSimpan2.setText("Ubah Data");
            InputAkun.setVisible(true);
        InputAkun.setSize(500, 450);
        InputAkun.setLocationRelativeTo(this);
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
              try {
                String sqlCari = "";
                if(btnSimpan2.getText().equals("Simpan")){
                    sqlCari = "select * from perkiraan_akun where nm_perkiraan='"+txtNmPerkiraan.getText()+"'";
                }else{
                    sqlCari = "select * from perkiraan_akun where nm_perkiraan='"+txtNmPerkiraan.getText()+"' "
                            + "and nm_perkiraan not in ('"+visi_awal+"')";
                }
                Statement s = (Statement)getCnn.getConnection().createStatement();
                ResultSet r = s.executeQuery(sqlCari);
                if(r.next()){
                    JOptionPane.showMessageDialog(this, "perkiraan akun tersebut sudah ada ! ",
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);  
                }else{
                    aksiSimpan();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
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
        cariAkun();
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        clearForm();
        disableForm();

        setTabelAkun();
        showDataAkun();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame InputAkun;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan2;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbGolongan;
    private javax.swing.JComboBox<String> cmbTipe;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
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
