package Form;

import Tool.ConfigDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Laporan extends javax.swing.JInternalFrame {

    ConfigDB getCnn = new ConfigDB();
    Connection Conn;
    String sqlselect;
    String vkd_kat, vnm_kat, vtglAwal, vtglAkhir;
    int Saldo=0;
    SimpleDateFormat date_f = new SimpleDateFormat("yyyy-MM-dd");
//    String id = FrMenu.getU_id();
    
    public Laporan() {
        initComponents();
        Locale locale =new Locale ("id", "ID");
        locale.setDefault(locale);
        clearForm();
    }
    private void clearForm(){
        dtTglAwal.setDate(new Date());
        dtTglAkhir.setDate(new Date());
    }
   
  
    private void tanggal(){
        try{
        if(dtTglAwal.getDate() !=null && dtTglAkhir.getDate()!=null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            vtglAwal=format.format(dtTglAwal.getDate());
            vtglAkhir=format.format(dtTglAkhir.getDate());
            Date Tanggal1 = format.parse(vtglAwal);
            Date Tanggal2 = format.parse(vtglAkhir);
            long Hari1 = Tanggal1.getTime();
            long Hari2 = Tanggal2.getTime();
            long diff = Hari2 - Hari1;
            long Lama = diff / (24 * 60 * 60 * 1000);
            if(Lama<0){
                JOptionPane.showMessageDialog(null, "TANGGAL AKHIR TIDAK BISA KURANG DARI TANGGAL AWAL ! ");
                clearForm();
            }
        }
        } catch(Exception e){
            
        }
    }
    
    private void cetakLapPenjualanPerTgl(){
        String pth = System.getProperty("user.dir") + "/Laporan/lapPenjualan.jrxml";
        try{
            Map<String, Object> parameters = new HashMap<>();
            Conn = null;
            Conn = getCnn.getConnection();
            parameters.put("parAwal",dtTglAwal.getDate());
            parameters.put("parAkhir",dtTglAkhir.getDate());
            JasperReport jrpt = JasperCompileManager.compileReport(pth);
            JasperPrint jprint = JasperFillManager.fillReport(jrpt, parameters, Conn);
            JasperViewer.viewReport(jprint, false);
        }catch (SQLException | JRException ex){
            JOptionPane.showConfirmDialog(null, "Error method cetakLaporanperTanggal : " + ex,
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void cetakLapPembelianPerTgl(){
        String pth = System.getProperty("user.dir") + "/Laporan/lapPembelian.jrxml";
        try{
            Map<String, Object> parameters = new HashMap<>();
            Conn = null;
            Conn = getCnn.getConnection();
            parameters.put("parTglAwal",dtTglAwal.getDate());
            parameters.put("parTglAkhir",dtTglAkhir.getDate());
            JasperReport jrpt = JasperCompileManager.compileReport(pth);
            JasperPrint jprint = JasperFillManager.fillReport(jrpt, parameters, Conn);
            JasperViewer.viewReport(jprint, false);
        }catch (SQLException | JRException ex){
            JOptionPane.showConfirmDialog(null, "Error method cetakLaporanperTanggal : " + ex,
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void cetakLapPenggajianPerTgl(){
        String pth = System.getProperty("user.dir") + "/Laporan/lapPenggajian.jrxml";
        try{
            Map<String, Object> parameters = new HashMap<>();
            Conn = null;
            Conn = getCnn.getConnection();
            parameters.put("parTglAwal",dtTglAwal.getDate());
            parameters.put("parTglAkhir",dtTglAkhir.getDate());
            JasperReport jrpt = JasperCompileManager.compileReport(pth);
            JasperPrint jprint = JasperFillManager.fillReport(jrpt, parameters, Conn);
            JasperViewer.viewReport(jprint, false);
        }catch (SQLException | JRException ex){
            JOptionPane.showConfirmDialog(null, "Error method cetakLaporanperTanggal : " + ex,
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void cetakLapPiutangPerTgl(){
        String pth = System.getProperty("user.dir") + "/Laporan/lapPiutang.jrxml";
        try{
            Map<String, Object> parameters = new HashMap<>();
            Conn = null;
            Conn = getCnn.getConnection();
            parameters.put("parTglAwal",dtTglAwal.getDate());
            parameters.put("parTglAkhir",dtTglAkhir.getDate());
            JasperReport jrpt = JasperCompileManager.compileReport(pth);
            JasperPrint jprint = JasperFillManager.fillReport(jrpt, parameters, Conn);
            JasperViewer.viewReport(jprint, false);
        }catch (SQLException | JRException ex){
            JOptionPane.showConfirmDialog(null, "Error method cetakLaporanperTanggal : " + ex,
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void cetakLapMasukPerTgl(){
        String pth = System.getProperty("user.dir") + "/Laporan/lapMasuk.jrxml";
        try{
            Map<String, Object> parameters = new HashMap<>();
            Conn = null;
            Conn = getCnn.getConnection();
            parameters.put("parTglAwal",dtTglAwal.getDate());
            parameters.put("parTglAkhir",dtTglAkhir.getDate());
            JasperReport jrpt = JasperCompileManager.compileReport(pth);
            JasperPrint jprint = JasperFillManager.fillReport(jrpt, parameters, Conn);
            JasperViewer.viewReport(jprint, false);
        }catch (SQLException | JRException ex){
            JOptionPane.showConfirmDialog(null, "Error method cetakLaporanperTanggal : " + ex,
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void cetakLapKeluarPerTgl(){
        String pth = System.getProperty("user.dir") + "/Laporan/lapKeluar.jrxml";
        try{
            Map<String, Object> parameters = new HashMap<>();
            Conn = null;
            Conn = getCnn.getConnection();
            parameters.put("parTglAwal",dtTglAwal.getDate());
            parameters.put("parTglAkhir",dtTglAkhir.getDate());
            JasperReport jrpt = JasperCompileManager.compileReport(pth);
            JasperPrint jprint = JasperFillManager.fillReport(jrpt, parameters, Conn);
            JasperViewer.viewReport(jprint, false);
        }catch (SQLException | JRException ex){
            JOptionPane.showConfirmDialog(null, "Error method cetakLaporanperTanggal : " + ex,
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void cetakLapJurnalPerTgl(){
        String pth = System.getProperty("user.dir") + "/Laporan/lapJurnal.jrxml";
        try{
            Map<String, Object> parameters = new HashMap<>();
            Conn = null;
            Conn = getCnn.getConnection();
            parameters.put("parTglAwal",dtTglAwal.getDate());
            parameters.put("parTglAkhir",dtTglAkhir.getDate());
            JasperReport jrpt = JasperCompileManager.compileReport(pth);
            JasperPrint jprint = JasperFillManager.fillReport(jrpt, parameters, Conn);
            JasperViewer.viewReport(jprint, false);
        }catch (SQLException | JRException ex){
            JOptionPane.showConfirmDialog(null, "Error method cetakLaporanperTanggal : " + ex,
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void cetakLapArusPerTgl(){
        String pth = System.getProperty("user.dir") + "/Laporan/lapArus.jrxml";
        try{
            Map<String, Object> parameters = new HashMap<>();
            Conn = null;
            Conn = getCnn.getConnection();
            ResultSet rm = Conn.createStatement().executeQuery("select sum(nominal) as total from kas_masuk "
                    + "where tgl_nota between '"+date_f.format(dtTglAwal.getDate())+"' and "
                    + "'"+date_f.format(dtTglAkhir.getDate())+"'");
            if(rm.next()){
                Saldo = rm.getInt("total");
                System.out.println(rm.getInt("total"));
                System.out.println(Saldo);
            }
            ResultSet rk = Conn.createStatement().executeQuery("select sum(nominal) as total from kas_keluar "
                    + "where tgl_nota between '"+date_f.format(dtTglAwal.getDate())+"' and "
                    + "'"+date_f.format(dtTglAkhir.getDate())+"'");
            if(rk.next()){
                Saldo -= rk.getInt("total");
                System.out.println(rk.getInt("total"));
                System.out.println(Saldo);
            }
            parameters.put("Saldo", Saldo);
            parameters.put("parTglAwal",dtTglAwal.getDate());
            parameters.put("parTglAkhir",dtTglAkhir.getDate());
            JasperReport jrpt = JasperCompileManager.compileReport(pth);
            JasperPrint jprint = JasperFillManager.fillReport(jrpt, parameters, Conn);
            JasperViewer.viewReport(jprint, false);
        }catch (SQLException | JRException ex){
            JOptionPane.showConfirmDialog(null, "Error method cetakLaporanperTanggal : " + ex,
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnPenjualan = new javax.swing.JButton();
        btnPembelian = new javax.swing.JButton();
        btnPenggajian = new javax.swing.JButton();
        btnPiutang = new javax.swing.JButton();
        btnMasuk = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        btnJurnal = new javax.swing.JButton();
        btnArus = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        dtTglAwal = new com.toedter.calendar.JDateChooser();
        dtTglAkhir = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("CETAK LAPORAN - SILUB");

        jTabbedPane1.setBackground(new java.awt.Color(0, 0, 255));

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "Pilihan Laporan : "));
        jPanel5.setOpaque(false);

        btnPenjualan.setText("PENJUALAN");
        btnPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenjualanActionPerformed(evt);
            }
        });

        btnPembelian.setText("PEMBELIAN");
        btnPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPembelianActionPerformed(evt);
            }
        });

        btnPenggajian.setText("PENGGAJIAN");
        btnPenggajian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenggajianActionPerformed(evt);
            }
        });

        btnPiutang.setText("PIUTANG");
        btnPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPiutangActionPerformed(evt);
            }
        });

        btnMasuk.setText("KAS MASUK");
        btnMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasukActionPerformed(evt);
            }
        });

        btnKeluar.setText("KAS KELUAR");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        btnJurnal.setText("JURNAL UMUM");
        btnJurnal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJurnalActionPerformed(evt);
            }
        });

        btnArus.setText("ARUS KAS");
        btnArus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnPenjualan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPenggajian, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPiutang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPembelian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnMasuk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnJurnal, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnArus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPenjualan)
                    .addComponent(btnPembelian))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPenggajian)
                    .addComponent(btnPiutang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMasuk)
                    .addComponent(btnKeluar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnJurnal)
                    .addComponent(btnArus)))
        );

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 375, -1));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "Periode : "));
        jPanel7.setOpaque(false);

        dtTglAwal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dtTglAwalPropertyChange(evt);
            }
        });

        dtTglAkhir.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dtTglAkhirPropertyChange(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("S.D");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dtTglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dtTglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dtTglAkhir, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addComponent(dtTglAwal, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        jTabbedPane1.addTab("Transaksi", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenjualanActionPerformed
        cetakLapPenjualanPerTgl();
    }//GEN-LAST:event_btnPenjualanActionPerformed

    private void btnPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPembelianActionPerformed
        cetakLapPembelianPerTgl();
    }//GEN-LAST:event_btnPembelianActionPerformed

    private void btnPenggajianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenggajianActionPerformed
        cetakLapPenggajianPerTgl();
    }//GEN-LAST:event_btnPenggajianActionPerformed

    private void btnPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPiutangActionPerformed
        cetakLapPiutangPerTgl();
    }//GEN-LAST:event_btnPiutangActionPerformed

    private void btnJurnalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJurnalActionPerformed
        cetakLapJurnalPerTgl();
    }//GEN-LAST:event_btnJurnalActionPerformed

    private void dtTglAwalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dtTglAwalPropertyChange
        dtTglAkhir.setDate(dtTglAwal.getDate());
    }//GEN-LAST:event_dtTglAwalPropertyChange

    private void dtTglAkhirPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dtTglAkhirPropertyChange
        tanggal();
    }//GEN-LAST:event_dtTglAkhirPropertyChange

    private void btnMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasukActionPerformed
//        cetakLapReturPembelian();
        cetakLapMasukPerTgl();
    }//GEN-LAST:event_btnMasukActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
//        cetakLapReturPenjualan();
        cetakLapKeluarPerTgl();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void btnArusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArusActionPerformed
        cetakLapArusPerTgl();
    }//GEN-LAST:event_btnArusActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnArus;
    private javax.swing.JButton btnJurnal;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnMasuk;
    private javax.swing.JButton btnPembelian;
    private javax.swing.JButton btnPenggajian;
    private javax.swing.JButton btnPenjualan;
    private javax.swing.JButton btnPiutang;
    private com.toedter.calendar.JDateChooser dtTglAkhir;
    private com.toedter.calendar.JDateChooser dtTglAwal;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
