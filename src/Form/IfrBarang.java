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
    String vid_barang, vnm_barang, vspesifikasi, vharga_beli, vharga_jual, mid;
    
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
        txtHargaBeli.setText("");
        txtSpesifikasi.setText("");
        txtHargaJual.setText("");
        
    }
      private void disableForm(){
        txtKdBarang.setEnabled(false);
        txtNmBarang.setEnabled(false);
        txtHargaBeli.setEnabled(false);
        txtSpesifikasi.setEnabled(false);
        txtHargaJual.setEnabled(false);
        btnHapus.setEnabled(false);
    }
      
       private void enableForm(){
        txtKdBarang.setEnabled(true);
        txtNmBarang.setEnabled(true);
        txtHargaBeli.setEnabled(true);
        txtSpesifikasi.setEnabled(true);
        txtHargaJual.setEnabled(true);
        btnHapus.setEnabled(true);
    }
      
       private void setTabelBarang(){
        String[]kolom1 = {"Kode Barang", "Nama Barang", "Spesifikasi", "Harga Beli", "Harga Jual"};
        tblBarang= new DefaultTableModel(null,kolom1){
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
                int cola = tblDtBarang.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tblDtBarang.setModel(tblBarang);
        tblDtBarang.getColumnModel().getColumn(0).setPreferredWidth(75);
        tblDtBarang.getColumnModel().getColumn(1).setPreferredWidth(75);
        tblDtBarang.getColumnModel().getColumn(2).setPreferredWidth(75);
        tblDtBarang.getColumnModel().getColumn(3).setPreferredWidth(75);
        tblDtBarang.getColumnModel().getColumn(4).setPreferredWidth(75);
       
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
                vharga_beli = res.getString("harga_beli");
                vharga_jual = res.getString("harga_jual");
                
                Object[]data = {vid_barang, vnm_barang, vspesifikasi, vharga_beli, vharga_jual };
                tblBarang.addRow(data); 
                btnTambah.setText("Tambah");
            }Id();
            lblRecord.setText("Record : "+tblDtBarang.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdataSiswa : " + ex);
            }
         
 }
    
    private void aksiSimpan(){
          vid_barang = txtKdBarang.getText();
          vnm_barang = txtNmBarang.getText();
          vharga_beli = txtHargaBeli.getText();
          vharga_jual = txtHargaJual.getText();
          vspesifikasi = txtSpesifikasi.getText();
          
           
            if(btnSimpan2.getText().equals("Simpan")){
            sqlinsert = "insert into dt_barang values "
                    + " ('"+vid_barang+"', '"+vnm_barang+"', '"+vspesifikasi+"', '"+vharga_beli+"', '"+vharga_jual+"') ";
            
             JOptionPane.showMessageDialog(null,"Data Berhasil Disimpan",
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
           }else{
               sqlinsert = "update dt_barang set nm_barang ='"+vnm_barang+"', spesifikasi ='"+vspesifikasi+"', "
                       + " harga_beli='"+vharga_beli+"', harga_jual='"+vharga_jual+"' where id_barang = '"+vid_barang+"'";
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
                    txtKdBarang.setText(res.getString("id_barang"));
                    txtNmBarang.setText(res.getString("nm_barang"));
                    txtHargaBeli.setText(res.getString("harga_beli"));
                    txtHargaJual.setText(res.getString("harga_jual"));
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
                    vharga_beli = res.getString("harga_beli");
                    vharga_jual = res.getString("harga_jual");
                    Object[]data = { vid_barang, vnm_barang, vspesifikasi, vharga_beli, vharga_jual};              
                    tblBarang.addRow(data);   
                }
                lblRecord.setText("Record : "+tblDtBarang.getRowCount());
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
        txtNmBarang = new javax.swing.JTextField();
        txtHargaJual = new javax.swing.JTextField();
        txtHargaBeli = new javax.swing.JTextField();
        txtKdBarang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtSpesifikasi = new javax.swing.JTextArea();
        btnSimpan2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblRecord = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtCari = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblDtBarang = new javax.swing.JTable();
        jScrollBar1 = new javax.swing.JScrollBar();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Silahkan Input Data Barang"));

        txtNmBarang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNmBarang.setOpaque(false);
        txtNmBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNmBarangKeyTyped(evt);
            }
        });

        txtHargaJual.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtHargaJual.setOpaque(false);
        txtHargaJual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHargaJualKeyTyped(evt);
            }
        });

        txtHargaBeli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtHargaBeli.setOpaque(false);
        txtHargaBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHargaBeliKeyTyped(evt);
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

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setText("Harga Beli");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setText("Harga Jual");

        txtSpesifikasi.setColumns(20);
        txtSpesifikasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        txtSpesifikasi.setRows(5);
        txtSpesifikasi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportView(txtSpesifikasi);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKdBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNmBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtHargaJual)
                            .addComponent(txtHargaBeli)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
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
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(61, Short.MAX_VALUE))
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
                .addGap(234, 234, 234)
                .addComponent(btnSimpan2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(InputSiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        InputSiswaLayout.setVerticalGroup(
            InputSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InputSiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSimpan2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("DATA BARANG");

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

        tblDtBarang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblDtBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "Kode Barang", "Nama Barang", "Spesifikasi", "Harga Beli", "Harga Jual"
            }
        ));
        tblDtBarang.setRowHeight(25);
        tblDtBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDtBarangMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblDtBarang);

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
         Id();
        if(btnTambah.getText().equals("Tambah")){
        InputSiswa.setVisible(true);
        InputSiswa.setSize(500, 450);
        InputSiswa.setLocationRelativeTo(this);
        enableForm();
        txtKdBarang.requestFocus(true);
        btnSimpan2.setText("Simpan");
        }else{
            btnSimpan2.setText("Ubah Data");
            InputSiswa.setVisible(true);
        InputSiswa.setSize(500, 450);
        InputSiswa.setLocationRelativeTo(this);
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
        }else if(txtHargaBeli.getText().equals("    ")){
            JOptionPane.showMessageDialog(this, "Harga Beli harus diisi",  
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtHargaJual.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Harga Jual harus diisi",  
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtSpesifikasi.getText().equals("    ")){
            JOptionPane.showMessageDialog(this, "Spesifikasi harus diisi",  
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            aksiSimpan();
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

    private void tblDtBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDtBarangMouseClicked
    if(evt.getClickCount()==1){
            vid_barang = tblDtBarang.getValueAt(tblDtBarang.getSelectedRow(), 0).toString();
            btnHapus.setEnabled(true);
            btnTambah.setText("Ubah");
            getData();
            enableForm();
             
           }
    }//GEN-LAST:event_tblDtBarangMouseClicked

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        cariData();
    }//GEN-LAST:event_txtCariKeyTyped

    private void txtNmBarangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNmBarangKeyTyped
        if(txtNmBarang.getText().length() == 45){
         evt.consume();
     }
    }//GEN-LAST:event_txtNmBarangKeyTyped

    private void txtHargaJualKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaJualKeyTyped
         if(txtHargaJual.getText().length() == 24){
         evt.consume();
     }
    }//GEN-LAST:event_txtHargaJualKeyTyped

    private void txtHargaBeliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaBeliKeyTyped
        if(txtHargaBeli.getText().length() == 35){
            evt.consume();
        }
    }//GEN-LAST:event_txtHargaBeliKeyTyped

    private void txtKdBarangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKdBarangKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKdBarangKeyTyped

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame InputSiswa;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan2;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tblDtBarang;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHargaBeli;
    private javax.swing.JTextField txtHargaJual;
    private javax.swing.JTextField txtKdBarang;
    private javax.swing.JTextField txtNmBarang;
    private javax.swing.JTextArea txtSpesifikasi;
    // End of variables declaration//GEN-END:variables
}
