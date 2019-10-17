
package Form;

import Tool.ConfigDB;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrPenjualan extends javax.swing.JInternalFrame {


    ConfigDB getCnn = new ConfigDB();
    Connection _Cnn;
    
    String sqlselect, sqlinsert, sqldelete,sqldelete1 ,sqldelete2, sqldelete3, sqlinsertt, sqlkas, sqldebet, sqlkredit;
    String vno_transaksi, veartag, vsex, vbb, vharga, vketerangan, mid, vtgl, vcustomer, mids,bit, vidakun, vket;
    
    SimpleDateFormat tglview = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat tglinput = new SimpleDateFormat("yyyy-MM-dd");
    DefaultTableModel tblpenjualan;
    DecimalFormat uang_indo = new DecimalFormat("Rp #,##0.00");
    
    public IfrPenjualan() {
        initComponents();
        
        Id();
        Ids();
        clearForm();
        disableForm();
        setTabel();
        showData();
//        listSum();
        
    }
    
    private void clearForm(){
       
        txtNoTransaksi.setText("");
        txtEartag.setText("");
        cmbSex.setSelectedIndex(0);
        txtBB.setText("");
        txtHarga.setText("");
        txtNmCustomer.setText("");
    
       
        txtKeterangan.setText("");
        dtTrans.setDate(new java.util.Date());
    }
    
    private void disableForm(){
        txtNoTransaksi.setEnabled(false);
         txtNmCustomer.setEnabled(false);
        txtEartag.setEnabled(false);
        cmbSex.setEnabled(false);
        txtBB.setEnabled(false);
        txtHarga.setEnabled(false);
        dtTrans.setEnabled(false);
        txtKeterangan.setEnabled(false);
        
      
        btnSimpan.setEnabled(false);
        //btnHapus.setEnabled(false);
    }

     private void enableForm(){
        txtNoTransaksi.setEnabled(true);
         txtNmCustomer.setEnabled(true);
        txtEartag.setEnabled(true);
        cmbSex.setEnabled(true);
        txtBB.setEnabled(true);
        txtHarga.setEnabled(true);
        txtKeterangan.setEnabled(true);
        dtTrans.setEnabled(true);
        btnSimpan.setEnabled(true);
        
        
        //btnHapus.setEnabled(true);
    }
     
   
    private void setTabel(){
        String[]kolom1 = {"No Faktur", "Tanggal", "Nama Customer", "Eartag" ,"Sex", "BB Kg", "Harga", "Keterangan"};
        tblpenjualan = new DefaultTableModel(null,kolom1){
            Class[] types = new Class[]{
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
                int cola = tblpenjualan.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tbDataPenjualan.setModel(tblpenjualan);
        tbDataPenjualan.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataPenjualan.getColumnModel().getColumn(1).setPreferredWidth(75);
        tbDataPenjualan.getColumnModel().getColumn(2).setPreferredWidth(75);
        tbDataPenjualan.getColumnModel().getColumn(3).setPreferredWidth(75);
        tbDataPenjualan.getColumnModel().getColumn(4).setPreferredWidth(75);
        tbDataPenjualan.getColumnModel().getColumn(5).setPreferredWidth(75);
        tbDataPenjualan.getColumnModel().getColumn(6).setPreferredWidth(75);
        tbDataPenjualan.getColumnModel().getColumn(7).setPreferredWidth(75);
    }
    
    private void clearTabel(){
        int row = tblpenjualan.getRowCount();
        for (int i = 0;i < row;i++){
             tblpenjualan.removeRow(0);
        }
    }
    
     private void showData(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabel();
            sqlselect =  "select * from penjualan a, penjualan_header b WHERE a.no_trans=b.no_trans order by a.no_trans asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vno_transaksi = res.getString("no_trans");
                   vtgl = tglview.format(res.getDate("tgl"));
                vcustomer = res.getString("nm_customer");
                veartag = res.getString("eartag");
                vsex = res.getString("sex");
                vbb = res.getString("bb");
                vharga = uang_indo.format(res.getDouble("harga"));
                vketerangan = res.getString("keterangan");
             
                Object[]data = {vno_transaksi,  vtgl,  vcustomer, veartag, vsex, vbb, vharga, vketerangan};
                tblpenjualan.addRow(data);
            }Id();Ids();
                 btnTambah.setText("Tambah");
            lblRecord.setText("Record : "+tbDataPenjualan.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdataPenjulan : " + ex);
            }
    }
    private void aksiSimpan(){
        
//          bit = txtId.getText();
          vno_transaksi = txtNoTransaksi.getText();
             vtgl = tglinput.format(dtTrans.getDate());
          vcustomer = txtNmCustomer.getText();
          veartag = txtEartag.getText();
          vbb = txtBB.getText();
          vsex = cmbSex.getSelectedItem().toString();
          vharga = txtHarga.getText();
          vketerangan = txtKeterangan.getText();
       

           if(btnSimpan.getText().equals("Simpan")){
            sqlinsert = "insert into penjualan_header values "
                    + " ('"+vno_transaksi+"', '"+vcustomer+"', '"+vtgl+"') ";
            sqlinsertt = "insert into penjualan values "
                    + " (null,'"+vno_transaksi+"', '"+veartag+"', '"+vsex+"', '"+vbb+"', '"+vharga+"', '"+vketerangan+"') ";
            sqlkas = "insert into kas_masuk values "
                    + " ('"+vno_transaksi+"','"+vtgl+"', '4-1001', '"+vketerangan+"', '"+vharga+"') ";
            sqldebet = "insert into jurnal_umum values "
                    + " ('"+vno_transaksi+"','1-1001','"+vharga+"', '0', '"+vtgl+"') "; 
            sqlkredit = "insert into jurnal_umum values "
                    + " ('"+vno_transaksi+"','4-1001', '0','"+vharga+"', '"+vtgl+"') ";
            
            JOptionPane.showMessageDialog(this, "Data Berhasil disimpan");
         /*  }else{
               sqlinsert = "update penjualan set eartag ='"+veartag+"', sex ='"+vsex+"', bb ='"+vbb+"', harga ='"+vharga+"', keterangan = '"+vketerangan+"' where no_trans='"+vno_transaksi+"' ";
                 sqlkas = "update kas_masuk set nominal ='"+vharga+"' where no_transaksi ='"+vno_transaksi+"' and id_akun='4-1001'"; 
                 sqldebet = "update jurnal_umum set debet ='"+vharga+"' where no_transaksi ='"+vno_transaksi+"' and id_akun='1-1001'"; 
                sqlkredit = "update jurnal_umum set kredit ='"+vharga+"' where no_transaksi ='"+vno_transaksi+"' and id_akun='4-1001'";               
               JOptionPane.showMessageDialog(this, "Data Berhasil diUbah");
           }*/
           try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            Statement state = _Cnn.createStatement();
            if (btnSimpan.getText().equals("Simpan")){
            state.executeUpdate(sqlinsertt);
            state.executeUpdate(sqlkas);
           }
           state.executeUpdate(sqlinsert);
           state.executeUpdate(sqldebet);
           state.executeUpdate(sqlkredit);
           
            
            clearForm(); disableForm(); showData();Id();Ids();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method aksiSimpan() : "+ex);
        } 
    }
    
  /*  private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, 
                "Apakah anda yakin akan menghapus data ini ? ID : "+vno_transaksi,
                "Konfirmasi ",JOptionPane.YES_NO_OPTION);
        if(jawab== JOptionPane.YES_OPTION){
             try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqldelete = "delete from penjualan where no_trans= '"+vno_transaksi+"'"; 
             sqldelete1 = "delete from penjualan_header where no_trans= '"+vno_transaksi+"'"; 
             sqldelete2 = "delete from kas_masuk where no_transaksi= '"+vno_transaksi+"'"; 
             sqldelete3 = "delete from jurnal_umum where no_transaksi= '"+vno_transaksi+"'"; 
                  
            java.sql.Statement state = _Cnn.createStatement();
            state.executeUpdate(sqldelete);
             state.executeUpdate(sqldelete1); 
              state.executeUpdate(sqldelete2);
              state.executeUpdate(sqldelete3);
             
           JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
           clearForm();disableForm();showData();Id();Ids();
             btnHapus.setEnabled(false);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiHapus : " + ex);
        }
        
    }
        */
    }   
     private void getData() {
             try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqlselect = "select A.*,B.* from penjualan as A  "
                        + "inner join penjualan_header as B on A.no_trans=B.no_trans "
                
                    + " where B.no_trans='"+vno_transaksi+"' "
                    + " order by B.no_trans asc ";        
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                if(res.first()){
                    txtNoTransaksi.setText(res.getString("no_trans"));
                     dtTrans.setDate(res.getDate("tgl"));
                     txtNmCustomer.setText(res.getString("nm_customer"));
                    txtEartag.setText(res.getString("eartag"));
                   cmbSex.setSelectedItem(res.getString("sex"));
                    txtBB.setText(res.getString("bb"));
                    txtHarga.setText(res.getString("harga"));
                    txtKeterangan.setText(res.getString("keterangan"));
                    
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
                String id = "select max(id_penjualan) as id_penjualan from penjualan";
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
        if(btnSimpan.getText().equals("Simpan")){
               
            try{
                _Cnn = getCnn.getConnection();
                String id = "select max(right(no_trans,3)) as no_trans from penjualan_header";
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(id);
                while(res.next()){
                    if(res.first() == false){
                        mid = "TRANS-PNJ-" + "001";
                    } else{
                        res.last();
                        if(res.getString(1) == null){
                            mid = "TRANS-PNJ-" + "001";
                        }else{
                            int noID = Integer.parseInt(res.getString(1)) + 1;
                            String no = String.valueOf(noID);
    //                        int noLong = no.length();
    //                        for(int a=0;a<2-noLong;a++){
    //                            no = "TRANS-PNJ-" + no;
    //                        }
                            if(noID < 10){
                                mid =  "TRANS-PNJ-" + "00" + no;
                            } else if(noID < 100){
                                mid = "TRANS-PNJ-" + "0" + no;
                            } else{
                                mid= "TRANS-PNJ-" + no;
                            }
                        }
                       
                        txtNoTransaksi.setText(mid);
                        }
                   
                }
            } catch(SQLException ex){
                System.out.println("Error Method Id : " + ex);
            }
        }
    }
     
     
    
   /* String[] KeySum;
    private void listSum(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "SELECT * FROM dt_customer order by id_customer asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbIdCustomer.removeAllItems();
            cmbIdCustomer.repaint();
            cmbIdCustomer.addItem("-- PILIH CUSTOMER --");
            int i = 1;
            while(res.next()){
                cmbIdCustomer.addItem(res.getString("nm_customer"));
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
  private void cariPenjualan(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabel();
          
            sqlselect =  "select * from penjualan a, penjualan_header b, dt_customer c WHERE a.no_trans=b.no_trans like '%"+txtCari.getText()+"%' order by a.no_trans asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vno_transaksi = res.getString("no_trans");
               vtgl = res.getString("tgl");
                vcustomer = res.getString("nm_customer");
                veartag = res.getString("eartag");
                vsex = res.getString("sex");
                vbb = res.getString("bb");
                vharga = res.getString("harga");
                vketerangan = res.getString("keterangan");
              
                Object[]data = {vno_transaksi, vcustomer, veartag, vsex, vbb, vharga, vketerangan, vtgl};
                tblpenjualan.addRow(data);
            }
                 
            lblRecord.setText("Record : "+tbDataPenjualan.getRowCount());
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
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        dtTrans = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtKeterangan = new javax.swing.JTextArea();
        txtNmCustomer = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataPenjualan = new javax.swing.JTable();
        lblRecord = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtHarga = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtBB = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbSex = new javax.swing.JComboBox<String>();
        jLabel5 = new javax.swing.JLabel();
        txtEartag = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Form Input"));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setPreferredSize(new java.awt.Dimension(980, 519));
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVisible(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText(" Data Penjualan");
        jLabel2.setOpaque(true);
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 11, 287, 32));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtNoTransaksi.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNoTransaksi.setOpaque(false);
        txtNoTransaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNoTransaksiKeyTyped(evt);
            }
        });

        jLabel1.setText("No Faktur");

        jLabel3.setText("Nama Customer");

        jLabel8.setText("Keterangan");

        dtTrans.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dtTrans.setOpaque(false);

        jLabel9.setText("Tanggal");

        txtKeterangan.setColumns(20);
        txtKeterangan.setRows(5);
        jScrollPane2.setViewportView(txtKeterangan);

        txtNmCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNmCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8))
                .addGap(100, 100, 100)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(dtTrans, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                        .addComponent(txtNoTransaksi, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2))
                    .addComponent(txtNmCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNoTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNmCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dtTrans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addContainerGap(54, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 49, -1, -1));

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
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(233, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 230, -1, -1));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbDataPenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No. Faktur", "Tanggal", "Nama Customer", "Eartag", "Sex", "BB Kg", "Harga", "Keterangan"
            }
        ));
        tbDataPenjualan.setRowHeight(25);
        tbDataPenjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataPenjualanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataPenjualan);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 303, 976, 140));

        lblRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRecord.setText("Record : 0");
        getContentPane().add(lblRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(612, 449, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtHarga.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaActionPerformed(evt);
            }
        });

        jLabel7.setText("Harga");

        txtBB.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setText("BB Kg");

        cmbSex.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- PILIH --", "Jantan", "Betina" }));

        jLabel5.setText("Sex");

        txtEartag.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtEartag.setOpaque(false);
        txtEartag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEartagKeyTyped(evt);
            }
        });

        jLabel4.setText("Eartag");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtEartag, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBB, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtHarga, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbSex, 0, 205, Short.MAX_VALUE))
                .addGap(181, 181, 181))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEartag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbSex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(549, 49, 427, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/akuntansi (2).jpg"))); // NOI18N
        jLabel10.setText("jLabel10");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 990, 480));

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
        }else if(txtNmCustomer.getText().equals("")){
            JOptionPane.showMessageDialog(this, "ID Customer harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE); 
        }else{
            aksiSimpan();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void tbDataPenjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataPenjualanMouseClicked
          if(evt.getClickCount()==2){
            vno_transaksi = tbDataPenjualan.getValueAt(tbDataPenjualan.getSelectedRow(), 0).toString();
            enableForm();
            getData();
               
            
            btnSimpan.setText("Ubah");
            
        }
    }//GEN-LAST:event_tbDataPenjualanMouseClicked

    private void txtNoTransaksiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoTransaksiKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoTransaksiKeyTyped

    private void txtEartagKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEartagKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEartagKeyTyped

    private void txtHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaActionPerformed

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
        cariPenjualan();
    }//GEN-LAST:event_txtCariKeyTyped

    private void txtNmCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNmCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNmCustomerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbSex;
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
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tbDataPenjualan;
    private javax.swing.JTextField txtBB;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtEartag;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextArea txtKeterangan;
    private javax.swing.JTextField txtNmCustomer;
    private javax.swing.JTextField txtNoTransaksi;
    // End of variables declaration//GEN-END:variables
}
