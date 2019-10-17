
package Form;

import Tool.ConfigDB;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrAngsuran extends javax.swing.JInternalFrame {


    ConfigDB getCnn = new ConfigDB();
    Connection _Cnn;
    
    String sqlselect, sqlinsert, sqldelete, sqlkas, sqldebet, sqlkredit;
    String vid_angsuran, vid_piutang, vangsuran_ke, vjumlah, mid, vtgl, vketerangan, vidakun;
    SimpleDateFormat tglinput = new SimpleDateFormat("yyyy-MM-dd");
    DefaultTableModel tblangsuran;
    DecimalFormat uang_indo = new DecimalFormat("Rp #,##0.00");
    public IfrAngsuran() {
        initComponents();
        
        Auto();
        clearForm();
        disableForm();
        setTabelAng();
        showDataAng();
        listSum();
       
    }
    
    private void clearForm(){
       
        txtIdAngsuran.setText("");
        txtJumlah.setText("");
        txtAngsuran.setText("");
        cmbPiutang.setSelectedIndex(0);
       
        txtKeterangan.setText("");
        dtTrans.setDate(new java.util.Date());
    }
    
    private void disableForm(){
        dtTrans.setEnabled(false);
        txtKeterangan.setEnabled(false);
        txtIdAngsuran.setEnabled(false);
        txtJumlah.setEnabled(false);
        txtAngsuran.setEnabled(false);
       
        cmbPiutang.setSelectedIndex(0);
        btnSimpan.setEnabled(false);
//        btnHapus.setEnabled(false);
    }

     private void enableForm(){
        txtKeterangan.setEnabled(true);
        dtTrans.setEnabled(true);
        txtIdAngsuran.setEnabled(true);
        txtJumlah.setEnabled(true);
        txtAngsuran.setEnabled(true);
        btnSimpan.setEnabled(true);
        
        cmbPiutang.setSelectedIndex(0);
     //   btnHapus.setEnabled(true);
    }
     
   
    private void setTabelAng(){
        String[]kolom1 = {"ID Angsuran","ID Piutang", "Angsuran Ke" , "Jumlah"};
        tblangsuran = new DefaultTableModel(null,kolom1){
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
                int cola = tblangsuran.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tbDataAngsuran.setModel(tblangsuran);
        tbDataAngsuran.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataAngsuran.getColumnModel().getColumn(1).setPreferredWidth(75);
        tbDataAngsuran.getColumnModel().getColumn(2).setPreferredWidth(75);
        tbDataAngsuran.getColumnModel().getColumn(3).setPreferredWidth(75);
    }
    
    private void clearTabelAng(){
        int row = tblangsuran.getRowCount();
        for (int i = 0;i < row;i++){
             tblangsuran.removeRow(0);
        }
    }
    
     private void showDataAng(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabelAng();
            sqlselect =  "select * from angsuran order by id_angsuran asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vid_angsuran = res.getString("id_angsuran");
                vid_piutang = res.getString("id_piutang");
                vangsuran_ke = res.getString("angsuran_ke");
                
                vjumlah = uang_indo.format(res.getDouble("jumlah"));
                Object[]data = {vid_angsuran, vid_piutang, vangsuran_ke, vjumlah};
                tblangsuran.addRow(data);
            }Auto();
                 btnTambah.setText("Tambah");
            lblRecord.setText("Record : "+tbDataAngsuran.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdataUser : " + ex);
            }
    }
    private void aksiSimpan(){
          vid_angsuran = txtIdAngsuran.getText();
          vid_piutang = KeySum[cmbPiutang.getSelectedIndex()];
         
          vangsuran_ke = txtAngsuran.getText();
          vjumlah = txtJumlah.getText();
          vtgl = tglinput.format(dtTrans.getDate());
           if(btnSimpan.getText().equals("Simpan")){
            sqlinsert = "insert into angsuran values "
                        + " ('"+vid_angsuran+"','"+vid_piutang+"', '"+vangsuran_ke+"', '"+vjumlah+"', '"+vtgl+"') ";
            sqlkas = "insert into kas_masuk values "
                    + " ('"+vid_angsuran+"','"+vtgl+"', '1-1011', '"+vketerangan+"', '"+vjumlah+"') ";
             sqldebet = "insert into jurnal_umum values "
                    + " ('"+vid_angsuran+"','1-1001','"+vjumlah+"', '0', '"+vtgl+"') "; 
            sqlkredit = "insert into jurnal_umum values "
                    + " ('"+vid_angsuran+"','1-1011', '0','"+vjumlah+"', '"+vtgl+"') "; 
            JOptionPane.showMessageDialog(this, "Data Berhasil disimpan");
           }else{
               sqlinsert = "update angsuran set id_piutang ='"+vid_piutang+"', angsuran_ke ='"+vangsuran_ke+"', jumlah = '"+vjumlah+"' where id_angsuran ='"+vid_angsuran+"' ";
                              
               JOptionPane.showMessageDialog(this, "Data Berhasil diUbah");
           }
           try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            Statement state = _Cnn.createStatement();
            state.executeUpdate(sqlinsert);
            state.executeUpdate(sqlkas);
            state.executeUpdate(sqldebet);
            state.executeUpdate(sqlkredit);
            clearForm(); disableForm(); showDataAng();Auto();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method aksiSimpan() : "+ex);
        } 
    }
    
   /* private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, 
                "Apakah anda yakin akan menghapus data ini ? ID : "+vid_angsuran,
                "Konfirmasi ",JOptionPane.YES_NO_OPTION);
        if(jawab== JOptionPane.YES_OPTION){
             try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqldelete = "delete from angsuran where id_angsuran= '"+vid_angsuran+"'"; 
            java.sql.Statement state = _Cnn.createStatement();
            state.executeUpdate(sqldelete);
           JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
           clearForm();disableForm();showDataAng();Auto();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiHapus : " + ex);
        }
        
    }
        
    }   */
    private void cariAngsuran(){
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                clearTabelAng();
                sqlselect ="select * from angsuran "
                    + " where id_piutang like '%"+txtCari.getText()+"%' order by id_angsuran asc ";     
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                while(res.next()){
                    
                    vid_angsuran = res.getString("id_angsuran");
                    vid_piutang =res.getString("id_piutang");
                    vangsuran_ke = res.getString("angsuran_ke");
                    vjumlah = res.getString("jumlah");
                    Object[]data = { vid_angsuran, vid_piutang, vangsuran_ke, vjumlah};              
                 tblangsuran.addRow(data);   
                }
                lblRecord.setText("Record : "+tbDataAngsuran.getRowCount());
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Eror Method showDataPasien : " + ex);
            }
    }

    
     private void Auto(){
        //kode jenis
        if(btnSimpan.getText().equals("Simpan")){
            try{
                _Cnn = getCnn.getConnection();
                String id = "select max(right(id_angsuran,1)) as id_angsuran from angsuran";
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
                            no = "AG-" + no;
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
                        txtIdAngsuran.setText(mid);
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
                sqlselect = "select * from angsuran "
                    + " where id_angsuran='"+vid_angsuran+"'"
                    + " order by id_angsuran asc ";        
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                if(res.first()){
                    txtIdAngsuran.setText(res.getString("id_angsuran"));
                    cmbPiutang.setSelectedItem(res.getString("id_piutang"));
                    txtAngsuran.setText(res.getString("angsuran_ke"));
                    txtJumlah.setText(res.getString("jumlah"));

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
            sqlselect = "SELECT * FROM piutang order by id_piutang asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbPiutang.removeAllItems();
            cmbPiutang.repaint();
            cmbPiutang.addItem("-- PILIH PIUTANG --");
            int i = 1;
            while(res.next()){
                cmbPiutang.addItem(res.getString("id_piutang"));
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
 
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtAngsuran = new javax.swing.JTextField();
        txtIdAngsuran = new javax.swing.JTextField();
        cmbPiutang = new javax.swing.JComboBox<String>();
        txtJumlah = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dtTrans = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtKeterangan = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataAngsuran = new javax.swing.JTable();
        lblRecord = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

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
        jLabel2.setText(" Data Angsuran");
        jLabel2.setOpaque(true);
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtAngsuran.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtAngsuran.setOpaque(false);
        txtAngsuran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAngsuranKeyTyped(evt);
            }
        });

        txtIdAngsuran.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtIdAngsuran.setOpaque(false);
        txtIdAngsuran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdAngsuranKeyTyped(evt);
            }
        });

        cmbPiutang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtJumlah.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtJumlah.setOpaque(false);
        txtJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtJumlahKeyTyped(evt);
            }
        });

        jLabel1.setText("ID Angsuran");

        jLabel4.setText("ID Piutang");

        jLabel5.setText("Angsuran Ke");

        jLabel6.setText("Jumlah");

        dtTrans.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dtTrans.setOpaque(false);

        txtKeterangan.setColumns(20);
        txtKeterangan.setRows(5);
        jScrollPane2.setViewportView(txtKeterangan);

        jLabel3.setText("Tanggal");

        jLabel7.setText("Keterangan");

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
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jScrollPane2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtIdAngsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(dtTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 2, Short.MAX_VALUE))
                                            .addComponent(txtAngsuran)
                                            .addComponent(cmbPiutang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtJumlah))))
                                .addGap(184, 184, 184))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdAngsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbPiutang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAngsuran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dtTrans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, -1, -1));

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

        txtCari.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCariKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel8.setText("Silahkan Mencari");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(0, 13, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 740, -1));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbDataAngsuran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "ID Angsuran", "ID Piutang", "Angsuran Ke", "Jumlah"
            }
        ));
        tbDataAngsuran.setRowHeight(25);
        tbDataAngsuran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataAngsuranMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataAngsuran);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 737, 155));

        lblRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRecord.setText("Record : 0");
        getContentPane().add(lblRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 570, -1, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/akuntansi (1).jpg"))); // NOI18N
        jLabel9.setText("jLabel9");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 770, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
      
        Auto();
        enableForm();
        clearForm();
        txtIdAngsuran.requestFocus(true);
        btnSimpan.setText("Simpan");
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(txtIdAngsuran.getText().equals("")){
            JOptionPane.showMessageDialog(this, "ID Angsuran harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE);  
        }else if(cmbPiutang.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(this, "ID Piutang harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE); 
        }else{
            aksiSimpan();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void tbDataAngsuranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataAngsuranMouseClicked
          /*if(evt.getClickCount()==2){
            vid_angsuran = tbDataAngsuran.getValueAt(tbDataAngsuran.getSelectedRow(), 0).toString();
               getData();
               
            
            btnSimpan.setText("Ubah");
            enableForm();
        }*/
    }//GEN-LAST:event_tbDataAngsuranMouseClicked

    private void txtJumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahKeyTyped

    private void txtIdAngsuranKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdAngsuranKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdAngsuranKeyTyped

    private void txtAngsuranKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAngsuranKeyTyped
        if(txtAngsuran.getText().length() == 45){
            evt.consume();
        }
    }//GEN-LAST:event_txtAngsuranKeyTyped

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        
    }//GEN-LAST:event_txtCariKeyTyped

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        // TODO add your handling code here:
        cariAngsuran();
    }//GEN-LAST:event_txtCariKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        clearForm();
        disableForm();

        setTabelAng();
        showDataAng();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbPiutang;
    private com.toedter.calendar.JDateChooser dtTrans;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tbDataAngsuran;
    private javax.swing.JTextField txtAngsuran;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtIdAngsuran;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextArea txtKeterangan;
    // End of variables declaration//GEN-END:variables
}
