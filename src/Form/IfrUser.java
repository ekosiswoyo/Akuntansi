
package Form;

import Tool.ConfigDB;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrUser extends javax.swing.JInternalFrame {


    ConfigDB getCnn = new ConfigDB();
    Connection _Cnn;
    
    String sqlselect, sqlinsert, sqldelete;
    String vid_user, vusername, vpassword, vlevel, vdeskripsi, vnm_user, mid;
    
    DefaultTableModel tbluser;
    
    public IfrUser() {
        initComponents();
        

        
        Id();
        clearForm();
        disableForm();
        setTabelUser();
        showDataUser();

    }
    
    private void clearForm(){
       txtUsername.setText("");
       txtIdUser.setText("");
       txtNmUser.setText("");
        txtPassword.setText("");
        txtDeskripsi.setText("");
        cmbLevel.setSelectedIndex(0);
    }
    
    private void disableForm(){
        txtUsername.setEnabled(false);
        txtPassword.setEnabled(false);
        txtNmUser.setEnabled(false);
        cmbLevel.setEnabled(false);
        txtDeskripsi.setEnabled(false);
        txtIdUser.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }

     private void enableForm(){
        txtUsername.setEnabled(true);
        txtPassword.setEnabled(true);
        txtDeskripsi.setEnabled(true);
        txtNmUser.setEnabled(true);
        txtIdUser.setEnabled(true);
        cmbLevel.setEnabled(true);
        btnSimpan.setEnabled(true);
        btnHapus.setEnabled(true);
    }
     
   
    private void setTabelUser(){
        String[]kolom1 = {"ID User","Nama Lengkap","Username", "Password", "Deskripsi"};
        tbluser = new DefaultTableModel(null,kolom1){
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
                int cola = tbluser.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tbDataUser.setModel(tbluser);
        tbDataUser.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataUser.getColumnModel().getColumn(1).setPreferredWidth(75);
        tbDataUser.getColumnModel().getColumn(2).setPreferredWidth(75);
        tbDataUser.getColumnModel().getColumn(3).setPreferredWidth(75);
        tbDataUser.getColumnModel().getColumn(4).setPreferredWidth(75);
    }
    
    private void clearTabelUser(){
        int row = tbluser.getRowCount();
        for (int i = 0;i < row;i++){
             tbluser.removeRow(0);
        }
    }
    private void Id(){
         if(btnSimpan.getText().equals("Simpan")){
            try{
                _Cnn = getCnn.getConnection();
                String id = "select max(right(id_supplier,1)) as id_supplier from dt_supplier";
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(id);
                while(res.next()){
                    if(res.first() == false){
                        mid = "1";
                    } else{
                        res.last();
                        int noID = res.getInt(1) + 1;
                        String no = String.valueOf(noID);
                        int noLong = no.length();
                        for(int a=0;a<2-noLong;a++){
                            no = "ID" + no;
                        }
                        if(noID < 10){
                            mid =  no;
                        } else if(noID < 100){
                            mid = no;
                        }else if(noID < 1000){
                            mid = no;
                        }else if(noID < 10000){
                            mid = no;
                        }else if(noID < 100000){
                            mid = no;
                        }else if(noID < 1000000){
                            mid = no;    
                        }else if(noID < 10000000){
                            mid = no;
                        }else if(noID < 100000000){
                            mid = no;    
                        }else if(noID < 1000000000){
                            mid = no;  
                         
                        } else{
                            mid= ""+ no;
                        }
                        txtIdUser.setText(mid);
                        }
                   
                }
            } catch(SQLException ex){
                System.out.println("Error Method Id : " + ex);
            }
        }
        //kode jenis
    }
    
     private void showDataUser(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabelUser();
            sqlselect =  "select * from user order by id_user";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vid_user = res.getString("id_user");
                vnm_user = res.getString("nm_user");
                vusername = res.getString("username");
                vpassword = res.getString("password");
                vdeskripsi = res.getString("deskripsi");
                Object[]data = {vid_user,vnm_user, vusername, vpassword, vdeskripsi};
                tbluser.addRow(data);
            }
            lblRecord.setText("Record : "+tbDataUser.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdataUser : " + ex);
            }
    }
     
    private void aksiSimpan(){
          vid_user = txtIdUser.getText();
          vpassword = txtPassword.getText();
          vusername = txtUsername.getText();
          vnm_user = txtNmUser.getText();
          vdeskripsi = txtDeskripsi.getText();
          vlevel = cmbLevel.getSelectedItem().toString();
           if(btnSimpan.getText().equals("Simpan")){
            sqlinsert = "insert into user values "
                    + " ('"+vid_user+"', '"+vnm_user+"','"+vusername+"', '"+vpassword+"','"+vdeskripsi+"',  '"+vlevel+"') ";
            
            JOptionPane.showMessageDialog(this, "Data Berhasil disimpan");
           }else{
               sqlinsert = "update user set nm_user ='"+vnm_user+"', username ='"+vusername+"', password ='"+vpassword+"',  "
                       + " deskripsi ='"+vdeskripsi+"', level='"+vlevel+"' where id_user='"+vid_user+"' ";
                              
               JOptionPane.showMessageDialog(this, "Data Berhasil diUbah");
           }
           try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            Statement state = _Cnn.createStatement();
            state.executeUpdate(sqlinsert);
            
            clearForm(); disableForm(); showDataUser();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method aksiSimpan() : "+ex);
        } 
    }
    
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, 
                "Apakah anda yakin akan menghapus data ini ? Kode : "+vid_user,
                "Konfirmasi ",JOptionPane.YES_NO_OPTION);
        if(jawab== JOptionPane.YES_OPTION){
             try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqldelete = "delete from user where id_user= '"+vid_user+"'"; 
            java.sql.Statement state = _Cnn.createStatement();
            state.executeUpdate(sqldelete);
           JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
           clearForm();disableForm();showDataUser();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiHapus : " + ex);
        }
        
    }
        
    }   
     private void getDataUser() {
             try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqlselect = "select * from user "
                    + " where id_user='"+vid_user+"'"
                    + " order by id_user asc ";        
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                if(res.first()){
                    txtIdUser.setText(res.getString("id_user"));
                    txtNmUser.setText(res.getString("nm_user"));
                    txtUsername.setText(res.getString("username"));
                    txtPassword.setText(res.getString("password"));
                    txtDeskripsi.setText(res.getString("deskripsi"));
                    cmbLevel.setSelectedItem(res.getString("level"));
                }   
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Eror Method GetDataUser : " + ex);
            }
    } 
     
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtPassword = new javax.swing.JPasswordField();
        txtUsername = new javax.swing.JTextField();
        txtIdUser = new javax.swing.JTextField();
        cmbLevel = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDeskripsi = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNmUser = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataUser = new javax.swing.JTable();
        lblRecord = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();

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

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Data Pengguna");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Silahkan Input Data Pengguna :"));
        jPanel1.setOpaque(false);

        txtPassword.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtPassword.setOpaque(false);
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPasswordKeyTyped(evt);
            }
        });

        txtUsername.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtUsername.setOpaque(false);
        txtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUsernameKeyTyped(evt);
            }
        });

        txtIdUser.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cmbLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --", "General Manager", "Manager Keuangan", "Staff Administrasi" }));
        cmbLevel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cmbLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbLevelActionPerformed(evt);
            }
        });

        jLabel1.setText("Nama Lengkap");

        jLabel3.setText("Username");

        jLabel4.setText("Password");

        jLabel5.setText("Level");

        txtDeskripsi.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setText("Deskripsi");

        txtNmUser.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setText("Id User");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel6)
                                .addComponent(jLabel5))
                            .addGap(32, 32, 32))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNmUser)
                            .addComponent(txtPassword)
                            .addComponent(txtUsername)
                            .addComponent(txtDeskripsi, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbLevel, javax.swing.GroupLayout.Alignment.TRAILING, 0, 269, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtIdUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtIdUser, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtNmUser, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbDataUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "ID User", "Nama Lengkap", "Username", "Password", "Deskripsi"
            }
        ));
        tbDataUser.setRowHeight(25);
        tbDataUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataUser);
        if (tbDataUser.getColumnModel().getColumnCount() > 0) {
            tbDataUser.getColumnModel().getColumn(0).setResizable(false);
        }

        lblRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRecord.setText("Record : 0");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(270, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(470, Short.MAX_VALUE)
                .addComponent(lblRecord)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(lblRecord))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
      
        enableForm();
        clearForm();
        txtIdUser.requestFocus(true);
        btnSimpan.setText("Simpan");
 
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(txtUsername.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Username harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE);   
        }else if(txtPassword.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Password harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE);   
        }else if(txtDeskripsi.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Deskripsi harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE);   
        }else if(cmbLevel.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Level User harus diisi",  
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
            
       
        }else{
            aksiSimpan();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
      
         if(txtUsername.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Belum ada data yang dipilih ! ",
            "Informasi",JOptionPane.INFORMATION_MESSAGE );  
         
        }else{
            aksiHapus();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tbDataUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataUserMouseClicked
          if(evt.getClickCount()==2){
            vid_user = tbDataUser.getValueAt(tbDataUser.getSelectedRow(), 0).toString();
               getDataUser();
               
            
            btnSimpan.setText("Ubah");
            enableForm();
        }
    }//GEN-LAST:event_tbDataUserMouseClicked

    private void txtPasswordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyTyped
      if(txtPassword.getText().length() == 20){
         evt.consume();
     }
    }//GEN-LAST:event_txtPasswordKeyTyped

    private void txtUsernameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernameKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameKeyTyped

    private void cmbLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbLevelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbLevelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbLevel;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tbDataUser;
    private javax.swing.JTextField txtDeskripsi;
    private javax.swing.JTextField txtIdUser;
    private javax.swing.JTextField txtNmUser;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
