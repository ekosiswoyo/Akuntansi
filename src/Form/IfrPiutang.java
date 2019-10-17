
package Form;

import Tool.ConfigDB;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrPiutang extends javax.swing.JInternalFrame {


    ConfigDB getCnn = new ConfigDB();
    Connection _Cnn;
    
    String sqlselect, sqlinsert, sqldelete, sqlkas, sqldebet, sqlkredit;
    String vid_piutang, vid_karyawan, vjml_piutang, vpotongan, vketerangan, mid, vtgl, vkaryawan, vidakun;
    
    
    SimpleDateFormat tglview = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat tglinput = new SimpleDateFormat("yyyy-MM-dd");
    DefaultTableModel tblpiutang;
    DecimalFormat uang_indo = new DecimalFormat("Rp #,##0.00");
    public IfrPiutang() {
        initComponents();
        
        Id();
        clearForm();
        disableForm();
        setTabel();
        showData();
        listSum();
       
    }
    
    private void clearForm(){
       
        txtPiutang.setText("");
        txtPotongan.setText("");
        txtJumlah.setText("");
        txtKeterangan.setText("");
        cmbKaryawan.setSelectedIndex(0);
       
        dtTrans.setDate(new java.util.Date());
    }
    
    private void disableForm(){
        txtPiutang.setEnabled(false);
        txtPotongan.setEnabled(false);
        txtJumlah.setEnabled(false);
        txtKeterangan.setEnabled(false);
        cmbKaryawan.setSelectedIndex(0);
        dtTrans.setEnabled(false);
        btnSimpan.setEnabled(false);
      //  btnHapus.setEnabled(false);
    }

     private void enableForm(){
        txtPiutang.setEnabled(true);
        txtPotongan.setEnabled(true);
        txtJumlah.setEnabled(true);
        txtKeterangan.setEnabled(true);
        btnSimpan.setEnabled(true);
        dtTrans.setEnabled(true);
        cmbKaryawan.setSelectedIndex(0);
      //  btnHapus.setEnabled(true);
    }
     
   
    private void setTabel(){
        String[]kolom1 = {"ID Piutang","ID Karyawan","Nama Karyawan", "Jumlah Piutang" , "Potongan", "Keterangan", "Tanggal"};
        tblpiutang = new DefaultTableModel(null,kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
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
                int cola = tblpiutang.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tbDataPiutang.setModel(tblpiutang);
        tbDataPiutang.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataPiutang.getColumnModel().getColumn(1).setPreferredWidth(75);
        tbDataPiutang.getColumnModel().getColumn(2).setPreferredWidth(75);
        tbDataPiutang.getColumnModel().getColumn(3).setPreferredWidth(75);
        tbDataPiutang.getColumnModel().getColumn(4).setPreferredWidth(75);
        tbDataPiutang.getColumnModel().getColumn(4).setPreferredWidth(75);
        tbDataPiutang.getColumnModel().getColumn(4).setPreferredWidth(75);
    }
    
    private void clearTabel(){
        int row = tblpiutang.getRowCount();
        for (int i = 0;i < row;i++){
             tblpiutang.removeRow(0);
        }
    }
    
     private void showData(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabel();
            sqlselect =  "select * from piutang a, dt_karyawan b where a.id_karyawan=b.id_karyawan order by a.id_piutang asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vid_piutang = res.getString("id_piutang");
                vid_karyawan = res.getString("id_karyawan");
                vkaryawan = res.getString("nama");
                vjml_piutang = uang_indo.format(res.getDouble("jml_piutang"));
                vpotongan = uang_indo.format(res.getDouble("potongan"));
                vketerangan = res.getString("keterangan");
                vtgl = res.getString("tgl");
                
                Object[]data = {vid_piutang, vid_karyawan, vkaryawan, vjml_piutang, vpotongan, vketerangan, vtgl};
                tblpiutang.addRow(data);
            }Id();
                 btnTambah.setText("Tambah");
            lblRecord.setText("Record : "+tbDataPiutang.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdataPiutang : " + ex);
            }
    }
    private void aksiSimpan(){
          vid_piutang = txtPiutang.getText();
          vid_karyawan = KeySum[cmbKaryawan.getSelectedIndex()];
          vjml_piutang = txtJumlah.getText();
          vpotongan = txtPotongan.getText();
          vketerangan = txtKeterangan.getText();
          vtgl = tglinput.format(dtTrans.getDate());
           if(btnSimpan.getText().equals("Simpan")){
            sqlinsert = "insert into piutang values "
                        + " ('"+vid_piutang+"','"+vid_karyawan+"', '"+vjml_piutang+"', '"+vpotongan+"','"+vketerangan+"','"+vtgl+"') ";
            sqlkas = "insert into kas_keluar values "
                    + " ('"+vid_piutang+"','"+vtgl+"', '1-1011', '"+vketerangan+"', '"+vjml_piutang+"') ";
             sqldebet = "insert into jurnal_umum values "
                    + " ('"+vid_piutang+"','1-1011','"+vjml_piutang+"', '0', '"+vtgl+"') "; 
            sqlkredit = "insert into jurnal_umum values "
                    + " ('"+vid_piutang+"','1-1001', '0','"+vjml_piutang+"', '"+vtgl+"') ";
            
            JOptionPane.showMessageDialog(this, "Data Berhasil disimpan");
          /* }else{
               sqlinsert = "update piutang set id_karyawan ='"+vid_karyawan+"', jml_piutang ='"+vjml_piutang+"', potongan = '"+vpotongan+"', keterangan = '"+vketerangan+"', tgl = '"+vtgl+"' where id_piutang ='"+vid_piutang+"' ";
                              
               JOptionPane.showMessageDialog(this, "Data Berhasil diUbah");
           }*/
           try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            Statement state = _Cnn.createStatement();
             state.executeUpdate(sqlinsert);
            state.executeUpdate(sqlkas);
            state.executeUpdate(sqldebet);
            state.executeUpdate(sqlkredit);
            clearForm(); disableForm(); showData();Id();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method aksiSimpan() : "+ex);
        } 
    }
    
   /* private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, 
                "Apakah anda yakin akan menghapus data ini ? ID : "+vid_piutang,
                "Konfirmasi ",JOptionPane.YES_NO_OPTION);
        if(jawab== JOptionPane.YES_OPTION){
             try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqldelete = "delete from piutang where id_piutang= '"+vid_piutang+"'"; 
            java.sql.Statement state = _Cnn.createStatement();
            state.executeUpdate(sqldelete);
           JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
           clearForm();disableForm();showData();Id();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiHapus : " + ex);
        }
        
    }
  */       
    }   
   
     
private void Id(){
        //kode jenis
        if(btnSimpan.getText().equals("Simpan")){
            try{
                _Cnn = getCnn.getConnection();
                String id = "select max(right(id_piutang,3)) as id_piutang from piutang";
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(id);
               while(res.next()){
                    if(res.first() == false){
                        mid = "PIU-" + "001";
                    } else{
                        res.last();
                        int noID = res.getInt(1) + 1;
                        String no = String.valueOf(noID);
//                        int noLong = no.length();
//                        for(int a=0;a<2-noLong;a++){
//                            no = "TRANS-PNJ-" + no;
//                        }
                        if(noID < 10){
                            mid =  "PIU-" + "00" + no;
                        } else if(noID < 100){
                            mid = "PIU-" + "0" + no;
                        } else{
                            mid= "PIU-" + no;
                        }
                        txtPiutang.setText(mid);
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
                sqlselect = "select * from piutang "
                    + " where id_piutang='"+vid_piutang+"'"
                    + " order by id_piutang asc ";        
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                if(res.first()){
                    txtPiutang.setText(res.getString("id_piutang"));
                    cmbKaryawan.setSelectedItem(res.getString("id_karyawan"));
                    txtJumlah.setText(res.getString("jml_piutang"));
                    txtKeterangan.setText(res.getString("keterangan"));
                    txtPotongan.setText(res.getString("potongan"));
                    dtTrans.setDate(res.getDate("tgl"));
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
            sqlselect = "SELECT * FROM dt_karyawan order by id_karyawan asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbKaryawan.removeAllItems();
            cmbKaryawan.repaint();
            cmbKaryawan.addItem("-- PILIH KARYAWAN --");
            int i = 1;
            while(res.next()){
                cmbKaryawan.addItem(res.getString("nama"));
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
 
     private void cariPiutang(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabel();
            sqlselect =  "select * from piutang a, dt_karyawan b where a.id_karyawan=b.id_karyawan like '%"+txtCari.getText()+"%' order by a.id_piutang asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vid_piutang = res.getString("id_piutang");
                vid_karyawan = res.getString("id_karyawan");
                vkaryawan = res.getString("nama");
                vjml_piutang = res.getString("jml_piutang");
                vpotongan = res.getString("potongan");
                vketerangan = res.getString("keterangan");
                vtgl = res.getString("tgl");
                
                Object[]data = {vid_piutang, vid_karyawan, vkaryawan, vjml_piutang, vpotongan, vketerangan, vtgl};
                tblpiutang.addRow(data);
            }
            lblRecord.setText("Record : "+tbDataPiutang.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdataPiutang: " + ex);
            }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtJumlah = new javax.swing.JTextField();
        txtPiutang = new javax.swing.JTextField();
        cmbKaryawan = new javax.swing.JComboBox<String>();
        txtPotongan = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtKeterangan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        dtTrans = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataPiutang = new javax.swing.JTable();
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
        jLabel2.setText(" Data Piutang");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 4, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtJumlah.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtJumlah.setOpaque(false);
        txtJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtJumlahKeyTyped(evt);
            }
        });

        txtPiutang.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtPiutang.setOpaque(false);
        txtPiutang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPiutangKeyTyped(evt);
            }
        });

        cmbKaryawan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtPotongan.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtPotongan.setOpaque(false);
        txtPotongan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPotonganKeyTyped(evt);
            }
        });

        jLabel1.setText("ID Piutang");

        jLabel4.setText("ID Karyawan");

        jLabel5.setText("Jumlah Piutang");

        jLabel6.setText("Potongan");

        txtKeterangan.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setText("Keterangan");

        dtTrans.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dtTrans.setOpaque(false);

        jLabel3.setText("Tanggal");

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
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(txtPotongan))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPiutang, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtJumlah, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cmbKaryawan, 0, 342, Short.MAX_VALUE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3))
                        .addGap(53, 53, 53)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dtTrans, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtKeterangan))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPiutang, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtPotongan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dtTrans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 37, -1, -1));

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
                .addContainerGap()
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 13, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 282, 626, -1));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbDataPiutang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Piutang", "ID Karyawan", "Nama Karyawan", "Jumlah", "Potongan", "Keterangan"
            }
        ));
        tbDataPiutang.setRowHeight(25);
        tbDataPiutang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataPiutangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataPiutang);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 355, 616, 118));

        lblRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRecord.setText("Record : 0");
        getContentPane().add(lblRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(524, 491, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/akuntansi (2).jpg"))); // NOI18N
        jLabel8.setText("jLabel8");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, 510));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
      
        
        enableForm();
        clearForm();
        Id();
        txtPiutang.requestFocus(true);
        btnSimpan.setText("Simpan");
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(txtPiutang.getText().equals("")){
            JOptionPane.showMessageDialog(this, "ID Piutang harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE);  
        }else if(cmbKaryawan.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(this, "ID Karyawan harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE); 
        }else{
            aksiSimpan();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void tbDataPiutangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataPiutangMouseClicked
         /* if(evt.getClickCount()==2){
            vid_piutang = tbDataPiutang.getValueAt(tbDataPiutang.getSelectedRow(), 0).toString();
               getData();
               
            
            btnSimpan.setText("Ubah");
            enableForm();
        }*/
    }//GEN-LAST:event_tbDataPiutangMouseClicked

    private void txtPotonganKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPotonganKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPotonganKeyTyped

    private void txtPiutangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPiutangKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPiutangKeyTyped

    private void txtJumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahKeyTyped
        if(txtJumlah.getText().length() == 45){
            evt.consume();
        }
    }//GEN-LAST:event_txtJumlahKeyTyped

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
        cariPiutang();
    }//GEN-LAST:event_txtCariKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbKaryawan;
    private com.toedter.calendar.JDateChooser dtTrans;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JTable tbDataPiutang;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKeterangan;
    private javax.swing.JTextField txtPiutang;
    private javax.swing.JTextField txtPotongan;
    // End of variables declaration//GEN-END:variables
}
