    
package Form;

import Tool.ConfigDB;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrKasMasuk extends javax.swing.JInternalFrame {


    
    ConfigDB getCnn = new ConfigDB();
    Connection _Cnn;
    
    String sqlselect, sqlinsert, sqldelete, sqldebet, sqlkredit;
    String vid_transaksi, vakun,vnamaakun, vtanggal, vketerangan, vnominal, mid;
    
    DefaultTableModel tblkas;
    DecimalFormat uang_indo = new DecimalFormat("Rp #,##0.00");
    SimpleDateFormat tglview = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat tglinput = new SimpleDateFormat("dd-MM-yyyy");
    public IfrKasMasuk() {
        initComponents();
        
        Id();
        clearForm();
        disableForm();
        setTabel();
        showData();
        listSum();
    }
    
    private void clearForm(){
       
        txtIdTransaksi.setText("");
        txtNominal.setText("");
        txtKeterangan.setText("");
        dtKas.setDate(new java.util.Date());
        cmbIdAkun.setSelectedIndex(0);
    }
    
    private void disableForm(){
        txtIdTransaksi.setEnabled(false);
        txtNominal.setEnabled(false);
        txtKeterangan.setEnabled(false);
        dtKas.setEnabled(false);
        cmbIdAkun.setSelectedIndex(0);
        btnSimpan.setEnabled(false);
//        btnHapus.setEnabled(false);
    }

     private void enableForm(){
        txtIdTransaksi.setEnabled(true);
        txtNominal.setEnabled(true);
        txtKeterangan.setEnabled(true);
        dtKas.setEnabled(true);
        btnSimpan.setEnabled(true);
        cmbIdAkun.setSelectedIndex(0);
//        btnHapus.setEnabled(true);
    }
     
   
    private void setTabel(){
        String[]kolom1 = {"ID Transaksi", "ID Akun" , "Nama Perkiraan", "Tanggal Nota", "Keterangan", "Nominal"};
        tblkas = new DefaultTableModel(null,kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
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
                int cola = tblkas.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tbDataKas.setModel(tblkas);
        tbDataKas.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataKas.getColumnModel().getColumn(1).setPreferredWidth(75);
        tbDataKas.getColumnModel().getColumn(2).setPreferredWidth(75);
        tbDataKas.getColumnModel().getColumn(3).setPreferredWidth(75);
        tbDataKas.getColumnModel().getColumn(4).setPreferredWidth(75);
        tbDataKas.getColumnModel().getColumn(4).setPreferredWidth(75);
    }
    
    private void clearTabel(){
        int row = tblkas.getRowCount();
        for (int i = 0;i < row;i++){
             tblkas.removeRow(0);
        }
    }
    
     private void showData(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabel();
            sqlselect =  "select * from kas_masuk a, perkiraan_akun b where a.id_akun=b.id_akun order by a.no_transaksi asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vid_transaksi = res.getString("no_transaksi");
                vakun = res.getString("id_akun");
                vnamaakun = res.getString("nm_perkiraan");
                vtanggal = res.getString("tgl_nota");
                vketerangan = res.getString("keterangan");
                vnominal =  uang_indo.format(res.getDouble("nominal"));
               
                
                Object[]data = {vid_transaksi, vakun,vnamaakun, vtanggal,  vketerangan, vnominal};
                tblkas.addRow(data);
            }Id();
                 btnTambah.setText("Tambah");
            lblRecord.setText("Record : "+tbDataKas.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdataUser : " + ex);
            }
    }
    private void aksiSimpan(){
          vid_transaksi = txtIdTransaksi.getText();
          vakun = KeySum[cmbIdAkun.getSelectedIndex()];
          vtanggal = tglinput.format(dtKas.getDate());
          vketerangan = txtKeterangan.getText();
          vnominal = txtNominal.getText();
           if(btnSimpan.getText().equals("Simpan")){
            sqlinsert = "insert into kas_masuk values "
                        + " ('"+vid_transaksi+"', '"+vtanggal+"', '"+vakun+"',  '"+vketerangan+"','"+vnominal+"') ";
            sqldebet = "insert into jurnal_umum values "
                    + " ('"+vid_transaksi+"','1-1001', '"+vnominal+"', '0', '"+vtanggal+"') ";
            sqlkredit = "insert into jurnal_umum values "
                    + " ('"+vid_transaksi+"','"+vakun+"',  '0','"+vnominal+"', '"+vtanggal+"') ";
            JOptionPane.showMessageDialog(this, "Data Berhasil disimpan");
           }/*else{
               sqlinsert = "update kas_masuk set tgl_nota ='"+vtanggal+"', id_akun ='"+vakun+"',  keterangan= '"+vketerangan+"', nominal = '"+vnominal+"' where no_transaksi='"+vid_transaksi+"' ";
                              
               JOptionPane.showMessageDialog(this, "Data Berhasil diUbah");
           }*/
           try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            Statement state = _Cnn.createStatement();
            state.executeUpdate(sqlinsert);
            state.executeUpdate(sqldebet);
             state.executeUpdate(sqlkredit);
            clearForm(); disableForm(); showData();Id();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method aksiSimpan() : "+ex);
        } 
    }
    
    /*private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, 
                "Apakah anda yakin akan menghapus data ini ? ID : "+vid_transaksi,
                "Konfirmasi ",JOptionPane.YES_NO_OPTION);
        if(jawab== JOptionPane.YES_OPTION){
             try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqldelete = "delete from kas_masuk where no_transaksi= '"+vid_transaksi+"'"; 
            java.sql.Statement state = _Cnn.createStatement();
            state.executeUpdate(sqldelete);
           JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
           clearForm();disableForm();showData();Id();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiHapus : " + ex);
        }
        
    }
        
    }*/   
    
     private void Id(){
        //kode jenis
        if(btnSimpan.getText().equals("Simpan")){
            try{
                _Cnn = getCnn.getConnection();
                String id = "select max(right(no_transaksi,3)) as no_transaksi from kas_masuk";
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(id);
               while(res.next()){
                    if(res.first() == false){
                        mid = "TRANS-KM-" + "001";
                    } else{
                        res.last();
                        int noID = res.getInt(1) + 1;
                        String no = String.valueOf(noID);
//                        int noLong = no.length();
//                        for(int a=0;a<2-noLong;a++){
//                            no = "TRANS-PNJ-" + no;
//                        }
                        if(noID < 10){
                            mid =  "TRANS-KM-" + "00" + no;
                        } else if(noID < 100){
                            mid = "TRANS-KM-" + "0" + no;
                        } else{
                            mid= "TRANS-KM-" + no;
                        }
                        txtIdTransaksi.setText(mid);
                        }
                   
                }
            } catch(SQLException ex){
                System.out.println("Error Method Id : " + ex);
            }
        }
        //kode jenis
    }
     
     private void getData() {
             try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqlselect = "select * from kas_masuk "
                    + " where no_transaksi='"+vid_transaksi+"'"
                    + " order by no_transaksi asc ";        
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                if(res.first()){
                    txtIdTransaksi.setText(res.getString("no_transaksi"));
                    cmbIdAkun.setSelectedItem(res.getString("id_akun"));
                    
                    dtKas.setDate(res.getDate("tgl_nota"));
                    txtKeterangan.setText(res.getString("keterangan"));
                    txtNominal.setText(res.getString("nominal"));

                }   
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Eror Method GetDataUser : " + ex);
            }
    } 
    String[] KeySum;
    private void listSum(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "SELECT * FROM perkiraan_akun order by id_akun asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbIdAkun.removeAllItems();
            cmbIdAkun.repaint();
            cmbIdAkun.addItem("-- PILIH AKUN --");
            int i = 1;
            while(res.next()){
                cmbIdAkun.addItem(res.getString("nm_perkiraan"));
                i++;
            }
            res.first();
            KeySum = new String[i+1];
            for(Integer x =1;x < i;x++){
                KeySum[x] = res.getString(1);
                res.next();
            }
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method listSum " +ex);
        }
    }
 
     private void cariKasMas(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabel();
            sqlselect =  "select * from kas_masuk a, perkiraan_akun b where a.id_akun=b.id_akun like '%"+txtCari.getText()+"%' order by a.no_transaksi asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vid_transaksi = res.getString("no_transaksi");
                vakun = res.getString("id_akun");
                vnamaakun = res.getString("nm_perkiraan");
                vtanggal = res.getString("tgl_nota");
                vketerangan = res.getString("keterangan");
                vnominal = res.getString("nominal");
                
                Object[]data = {vid_transaksi, vakun,vnamaakun, vtanggal,  vketerangan, vnominal};
                tblkas.addRow(data);
            }
            lblRecord.setText("Record : "+tbDataKas.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdataUser : " + ex);
            }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtIdTransaksi = new javax.swing.JTextField();
        cmbIdAkun = new javax.swing.JComboBox<String>();
        txtNominal = new javax.swing.JTextField();
        txtKeterangan = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dtKas = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataKas = new javax.swing.JTable();
        lblRecord = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

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
        jLabel2.setText(" Data Penerimaan Kas");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 11, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtIdTransaksi.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtIdTransaksi.setOpaque(false);
        txtIdTransaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdTransaksiKeyTyped(evt);
            }
        });

        cmbIdAkun.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtNominal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNominal.setOpaque(false);
        txtNominal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNominalKeyTyped(evt);
            }
        });

        txtKeterangan.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("ID Transaksi");

        jLabel3.setText("ID Akun");

        jLabel4.setText("Tanggal");

        jLabel5.setText("Keterangan");

        jLabel6.setText("Nominal");

        dtKas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dtKas.setOpaque(false);

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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(txtIdTransaksi)
                                        .addGap(10, 10, 10))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cmbIdAkun, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addContainerGap())))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(dtKas, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                                    .addComponent(txtKeterangan)
                                    .addComponent(txtNominal, javax.swing.GroupLayout.Alignment.LEADING))
                                .addContainerGap())))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbIdAkun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(dtKas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNominal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 37, -1, 183));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Navigasi"));
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

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setText("Silahkan Mencari");

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

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh.png"))); // NOI18N
        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 225, -1, -1));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbDataKas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Transaksi", "ID Akun", "Nama Perkiraan", "Tanggal", "Keterangan", "Nominal"
            }
        ));
        tbDataKas.setRowHeight(25);
        tbDataKas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataKasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataKas);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 309, 644, 129));

        lblRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRecord.setText("Record : 0");
        getContentPane().add(lblRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 444, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/akuntansi (1).jpg"))); // NOI18N
        jLabel8.setText("jLabel8");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 660, 490));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
      
        
        enableForm();
        clearForm();
        Id();
        txtIdTransaksi.requestFocus(true);
        btnSimpan.setText("Simpan");
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(txtIdTransaksi.getText().equals("")){
            JOptionPane.showMessageDialog(this, "NO Transaksi harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE);  
        }else if(cmbIdAkun.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(this, "ID Akun harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE); 
        }else{
            aksiSimpan();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void tbDataKasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataKasMouseClicked
        /*  if(evt.getClickCount()==2){
            vid_transaksi = tbDataKas.getValueAt(tbDataKas.getSelectedRow(), 0).toString();
               getData();
               
            
            btnSimpan.setText("Ubah");
            enableForm();
        }*/
    }//GEN-LAST:event_tbDataKasMouseClicked

    private void txtIdTransaksiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdTransaksiKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdTransaksiKeyTyped

    private void txtNominalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNominalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNominalKeyTyped

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        cariKasMas();
    }//GEN-LAST:event_txtCariKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        clearForm();
        disableForm();

        setTabel();
        showData();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbIdAkun;
    private com.toedter.calendar.JDateChooser dtKas;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tbDataKas;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtIdTransaksi;
    private javax.swing.JTextField txtKeterangan;
    private javax.swing.JTextField txtNominal;
    // End of variables declaration//GEN-END:variables
}
