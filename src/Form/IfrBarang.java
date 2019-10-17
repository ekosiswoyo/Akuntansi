package Form;

import Tool.ConfigDB;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrBarang extends javax.swing.JInternalFrame {
ConfigDB getCnn = new ConfigDB();
    Connection _Cnn;
    
    String sqlselect, sqlinsert, sqldelete;
    String vid_barang, vnm_barang, vspesifikasi, vkategori, mid;
    String visi_awal;
    
    DefaultTableModel tblBarang;
    
    public IfrBarang() {
        initComponents();
         Id();
        clearForm();
        disableForm();
        enableForm();
        setTabelBarang();  
        showData();
        
        btnHapus.setEnabled(false);
    }
    private void clearForm(){
        txtKdBarang.setText("");
        txtNmBarang.setText("");
        cmbKategori.setSelectedIndex(0);
        txtSpesifikasi.setText("");
        
    }
      private void disableForm(){
        txtKdBarang.setEnabled(false);
        txtNmBarang.setEnabled(false);
        cmbKategori.setEnabled(false);
        txtSpesifikasi.setEnabled(false);
        btnHapus.setEnabled(false);
    }
      
       private void enableForm(){
        txtKdBarang.setEnabled(true);
        txtNmBarang.setEnabled(true);
        cmbKategori.setEnabled(true);
        txtSpesifikasi.setEnabled(true);
        btnHapus.setEnabled(true);
    }
      
       private void setTabelBarang(){
        String[]kolom1 = {"Kode Barang", "Nama Barang", "Spesifikasi", "Kategori"};
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
                int cola = InputBarang.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        InputBarang.setModel(tblBarang);
        InputBarang.getColumnModel().getColumn(0).setPreferredWidth(75);
        InputBarang.getColumnModel().getColumn(1).setPreferredWidth(75);
        InputBarang.getColumnModel().getColumn(2).setPreferredWidth(75);
        InputBarang.getColumnModel().getColumn(3).setPreferredWidth(75); 
    }
    private void clearTabelBarang(){
        int row = tblBarang.getRowCount();
        for (int i = 0;i < row;i++){
             tblBarang.removeRow(0);
        }
    }
    
    private void showData(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabelBarang();
            sqlselect =  "select * from dt_barang order by id_barang";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vid_barang = res.getString("id_barang");
                vnm_barang = res.getString("nm_barang");
                vspesifikasi = res.getString("spesifikasi");
                vkategori = res.getString("kategori");
                Object[]data = {vid_barang, vnm_barang, vspesifikasi, vkategori };
                tblBarang.addRow(data); 
                btnTambah.setText("Tambah");
            }Id();
            lblRecord.setText("Record : "+InputBarang.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdataSiswa : " + ex);
            }
         
 }
    
    private void aksiSimpan(){
          vid_barang = txtKdBarang.getText();
          vnm_barang = txtNmBarang.getText();
          vkategori = (String)cmbKategori.getSelectedItem();
          vspesifikasi = txtSpesifikasi.getText();
            if(btnSimpan2.getText().equals("Simpan")){
            sqlinsert = "insert into dt_barang values "
                    + " ('"+vid_barang+"', '"+vnm_barang+"', '"+vspesifikasi+"', '"+vkategori+"') ";
            
             JOptionPane.showMessageDialog(null,"Data Berhasil Disimpan",
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
           }else{
               sqlinsert = "update dt_barang set nm_barang ='"+vnm_barang+"', spesifikasi ='"+vspesifikasi+"', "
                       + " harga_beli='"+vkategori+"' where id_barang = '"+vid_barang+"'";
               InputDataBarang.dispose();
               
                JOptionPane.showMessageDialog(null,"Data Berhasil DiUbah",
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
           try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            Statement state = _Cnn.createStatement();
            state.executeUpdate(sqlinsert);
            JOptionPane.showMessageDialog(this, "Data Berhasil disimpan");
            clearForm(); enableForm(); showData();Id();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method aksiSimpan() : "+ex);
        } 
              
    }
    
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, 
                "Apakah anda yakin akan menghapus data ini ? ID BARANG : "+vid_barang,
                "Konfirmasi ",JOptionPane.YES_NO_OPTION);
        if(jawab== JOptionPane.YES_OPTION){
             try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqldelete = "delete from dt_barang where id_barang= '"+vid_barang+"'"; 
            java.sql.Statement state = _Cnn.createStatement();
            state.executeUpdate(sqldelete);
           JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
           clearForm();disableForm();showData();Id();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiHapus : " + ex);
            }
        }   
    }
    
    private void getData() {
             try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqlselect = "select * from dt_barang "
                    + " where id_barang='"+vid_barang+"'"
                    + " order by id_barang asc ";        
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                if(res.first()){
                     visi_awal = res.getString("nm_barang");
                    txtKdBarang.setText(res.getString("id_barang"));
                    txtNmBarang.setText(res.getString("nm_barang"));
                    cmbKategori.setSelectedItem(res.getString("harga_beli"));
                    txtSpesifikasi.setText(res.getString("spesifikasi")); 
                }   
                btnSimpan2.setText("Ubah Data");
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Eror Method GetDataPaien : " + ex);
            }    
    }
    
    private void Id(){
        //kode jenis
        if(btnSimpan2.getText().equals("Simpan")){
            try{
                _Cnn = getCnn.getConnection();
                String id = "select max(right(id_barang,3)) as id_barang from dt_barang";
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(id);
                while(res.next()){
                    if(res.first() == false){
                        mid = "BRG-" + "001";
                    } else{
                        res.last();
                        int noID = res.getInt(1) + 1;
                        String no = String.valueOf(noID);
//                        int noLong = no.length();
//                        for(int a=0;a<2-noLong;a++){
//                            no = "TRANS-PNJ-" + no;
//                        }
                        if(noID < 10){
                            mid =  "BRG--" + "00" + no;
                        } else if(noID < 100){
                            mid = "BRG-" + "0" + no;
                        } else{
                            mid= "BRG-" + no;
                        }
                        txtKdBarang.setText(mid);
                        }
                   
                }
            } catch(SQLException ex){
                System.out.println("Error Method Id : " + ex);
            }
        }
        //kode jenis
    }
    private void cariData(){
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                clearTabelBarang();
                sqlselect ="select * from dt_barang "
                    + " where nm_barang like '%"+txtCari.getText()+"%' order by id_barang asc ";     
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                while(res.next()){
                    
                    vid_barang = res.getString("id_barang");
                    vnm_barang =res.getString("nm_barang");
                    vspesifikasi = res.getString("spesifikasi");
                    vkategori = res.getString("kategori");
                  
                    Object[]data = { vid_barang, vnm_barang, vspesifikasi, vkategori};              
                    tblBarang.addRow(data);   
                }
                lblRecord.setText("Record : "+InputBarang.getRowCount());
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Eror Method showDataBarang : " + ex);
            }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        InputDataBarang = new javax.swing.JFrame();
        jPanel4 = new javax.swing.JPanel();
        txtNmBarang = new javax.swing.JTextField();
        txtKdBarang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtSpesifikasi = new javax.swing.JTextArea();
        cmbKategori = new javax.swing.JComboBox<String>();
        jLabel6 = new javax.swing.JLabel();
        btnSimpan2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblRecord = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtCari = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        InputBarang = new javax.swing.JTable();
        jScrollBar1 = new javax.swing.JScrollBar();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        InputDataBarang.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Silahkan Input Data Barang", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 14))); // NOI18N

        txtNmBarang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNmBarang.setOpaque(false);
        txtNmBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNmBarangKeyTyped(evt);
            }
        });

        txtKdBarang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtKdBarang.setOpaque(false);
        txtKdBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKdBarangKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setText("Kode Barang");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setText("Nama Barang");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setText("Spesifikasi");

        txtSpesifikasi.setColumns(20);
        txtSpesifikasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        txtSpesifikasi.setRows(5);
        txtSpesifikasi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportView(txtSpesifikasi);

        cmbKategori.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Pilih Kategori --", "Pakan", "Obat", "Peralatan", " " }));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setText("Kategori");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtKdBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNmBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(5, 15, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(38, 38, 38)
                        .addComponent(cmbKategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(15, 15, 15))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtKdBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNmBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(72, Short.MAX_VALUE))
        );

        InputDataBarang.getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        btnSimpan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/save-black.png"))); // NOI18N
        btnSimpan2.setText("Simpan");
        btnSimpan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpan2ActionPerformed(evt);
            }
        });
        InputDataBarang.getContentPane().add(btnSimpan2, new org.netbeans.lib.awtextra.AbsoluteConstraints(223, 324, 99, 30));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/akun.jpg"))); // NOI18N
        jLabel8.setText("jLabel8");
        InputDataBarang.getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 370, 400));

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("DATA BARANG");
        jLabel2.setOpaque(true);
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 227, -1));

        lblRecord.setText("Record 0");
        getContentPane().add(lblRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 441, -1, -1));

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
                .addContainerGap(374, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addGap(6, 6, 6))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtCari)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 62, -1, -1));

        jScrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        InputBarang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        InputBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Kode Barang", "Nama Barang", "Spesifikasi", "Kategori"
            }
        ));
        InputBarang.setRowHeight(25);
        InputBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                InputBarangMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(InputBarang);

        getContentPane().add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 133, 848, 297));
        getContentPane().add(jScrollBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(868, 62, -1, 407));

        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/insert.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });
        getContentPane().add(btnTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 441, 109, 30));

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/btn_delete.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });
        getContentPane().add(btnHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(765, 441, 93, 30));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/akuntansi (2).jpg"))); // NOI18N
        jLabel7.setText("jLabel7");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
         Id();
        if(btnTambah.getText().equals("Tambah")){
        InputDataBarang.setVisible(true);
        InputDataBarang.setSize(500, 450);
        InputDataBarang.setLocationRelativeTo(this);
        enableForm();
        txtKdBarang.requestFocus(true);
        btnSimpan2.setText("Simpan");
        }else{
            btnSimpan2.setText("Ubah Data");
            InputDataBarang.setVisible(true);
        InputDataBarang.setSize(500, 450);
        InputDataBarang.setLocationRelativeTo(this);
        enableForm();
        txtKdBarang.requestFocus(true);
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpan2ActionPerformed
        if(txtKdBarang.getText().equals("")){
            JOptionPane.showMessageDialog(this, "ID Barang harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE); 
        }else if(txtNmBarang.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Nama Barang harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(cmbKategori.getSelectedIndex()==0){
            JOptionPane.showMessageDialog(this, "Kategori harus dipilih",  
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtSpesifikasi.getText().equals("    ")){
            JOptionPane.showMessageDialog(this, "Spesifikasi harus diisi",  
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            try {
                String sqlCari = "";
                if(btnSimpan2.getText().equals("Simpan")){
                    sqlCari = "select * from dt_barang where nm_barang='"+txtNmBarang.getText()+"'";
                }else{
                    sqlCari = "select * from dt_barang where nm_barang='"+txtNmBarang.getText()+"' "
                            + "and nm_barang not in ('"+visi_awal+"')";
                }
                Statement s = (Statement)getCnn.getConnection().createStatement();
                ResultSet r = s.executeQuery(sqlCari);
                if(r.next()){
                    JOptionPane.showMessageDialog(this, "Barang tersebut sudah ada ! ",
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
         if(txtKdBarang.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Belum ada data yang dipilih ! ",
            "Informasi",JOptionPane.INFORMATION_MESSAGE );  
         
        }else{
            aksiHapus();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void InputBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InputBarangMouseClicked
    if(evt.getClickCount()==1){
            vid_barang = InputBarang.getValueAt(InputBarang.getSelectedRow(), 0).toString();
            btnHapus.setEnabled(true);
            btnTambah.setText("Ubah");
            getData();
            
            enableForm();
             
           }
    }//GEN-LAST:event_InputBarangMouseClicked

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        cariData();
    }//GEN-LAST:event_txtCariKeyTyped

    private void txtNmBarangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNmBarangKeyTyped
        if(txtNmBarang.getText().length() == 45){
         evt.consume();
     }
    }//GEN-LAST:event_txtNmBarangKeyTyped

    private void txtKdBarangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKdBarangKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKdBarangKeyTyped

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        clearForm();
        disableForm();
       
        setTabelBarang();  
        showData();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable InputBarang;
    private javax.swing.JFrame InputDataBarang;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan2;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbKategori;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtKdBarang;
    private javax.swing.JTextField txtNmBarang;
    private javax.swing.JTextArea txtSpesifikasi;
    // End of variables declaration//GEN-END:variables
}
