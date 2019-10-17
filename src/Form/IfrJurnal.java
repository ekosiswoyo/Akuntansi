
package Form;

import Tool.ConfigDB;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrJurnal extends javax.swing.JInternalFrame {


    ConfigDB getCnn = new ConfigDB();
    Connection _Cnn;
    
    String sqlselect, sqlinsert, sqlinsert1, sqldelete;
    String vno_transaksi, vid_akun, vid_Kredit, vdebet, vkredit, vtgl, mid, vakun;
    
    SimpleDateFormat tglview = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat tglinput = new SimpleDateFormat("yyyy-MM-dd");
    DefaultTableModel tblJurnal;
    DecimalFormat uang_indo = new DecimalFormat("Rp #,##0.00");
    public IfrJurnal() {
        initComponents();
        
      //  Id();
        clearForm();
        disableForm();
         enableForm();
        setTabel();
        showDataSum();
     //   listSum();
    }
    
    private void clearForm(){
       
//        txtNoTransaksi.setText("");
  //      txtKredit.setText("");
    //    txtDebet.setText("");
      //  cmbAkun.setSelectedIndex(0);
        //cmbKredit.setSelectedIndex(0);
        //dtTrans.setDate(new java.util.Date());
    }
    
    private void disableForm(){
        //txtNoTransaksi.setEnabled(false);
   //     txtKredit.setEnabled(false);
     //   txtDebet.setEnabled(false);
       // cmbAkun.setSelectedIndex(0);
       //  cmbKredit.setSelectedIndex(0);
       // dtTrans.setEnabled(false);
       // btnSimpan.setEnabled(false);
      //  btnHapus.setEnabled(false);
    }

     private void enableForm(){
       // txtNoTransaksi.setEnabled(true);
       // txtKredit.setEnabled(true);
      //  txtDebet.setEnabled(true);
        
      //  dtTrans.setEnabled(true);
       // btnSimpan.setEnabled(true);
       // cmbAkun.setSelectedIndex(0);
      //  cmbKredit.setSelectedIndex(0);
//        btnHapus.setEnabled(true);
    }
     
   
    private void setTabel(){
        String[]kolom1 = {"No Transaksi","ID Akun","Nama Akun", "Debet" , "Kredit", "Tanggal Transaksi"};
        tblJurnal= new DefaultTableModel(null,kolom1){
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
                int cola = tblJurnal.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tbDataJurnal.setModel(tblJurnal);
        tbDataJurnal.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataJurnal.getColumnModel().getColumn(1).setPreferredWidth(75);
        tbDataJurnal.getColumnModel().getColumn(2).setPreferredWidth(75);
        tbDataJurnal.getColumnModel().getColumn(3).setPreferredWidth(75);
        tbDataJurnal.getColumnModel().getColumn(4).setPreferredWidth(75);
        tbDataJurnal.getColumnModel().getColumn(4).setPreferredWidth(75);
    }
    
    private void clearTabel(){
        int row = tblJurnal.getRowCount();
        for (int i = 0;i < row;i++){
             tblJurnal.removeRow(0);
        }
    }
    
     private void showDataSum(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabel();
            sqlselect =  "select * from jurnal_umum a, perkiraan_akun b where a.id_akun=b.id_akun order by a.tgl_transaksi, a.no_transaksi asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vno_transaksi = res.getString("no_transaksi");
                vid_akun = res.getString("id_akun");
                vdebet =  uang_indo.format(res.getDouble("debet"));
                vkredit = uang_indo.format(res.getDouble("kredit"));
                vtgl = res.getString("tgl_transaksi");
                vakun = res.getString("nm_perkiraan");
                Object[]data = {vno_transaksi, vid_akun,vakun, vdebet, vkredit, vtgl};
                tblJurnal.addRow(data);
            }//Id();
         //        btnTambah.setText("Tambah");
            lblRecord.setText("Record : "+tbDataJurnal.getRowCount());
            sqlselect =  "select sum(debet),sum(kredit) from jurnal_umum";
            ResultSet res1 = stat.executeQuery(sqlselect);
            while(res1.next()){
                lblDebet.setText("Debet : "+res1.getString("sum(debet)"));
                lblKredit.setText("Kredit : "+res1.getString("sum(kredit)"));
            }
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdatajurnal : " + ex);
            }
    }
  /*  private void aksiSimpan(){
          vno_transaksi = txtNoTransaksi.getText();
          vid_akun = KeySum[cmbAkun.getSelectedIndex()];
          vid_Kredit = KeySum[cmbKredit.getSelectedIndex()];
          vdebet = txtDebet.getText();
          vkredit = txtKredit.getText();
          vtgl = tglinput.format(dtTrans.getDate());
           if(btnSimpan.getText().equals("Simpan")){
            sqlinsert = "insert into jurnal_umum values "
                        + " ('"+vno_transaksi+"','"+vid_akun+"', '"+vdebet+"', '0', '"+vtgl+"') ";
            sqlinsert1 = "insert into jurnal_umum values "
                        + " ('"+vno_transaksi+"','"+vid_Kredit+"', '0', '"+vkredit+"', '"+vtgl+"') ";
            JOptionPane.showMessageDialog(this, "Data Berhasil disimpan");
           }/*else{
               sqlinsert = "update jurnal_umum set id_akun ='"+vid_akun+"', debet ='"+vdebet+"', kredit = '"+vkredit+"', tgl_transaksi = '"+vtgl+"' where no_transaksi ='"+vno_transaksi+"' ";
                              
               JOptionPane.showMessageDialog(this, "Data Berhasil diUbah");
           }
           try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            Statement state = _Cnn.createStatement();
            state.executeUpdate(sqlinsert);
            if(btnSimpan.getText().equals("Simpan")){
             state.executeUpdate(sqlinsert1);
            }
            clearForm(); disableForm(); showDataSum();Id();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method aksiSimpan() : "+ex);
        } 
    }*/
   
  /*  private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, 
                "Apakah anda yakin akan menghapus data ini ? ID : "+vno_transaksi,
                "Konfirmasi ",JOptionPane.YES_NO_OPTION);
        if(jawab== JOptionPane.YES_OPTION){
             try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqldelete = "delete from jurnal_umum where no_transaksi= '"+vno_transaksi+"'"; 
            java.sql.Statement state = _Cnn.createStatement();
            state.executeUpdate(sqldelete);
           JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
           clearForm();disableForm();showDataSum();Id();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiHapus : " + ex);
        }
        
    }
        
    }*/   
    
    /* private void Id(){
        //kode jenis
        if(btnSimpan.getText().equals("Simpan")){
            try{
                _Cnn = getCnn.getConnection();
                String id = "select max(right(no_transaksi,3)) as no_transaksi from jurnal_umum "
                        + "where no_transaksi like 'JUR%'";
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(id);
                while(res.next()){
                    if(res.first() == false){
                        mid = "JUR-" + "001";
                    } else{
                        res.last();
                        int noID = res.getInt(1) + 1;
                        String no = String.valueOf(noID);
//                        int noLong = no.length();
//                        for(int a=0;a<2-noLong;a++){
//                            no = "TRANS-PNJ-" + no;
//                        }
                        if(noID < 10){
                            mid =  "JUR-" + "00" + no;
                        } else if(noID < 100){
                            mid = "JUR-" + "0" + no;
                        } else{
                            mid= "JUR-" + no;
                        }
                        txtNoTransaksi.setText(mid);
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
                sqlselect = "select A.*,B.nm_perkiraan from jurnal_umum as A "
                        + "inner join perkiraan_akun as B on A.id_akun=B.id_akun "
                    + " where A.no_transaksi='"+vno_transaksi+"'"
                    + " order by A.debet asc ";        
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                if(res.first()){
                    txtNoTransaksi.setText(res.getString("no_transaksi"));
                    dtTrans.setDate(res.getDate("tgl_transaksi"));
                    cmbKredit.setSelectedItem(res.getString("nm_perkiraan"));
                    txtKredit.setText(res.getString("kredit"));
                    res.next();
                    cmbAkun.setSelectedItem(res.getString("nm_perkiraan"));
                    txtDebet.setText(res.getString("debet"));
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
            cmbAkun.removeAllItems();
            cmbAkun.repaint();
            cmbAkun.addItem("-- PILIH PERKIRAAN --");
            cmbKredit.removeAllItems();
            cmbKredit.repaint();
            cmbKredit.addItem("-- PILIH PERKIRAAN --");
            int i = 1;
            while(res.next()){
                cmbAkun.addItem(res.getString("nm_perkiraan"));
                cmbKredit.addItem(res.getString("nm_perkiraan"));
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
    }*/
  
     private void cariJurnal(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabel();
             sqlselect =  "select * from jurnal_umum a, perkiraan_akun b where a.id_akun=b.id_akun  like '%\"+txtCari.getText()+\"%' order by a.tgl_transaksi, a.no_transaksi asc";
         
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vno_transaksi = res.getString("no_transaksi");
                vid_akun = res.getString("id_akun");
                vdebet = res.getString("debet");
                vkredit = res.getString("kredit");
                vtgl = res.getString("tgl_transaksi");
                vakun = res.getString("nm_perkiraan");
                Object[]data = {vno_transaksi, vid_akun,vakun, vdebet, vkredit, vtgl};
                tblJurnal.addRow(data);
            }
            lblRecord.setText("Record : "+tbDataJurnal.getRowCount());
            sqlselect =  "select sum(debet),sum(kredit) from jurnal_umum";
            ResultSet res1 = stat.executeQuery(sqlselect);
            while(res1.next()){
                lblDebet.setText("Debet : "+res1.getString("sum(debet)"));
                lblKredit.setText("Kredit : "+res1.getString("sum(kredit)"));
            }
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdataJurnal : " + ex);
            }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataJurnal = new javax.swing.JTable();
        lblRecord = new javax.swing.JLabel();
        lblDebet = new javax.swing.JLabel();
        lblKredit = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

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
        jLabel2.setText("Data Jurnal Umum");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Navigasi"));
        jPanel2.setOpaque(false);

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
                .addContainerGap(317, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton1)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(txtCari)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel7))
                .addGap(0, 5, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 44, -1, -1));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbDataJurnal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "No Transaksi", "ID Akun", "Nama Akun", "Debet", "Kredit", "Tanggal Transaksi"
            }
        ));
        tbDataJurnal.setRowHeight(25);
        tbDataJurnal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataJurnalMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataJurnal);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 114, 714, 298));

        lblRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRecord.setText("Record : 0");
        getContentPane().add(lblRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(431, 542, -1, -1));

        lblDebet.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDebet.setText("Record : 0");
        getContentPane().add(lblDebet, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 542, -1, -1));

        lblKredit.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblKredit.setText("Record : 0");
        getContentPane().add(lblKredit, new org.netbeans.lib.awtextra.AbsoluteConstraints(143, 542, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/akuntansi (1).jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 560));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbDataJurnalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataJurnalMouseClicked
        /*  if(evt.getClickCount()==2){
            vno_transaksi = tbDataJurnal.getValueAt(tbDataJurnal.getSelectedRow(), 0).toString();
               enableForm();
               getData();
               
            
            btnSimpan.setText("Ubah");
           
        }*/
    }//GEN-LAST:event_tbDataJurnalMouseClicked

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        cariJurnal();
    }//GEN-LAST:event_txtCariKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        clearForm();
        disableForm();

        setTabel();
        showDataSum();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDebet;
    private javax.swing.JLabel lblKredit;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tbDataJurnal;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables
}
