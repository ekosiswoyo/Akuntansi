
package Form;

import Tool.ConfigDB;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrSupplier extends javax.swing.JInternalFrame {


    ConfigDB getCnn = new ConfigDB();
    Connection _Cnn;
    
    String sqlselect, sqlinsert, sqldelete;
    String vid_supplier, vnm_supplier, valamat, vno_telepon, vno_rekening, mid;
    String visi_awal;
    
    DefaultTableModel tblsupplier;
    
    public IfrSupplier() {
        initComponents();
        

        
        Id();
        clearForm();
        disableForm();
        setTabel();
        showData();
    }
    
    private void clearForm(){
       
        txtIdSupplier.setText("");
        txtNmSupplier.setText("");
        txtAlamat.setText("");
        txtNoTelepon.setText("");
        txtNoRekening.setText("");
    }
    
    private void disableForm(){
        txtIdSupplier.setEnabled(false);
        txtNmSupplier.setEnabled(false);
        txtAlamat.setEnabled(false);
        txtNoTelepon.setEnabled(false);
        txtNoRekening.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }

     private void enableForm(){
        txtIdSupplier.setEnabled(true);
        txtNmSupplier.setEnabled(true);
        txtAlamat.setEnabled(true);
        txtNoTelepon.setEnabled(true);
        txtNoRekening.setEnabled(true);
        btnSimpan.setEnabled(true);
        btnHapus.setEnabled(true);
    }
     
   
    private void setTabel(){
        String[]kolom1 = {"ID. Supplier", "Nama Supplier", "Alamat", "No Telepon", "No Rekening"};
        tblsupplier = new DefaultTableModel(null,kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
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
                int cola = tblsupplier.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tbDataSupplier.setModel(tblsupplier);
        tbDataSupplier.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataSupplier.getColumnModel().getColumn(1).setPreferredWidth(75);
        tbDataSupplier.getColumnModel().getColumn(2).setPreferredWidth(75);
        tbDataSupplier.getColumnModel().getColumn(3).setPreferredWidth(75);
        tbDataSupplier.getColumnModel().getColumn(4).setPreferredWidth(75);
    }
    
    private void clearTabel(){
        int row = tblsupplier.getRowCount();
        for (int i = 0;i < row;i++){
             tblsupplier.removeRow(0);
        }
    }
    
     private void showData(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabel();
            sqlselect =  "select * from dt_supplier order by id_supplier";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vid_supplier = res.getString("id_supplier");
                vnm_supplier = res.getString("nm_supplier");
                valamat = res.getString("alamat");
                vno_telepon = res.getString("no_telepon");
                vno_rekening = res.getString("no_rekening");
                Object[]data = {vid_supplier, vnm_supplier, valamat, vno_telepon, vno_rekening};
                tblsupplier.addRow(data);
            }Id();
                 btnTambah.setText("Tambah");
            lblRecord.setText("Record : "+tbDataSupplier.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method : " + ex);
            }
    }
     
    private void aksiSimpan(){
          vid_supplier = txtIdSupplier.getText();
          vnm_supplier = txtNmSupplier.getText();
          valamat = txtAlamat.getText();
          vno_telepon = txtNoTelepon.getText();
          vno_rekening = txtNoRekening.getText();
           if(btnSimpan.getText().equals("Simpan")){
            sqlinsert = "insert into dt_supplier values "
                    + " ('"+vid_supplier+"', '"+vnm_supplier+"', '"+valamat+"', '"+vno_telepon+"', '"+vno_rekening+"') ";
            
            JOptionPane.showMessageDialog(this, "Data Berhasil disimpan");
           }else{
               sqlinsert = "update dt_supplier set nm_supplier ='"+vnm_supplier+"', alamat ='"+valamat+"',no_telepon ='"+vno_telepon+"',no_rekening ='"+vno_rekening+"' where id_supplier='"+vid_supplier+"' ";
                              
               JOptionPane.showMessageDialog(this, "Data Berhasil diUbah");
           }
           try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            Statement state = _Cnn.createStatement();
            state.executeUpdate(sqlinsert);
            
            clearForm(); disableForm(); showData(); Id();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method aksiSimpan() : "+ex);
        } 
    }
    
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, 
                "Apakah anda yakin akan menghapus data ini ? ID : "+vid_supplier,
                "Konfirmasi ",JOptionPane.YES_NO_OPTION);
        if(jawab== JOptionPane.YES_OPTION){
             try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqldelete = "delete from dt_supplier where id_supplier= '"+vid_supplier+"'"; 
            java.sql.Statement state = _Cnn.createStatement();
            state.executeUpdate(sqldelete);
           JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
           clearForm();disableForm();showData(); Id();
                btnHapus.setEnabled(false);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiHapus : " + ex);
        }
        
    }
        
    }   
     private void getData() {
             try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqlselect = "select * from dt_supplier "
                    + " where id_supplier='"+vid_supplier+"'"
                    + " order by id_supplier asc ";        
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                if(res.first()){
                    visi_awal = res.getString("nm_supplier");
                    txtIdSupplier.setText(res.getString("id_supplier"));
                    txtNmSupplier.setText(res.getString("nm_supplier"));
                    txtAlamat.setText(res.getString("alamat"));
                    txtNoTelepon.setText(res.getString("no_telepon"));
                    txtNoRekening.setText(res.getString("no_rekening"));
                }   
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Eror Method GetDataJur : " + ex);
            }
    } 
   
 private void Id(){
        //kode jenis
       if(btnSimpan.getText().equals("Simpan")){
               
            try{
                _Cnn = getCnn.getConnection();
                String id = "select max(right(id_supplier,3)) as id_supplier from dt_supplier";
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(id);
                while(res.next()){
                    if(res.first() == false){
                        mid = "SUP-" + "001";
                    } else{
                        res.last();
                        int noID = res.getInt(1) + 1;
                        String no = String.valueOf(noID);
//                        int noLong = no.length();
//                        for(int a=0;a<2-noLong;a++){
//                            no = "SUP-" + no;
//                        }
                        if(noID < 10){
                            mid =  "SUP-" + "00" + no;
                        } else if(noID < 100){
                            mid = "SUP-" + "0" + no;
                        } else{
                            mid= "SUP-" + no;
                        }
                        txtIdSupplier.setText(mid);
                        }
                   
                }
            } catch(SQLException ex){
                System.out.println("Error Method Id : " + ex);
            }
        }
        //kode jenis
    }
  private void cariSupplier(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabel(); 
           
            sqlselect =  "select * from dt_supplier where nm_supplier like '%"+txtCari.getText()+"%' order by id_supplier asc ";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vid_supplier = res.getString("id_supplier");
                vnm_supplier = res.getString("nm_supplier");
                valamat = res.getString("alamat");
                vno_telepon = res.getString("no_telepon");
                vno_rekening = res.getString("no_rekening");
                Object[]data = {vid_supplier, vnm_supplier, valamat, vno_telepon, vno_rekening};
                tblsupplier.addRow(data);
            }
            lblRecord.setText("Record : "+tbDataSupplier.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method : " + ex);
            }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtNmSupplier = new javax.swing.JTextField();
        txtIdSupplier = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtAlamat = new javax.swing.JTextField();
        txtNoTelepon = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNoRekening = new javax.swing.JTextField();
        jLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataSupplier = new javax.swing.JTable();
        lblRecord = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        jScrollPane2.setViewportView(jEditorPane1);

        setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Form Input"));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVisible(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Data Supplier");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 13, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtNmSupplier.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNmSupplier.setOpaque(false);
        txtNmSupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNmSupplierKeyTyped(evt);
            }
        });

        txtIdSupplier.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtIdSupplier.setOpaque(false);
        txtIdSupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdSupplierKeyTyped(evt);
            }
        });

        jLabel1.setText("Id Supplier");

        jLabel3.setText("Nama");

        txtAlamat.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtAlamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlamatActionPerformed(evt);
            }
        });

        jLabel4.setText("Alamat");

        jLabel5.setText("No Telepon");

        txtNoRekening.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNoRekening.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNoRekeningKeyTyped(evt);
            }
        });

        jLabel.setText("No Rekening");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNmSupplier)
                            .addComponent(txtAlamat, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtIdSupplier)
                            .addComponent(txtNoTelepon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNoRekening, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtIdSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNmSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNoTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNoRekening, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jLabel))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 53, -1, 218));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Navigasi"));
        jPanel2.setOpaque(false);

        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/insert.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/save-black.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/btn_delete.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh.png"))); // NOI18N
        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel14.setText("Silahkan Mencari");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 284, -1, -1));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbDataSupplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "ID Customer", "Nama Customer", "Alamat", "No Telepon", "No Rekening"
            }
        ));
        tbDataSupplier.setRowHeight(25);
        tbDataSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataSupplierMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataSupplier);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 362, 694, 173));

        lblRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRecord.setText("Record : 0");
        getContentPane().add(lblRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(551, 541, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/akuntansi (2).jpg"))); // NOI18N
        jLabel6.setText("jLabel6");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 580));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
      
        enableForm();
        clearForm();
        Id();
        txtIdSupplier.requestFocus(true);
        btnSimpan.setText("Simpan");
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(txtIdSupplier.getText().equals("")){
            JOptionPane.showMessageDialog(this, "ID Supplier harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE);  
        }else if(txtNmSupplier.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Nama Supplier harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE); 
        }else if(txtAlamat.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Alamat harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE); 
        }else if(txtNoTelepon.getText().equals("")){
            JOptionPane.showMessageDialog(this, "No Telepon harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE); 
        }else if(txtNoTelepon.getText().equals("")){
            JOptionPane.showMessageDialog(this, "No Rekening harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE); 
        }else{
            try {
                String sqlCari = "";
                if(btnSimpan.getText().equals("Simpan")){
                    sqlCari = "select * from dt_supplier where nm_supplier='"+txtNmSupplier.getText()+"'";
                }else{
                    sqlCari = "select * from dt_supplier where nm_supplier='"+txtNmSupplier.getText()+"' "
                            + "and nm_supplier not in ('"+visi_awal+"')";
                }
                Statement s = (Statement)getCnn.getConnection().createStatement();
                ResultSet r = s.executeQuery(sqlCari);
                if(r.next()){
                    JOptionPane.showMessageDialog(this, "Supplier tersebut sudah ada ! ",
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);  
                }else{
                    aksiSimpan();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
      
         if(txtIdSupplier.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Belum ada data yang dipilih ! ",
            "Informasi",JOptionPane.INFORMATION_MESSAGE );  
         
        }else{
            aksiHapus();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tbDataSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataSupplierMouseClicked
          if(evt.getClickCount()==2){
            vid_supplier = tbDataSupplier.getValueAt(tbDataSupplier.getSelectedRow(), 0).toString();
               getData();
               
            
            btnSimpan.setText("Ubah");
            enableForm();
        }
    }//GEN-LAST:event_tbDataSupplierMouseClicked

    private void txtIdSupplierKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdSupplierKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdSupplierKeyTyped

    private void txtNmSupplierKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNmSupplierKeyTyped
        if(txtNmSupplier.getText().length() == 45){
            evt.consume();
        }
    }//GEN-LAST:event_txtNmSupplierKeyTyped

    private void txtAlamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlamatActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        clearForm();
        disableForm();

        setTabel();
        showData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        cariSupplier();
    }//GEN-LAST:event_txtCariKeyTyped

    private void txtNoRekeningKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoRekeningKeyTyped
        // TODO add your handling code here:
        char inputan = evt.getKeyChar();
        if(txtNoRekening.getText().length() > 19){
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtNoRekeningKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton jButton1;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tbDataSupplier;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtIdSupplier;
    private javax.swing.JTextField txtNmSupplier;
    private javax.swing.JTextField txtNoRekening;
    private javax.swing.JTextField txtNoTelepon;
    // End of variables declaration//GEN-END:variables
}
