
package Form;

import Tool.ConfigDB;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrPenggajian extends javax.swing.JInternalFrame {


    ConfigDB getCnn = new ConfigDB();
    Connection _Cnn;
    
    String sqlselect, sqlinsert, sqldelete, sqldelete1, sqldelete2, sqlkas, sqlkas1, sqldebet, sqldebet1, sqlkredit, sqlkredit1, sqlangsuran;
    String vno_transaksi, vid_karyawan, vgaji, vlemburan, vtransport, vinsentif, vpotongan, vtotal, mid, vtgl, vkaryawan, vidakun, vketerangan;
    String vi_karyawan, vnama, valamat, vtelepon, vjabatan, vstatus, vket, vmasuk, vkerja, vutang, vangsur, vangsuran;
    SimpleDateFormat tglview = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat tglinput = new SimpleDateFormat("yyyy-MM-dd");
    DefaultTableModel tblgaji,tblkaryawan;
    DecimalFormat uang_indo = new DecimalFormat("Rp #,##0.00");
    public IfrPenggajian() {
        initComponents();
        
        Id();
        clearForm();
        disableForm();
        setTabel();
        showData();
        listSum();
        
    }
    
    private void clearForm(){
       
        txtNoTransaksi.setText("");
        txtPotongan.setText("");
        txtGaji.setText("");
        txtLembur.setText("");
        txtTransport.setText("");
        txtInsentif.setText("");
        txtTotal.setText("");
        tfKaryawan.setText("");
        txtKeterangan.setText("");
        
        dtTrans.setDate(new java.util.Date());
    }
    
    private void disableForm(){
        txtNoTransaksi.setEnabled(false);
        txtPotongan.setEnabled(false);
        txtGaji.setEnabled(false);
        txtLembur.setEnabled(false);
        txtTransport.setEnabled(false);
        txtInsentif.setEnabled(false);
        txtTotal.setEnabled(false);
        tfKaryawan.setEnabled(false);
        txtKeterangan.setEnabled(false);
        
        dtTrans.setEnabled(false);
        btncari.setEnabled(false);
        btnSimpan.setEnabled(false);
       // btnHapus.setEnabled(false);
    }

     private void enableForm(){
        txtNoTransaksi.setEnabled(true);
        txtPotongan.setEnabled(true);
        txtGaji.setEnabled(true);
        txtLembur.setEnabled(true);
        txtTransport.setEnabled(true);
        txtTotal.setEnabled(true);
        txtInsentif.setEnabled(true);
        btnSimpan.setEnabled(true);
        dtTrans.setEnabled(true);
        tfKaryawan.setEnabled(false);
        txtKeterangan.setEnabled(true);
      
        btncari.setEnabled(true);
      //  btnHapus.setEnabled(true);
    }
     
   
    private void setTabel(){
        String[]kolom1 = {"No Transaksi","Id Karyawan","Nama Karyawan", "Tanggal","Gaji" , "Lemburan", "Transport", "Insentif", "Potongan", "Total", "Keterangan"};
        tblgaji = new DefaultTableModel(null,kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class,
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
                int cola = tblgaji.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tbDataGaji.setModel(tblgaji);
        tbDataGaji.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataGaji.getColumnModel().getColumn(1).setPreferredWidth(75);
        tbDataGaji.getColumnModel().getColumn(2).setPreferredWidth(75);
        tbDataGaji.getColumnModel().getColumn(3).setPreferredWidth(75);
        tbDataGaji.getColumnModel().getColumn(4).setPreferredWidth(75);
        tbDataGaji.getColumnModel().getColumn(5).setPreferredWidth(75);
        tbDataGaji.getColumnModel().getColumn(6).setPreferredWidth(75);
        tbDataGaji.getColumnModel().getColumn(7).setPreferredWidth(75);
        tbDataGaji.getColumnModel().getColumn(8).setPreferredWidth(75);
        tbDataGaji.getColumnModel().getColumn(9).setPreferredWidth(75);
        tbDataGaji.getColumnModel().getColumn(10).setPreferredWidth(75);
    }
    
    private void clearTabel(){
        int row = tblgaji.getRowCount();
        for (int i = 0;i < row;i++){
             tblgaji.removeRow(0);
        }
    }
    
     private void showData(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabel();
            sqlselect =  "select * from penggajian a, dt_karyawan b where a.id_karyawan=b.id_karyawan order by a.no_transaksi asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vno_transaksi = res.getString("no_transaksi");
                vid_karyawan = res.getString("id_karyawan");
                vkaryawan = res.getString("nama");
                vgaji = uang_indo.format(res.getDouble("gaji"));
                vlemburan = uang_indo.format(res.getDouble("lemburan"));
                vtransport =  uang_indo.format(res.getDouble("transport"));
                vinsentif =  uang_indo.format(res.getDouble("insentif"));
                vpotongan = uang_indo.format(res.getDouble("potongan"));
                vtotal =  uang_indo.format(res.getDouble("total"));
                vtgl = res.getString("tgl");
                vketerangan = res.getString("ket");
                
                Object[]data = {vno_transaksi, vid_karyawan, vkaryawan, vtgl, vgaji, vlemburan, vtransport, vinsentif, vpotongan, vtotal,  vketerangan};
                tblgaji.addRow(data);
            }Id();
                 btnTambah.setText("Tambah");
            lblRecord.setText("Record : "+tbDataGaji.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdataUser : " + ex);
            }
    }
      private void showDataKaryawan(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabel();
            sqlselect =  "select * from dt_karyawan order by id_karyawan asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vi_karyawan = res.getString("id_karyawan");
                vnama = res.getString("nama");
                valamat = res.getString("alamat");
                vtelepon = res.getString("no_telepon");
                vjabatan = res.getString("jabatan");
                vstatus = res.getString("status");
                vket = res.getString("keterangan");
                vmasuk = res.getString("tgl_masuk");
                vkerja = res.getString("masa_kerja");
                
                Object[]data = {vi_karyawan, vnama, valamat, vtelepon, vjabatan, vstatus, vketerangan, vmasuk, vkerja};
                tblkaryawan.addRow(data);
            }
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdataUser : " + ex);
            }
    }
      private void setTabelKaryawan(){
        String[]kolom1 = {"Id Karyawan", "Nama" , "Alamat", "No Telepon", "Jabatan", "Status", "Keterangan", "Tanggal Masuk", "Masa Kerja"};
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
        tbDataKaryawan.getColumnModel().getColumn(8).setPreferredWidth(75);
        tbDataKaryawan.getColumnModel().getColumn(8).setPreferredWidth(75);
         
    }
    private void aksiSimpan(){
          
          vketerangan = txtKeterangan.getText();
          
          vno_transaksi = txtNoTransaksi.getText();
          vid_karyawan = tfKaryawan.getText().substring(0,7);
          int vgaji = Integer.parseInt(txtGaji.getText());
          int vlemburan = Integer.parseInt(txtLembur.getText());
          int vtransport = Integer.parseInt(txtTransport.getText());
          int vinsentif = Integer.parseInt(txtInsentif.getText());
          int vpotongan = Integer.parseInt(txtPotongan.getText());
          vtotal = String.valueOf(vgaji+vlemburan+vtransport+vinsentif-vpotongan);
          vtgl = tglinput.format(dtTrans.getDate());
          
           if(btnSimpan.getText().equals("Simpan")){
            sqlinsert = "insert into penggajian values "
                        + " ('"+vno_transaksi+"','"+vid_karyawan+"', '"+vgaji+"', '"+vlemburan+"','"+vtransport+"',  '"+vinsentif+"', '"+vpotongan+"', '"+vtotal+"', '"+vtgl+"', '"+vketerangan+"') ";
            sqlkas = "insert into kas_keluar values "
                    + " ('"+vno_transaksi+"','"+vtgl+"', '5-1003', '"+vketerangan+"', '"+vtotal+"') ";
           //  sqlkas1 = "insert into kas_masuk values "
           //         + " ('"+vno_transaksi+"','"+vtgl+"', '1-1011', '"+vketerangan+"', '"+vtotal+"') ";
            sqlangsuran = "insert into angsuran values "
                    + " ('"+vno_transaksi+"', '"+vutang+"', '"+vangsur+"',  '"+vangsuran+"') ";
           sqldebet = "insert into jurnal_umum values "
                    + " ('"+vno_transaksi+"','5-0003','"+vtotal+"', '0', '"+vtgl+"') "; 
           sqlkredit = "insert into jurnal_umum values "
                    + " ('"+vno_transaksi+"','1-1001', '0','"+vtotal+"', '"+vtgl+"') ";
          //  sqldebet1 = "insert into jurnal_umum values "
           //         + " ('"+vno_transaksi+"', '1-1001', '"+vpotongan+"', '0', '"+vtgl+"') "; 
          //  sqlkredit1 = "insert into jurnal_umum values "
           //         + " ('"+vno_transaksi+"', '1-1011', '0','"+vpotongan+"', '"+vtgl+"') ";
            
            JOptionPane.showMessageDialog(this, "Data Berhasil disimpan");
          /*}else{
               sqlinsert = "update penggajian set id_karyawan ='"+vid_karyawan+"', gaji ='"+vgaji+"', lemburan = '"+vlemburan+"', transport = '"+vtransport+"', insentif = '"+vinsentif+"', potongan= '"+vpotongan+"', total = '"+vtotal+"', tgl = '"+vtgl+"', keterangan ='"+vketerangan+"' where no_transaksi ='"+vno_transaksi+"' ";
                sqldebet = "update jurnal_umum set debet ='"+vtotal+"' where no_transaksi ='"+vno_transaksi+"' and id_akun='5-1001'"; 
                 sqldebet1 = "update jurnal_umum set debet ='"+vpotongan+"' where no_transaksi ='"+vno_transaksi+"' and id_akun='4-1002'"; 
                sqlkredit = "update jurnal_umum set kredit ='"+vtotal+"' where no_transaksi ='"+vno_transaksi+"' and id_akun='1-1001'";      
                 sqlkas = "update kas_keluar set nominal ='"+vtotal+"' where no_transaksi ='"+vno_transaksi+"' and id_akun='5-1001'";
               JOptionPane.showMessageDialog(this, "Data Berhasil diUbah");
           }*/
           try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            Statement state = _Cnn.createStatement();

             if(txtPotongan.getText().equals("0")){
                 
             }else{
                 state.executeUpdate(sqlangsuran);
             }
           state.executeUpdate(sqlinsert);
           state.executeUpdate(sqldebet);
           state.executeUpdate(sqlkredit);
          // state.executeUpdate(sqldebet1);
          // state.executeUpdate(sqlkredit1);
            state.executeUpdate(sqlkas);
           //  state.executeUpdate(sqlkas1);
            clearForm(); disableForm(); showData();Id();
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
            sqldelete = "delete from penggajian where no_transaksi= '"+vno_transaksi+"'"; 
            sqldelete1 = "delete from kas_keluar where no_transaksi= '"+vno_transaksi+"'"; 
              sqldelete2= "delete from jurnal_umum where no_transaksi= '"+vno_transaksi+"'"; 
            java.sql.Statement state = _Cnn.createStatement();
            state.executeUpdate(sqldelete);
             state.executeUpdate(sqldelete1);
              state.executeUpdate(sqldelete2);
           JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
           clearForm();disableForm();showData();Id();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiHapus : " + ex);
        }
        
    }*/
        
    }  
    
     private void Id(){
        //kode jenis
        if(btnSimpan.getText().equals("Simpan")){
            try{
                _Cnn = getCnn.getConnection();
                String id = "select max(right(no_transaksi,3)) as no_transaksi from penggajian";
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(id);
                while(res.next()){
                    if(res.first() == false){
                        mid = "TRANS-PGJ-" + "001";
                    } else{
                        res.last();
                        int noID = res.getInt(1) + 1;
                        String no = String.valueOf(noID);
//                        int noLong = no.length();
//                        for(int a=0;a<2-noLong;a++){
//                            no = "TRANS-PNJ-" + no;
//                        }
                        if(noID < 10){
                            mid =  "TRANS-PGJ-" + "00" + no;
                        } else if(noID < 100){
                            mid = "TRANS-PGJ-" + "0" + no;
                        } else{
                            mid= "TRANS-PGJ-" + no;
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
                sqlselect = "select * from penggajian "
                    + " where no_transaksi='"+vno_transaksi+"'"
                    + " order by no_transaksi asc ";        
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                if(res.first()){
                    txtNoTransaksi.setText(res.getString("no_transaksi"));
                    tfKaryawan.setText(res.getString("id_karyawan"));
                    txtGaji.setText(res.getString("gaji"));
                    txtLembur.setText(res.getString("lemburan"));
                    txtTransport.setText(res.getString("transport"));
                    txtInsentif.setText(res.getString("insentif"));
                    txtPotongan.setText(res.getString("potongan"));
                    dtTrans.setDate(res.getDate("tgl"));
                    txtKeterangan.setText(res.getString("ket"));

                }   
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Eror Method GetDataUser : " + ex);
            }
    }
     private void getDataKaryawan() {
             try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqlselect = "select * from piutang "
                    + " where id_karyawan='"+vi_karyawan+"'";
                Statement stat = _Cnn.createStatement();
                Statement stat1 = _Cnn.createStatement();
                Statement s = (Statement)getCnn.getConnection().createStatement();
                ResultSet r = s.executeQuery(sqlselect);
                int tot_angsur = 0 ;
                vangsur = "1";
                vutang = "";
                if(r.next()){
                    ResultSet res2 = stat.executeQuery("select sum(jumlah),count(*) from angsuran "
                            + "where id_piutang='"+r.getString("id_piutang")+"'");
                    if(res2.next()){
                        tot_angsur = res2.getInt("sum(jumlah)");
                        vangsur = Integer.toString(res2.getInt("count(*)") + 1);
                    }
                    vutang = r.getString("id_piutang");
                    vangsuran = r.getString("potongan");
                    if((r.getInt("jml_piutang") - tot_angsur) == 0){
                        txtPotongan.setText("0");
                        txtSisaPiutang.setText("0");
                    }else{
                        txtPotongan.setText(r.getString("potongan"));
                        txtSisaPiutang.setText(Integer.toString(r.getInt("jml_piutang") - tot_angsur));
                    }
                }else{
                    txtPotongan.setText("0");
                    txtSisaPiutang.setText("0");
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
            int i = 1;
            while(res.next()){
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
     private void cariGaji(){
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                clearTabel();
                sqlselect ="select * from penggajian "
                    + " where nama like '%"+txtCari.getText()+"%' order by id_karyawan asc ";     
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                while(res.next()){
                    
                    vi_karyawan = res.getString("id_karyawan");
                vnama = res.getString("nama");
                valamat = res.getString("alamat");
                vtelepon = res.getString("no_telepon");
                vjabatan = res.getString("jabatan");
                vstatus = res.getString("status");
                vket = res.getString("keterangan");
                vmasuk = res.getString("tgl_masuk");
                vkerja = res.getString("masa_kerja");
                
                Object[]data = {vi_karyawan, vnama, valamat, vtelepon, vjabatan, vstatus, vketerangan, vmasuk, vkerja};
                tblkaryawan.addRow(data);
            }
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Eror Method showDataGaji : " + ex);
            }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jd_pegawai = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbDataKaryawan = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtNoTransaksi = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        dtTrans = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtKeterangan = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        tfKaryawan = new javax.swing.JTextField();
        btncari = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataGaji = new javax.swing.JTable();
        lblRecord = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtPotongan = new javax.swing.JTextField();
        txtGaji = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtLembur = new javax.swing.JTextField();
        txtInsentif = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtTransport = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtSisaPiutang = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        jd_pegawai.setBounds(new java.awt.Rectangle(0, 0, 750, 320));

        jScrollPane3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbDataKaryawan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Karyawan", "Nama", "Alamat", "No Telepon", "Jabatan", "Status", "Keterangan", "Tanggal Masuk", "Masa Kerja"
            }
        ));
        tbDataKaryawan.setRowHeight(25);
        tbDataKaryawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataKaryawanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbDataKaryawanMouseEntered(evt);
            }
        });
        jScrollPane3.setViewportView(tbDataKaryawan);

        javax.swing.GroupLayout jd_pegawaiLayout = new javax.swing.GroupLayout(jd_pegawai.getContentPane());
        jd_pegawai.getContentPane().setLayout(jd_pegawaiLayout);
        jd_pegawaiLayout.setHorizontalGroup(
            jd_pegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
        );
        jd_pegawaiLayout.setVerticalGroup(
            jd_pegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_pegawaiLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 98, Short.MAX_VALUE))
        );

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
        jLabel2.setText("Data Penggajian");
        jLabel2.setOpaque(true);
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 4, 190, 30));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtNoTransaksi.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNoTransaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNoTransaksiKeyTyped(evt);
            }
        });

        jLabel1.setText("No Transaksi");

        jLabel4.setText("ID Karyawan");

        dtTrans.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dtTrans.setOpaque(false);

        jLabel3.setText("Tanggal");

        txtKeterangan.setColumns(20);
        txtKeterangan.setRows(5);
        jScrollPane2.setViewportView(txtKeterangan);

        jLabel12.setText("Keterangan");

        tfKaryawan.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tfKaryawan.setEnabled(false);
        tfKaryawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfKaryawanActionPerformed(evt);
            }
        });

        btncari.setText("Cari Karyawan");
        btncari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncariActionPerformed(evt);
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
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel12))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtNoTransaksi)
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                            .addComponent(dtTrans, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tfKaryawan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncari)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNoTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncari))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dtTrans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addContainerGap(18, Short.MAX_VALUE))
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
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCari)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(176, 176, 176))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton1))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel14)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 292, 827, -1));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbDataGaji.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No Transaksi", "ID Karyawan", "Nama Karyawan", "Tanggal", "Gaji", "Lemburan", "Transport", "Insentif", "Potongan", "Total", "Keterangan"
            }
        ));
        tbDataGaji.setRowHeight(25);
        tbDataGaji.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataGajiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataGaji);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 364, 827, 160));

        lblRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRecord.setText("Record : 0");
        getContentPane().add(lblRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(741, 535, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtPotongan.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtPotongan.setOpaque(false);
        txtPotongan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPotonganKeyTyped(evt);
            }
        });

        txtGaji.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtGaji.setOpaque(false);
        txtGaji.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGajiKeyTyped(evt);
            }
        });

        txtTotal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtTotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTotalMouseClicked(evt);
            }
        });

        jLabel7.setText("Transport");

        txtLembur.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtInsentif.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setText("Lemburan");

        jLabel9.setText("Potongan Piutang");

        jLabel5.setText("Gaji");

        jLabel11.setText("Total");

        txtTransport.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setText("Insentif");

        txtSisaPiutang.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtSisaPiutang.setOpaque(false);
        txtSisaPiutang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSisaPiutangKeyTyped(evt);
            }
        });

        jLabel10.setText("Sisa Piutang");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10))
                .addGap(53, 53, 53)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSisaPiutang)
                    .addComponent(txtLembur, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                    .addComponent(txtTransport, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtGaji)
                    .addComponent(txtTotal)
                    .addComponent(txtPotongan, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtInsentif, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLembur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtTransport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtInsentif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPotongan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSisaPiutang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(423, 37, -1, 244));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/akuntansi (2).jpg"))); // NOI18N
        jLabel13.setText("jLabel13");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 840, 580));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
      
        
        enableForm();
        clearForm();
        Id();
        txtNoTransaksi.requestFocus(true);
        btnSimpan.setText("Simpan");
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(txtNoTransaksi.getText().equals("")){
            JOptionPane.showMessageDialog(this, "No Transaksi harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE);  
        }else if(tfKaryawan.getText().equals("")){
            JOptionPane.showMessageDialog(this, "ID Karyawan harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE); 
        }else{
            aksiSimpan();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void tbDataGajiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataGajiMouseClicked
          /*if(evt.getClickCount()==2){
            vno_transaksi = tbDataGaji.getValueAt(tbDataGaji.getSelectedRow(), 0).toString();
               enableForm();
               getData();
               
            
            btnSimpan.setText("Ubah");
            
        }*/
    }//GEN-LAST:event_tbDataGajiMouseClicked
 
    private void txtPotonganKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPotonganKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPotonganKeyTyped

    private void txtNoTransaksiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoTransaksiKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoTransaksiKeyTyped

    private void txtGajiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGajiKeyTyped
        if(txtGaji.getText().length() == 45){
            evt.consume();
        }
    }//GEN-LAST:event_txtGajiKeyTyped

    private void txtTotalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTotalMouseClicked
int vgaji = Integer.parseInt(txtGaji.getText());
          int vlemburan = Integer.parseInt(txtLembur.getText());
          int vtransport = Integer.parseInt(txtTransport.getText());
          int vinsentif = Integer.parseInt(txtInsentif.getText());
          int vpotongan = Integer.parseInt(txtPotongan.getText()); 
          txtTotal.setText(String.valueOf(vgaji+vlemburan+vtransport+vinsentif-vpotongan));
    }//GEN-LAST:event_txtTotalMouseClicked

    private void tbDataKaryawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataKaryawanMouseClicked
        if(evt.getClickCount()==2){
            vid_karyawan = tbDataKaryawan.getValueAt(tbDataKaryawan.getSelectedRow(), 0).toString();
            vi_karyawan = tbDataKaryawan.getValueAt(tbDataKaryawan.getSelectedRow(), 0).toString();
            String id_kry = tbDataKaryawan.getValueAt(tbDataKaryawan.getSelectedRow(), 0).toString();
            String nm_kry = tbDataKaryawan.getValueAt(tbDataKaryawan.getSelectedRow(), 1).toString();
            tfKaryawan.setText(id_kry+" - "+nm_kry);
            getDataKaryawan();
            
            jd_pegawai.setModal(false);
            jd_pegawai.setVisible(false);
        }
    }//GEN-LAST:event_tbDataKaryawanMouseClicked

    private void btncariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncariActionPerformed
        // TODO add your handling code here:
        setTabelKaryawan();
        showDataKaryawan();
        jd_pegawai.setLocationRelativeTo(this);
        jd_pegawai.setModal(true);
        jd_pegawai.setVisible(true);
    }//GEN-LAST:event_btncariActionPerformed

    private void tfKaryawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfKaryawanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfKaryawanActionPerformed

    private void txtSisaPiutangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSisaPiutangKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSisaPiutangKeyTyped

    private void tbDataKaryawanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataKaryawanMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDataKaryawanMouseEntered

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        cariGaji();
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
    private javax.swing.JButton btncari;
    private com.toedter.calendar.JDateChooser dtTrans;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JDialog jd_pegawai;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tbDataGaji;
    private javax.swing.JTable tbDataKaryawan;
    private javax.swing.JTextField tfKaryawan;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtGaji;
    private javax.swing.JTextField txtInsentif;
    private javax.swing.JTextArea txtKeterangan;
    private javax.swing.JTextField txtLembur;
    private javax.swing.JTextField txtNoTransaksi;
    private javax.swing.JTextField txtPotongan;
    private javax.swing.JTextField txtSisaPiutang;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTransport;
    // End of variables declaration//GEN-END:variables
}
