package Form;
import Tool.ConfigDB;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrKaryawan extends javax.swing.JInternalFrame {


    ConfigDB getCnn = new ConfigDB();
    Connection _Cnn;
    
    String sqlselect, sqlinsert, sqldelete;
    String vid_karyawan, vnama, valamat, vtelepon, vjabatan, vstatus, vketerangan, vmasuk, mid;
    String visi_awal;
    DefaultTableModel tblkaryawan;
    
    SimpleDateFormat tglview = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat tglinput = new SimpleDateFormat("yyyy-MM-dd");
    public IfrKaryawan() {
        initComponents();
        
        Id();
        clearForm();
        disableForm();
        setTabel();
        showData();
//        listSum();
    }
    
    private void clearForm(){
       
        txtIdKaryawan.setText("");
        txtNama.setText("");
        txtAlamat.setText("");
        txtNoTelepon.setText("");
        txtStatus.setText("");
        txtKeterangan.setText("");
        dtMasuk.setDate(new java.util.Date());
//        dtKerja.setDate(new java.util.Date());
        cmbJabatan.setSelectedIndex(0);
    }
    
    private void disableForm(){
        txtIdKaryawan.setEnabled(false);
        txtNama.setEnabled(false);
        txtAlamat.setEnabled(false);
        txtNoTelepon.setEnabled(false);
        txtStatus.setEnabled(false);
        txtKeterangan.setEnabled(false);
        dtMasuk.setEnabled(false);
//        dtKerja.setEnabled(false);
        cmbJabatan.setSelectedIndex(0);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }

     private void enableForm(){
        txtIdKaryawan.setEnabled(true);
        txtNama.setEnabled(true);
        txtAlamat.setEnabled(true);
        txtNoTelepon.setEnabled(true);
        txtStatus.setEnabled(true);
        dtMasuk.setEnabled(true);
//        dtKerja.setEnabled(true);
        txtKeterangan.setEnabled(true);
        btnSimpan.setEnabled(true);
        cmbJabatan.setSelectedIndex(0);
        btnHapus.setEnabled(true);
    }
     
   
    private void setTabel(){
        String[]kolom1 = {"Id Karyawan", "Nama" , "Alamat", "No Telepon", "Jabatan", "Status", "Keterangan", "Tanggal Masuk"};
        tblkaryawan = new DefaultTableModel(null,kolom1){
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
                int cola = tblkaryawan.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tbDataKaryawan.setModel(tblkaryawan);
        tbDataKaryawan.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataKaryawan.getColumnModel().getColumn(1).setPreferredWidth(75);
        tbDataKaryawan.getColumnModel().getColumn(2).setPreferredWidth(75);
        tbDataKaryawan.getColumnModel().getColumn(3).setPreferredWidth(75);
        tbDataKaryawan.getColumnModel().getColumn(4).setPreferredWidth(75);
        tbDataKaryawan.getColumnModel().getColumn(5).setPreferredWidth(75);
        tbDataKaryawan.getColumnModel().getColumn(6).setPreferredWidth(75);
        tbDataKaryawan.getColumnModel().getColumn(7).setPreferredWidth(75);
       
    }
    
    private void clearTabel(){
        int row = tblkaryawan.getRowCount();
        for (int i = 0;i < row;i++){
             tblkaryawan.removeRow(0);
        }
    }
    
     private void showData(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabel();
            sqlselect =  "select * from dt_karyawan order by id_karyawan asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vid_karyawan = res.getString("id_karyawan");
                vnama = res.getString("nama");
                valamat = res.getString("alamat");
                vtelepon = res.getString("no_telepon");
                vjabatan = res.getString("jabatan");
                vstatus = res.getString("status");
                vketerangan = res.getString("keterangan");
                vmasuk = res.getString("tgl_masuk");
               
                
                Object[]data = {vid_karyawan, vnama, valamat, vtelepon, vjabatan, vstatus, vketerangan, vmasuk};
                tblkaryawan.addRow(data);
            }Id();
                 btnTambah.setText("Tambah");
            lblRecord.setText("Record : "+tbDataKaryawan.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdataKaryawan: " + ex);
            }
    }
    private void aksiSimpan(){
          vid_karyawan = txtIdKaryawan.getText();
          vjabatan = cmbJabatan.getSelectedItem().toString();
          vnama = txtNama.getText();
          valamat = txtAlamat.getText();
          vtelepon = txtNoTelepon.getText();
          vstatus = txtStatus.getText();
          vketerangan = txtKeterangan.getText();
          vmasuk = tglinput.format(dtMasuk.getDate());
//          vkerja = tglinput.format(dtKerja.getDate());
           if(btnSimpan.getText().equals("Simpan")){
            sqlinsert = "insert into dt_karyawan values "
                        + " ('"+vid_karyawan+"', '"+vnama+"', '"+valamat+"', '"+vtelepon+"','"+vjabatan+"',  '"+vstatus+"', '"+vketerangan+"', '"+vmasuk+"') ";
            
            JOptionPane.showMessageDialog(this, "Data Berhasil disimpan");
           }else{
               sqlinsert = "update dt_karyawan set nama ='"+vnama+"', alamat ='"+valamat+"', no_telepon = '"+vtelepon+"', jabatan = '"+vjabatan+"', status = '"+vstatus+"', keterangan = '"+vketerangan+"', tgl_masuk = '"+vmasuk+"' where id_karyawan='"+vid_karyawan+"' ";
                              
               JOptionPane.showMessageDialog(this, "Data Berhasil diUbah");
           }
           try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            Statement state = _Cnn.createStatement();
            state.executeUpdate(sqlinsert);
            
            clearForm(); disableForm(); showData();Id();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method aksiSimpan() : "+ex);
        } 
    }
    
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, 
                "Apakah anda yakin akan menghapus data ini ? ID : "+vid_karyawan,
                "Konfirmasi ",JOptionPane.YES_NO_OPTION);
        if(jawab== JOptionPane.YES_OPTION){
             try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqldelete = "delete from dt_karyawan where id_karyawan= '"+vid_karyawan+"'"; 
            java.sql.Statement state = _Cnn.createStatement();
            state.executeUpdate(sqldelete);
           JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
           clearForm();disableForm();showData();Id();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiHapus : " + ex);
        }
        
    }
        
    }   
    
     private void Id(){
        //kode jenis
        if(btnSimpan.getText().equals("Simpan")){
            try{
                _Cnn = getCnn.getConnection();
                String id = "select max(right(id_karyawan,3)) as id_karyawan from dt_karyawan";
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(id);
                while(res.next()){
                    if(res.first() == false){
                        mid = "KRY-" + "001";
                    } else{
                        res.last();
                        int noID = res.getInt(1) + 1;
                        String no = String.valueOf(noID);
//                        int noLong = no.length();
//                        for(int a=0;a<2-noLong;a++){
//                            no = "TRANS-PNJ-" + no;
//                        }
                        if(noID < 10){
                            mid =  "KRY-" + "00" + no;
                        } else if(noID < 100){
                            mid = "KRY-" + "0" + no;
                        } else{
                            mid= "KRY-" + no;
                        }
                        txtIdKaryawan.setText(mid);
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
                sqlselect = "select * from dt_karyawan "
                    + " where id_karyawan='"+vid_karyawan+"'"
                    + " order by id_karyawan asc ";        
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                if(res.first()){
                     visi_awal = res.getString("nama");
                    txtIdKaryawan.setText(res.getString("id_karyawan"));
                    cmbJabatan.setSelectedItem(res.getString("jabatan"));
                    txtNama.setText(res.getString("nama"));
                    txtAlamat.setText(res.getString("alamat"));
                    txtNoTelepon.setText(res.getString("no_telepon"));
                    txtStatus.setText(res.getString("status"));
                    txtKeterangan.setText(res.getString("keterangan"));
                    dtMasuk.setDate(res.getDate("tgl_masuk"));
//                    dtKerja.setDate(res.getDate("masa_kerja"));

                }   
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Eror Method GetDataUser : " + ex);
            }
    } 
//    String[] KeySum;
//    private void listSum(){
//        try{
//            _Cnn = null;
//            _Cnn = getCnn.getConnection();
//            sqlselect = "SELECT * FROM dt_customer order by id_customer asc";
//            Statement stat = _Cnn.createStatement();
//            ResultSet res = stat.executeQuery(sqlselect);
//            cmbJabatan.removeAllItems();
//            cmbJabatan.repaint();
//            cmbJabatan.addItem("-- PILIH CUSTOMER --");
//            int i = 1;
//            while(res.next()){
//                cmbJabatan.addItem(res.getString("nm_customer"));
//                i++;
//            }
//            res.first();
//            KeySum = new String[i+1];
//            for(Integer x =1;x < i;x++){
//                KeySum[x] = res.getString(1);
//                res.next();
//            }
//        } catch (SQLException ex){
//            JOptionPane.showMessageDialog(this, "Error Method listSum " +ex);
//        }
//    }
 
    private void cariKaryawan(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabel();
            sqlselect =  "select * from dt_karyawan where nm_karyawan like '%"+txtCari.getText()+"%' order by id_karyawan asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vid_karyawan = res.getString("id_karyawan");
                vnama = res.getString("nama");
                valamat = res.getString("alamat");
                vtelepon = res.getString("no_telepon");
                vjabatan = res.getString("jabatan");
                vstatus = res.getString("status");
                vketerangan = res.getString("keterangan");
                vmasuk = res.getString("tgl_masuk");
               
                
                Object[]data = {vid_karyawan, vnama, valamat, vtelepon, vjabatan, vstatus, vketerangan, vmasuk};
                tblkaryawan.addRow(data);
            }
            lblRecord.setText("Record : "+tbDataKaryawan.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdataKaryawan: " + ex);
            }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtAlamat = new javax.swing.JTextField();
        txtIdKaryawan = new javax.swing.JTextField();
        cmbJabatan = new javax.swing.JComboBox<String>();
        txtNama = new javax.swing.JTextField();
        txtNoTelepon = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        txtKeterangan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        dtMasuk = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataKaryawan = new javax.swing.JTable();
        lblRecord = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Entri Data Karyawan");

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

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtAlamat.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtAlamat.setOpaque(false);
        txtAlamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAlamatKeyTyped(evt);
            }
        });

        txtIdKaryawan.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtIdKaryawan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdKaryawanKeyTyped(evt);
            }
        });

        cmbJabatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- PILIH JABATAN --", "Staff", "Operator", "Kandang", "Lahan" }));

        txtNama.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNama.setOpaque(false);
        txtNama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNamaKeyTyped(evt);
            }
        });

        txtNoTelepon.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Id Karyawan");

        jLabel3.setText("Nama");

        jLabel4.setText("Alamat");

        jLabel5.setText("No Telepon");

        jLabel6.setText("Jabatan");

        txtStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtKeterangan.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setText("Status");

        jLabel8.setText("Keterangan");

        jLabel9.setText("Tanggal Masuk");

        dtMasuk.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dtMasuk.setOpaque(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdKaryawan)
                            .addComponent(txtNama)))
                    .addComponent(cmbJabatan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtAlamat, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNoTelepon)
                    .addComponent(txtKeterangan)
                    .addComponent(dtMasuk, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                    .addComponent(txtStatus, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNoTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cmbJabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(dtMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, -1, 240));

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

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel10.setText("Silahkan Mencari");

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
                .addGap(18, 18, 18)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jButton1)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, -1, -1));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbDataKaryawan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Karyawan", "Nama", "Alamat", "No Telepon", "Jabatan", "Status", "Keterangan", "Tanggal Masuk"
            }
        ));
        tbDataKaryawan.setRowHeight(25);
        tbDataKaryawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataKaryawanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataKaryawan);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 802, 123));

        lblRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRecord.setText("Record : 0");
        getContentPane().add(lblRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 500, -1, 20));

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel12.setText("DATA KARYAWAN");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/akuntansi (2).jpg"))); // NOI18N
        jLabel11.setText("jLabel11");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 520));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
      
        
        enableForm();
        clearForm();
        Id();
        txtIdKaryawan.requestFocus(true);
        btnSimpan.setText("Simpan");
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(txtIdKaryawan.getText().equals("")){
            JOptionPane.showMessageDialog(this, "ID Karyawan harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE);  
        }else if(cmbJabatan.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(this, "Jabatan harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE); 
        }else{
            try {
                String sqlCari = "";
                if(btnSimpan.getText().equals("Simpan")){
                    sqlCari = "select * from dt_karyawan where nama='"+txtNama.getText()+"'";
                }else{
                    sqlCari = "select * from dt_karyawan where nama='"+txtNama.getText()+"' "
                            + "and nama not in ('"+visi_awal+"')";
                }
                Statement s = (Statement)getCnn.getConnection().createStatement();
                ResultSet r = s.executeQuery(sqlCari);
                if(r.next()){
                    JOptionPane.showMessageDialog(this, "Karyawan tersebut sudah ada ! ",
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);  
                }else{
                    aksiSimpan();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }                    
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
      
         if(txtIdKaryawan.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Belum ada data yang dipilih ! ",
            "Informasi",JOptionPane.INFORMATION_MESSAGE );  
         
        }else{
            aksiHapus();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tbDataKaryawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataKaryawanMouseClicked
          if(evt.getClickCount()==2){
            vid_karyawan = tbDataKaryawan.getValueAt(tbDataKaryawan.getSelectedRow(), 0).toString();
               getData();
               
            
            btnSimpan.setText("Ubah");
            enableForm();
        }
    }//GEN-LAST:event_tbDataKaryawanMouseClicked

    private void txtAlamatKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAlamatKeyTyped
        if(txtAlamat.getText().length() == 45){
         evt.consume();
     }
    }//GEN-LAST:event_txtAlamatKeyTyped

    private void txtIdKaryawanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKaryawanKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdKaryawanKeyTyped

    private void txtNamaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaKeyTyped

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        cariKaryawan();
    }//GEN-LAST:event_txtCariKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        clearForm();
        disableForm();

        setTabel();
        showData();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbJabatan;
    private com.toedter.calendar.JDateChooser dtMasuk;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tbDataKaryawan;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtIdKaryawan;
    private javax.swing.JTextField txtKeterangan;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNoTelepon;
    private javax.swing.JTextField txtStatus;
    // End of variables declaration//GEN-END:variables
}
