package Form;





import Tool.ConfigDB;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.Timer;
import java.util.Date;


public class FrMenu extends javax.swing.JFrame {

    
    Connection _Cnn;
    ConfigDB getCnn = new ConfigDB();
    
    public String vid_user, vnm_user, vlevel ;
    
    private static String username, password, level;
    
    static String get_nik(){
        return username;
    }
    public static void set_nik(String nik){
        FrMenu.username=nik;
    }
    public static String get_pass(){
        return password;
    }
    public static void set_pass(String pass){
        FrMenu.password=pass;
    }
    public static String get_akses(){
        return level;
    }
    public static void set_akses(String hak_akses){
        FrMenu.level=hak_akses;
    }
    
    public FrMenu() {
        initComponents();
        tabLogin.setVisible(false);
        TanggalOtomatis();
        setJam();
        this.setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
        mnMaster.setEnabled(false);
        mnTransaksi.setEnabled(false);
        mnLaporan.setEnabled(false);
        mnPengaturan.setEnabled(false); 
        
    }
    
    private void userGeneral(){
        mnMaster.setEnabled(true);
        mnBarang.setVisible(false);
        mnKaryawan.setVisible(false);
        mnAkun.setVisible(false);
        
        mnTransaksi.setEnabled(true);
        mnTransAngsuran.setVisible(false);
        
        mnLaporan.setEnabled(true);
//        mnAngsuran.setVisible(false);
        
        mnPengaturan.setEnabled(true);
        
        back.setVisible(false);
        btnLogin.setVisible(false);
    }
    private void userKeuangan(){
        mnMaster.setEnabled(true);
        mnSupplier.setVisible(false);
        mnUser.setVisible(false);
        mnBarang.setVisible(false);
        
        mnTransaksi.setEnabled(true);
        mnTransPembelian.setVisible(false);
        mnTransPiutang.setVisible(false);
        mnTransAngsuran.setVisible(false);
        
        mnLaporan.setEnabled(true);
//        mnPembelian.setVisible(false);
//        mnAngsuran.setVisible(false);
//        
        mnPengaturan.setEnabled(true);
        mnBantuan.setVisible(false);
        
        back.setVisible(false);
        btnLogin.setVisible(false);
        
    }
    private void userAdministrasi(){
        mnMaster.setEnabled(true);
        mnSupplier.setVisible(false);
        mnCustomer.setVisible(false);
        mnUser.setVisible(false);
        mnAkun.setVisible(false);
        
        mnTransaksi.setEnabled(true);
        mnTransPenjualan.setVisible(false);
        mnTransPiutang.setVisible(false);
        mnTransJurnal.setVisible(false);
        mnTransMasuk.setVisible(false);
        
        mnLaporan.setEnabled(true);
        mnPenjualan.setVisible(false);
//        mnPiutang.setVisible(false);
//        mnJurnal.setVisible(false);
//        mnMasuk.setVisible(false);
//        mnKas.setVisible(false);
       
        mnPengaturan.setEnabled(true);
        mnBackup.setVisible(false);
        
        back.setVisible(false);
        btnLogin.setVisible(false);
        
    }
     public void TanggalOtomatis(){
      Date tanggal = new Date();
      tgl.setText(""+ (String.format("%1$td/%1$tm/%1$tY",tanggal)));
  
} 
    
     public void setJam() {
        ActionListener taskPerformer = new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        String nol_jam = "", nol_menit = "", nol_detik = "";

        Date dateTime = new Date();
        int nilai_jam = dateTime.getHours();
        int nilai_menit = dateTime.getMinutes();
        int nilai_detik = dateTime.getSeconds();

        if (nilai_jam <= 9) nol_jam = "0";
        if (nilai_menit <= 9) nol_menit = "0";
        if (nilai_detik <= 9) nol_detik = "0";

        String jam = nol_jam + Integer.toString(nilai_jam);
        String menit = nol_menit + Integer.toString(nilai_menit);
        String detik = nol_detik + Integer.toString(nilai_detik);

        lbljam.setText(jam + ":" + menit + ":" + detik + " ");
        }
      };
      new Timer(1000, taskPerformer).start();
      }

    private void aksi_login(){
        try{
            String akses = "";
            Connection _Cnn;
            ConfigDB getCnn = new ConfigDB();
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            String sql = " select nm_user, username, password, level"
                    + " from user WHERE username='"+txtIdUser.getText().replaceAll("'", "")+"' "
                    + " and password = '"+txtPass.getText().replaceAll("'", "")+"'";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            if(res.first()){
                username = res.getString("username");
                password = res.getString("password");
                level = res.getString("level");
                FrMenu.set_nik(username);
                
                tabLogin.setVisible(false);
                lblId.setText("ID. USER : " +username);
            lblHak_akses.setText("HAK AKSES : " +level);

            switch(level) {
                case "General Manager":
                    userGeneral();
                    break;
                case "Manager Keuangan":
                    userKeuangan();
                    break;
                case "Staff Administrasi":
                    userAdministrasi();
                    break;
            }
            }else{
                JOptionPane.showMessageDialog(this, "PERIKSA KEMBALI !");
            }
        }catch(Exception ex){}
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelDesktop = new javax.swing.JPanel();
        tabLogin = new javax.swing.JTabbedPane();
        panelLogin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtIdUser = new javax.swing.JTextField();
        btnLogin1 = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        txtPass = new javax.swing.JPasswordField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        tgl = new javax.swing.JLabel();
        lbljam = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        lblHak_akses = new javax.swing.JLabel();
        back = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        mnMaster = new javax.swing.JMenu();
        mnSupplier = new javax.swing.JMenuItem();
        mnCustomer = new javax.swing.JMenuItem();
        mnUser = new javax.swing.JMenuItem();
        mnKaryawan = new javax.swing.JMenuItem();
        mnAkun = new javax.swing.JMenuItem();
        mnBarang = new javax.swing.JMenuItem();
        mnTransaksi = new javax.swing.JMenu();
        mnTransPenjualan = new javax.swing.JMenuItem();
        mnTransPembelian = new javax.swing.JMenuItem();
        mnTransPenggajian = new javax.swing.JMenuItem();
        mnTransPiutang = new javax.swing.JMenuItem();
        mnTransJurnal = new javax.swing.JMenuItem();
        mnTransMasuk = new javax.swing.JMenuItem();
        mnTransKeluar = new javax.swing.JMenuItem();
        mnTransAngsuran = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        mnLaporan = new javax.swing.JMenu();
        mnPenjualan = new javax.swing.JMenuItem();
        mnPengaturan = new javax.swing.JMenu();
        mnBackup = new javax.swing.JMenuItem();
        mnBantuan = new javax.swing.JMenuItem();
        mnExit = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelDesktop.setBackground(new java.awt.Color(255, 255, 255));
        panelDesktop.setLayout(null);

        tabLogin.setBackground(new java.awt.Color(51, 102, 255));
        tabLogin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("LOGIN");

        txtIdUser.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtIdUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIdUserKeyPressed(evt);
            }
        });

        btnLogin1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/login-blue.png"))); // NOI18N
        btnLogin1.setText("Login");
        btnLogin1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogin1ActionPerformed(evt);
            }
        });

        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/0 - login.png"))); // NOI18N
        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        txtPass.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPassKeyPressed(evt);
            }
        });

        jLabel2.setText("Username");

        jLabel6.setText("Password");

        javax.swing.GroupLayout panelLoginLayout = new javax.swing.GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLoginLayout.createSequentialGroup()
                .addComponent(jSeparator4)
                .addGap(218, 218, 218))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLoginLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLoginLayout.createSequentialGroup()
                        .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                        .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdUser, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPass, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdUser, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(27, 27, 27)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tabLogin.addTab("Login System", panelLogin);

        panelDesktop.add(tabLogin);
        tabLogin.setBounds(321, 202, 347, 210);

        jLabel3.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("PT. KEJORA PELITA SEMESTA");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panelDesktop.add(jLabel3);
        jLabel3.setBounds(0, 11, 1062, 28);

        jLabel4.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("JL.SUMATERA nO.19 PEKALONGAN");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panelDesktop.add(jLabel4);
        jLabel4.setBounds(0, 54, 1062, 28);

        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Login.png"))); // NOI18N
        btnLogin.setText("LOGIN");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        panelDesktop.add(btnLogin);
        btnLogin.setBounds(20, 580, 130, 32);

        tgl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tgl.setText("Tanggal");
        panelDesktop.add(tgl);
        tgl.setBounds(610, 550, 200, 22);

        lbljam.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbljam.setText("Jam");
        panelDesktop.add(lbljam);
        lbljam.setBounds(610, 580, 200, 22);

        lblId.setText("ID. USER :");
        panelDesktop.add(lblId);
        lblId.setBounds(870, 560, 210, 14);

        lblHak_akses.setText("HAK AKSES : ");
        panelDesktop.add(lblHak_akses);
        lblHak_akses.setBounds(870, 590, 230, 14);

        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/back.jpg"))); // NOI18N
        panelDesktop.add(back);
        back.setBounds(470, -10, 1090, 820);

        mnMaster.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/master2.png"))); // NOI18N
        mnMaster.setText("Master");

        mnSupplier.setText("Supplier");
        mnSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSupplierActionPerformed(evt);
            }
        });
        mnMaster.add(mnSupplier);

        mnCustomer.setText("Customer");
        mnCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCustomerActionPerformed(evt);
            }
        });
        mnMaster.add(mnCustomer);

        mnUser.setText("User");
        mnUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnUserActionPerformed(evt);
            }
        });
        mnMaster.add(mnUser);

        mnKaryawan.setText("Karyawan");
        mnKaryawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnKaryawanActionPerformed(evt);
            }
        });
        mnMaster.add(mnKaryawan);

        mnAkun.setText("Akun");
        mnAkun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnAkunActionPerformed(evt);
            }
        });
        mnMaster.add(mnAkun);

        mnBarang.setText("Barang");
        mnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnBarangActionPerformed(evt);
            }
        });
        mnMaster.add(mnBarang);

        jMenuBar2.add(mnMaster);

        mnTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/trans1.png"))); // NOI18N
        mnTransaksi.setText("Transaksi");
        mnTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTransaksiActionPerformed(evt);
            }
        });

        mnTransPenjualan.setText("Penjualan");
        mnTransPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTransPenjualanActionPerformed(evt);
            }
        });
        mnTransaksi.add(mnTransPenjualan);

        mnTransPembelian.setText("Pembelian");
        mnTransPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTransPembelianActionPerformed(evt);
            }
        });
        mnTransaksi.add(mnTransPembelian);

        mnTransPenggajian.setText("Penggajian");
        mnTransPenggajian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTransPenggajianActionPerformed(evt);
            }
        });
        mnTransaksi.add(mnTransPenggajian);

        mnTransPiutang.setText("Piutang");
        mnTransPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTransPiutangActionPerformed(evt);
            }
        });
        mnTransaksi.add(mnTransPiutang);

        mnTransJurnal.setText("Jurnal Umum");
        mnTransJurnal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTransJurnalActionPerformed(evt);
            }
        });
        mnTransaksi.add(mnTransJurnal);

        mnTransMasuk.setText("Kas Masuk");
        mnTransMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTransMasukActionPerformed(evt);
            }
        });
        mnTransaksi.add(mnTransMasuk);

        mnTransKeluar.setText("Kas Keluar");
        mnTransKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTransKeluarActionPerformed(evt);
            }
        });
        mnTransaksi.add(mnTransKeluar);

        mnTransAngsuran.setText("Angsuran");
        mnTransAngsuran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTransAngsuranActionPerformed(evt);
            }
        });
        mnTransaksi.add(mnTransAngsuran);
        mnTransaksi.add(jSeparator7);
        mnTransaksi.add(jSeparator8);

        jMenuBar2.add(mnTransaksi);

        mnLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/report-small1.png"))); // NOI18N
        mnLaporan.setText("Laporan");
        mnLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLaporanActionPerformed(evt);
            }
        });

        mnPenjualan.setText("Laporan");
        mnPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnPenjualanActionPerformed(evt);
            }
        });
        mnLaporan.add(mnPenjualan);

        jMenuBar2.add(mnLaporan);

        mnPengaturan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/aktif-small.png"))); // NOI18N
        mnPengaturan.setText("Pengaturan");

        mnBackup.setText("Backup");
        mnBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnBackupActionPerformed(evt);
            }
        });
        mnPengaturan.add(mnBackup);

        mnBantuan.setText("Bantuan");
        mnBantuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnBantuanActionPerformed(evt);
            }
        });
        mnPengaturan.add(mnBantuan);

        mnExit.setText("Keluar");
        mnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnExitActionPerformed(evt);
            }
        });
        mnPengaturan.add(mnExit);
        mnPengaturan.add(jSeparator12);
        mnPengaturan.add(jSeparator13);
        mnPengaturan.add(jSeparator14);

        jMenuBar2.add(mnPengaturan);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panelDesktop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelDesktop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnTransMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTransMasukActionPerformed
        IfrKasMasuk internal;
        
        panelDesktop.removeAll();
        panelDesktop.repaint();
        internal = new IfrKasMasuk();
        internal.setVisible(true);
        try{
            internal.setMaximum(true);
        }catch(Exception e) {
            
        }
            panelDesktop.add(internal);
    }//GEN-LAST:event_mnTransMasukActionPerformed

    private void mnTransPenggajianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTransPenggajianActionPerformed
    IfrPenggajian internal;
        
        panelDesktop.removeAll();
        panelDesktop.repaint();
        internal = new IfrPenggajian();
        internal.setVisible(true);
        try{
            internal.setMaximum(true);
        }catch(Exception e) {
            
        }
            panelDesktop.add(internal);
    }//GEN-LAST:event_mnTransPenggajianActionPerformed

    private void mnTransKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTransKeluarActionPerformed
IfrKasKeluar internal;
        
        panelDesktop.removeAll();
        panelDesktop.repaint();
        internal = new IfrKasKeluar();
        internal.setVisible(true);
        try{
            internal.setMaximum(true);
        }catch(Exception e) {
            
        }
            panelDesktop.add(internal);
    }//GEN-LAST:event_mnTransKeluarActionPerformed

    private void mnUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnUserActionPerformed
//       int selectedOption = JOptionPane.showConfirmDialog(null,
//            "Tutup Aplikasi ?", "Tutup Aplikasi", JOptionPane.YES_NO_OPTION);
//       if(selectedOption == JOptionPane.YES_OPTION) {
//           System.exit(0);
//       }
         IfrUser internal;
        
//        panelDesktop.removeAll();
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        panelDesktop.repaint();
        internal = new IfrUser();
        internal.setVisible(true);
        try{
            internal.setMaximum(false);
        }catch(Exception e) {
            
        }
            panelDesktop.add(internal);
    }//GEN-LAST:event_mnUserActionPerformed

    private void mnBantuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnBantuanActionPerformed
        FrBantuan internal;
        
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        internal = new FrBantuan();
        internal.setVisible(true);
        try{
            internal.setMaximum(true);
        }catch(Exception e) {
            
        }
            panelDesktop.add(internal);
    }//GEN-LAST:event_mnBantuanActionPerformed

    private void mnTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTransaksiActionPerformed
        
    }//GEN-LAST:event_mnTransaksiActionPerformed

    private void mnTransPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTransPiutangActionPerformed
    IfrPiutang internal;
        

        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        panelDesktop.repaint();
        internal = new IfrPiutang();
        internal.setVisible(true);
        try{
            internal.setMaximum(false);
        }catch(Exception e) {
            
        }
            panelDesktop.add(internal);
    }//GEN-LAST:event_mnTransPiutangActionPerformed

    private void mnTransPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTransPenjualanActionPerformed
    IfrPenjualan internal;
        
//        panelDesktop.removeAll();
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        panelDesktop.repaint();
        internal = new IfrPenjualan();
        internal.setVisible(true);
        try{
            internal.setMaximum(false);
        }catch(Exception e) {
            
        }
            panelDesktop.add(internal);
            
    }//GEN-LAST:event_mnTransPenjualanActionPerformed

    private void mnTransPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTransPembelianActionPerformed
    IfrPembelian internal;
        
//        panelDesktop.removeAll();
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        panelDesktop.repaint();
        internal = new IfrPembelian();
        internal.setVisible(true);
        try{
            internal.setMaximum(false);
        }catch(Exception e) {
            
        }
            panelDesktop.add(internal);       
    }//GEN-LAST:event_mnTransPembelianActionPerformed

    private void mnTransAngsuranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTransAngsuranActionPerformed
 IfrAngsuran internal;
        
//        panelDesktop.removeAll();
        panelDesktop.repaint();
        
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        internal = new IfrAngsuran();
        internal.setVisible(true);
        try{
            internal.setMaximum(false);
        }catch(Exception e) {
            
        }
            panelDesktop.add(internal);  
    }//GEN-LAST:event_mnTransAngsuranActionPerformed

    private void mnCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCustomerActionPerformed
       IfrCustomer internal;
        
//        panelDesktop.removeAll();
        panelDesktop.repaint();
        
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        internal = new IfrCustomer();
        internal.setVisible(true);
        try{
            internal.setMaximum(false);
        }catch(Exception e) {
            
        }
            panelDesktop.add(internal);  
          
    }//GEN-LAST:event_mnCustomerActionPerformed

    private void mnSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSupplierActionPerformed
        IfrSupplier internal;
        
//        panelDesktop.removeAll();
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        panelDesktop.repaint();
        internal = new IfrSupplier();
        internal.setVisible(true);
        try{
            internal.setMaximum(false);
        }catch(Exception e) {
            
        }
            panelDesktop.add(internal);  
    }//GEN-LAST:event_mnSupplierActionPerformed

    private void mnTransJurnalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTransJurnalActionPerformed
IfrJurnal internal;
        
//        panelDesktop.removeAll();
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        panelDesktop.repaint();
        internal = new IfrJurnal();
        internal.setVisible(true);
        try{
            internal.setMaximum(false);
        }catch(Exception e) {
            
        }
            panelDesktop.add(internal);  
    }//GEN-LAST:event_mnTransJurnalActionPerformed

    private void mnKaryawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnKaryawanActionPerformed
        IfrKaryawan internal;
        
//        panelDesktop.removeAll();
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        panelDesktop.repaint();
        internal = new IfrKaryawan();
        internal.setVisible(true);
        try{
            internal.setMaximum(false);
        }catch(Exception e) {
            
        }
            panelDesktop.add(internal);  
    }//GEN-LAST:event_mnKaryawanActionPerformed

    private void mnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnBarangActionPerformed
        IfrBarang internal;
        
//        panelDesktop.removeAll();
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        panelDesktop.repaint();
        internal = new IfrBarang();
        internal.setVisible(true);
        try{
            internal.setMaximum(false);
        }catch(Exception e) {
            
        }
            panelDesktop.add(internal); 
    }//GEN-LAST:event_mnBarangActionPerformed

    private void mnAkunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnAkunActionPerformed
        IfrAkun internal;
        
//        panelDesktop.removeAll();
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        panelDesktop.repaint();
        internal = new IfrAkun();
        internal.setVisible(true);
        try{
            internal.setMaximum(false);
        }catch(Exception e) {
            
        }
            panelDesktop.add(internal); 
    }//GEN-LAST:event_mnAkunActionPerformed

    private void mnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnExitActionPerformed
        int selectedOption = JOptionPane.showConfirmDialog(null,
            "Tutup Aplikasi ?", "Tutup Aplikasi", JOptionPane.YES_NO_OPTION);
       if(selectedOption == JOptionPane.YES_OPTION) {
           System.exit(0);
       }
    }//GEN-LAST:event_mnExitActionPerformed

    private void mnBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnBackupActionPerformed
         BackupRestore internal;
         jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        internal = new BackupRestore();
        internal.setVisible(true);
        try{
            internal.setMaximum(true);
        }catch(Exception e) {

        }
        panelDesktop.add(internal);
    }//GEN-LAST:event_mnBackupActionPerformed

    private void mnLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLaporanActionPerformed
        Laporan internal;

        panelDesktop.removeAll();
        panelDesktop.repaint();
        internal = new Laporan();
        internal.setVisible(true);
        try{
            internal.setMaximum(true);
        }catch(Exception e) {

        }
        panelDesktop.add(internal);
    }//GEN-LAST:event_mnLaporanActionPerformed

    private void mnPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnPenjualanActionPerformed
        Laporan internal;
          jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        internal = new Laporan();
        internal.setVisible(true);
        try{
            internal.setMaximum(true);
        }catch(Exception e) {

        }
        panelDesktop.add(internal);
    }//GEN-LAST:event_mnPenjualanActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        //panelDesktop.add(tabLogin);
        tabLogin.setVisible(true);
    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER){
            btnLogin.requestFocus();
        }
    }//GEN-LAST:event_txtPassKeyPressed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        txtIdUser.setText("");
        txtPass.setText("");
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnLogin1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogin1ActionPerformed
        aksi_login();
    }//GEN-LAST:event_btnLogin1ActionPerformed

    private void txtIdUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdUserKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER){
            txtPass.requestFocus();
        }
    }//GEN-LAST:event_txtIdUserKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        try
        {
            com.jtattoo.plaf.mcwin.McWinLookAndFeel.setTheme("Default", "Java Swing", "");
            UIManager.setLookAndFeel("com.jtatto.plaf.mcwin.McWinLookAndFeel");
            SwingUtilities.updateComponentTreeUI(new FrMenu());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e){
        }
        
       new FrMenu().setVisible(true);        
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel back;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnLogin1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JLabel lblHak_akses;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lbljam;
    private javax.swing.JMenuItem mnAkun;
    private javax.swing.JMenuItem mnBackup;
    private javax.swing.JMenuItem mnBantuan;
    private javax.swing.JMenuItem mnBarang;
    private javax.swing.JMenuItem mnCustomer;
    private javax.swing.JMenuItem mnExit;
    private javax.swing.JMenuItem mnKaryawan;
    private javax.swing.JMenu mnLaporan;
    private javax.swing.JMenu mnMaster;
    private javax.swing.JMenu mnPengaturan;
    private javax.swing.JMenuItem mnPenjualan;
    private javax.swing.JMenuItem mnSupplier;
    private javax.swing.JMenuItem mnTransAngsuran;
    private javax.swing.JMenuItem mnTransJurnal;
    private javax.swing.JMenuItem mnTransKeluar;
    private javax.swing.JMenuItem mnTransMasuk;
    private javax.swing.JMenuItem mnTransPembelian;
    private javax.swing.JMenuItem mnTransPenggajian;
    private javax.swing.JMenuItem mnTransPenjualan;
    private javax.swing.JMenuItem mnTransPiutang;
    private javax.swing.JMenu mnTransaksi;
    private javax.swing.JMenuItem mnUser;
    private javax.swing.JPanel panelDesktop;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JTabbedPane tabLogin;
    private javax.swing.JLabel tgl;
    private javax.swing.JTextField txtIdUser;
    private javax.swing.JPasswordField txtPass;
    // End of variables declaration//GEN-END:variables

}