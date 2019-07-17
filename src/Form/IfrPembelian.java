
package Form;

import Tool.ConfigDB;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrPembelian extends javax.swing.JInternalFrame {


    ConfigDB getCnn = new ConfigDB();
    Connection _Cnn;
    
    String sqlselect, sqlinsert, sqldelete,sqlinsertt, sqlkas, sqljurnal;
    String vno_transaksi, vid_supplier, vbarang, vqty, vharga, vjumlah, vtgl, mid, vsupplier, vnmbarang, mids,bit, vidakun, vket;
    
    SimpleDateFormat tglview = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat tglinput = new SimpleDateFormat("yyyy-MM-dd");
    DefaultTableModel tblpembelian;
    
    public IfrPembelian() {
        initComponents();
        
        Id();
        Ids();
        clearForm();
        disableForm();
        setTabel();
        showData();
        listSum();
        listSumBar();
        listAkun();
    }
    
    private void clearForm(){
       
        txtQty.setText("");
        txtHarga.setText("");
        txtJumlah.setText("");
        cmbIdBarang.setSelectedIndex(0);
    }
    
    
    private void disableForm(){
        txtNoTransaksi.setEnabled(false);
        txtQty.setEnabled(false);
        txtHarga.setEnabled(false);
        txtJumlah.setEnabled(false);
        cmbIdSupplier.setSelectedIndex(0);
        cmbIdBarang.setSelectedIndex(0);
        cmbIdAkun.setSelectedIndex(0);
        dtTrans.setEnabled(false);
        txtKet.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }

     private void enableForm(){
        txtNoTransaksi.setEnabled(true);
        txtQty.setEnabled(true);
        txtHarga.setEnabled(true);
        txtJumlah.setEnabled(true);
        txtKet.setEnabled(true);
        btnSimpan.setEnabled(true);
        dtTrans.setEnabled(true);
        cmbIdSupplier.setSelectedIndex(0);
        cmbIdBarang.setSelectedIndex(0);
        cmbIdAkun.setSelectedIndex(0);
        btnHapus.setEnabled(true);
    }
     
   
    private void setTabel(){
        String[]kolom1 = {"Id Transaksi", "ID Supplier" , "Nama Supplier", "ID Barang" , "Nama Barang", "Qty", "Harga", "Jumlah", "Tanggal"};
        tblpembelian = new DefaultTableModel(null,kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class,
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
                int cola = tblpembelian.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tbDataPembelian.setModel(tblpembelian);
        tbDataPembelian.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataPembelian.getColumnModel().getColumn(1).setPreferredWidth(75);
        tbDataPembelian.getColumnModel().getColumn(2).setPreferredWidth(75);
        tbDataPembelian.getColumnModel().getColumn(3).setPreferredWidth(75);
        tbDataPembelian.getColumnModel().getColumn(4).setPreferredWidth(75);
        tbDataPembelian.getColumnModel().getColumn(5).setPreferredWidth(75);
        tbDataPembelian.getColumnModel().getColumn(5).setPreferredWidth(75);
        tbDataPembelian.getColumnModel().getColumn(5).setPreferredWidth(75);
        tbDataPembelian.getColumnModel().getColumn(5).setPreferredWidth(75);
    }
    
    private void clearTabel(){
        int row = tblpembelian.getRowCount();
        for (int i = 0;i < row;i++){
             tblpembelian.removeRow(0);
        }
    }
    
     private void showData(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabel();
            sqlselect =  "SELECT * from pembelian a, pembelian_header b, dt_supplier c, dt_barang d WHERE a.no_trans=b.no_trans AND b.id_supplier=c.id_supplier AND a.id_barang=d.id_barang order by a.no_trans asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vno_transaksi = res.getString("no_trans");
                vid_supplier = res.getString("id_supplier");
                vsupplier = res.getString("nm_supplier");
                vbarang = res.getString("id_barang");
                vnmbarang = res.getString("nm_barang");
                vqty = res.getString("qty");
                vharga = res.getString("harga");
                vjumlah = res.getString("jumlah");
                vbarang = res.getString("id_barang");
                vtgl = res.getString("tgl");
                Object[]data = {vno_transaksi, vid_supplier, vsupplier,vbarang,vnmbarang, vqty, vharga, vjumlah, vtgl};
                tblpembelian.addRow(data);
            }Id();Ids();
                 btnTambah.setText("Tambah");
            lblRecord.setText("Record : "+tbDataPembelian.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdataUser : " + ex);
            }
    }
    private void aksiSimpan(){
          vno_transaksi = txtNoTransaksi.getText();
          vket = txtKet.getText();
          bit = txtId.getText();
          vid_supplier = KeySum[cmbIdSupplier.getSelectedIndex()];
          vbarang = KeySumBar[cmbIdBarang.getSelectedIndex()];
          vidakun = KeyAkun[cmbIdAkun.getSelectedIndex()];
          int vqty = Integer.parseInt(txtQty.getText());
          int vharga = Integer.parseInt(txtHarga.getText());
          vjumlah = String.valueOf(vqty*vharga);
          vtgl = tglinput.format(dtTrans.getDate());
           if(btnSimpan.getText().equals("Simpan")){
            sqlinsert = "insert into pembelian_header values "
                    + " ('"+vno_transaksi+"', '"+vid_supplier+"', '"+vtgl+"') ";
            sqlinsertt = "insert into pembelian values "
                    + " ('"+bit+"','"+vno_transaksi+"', '"+vbarang+"', '"+vqty+"', '"+vharga+"', '"+vjumlah+"') ";
            sqlkas = "insert into kas_keluar values "
                    + " ('"+vno_transaksi+"','"+vtgl+"', '"+vidakun+"', '"+vket+"', '"+vjumlah+"') ";
            sqljurnal = "insert into jurnal_umum values "
                    + " ('"+vno_transaksi+"','"+vidakun+"', '"+vjumlah+"', '0', '"+vtgl+"') ";
            JOptionPane.showMessageDialog(this, "Data Berhasil disimpan");
           }else{
               sqlinsert = "update pembelian set id_barang ='"+vbarang+"', qty = '"+vqty+"', harga = '"+vharga+"', jumlah = '"+vjumlah+"' where no_trans='"+vno_transaksi+"' ";
                              
               JOptionPane.showMessageDialog(this, "Data Berhasil diUbah");
           }
           try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            Statement state = _Cnn.createStatement();
            state.executeUpdate(sqlinsert);
            state.executeUpdate(sqlinsertt);
            state.executeUpdate(sqlkas);
            state.executeUpdate(sqljurnal);
            
            clearForm(); disableForm(); showData();Id();Ids();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method aksiSimpan() : "+ex);
        } 
    }
    
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, 
                "Apakah anda yakin akan menghapus data ini ? ID : "+vno_transaksi,
                "Konfirmasi ",JOptionPane.YES_NO_OPTION);
        if(jawab== JOptionPane.YES_OPTION){
             try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqldelete = "delete from pembelian where no_transaksi= '"+vno_transaksi+"'"; 
            java.sql.Statement state = _Cnn.createStatement();
            state.executeUpdate(sqldelete);
           JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
           clearForm();disableForm();showData();Id();Ids();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiHapus : " + ex);
        }
        
    }
        
    }   
      private void Ids(){
        //kode jenis
        if(btnSimpan.getText().equals("Simpan")){
            try{
                _Cnn = getCnn.getConnection();
                String id = "select max(right(id_pembelian,1)) as id_pembelian from pembelian";
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(id);
                while(res.next()){
                    if(res.first() == false){
                        mids = "1";
                    } else{
                        res.last();
                        int noID = res.getInt(1) + 1;
                        String no = String.valueOf(noID);
//                        int noLong = no.length();
//                        for(int a=0;a<2-noLong;a++){
//                            no = "TRANS-PNJ-" + no;
//                        }
                        mids = no;
                        txtId.setText(mids);
                        }
                   
                }
            } catch(SQLException ex){
                System.out.println("Error Method Id : " + ex);
            }
        }
        //kode jenis
    }
     private void Id(){
        //kode jenis
        if(btnSimpan.getText().equals("Simpan")){
            try{
                _Cnn = getCnn.getConnection();
                String id = "select max(right(no_trans,3)) as no_trans from pembelian";
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(id);
                while(res.next()){
                    if(res.first() == false){
                        mid = "TRANS-PMB-" + "001";
                    } else{
                        res.last();
                        int noID = res.getInt(1) + 1;
                        String no = String.valueOf(noID);
//                        int noLong = no.length();
//                        for(int a=0;a<2-noLong;a++){
//                            no = "TRANS-PNJ-" + no;
//                        }
                        if(noID < 10){
                            mid =  "TRANS-PMB-" + "00" + no;
                        } else if(noID < 100){
                            mid = "TRANS-PMB-" + "0" + no;
                        } else{
                            mid= "TRANS-PMB-" + no;
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
                sqlselect = "select * from pembelian "
                    + " where no_transaksi='"+vno_transaksi+"'"
                    + " order by no_transaksi asc ";        
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                if(res.first()){
                    txtNoTransaksi.setText(res.getString("no_transaksi"));
                    cmbIdSupplier.setSelectedItem(res.getString("id_supplier"));
                    cmbIdBarang.setSelectedItem(res.getString("id_barang"));
                    txtQty.setText(res.getString("qty"));
                    txtHarga.setText(res.getString("harga"));
                    txtJumlah.setText(res.getString("jumlah"));
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
            sqlselect = "SELECT * FROM dt_supplier order by id_supplier asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbIdSupplier.removeAllItems();
            cmbIdSupplier.repaint();
            cmbIdSupplier.addItem("-- PILIH SUPPLIER --");
            int i = 1;
            while(res.next()){
                cmbIdSupplier.addItem(res.getString("nm_supplier"));
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
    
     String[] KeySumBar;
    private void listSumBar(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "SELECT * FROM dt_barang order by id_barang asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbIdBarang.removeAllItems();
            cmbIdBarang.repaint();
            cmbIdBarang.addItem("-- PILIH BARANG --");
            int i = 1;
            while(res.next()){
                cmbIdBarang.addItem(res.getString("nm_barang"));
                i++;
            }
            res.first();
            KeySumBar = new String[i+1];
            for(Integer x =1;x < i;x++){
                KeySumBar[x] = res.getString(1);
                res.next();
            }
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method listSum " +ex);
        }
    }
 
 String[] KeyAkun;
    private void listAkun(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "SELECT * FROM perkiraan_akun order by id_akun asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbIdAkun.removeAllItems();
            cmbIdAkun.repaint();
            cmbIdAkun.addItem("-- PILIH PERKIRAAN AKUN --");
            int i = 1;
            while(res.next()){
                cmbIdAkun.addItem(res.getString("nm_perkiraan"));
                i++;
            }
            res.first();
            KeyAkun = new String[i+1];
            for(Integer x =1;x < i;x++){
                KeyAkun[x] = res.getString(1);
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
        txtNoTransaksi = new javax.swing.JTextField();
        cmbIdSupplier = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dtTrans = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        cmbIdAkun = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtKet = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataPembelian = new javax.swing.JTable();
        lblRecord = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtId = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbIdBarang = new javax.swing.JComboBox<>();
        Qty = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JTextField();
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

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Entri Data Pembelian");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setOpaque(false);

        txtNoTransaksi.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNoTransaksi.setOpaque(false);
        txtNoTransaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNoTransaksiKeyTyped(evt);
            }
        });

        cmbIdSupplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("ID Transaksi");

        jLabel3.setText("ID Supplier");

        dtTrans.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dtTrans.setOpaque(false);

        jLabel5.setText("Tanggal");

        cmbIdAkun.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("ID Akun");

        jLabel10.setText("Keterangan");

        txtKet.setColumns(20);
        txtKet.setRows(5);
        jScrollPane2.setViewportView(txtKet);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10))
                .addGap(100, 100, 100)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbIdAkun, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNoTransaksi)
                    .addComponent(cmbIdSupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dtTrans, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbIdAkun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNoTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbIdSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(dtTrans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1))))
        );

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

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/btn_delete.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
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
                .addGap(18, 18, 18)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 5, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbDataPembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No. Transaksi", "ID Supplier", "Nama Supplier", "ID Barang", "Nama Barang", "Qty", "Harga", "Jumlah"
            }
        ));
        tbDataPembelian.setRowHeight(25);
        tbDataPembelian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataPembelianMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataPembelian);

        lblRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRecord.setText("Record : 0");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        jLabel9.setText("No.");

        jLabel4.setText("Nama Barang");

        cmbIdBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        Qty.setText("Qty");

        txtQty.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtQty.setOpaque(false);
        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtQtyKeyTyped(evt);
            }
        });

        jLabel7.setText("Harga");

        txtHarga.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaActionPerformed(evt);
            }
        });

        txtJumlah.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtJumlah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtJumlahMouseClicked(evt);
            }
        });
        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });

        jLabel8.setText("Jumlah");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel4)
                            .addComponent(Qty))
                        .addGap(94, 94, 94)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtId)
                            .addComponent(cmbIdBarang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtQty)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(125, 125, 125)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHarga)
                            .addComponent(txtJumlah))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbIdBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Qty)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblRecord)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(174, 174, 174)
                            .addComponent(jLabel2)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRecord)
                .addGap(4, 4, 4))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
      
      
        enableForm();
        clearForm();
          Id();
      Ids();
        txtNoTransaksi.requestFocus(true);
        btnSimpan.setText("Simpan");
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(txtNoTransaksi.getText().equals("")){
            JOptionPane.showMessageDialog(this, "No Transaksi harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE);  
        }else if(cmbIdSupplier.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(this, "ID Supplier harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE); 
        }else{
            aksiSimpan();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
      
         if(txtNoTransaksi.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Belum ada data yang dipilih ! ",
            "Informasi",JOptionPane.INFORMATION_MESSAGE );  
         
        }else{
            aksiHapus();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tbDataPembelianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataPembelianMouseClicked
          if(evt.getClickCount()==2){
            vno_transaksi = tbDataPembelian.getValueAt(tbDataPembelian.getSelectedRow(), 0).toString();
               getData();
               
            
            btnSimpan.setText("Ubah");
            enableForm();
        }
    }//GEN-LAST:event_tbDataPembelianMouseClicked

    private void txtQtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyTyped
        if(txtQty.getText().length() == 45){
         evt.consume();
     }
    }//GEN-LAST:event_txtQtyKeyTyped

    private void txtNoTransaksiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoTransaksiKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoTransaksiKeyTyped

    private void txtHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaActionPerformed

    private void txtJumlahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtJumlahMouseClicked
          int vqty = Integer.parseInt(txtQty.getText());
          int vharga = Integer.parseInt(txtHarga.getText());
          txtJumlah.setText(String.valueOf(vqty*vharga));
          
    }//GEN-LAST:event_txtJumlahMouseClicked

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Qty;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbIdAkun;
    private javax.swing.JComboBox<String> cmbIdBarang;
    private javax.swing.JComboBox<String> cmbIdSupplier;
    private com.toedter.calendar.JDateChooser dtTrans;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tbDataPembelian;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextArea txtKet;
    private javax.swing.JTextField txtNoTransaksi;
    private javax.swing.JTextField txtQty;
    // End of variables declaration//GEN-END:variables
}
