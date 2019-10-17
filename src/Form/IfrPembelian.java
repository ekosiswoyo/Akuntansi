
package Form;

import Tool.ConfigDB;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrPembelian extends javax.swing.JInternalFrame {


    ConfigDB getCnn = new ConfigDB();
    Connection _Cnn;
    
    String sqlselect, sqlinsert, sqldelete, sqldelete1, sqldelete2,sqldelete3, sqlinsertt, sqlkas, sqldebet, sqlkredit;
    String vno_transaksi, vid_supplier, vbarang, vqty, vharga, vjumlah, vtgl, mid, vsupplier, vnmbarang, mids,bit, vidakun, vket;
    
    SimpleDateFormat tglview = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat tglinput = new SimpleDateFormat("yyyy-MM-dd");
    DefaultTableModel tblpembelian;
    DecimalFormat uang_indo = new DecimalFormat("Rp #,##0.00");
    public IfrPembelian() {
        initComponents();
        
    //    Id();
      //  Ids();
        clearForm();
        disableForm();
        setTabel();
        showData();
        listSum();
        listSumBar();
        
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
        
        dtTrans.setEnabled(false);
        txtKet.setEnabled(false);
        btnSimpan.setEnabled(false);
//        btnHapus.setEnabled(false);
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
       
//        btnHapus.setEnabled(true);
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
                vharga = uang_indo.format(res.getDouble("harga"));
                vjumlah =  uang_indo.format(res.getDouble("jumlah"));
                vbarang = res.getString("id_barang");
                vtgl = res.getString("tgl");
                Object[]data = {vno_transaksi, vid_supplier, vsupplier,vbarang,vnmbarang, vqty, vharga, vjumlah, vtgl};
                tblpembelian.addRow(data);
            }
            //Id();Ids();
                 btnTambah.setText("Tambah");
            lblRecord.setText("Record : "+tbDataPembelian.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdataPembelian : " + ex);
            }
    }
    private void aksiSimpan(){
          vno_transaksi = txtNoTransaksi.getText();
          vket = txtKet.getText();
//          bit = txtId.getText();
          vid_supplier = KeySum[cmbIdSupplier.getSelectedIndex()];
          vbarang = KeySumBar[cmbIdBarang.getSelectedIndex()];
          
          int vqty = Integer.parseInt(txtQty.getText());
          int vharga = Integer.parseInt(txtHarga.getText());
          vjumlah = String.valueOf(vqty*vharga);
          vtgl = tglinput.format(dtTrans.getDate());
           if(btnSimpan.getText().equals("Simpan")){
            sqlinsert = "insert into pembelian_header values "
                    + " ('"+vno_transaksi+"', '"+vid_supplier+"', '"+vtgl+"') ";
            sqlinsertt = "insert into pembelian values "
                    + " (null, '"+vno_transaksi+"', '"+vbarang+"', '"+vqty+"', '"+vharga+"', '"+vjumlah+"') ";
            sqlkas = "insert into kas_keluar values "
                    + " ('"+vno_transaksi+"','"+vtgl+"', '5-1006', '"+vket+"', '"+vjumlah+"') ";
            sqldebet = "insert into jurnal_umum values "
                    + " ('"+vno_transaksi+"','5-1006','"+vjumlah+"', '0', '"+vtgl+"') "; 
            sqlkredit = "insert into jurnal_umum values "
                    + " ('"+vno_transaksi+"','1-1001', '0','"+vjumlah+"', '"+vtgl+"') ";
            
            JOptionPane.showMessageDialog(this, "Data Berhasil disimpan");
          }
           /*else{
               sqlinsert = "update pembelian set id_barang ='"+vbarang+"', qty = '"+vqty+"', harga = '"+vharga+"', jumlah = '"+vjumlah+"' where no_trans='"+vno_transaksi+"' ";
                sqldebet = "update jurnal_umum set debet ='"+vjumlah+"' where no_transaksi ='"+vno_transaksi+"' and id_akun='6-1001'"; 
                sqlkredit = "update jurnal_umum set kredit ='"+vjumlah+"' where no_transaksi ='"+vno_transaksi+"' and id_akun='1-1001'"; 
                sqlkas = "update kas_keluar set nominal ='"+vjumlah+"' where no_transaksi ='"+vno_transaksi+"' and id_akun='6-1001'"; 
               JOptionPane.showMessageDialog(this, "Data Berhasil diUbah");
           }*/
           try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            Statement state = _Cnn.createStatement();
            if (btnSimpan.getText().equals("Simpan")){
            state.executeUpdate(sqlinsertt);
            
           }
           state.executeUpdate(sqlinsert);
           state.executeUpdate(sqldebet);
           state.executeUpdate(sqlkredit);
           state.executeUpdate(sqlkas);
            
            clearForm(); disableForm(); showData();
            //Id();Ids();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method aksiSimpan() : "+ex);
        } 
    }
    
    /*private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, 
                "Apakah anda yakin akan menghapus data ini ? ID : "+vno_transaksi,
                "Konfirmasi ",JOptionPane.YES_NO_OPTION);
        if(jawab== JOptionPane.YES_OPTION){
             try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqldelete = "delete from pembelian where no_trans= '"+vno_transaksi+"'"; 
            sqldelete1 = "delete from pembelian_header where no_trans= '"+vno_transaksi+"'"; 
              sqldelete2 = "delete from kas_keluar where no_transaksi= '"+vno_transaksi+"'"; 
              sqldelete3= "delete from jurnal_umum where no_transaksi= '"+vno_transaksi+"'"; 
            java.sql.Statement state = _Cnn.createStatement();
            state.executeUpdate(sqldelete);
              state.executeUpdate(sqldelete1);
                state.executeUpdate(sqldelete2);
                 state.executeUpdate(sqldelete3);
           JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
           clearForm();disableForm();showData();Id();Ids();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiHapus : " + ex);
        }
        
    }
        
    } */  
     private void getData() {
             try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqlselect = "select A.*,B.*,C.nm_supplier, D.nm_barang from pembelian as A  "
                        + "inner join pembelian_header as B on A.no_trans=B.no_trans "
                        + "inner join dt_supplier as C on B.id_supplier=C.id_supplier "
                         + "inner join dt_barang as D on D.nm_barang=D.nm_barang "
                    + " where B.no_trans='"+vno_transaksi+"' "
                    + " order by B.no_trans asc ";        
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                if(res.first()){
                    txtNoTransaksi.setText(res.getString("no_trans"));
                    cmbIdSupplier.setSelectedItem(res.getString("nm_supplier"));
                    cmbIdBarang.setSelectedItem(res.getString("nm_barang"));
                    txtQty.setText(res.getString("qty"));
                    txtHarga.setText(res.getString("harga"));
                    txtJumlah.setText(res.getString("jumlah"));
                    dtTrans.setDate(res.getDate("tgl"));
                }   
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Eror Method GetDataUser : " + ex);
            }
    } 
      private void Ids(){
        //kode jenis
        if(btnSimpan.getText().equals("Simpan")){
            try{
                _Cnn = getCnn.getConnection();
                String id = "select max(id_pembelian) as id_pembelian from pembelian";
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
//                        txtId.setText(mids);
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
                String id = "select max(right(no_trans,3)) as no_trans from pembelian_header";
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
                        if(noID < 100){
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
 
  private void cariPembelian(){
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
            }
            lblRecord.setText("Record : "+tbDataPembelian.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdataUser : " + ex);
            }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtNoTransaksi = new javax.swing.JTextField();
        cmbIdSupplier = new javax.swing.JComboBox<String>();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dtTrans = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtKet = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataPembelian = new javax.swing.JTable();
        lblRecord = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cmbIdBarang = new javax.swing.JComboBox<String>();
        Qty = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

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
        jLabel2.setText(" Data Pembelian");
        jLabel2.setOpaque(true);
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(336, 9, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        txtNoTransaksi.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNoTransaksi.setOpaque(false);
        txtNoTransaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNoTransaksiKeyTyped(evt);
            }
        });

        cmbIdSupplier.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("No Invoice");

        jLabel3.setText("ID Supplier");

        dtTrans.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dtTrans.setOpaque(false);

        jLabel5.setText("Tanggal");

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
                    .addComponent(jLabel5)
                    .addComponent(jLabel10))
                .addGap(100, 100, 100)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNoTransaksi)
                    .addComponent(cmbIdSupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dtTrans, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
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
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 37, -1, 176));

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

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel14.setText("Silahkan Mencari");

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
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(171, 171, 171)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 219, 894, -1));

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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 297, 908, 155));

        lblRecord.setForeground(new java.awt.Color(255, 255, 255));
        lblRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRecord.setText("Record : 0");
        getContentPane().add(lblRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 460, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("Nama Barang");

        cmbIdBarang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbIdBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbIdBarangActionPerformed(evt);
            }
        });

        Qty.setText("Qty");

        txtQty.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtQty.setOpaque(false);
        txtQty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtQtyMouseClicked(evt);
            }
        });
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
                            .addComponent(jLabel4)
                            .addComponent(Qty))
                        .addGap(94, 94, 94)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbIdBarang, 0, 261, Short.MAX_VALUE)
                            .addComponent(txtQty)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(125, 125, 125)
                        .addComponent(txtJumlah))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(129, 129, 129)
                        .addComponent(txtHarga)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbIdBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Qty)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap())
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(451, 37, -1, 176));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/akuntansi (2).jpg"))); // NOI18N
        jLabel6.setText("jLabel6");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 520));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
      
      
        enableForm();
        clearForm();
        //  Id();
    //  Ids();
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

    private void tbDataPembelianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataPembelianMouseClicked
          if(evt.getClickCount()==2){
            vno_transaksi = tbDataPembelian.getValueAt(tbDataPembelian.getSelectedRow(), 0).toString();
                 enableForm();
                 getData();
               
            
            btnSimpan.setText("Ubah");
          
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

    private void cmbIdBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbIdBarangActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cmbIdBarangActionPerformed

    private void txtQtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtQtyMouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txtQtyMouseClicked

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        cariPembelian();
    }//GEN-LAST:event_txtCariKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        clearForm();
        disableForm();

        setTabel();
        showData();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Qty;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbIdBarang;
    private javax.swing.JComboBox<String> cmbIdSupplier;
    private com.toedter.calendar.JDateChooser dtTrans;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tbDataPembelian;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextArea txtKet;
    private javax.swing.JTextField txtNoTransaksi;
    private javax.swing.JTextField txtQty;
    // End of variables declaration//GEN-END:variables
}
