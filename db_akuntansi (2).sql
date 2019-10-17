-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 10 Sep 2019 pada 04.55
-- Versi server: 10.3.15-MariaDB
-- Versi PHP: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_akuntansi`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `angsuran`
--

CREATE TABLE `angsuran` (
  `id_angsuran` int(11) NOT NULL,
  `id_piutang` varchar(11) DEFAULT NULL,
  `angsuran_ke` int(4) DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  `tgl` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `angsuran`
--

INSERT INTO `angsuran` (`id_angsuran`, `id_piutang`, `angsuran_ke`, `jumlah`, `tgl`) VALUES
(8, 'PIU-001', 1, 500000, '0000-00-00'),
(9, 'PIU-001', 2, 500000, '0000-00-00'),
(10, 'PIU-001', 3, 500000, '2019-08-02'),
(11, 'PIU-002', 4, 10000000, '2019-08-02'),
(12, 'PIU-001', 4, 500000, '2019-08-22');

-- --------------------------------------------------------

--
-- Struktur dari tabel `dt_barang`
--

CREATE TABLE `dt_barang` (
  `id_barang` varchar(20) NOT NULL,
  `nm_barang` varchar(30) DEFAULT NULL,
  `spesifikasi` text DEFAULT NULL,
  `kategori` enum('Pakan','Obat','Peralatan') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `dt_barang`
--

INSERT INTO `dt_barang` (`id_barang`, `nm_barang`, `spesifikasi`, `kategori`) VALUES
('BRG--001', 'Bungkil Sawit', 'pakan sapi / ton', NULL),
('BRG--002', 'Bungkil Kopra', 'pakan sapi /Kg', NULL),
('BRG--003', 'Tandon Air', '5000 litter', NULL),
('BRG--004', 'Pompa air', ' unit', NULL),
('BRG--005', 'Pollard', 'pakan sapi / ton', NULL),
('BRG--006', 'Sanbe', 'Obat ternak/100ml', NULL),
('BRG--007', 'Phenylinject', 'obat dan vitamin /100ml', NULL),
('BRG--008', 'Timbangan Lantai', 'BSFX-2000', NULL),
('BRG--009', 'Bibit Rumput', 'pakan /100rb', NULL),
('BRG-010', 'Bungkil Kedelai Najich', 'pakan /1000kg ', NULL),
('BRG-011', 'Multipremix', 'pakan', NULL),
('BRG-012', 'Bungkil Kopra Hitam', 'pakan/1000kg', NULL),
('BRG-013', 'Obat Cacing', 'cair/ botol', NULL),
('BRG-014', 'bungkil sawit', 'ton', 'Pakan');

-- --------------------------------------------------------

--
-- Struktur dari tabel `dt_customer`
--

CREATE TABLE `dt_customer` (
  `id_customer` varchar(20) DEFAULT NULL,
  `nm_customer` varchar(30) DEFAULT NULL,
  `alamat` text DEFAULT NULL,
  `no_telepon` varchar(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `dt_customer`
--

INSERT INTO `dt_customer` (`id_customer`, `nm_customer`, `alamat`, `no_telepon`) VALUES
('CUS-001', 'Taryono', 'Pemalang', '089999344545'),
('CUS-002', 'Budi', 'Batang', '082223445563'),
('CUS-003', 'Rudi', 'Pekalongan', '084555777333'),
('CUS-004', 'Bambang', 'Semarang', '086524516721'),
('CUS-005', 'Widodo', 'Cirebon', '087776554330'),
('CUS-006', 'Barokah', 'Kajen', '085444333777'),
('CUS-007', 'fsd', 'fs', '234');

-- --------------------------------------------------------

--
-- Struktur dari tabel `dt_karyawan`
--

CREATE TABLE `dt_karyawan` (
  `id_karyawan` varchar(20) NOT NULL,
  `nama` varchar(30) DEFAULT NULL,
  `alamat` text DEFAULT NULL,
  `no_telepon` varchar(12) DEFAULT NULL,
  `jabatan` varchar(30) DEFAULT NULL,
  `status` varchar(14) DEFAULT NULL,
  `keterangan` text DEFAULT NULL,
  `tgl_masuk` date DEFAULT NULL,
  `masa_kerja` int(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `dt_karyawan`
--

INSERT INTO `dt_karyawan` (`id_karyawan`, `nama`, `alamat`, `no_telepon`, `jabatan`, `status`, `keterangan`, `tgl_masuk`, `masa_kerja`) VALUES
('KRY-001', 'Lieyo Wahyudi', 'Kandeman', '-', 'Staff', 'Aktif', 'Mnager Operasional Farm', '2019-08-05', 20190805),
('KRY-002', 'Putu Devandra', 'Kandeman', '-', 'Staff', 'Aktif', 'Kepala divisi Fattening', '2019-08-05', 20190805),
('KRY-003', 'Defriyaman', 'Kandeman', '-', 'Staff', 'Aktif', 'Kepala Divisi Breding', '2019-08-05', 20190805),
('KRY-004', 'Yuli Tri', 'Batang', '-', 'Operator', 'Aktif', 'Operator Hijauan Pakan Ternak', '2019-08-05', 20190805),
('KRY-005', 'Andika', 'Batang', '-', 'Staff', 'Aktif', 'Kepala Divisi Hijauan Pakan', '2016-12-08', 20190805),
('KRY-006', 'Khavida', 'Batang', '-', 'Staff', 'Aktif', 'Staff Administrasi', '2019-08-05', 20190805),
('KRY-007', 'Aulia', 'Batang', '-', 'Staff', 'Aktif', 'Staff Administrasi', '2019-08-05', 20190805),
('KRY-008', 'Dicky', 'Batang', '-', 'Operator', 'Aktif', 'Pengawas Lahan Bandar', '2019-08-05', 20190805),
('KRY-009', 'Irfan', 'Batang', '-', 'Operator', 'Aktif', 'Pengawas Lahan Tirto', '2019-08-05', 20190805),
('KRY-010', 'Adi', 'Batang', '-', 'Operator', 'Aktif', 'Pengawas Kandang Bunting Muda', '2019-08-05', 20190805),
('KRY-011', 'Edy Kusuma', 'Batang', '-', 'Operator', 'Aktif', 'Pengawas Kandang Lepas Sapih Penggemukan', '2019-08-05', 20190805),
('KRY-012', 'Kasriyah', 'Tulis', '-', 'Lahan', 'Aktif', 'Crew Babat', '2019-08-05', 20190805),
('KRY-013', 'Anung', 'Kandeman', '-', 'Kandang', 'Aktif', 'Chopper', '2019-08-05', 20190805);

-- --------------------------------------------------------

--
-- Struktur dari tabel `dt_supplier`
--

CREATE TABLE `dt_supplier` (
  `id_supplier` varchar(20) NOT NULL,
  `nm_supplier` varchar(30) DEFAULT NULL,
  `alamat` text DEFAULT NULL,
  `no_telepon` varchar(13) DEFAULT NULL,
  `no_rekening` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `dt_supplier`
--

INSERT INTO `dt_supplier` (`id_supplier`, `nm_supplier`, `alamat`, `no_telepon`, `no_rekening`) VALUES
('SUP-001', 'CPB', 'Jakarta', '08234372428', '8456472384829'),
('SUP-002', 'SANBE', 'Jakarta', '08346352598', '37845924298'),
('SUP-003', 'TMC', 'Purwokerto', '088845678844', '098745277'),
('SUP-004', 'MSM', 'Jawa Timur', '084562823722', '09876655325'),
('SUP-005', 'NAJICH', 'Jambi', '083562782911', '4567567674543'),
('SUP-006', 'SANBE', 'Jakarta', '082344555678', '76453244'),
('SUP-007', 'eq', 'da', '1', '2'),
('SUP-008', 'CPB', 'dasd', '88', '57'),
('SUP-009', 'CPB', 'hjh', '8', '09'),
('SUP-010', 'MSM', 'sa', '12', '3'),
('SUP-011', 'NAJICH', 'dsa', '12', '34');

-- --------------------------------------------------------

--
-- Struktur dari tabel `jurnal_umum`
--

CREATE TABLE `jurnal_umum` (
  `no_transaksi` varchar(20) NOT NULL,
  `id_akun` varchar(11) DEFAULT NULL,
  `debet` double DEFAULT NULL,
  `kredit` double DEFAULT NULL,
  `tgl_transaksi` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `jurnal_umum`
--

INSERT INTO `jurnal_umum` (`no_transaksi`, `id_akun`, `debet`, `kredit`, `tgl_transaksi`) VALUES
('PIU-001', '1-1011', 2000000, 0, '2019-08-05'),
('PIU-001', '1-1001', 0, 2000000, '2019-08-05'),
('PIU-002', '1-1011', 3000000, 0, '2019-08-05'),
('PIU-002', '1-1001', 0, 3000000, '2019-08-05'),
('TRANS-KM-001', '1-1001', 500000000, 0, '2019-08-05'),
('TRANS-KM-001', '3-3001', 0, 500000000, '2019-08-05'),
('TRANS-KM-002', '1-1001', 200000, 0, '2019-08-05'),
('TRANS-KM-002', '5-0005', 0, 200000, '2019-08-05'),
('TRANS-PNJ-001', '1-1001', 18000000, 0, '2019-08-05'),
('TRANS-PNJ-001', '4-1001', 0, 18000000, '2019-08-05'),
('TRANS-PNJ-002', '1-1001', 5700000, 0, '2019-08-05'),
('TRANS-PNJ-002', '4-1001', 0, 5700000, '2019-08-05'),
('TRANS-PMB-001', '5-1006', 30475000, 0, '2019-08-01'),
('TRANS-PMB-001', '1-1001', 0, 30475000, '2019-08-01'),
('TRANS-PMB-002', '5-1006', 51300000, 0, '2019-08-01'),
('TRANS-PMB-002', '1-1001', 0, 51300000, '2019-08-01'),
('TRANS-PMB-003', '5-1006', 80000, 0, '2019-08-03'),
('TRANS-PMB-003', '1-1001', 0, 80000, '2019-08-03'),
('TRANS-PNJ-003', '1-1001', 18000000, 0, '2019-08-05'),
('TRANS-PNJ-003', '4-1001', 0, 18000000, '2019-08-05'),
('TRANS-KK-004', '5-0003', 50000000, 0, '2019-08-05'),
('TRANS-KK-004', '1-1001', 0, 50000000, '2019-08-05'),
('TRANS-PNJ-004', '1-1001', 12000000, 0, '2019-08-06'),
('TRANS-PNJ-004', '4-1001', 0, 12000000, '2019-08-06'),
('TRANS-PGJ-001', '5-0003', 4, 0, '2019-08-10'),
('TRANS-PGJ-001', '1-1001', 0, 4, '2019-08-10'),
('TRANS-PGJ-002', '5-0003', 1060000, 0, '2019-08-12'),
('TRANS-PGJ-002', '1-1001', 0, 1060000, '2019-08-12'),
('TRANS-PGJ-003', '5-0003', 7000, 0, '2019-08-12'),
('TRANS-PGJ-003', '1-1001', 0, 7000, '2019-08-12'),
('TRANS-PNJ-005', '1-1001', 300000000, 0, '2019-08-14'),
('TRANS-PNJ-005', '4-1001', 0, 300000000, '2019-08-14'),
('201908014', '5-1006', 900000, 0, '2019-08-01'),
('201908014', '1-1001', 0, 900000, '2019-08-01'),
('TRANS-PNJ-006', '1-1001', 18000000, 0, '2019-08-15'),
('TRANS-PNJ-006', '4-1001', 0, 18000000, '2019-08-15'),
('TRANS-PNJ-007', '1-1001', 30000000, 0, '2019-08-15'),
('TRANS-PNJ-007', '4-1001', 0, 30000000, '2019-08-15'),
('TRANS-PNJ-008', '1-1001', 20000000, 0, '2019-08-15'),
('TRANS-PNJ-008', '4-1001', 0, 20000000, '2019-08-15'),
('TRANS-PNJ-009', '1-1001', 20000000, 0, '2019-08-16'),
('TRANS-PNJ-009', '4-1001', 0, 20000000, '2019-08-16'),
('13082019/43', '5-1006', 600000, 0, '2019-08-01'),
('13082019/43', '1-1001', 0, 600000, '2019-08-01'),
('13082019/43', '5-1006', 800000, 0, '2019-08-01'),
('13082019/43', '1-1001', 0, 800000, '2019-08-01'),
('13082019', '5-1006', 800000, 0, '2019-08-01'),
('13082019', '1-1001', 0, 800000, '2019-08-01'),
('12', '1-1001', 500000, 0, '2019-08-22'),
('12', '1-1011', 0, 500000, '2019-08-22');

-- --------------------------------------------------------

--
-- Struktur dari tabel `kas_keluar`
--

CREATE TABLE `kas_keluar` (
  `no_transaksi` varchar(20) NOT NULL,
  `tgl_nota` date DEFAULT NULL,
  `id_akun` varchar(11) DEFAULT NULL,
  `keterangan` text DEFAULT NULL,
  `nominal` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `kas_keluar`
--

INSERT INTO `kas_keluar` (`no_transaksi`, `tgl_nota`, `id_akun`, `keterangan`, `nominal`) VALUES
('13082019', '2019-08-01', '5-1006', '-', 800000),
('13082019/43', '2019-08-01', '5-1006', '-', 600000),
('201908014', '2019-08-01', '5-1006', 'pembelian', 900000),
('PIU-001', '2019-08-05', '1-1011', 'Piutang Karyawan', 2000000),
('PIU-002', '2019-08-05', '1-1011', 'Piutang Karyawan Kandang', 3000000),
('TRANS-KK-004', '2019-08-05', '5-0003', 'biaya gaji', 50000000),
('TRANS-PGJ-001', '2019-08-10', '5-1003', '-', 4),
('TRANS-PGJ-002', '2019-08-12', '5-1003', '-', 1060000),
('TRANS-PGJ-003', '2019-08-12', '5-1003', '-', 7000),
('TRANS-PMB-001', '2019-08-01', '5-1006', 'Pembelian Tunai', 30475000),
('TRANS-PMB-002', '2019-08-01', '5-1006', 'Pembelian Tunai', 51300000),
('TRANS-PMB-003', '2019-08-03', '5-1006', 'invoice pb001', 80000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `kas_masuk`
--

CREATE TABLE `kas_masuk` (
  `no_transaksi` varchar(20) NOT NULL,
  `tgl_nota` date DEFAULT NULL,
  `id_akun` varchar(11) DEFAULT NULL,
  `keterangan` text DEFAULT NULL,
  `nominal` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `kas_masuk`
--

INSERT INTO `kas_masuk` (`no_transaksi`, `tgl_nota`, `id_akun`, `keterangan`, `nominal`) VALUES
('12', '2019-08-22', '1-1011', 'null', 500000),
('TRANS-KM-001', '2019-08-05', '3-3001', 'Saldo Awal', 500000000),
('TRANS-KM-002', '2019-08-05', '5-0005', 'kontrakan karyawan', 200000),
('TRANS-PNJ-001', '2019-08-05', '4-1001', 'Penjualan Sapi Fattening', 18000000),
('TRANS-PNJ-002', '2019-08-05', '4-1001', 'Penjualan Sapi Afkir pedet', 5700000),
('TRANS-PNJ-003', '2019-08-05', '4-1001', '-', 18000000),
('TRANS-PNJ-004', '2019-08-06', '4-1001', '-', 12000000),
('TRANS-PNJ-005', '2019-08-14', '4-1001', '-', 300000000),
('TRANS-PNJ-006', '2019-08-15', '4-1001', '-', 18000000),
('TRANS-PNJ-007', '2019-08-15', '4-1001', 'i', 30000000),
('TRANS-PNJ-008', '2019-08-15', '4-1001', 's', 20000000),
('TRANS-PNJ-009', '2019-08-16', '4-1001', 's', 20000000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `pembelian`
--

CREATE TABLE `pembelian` (
  `id_pembelian` int(11) NOT NULL,
  `no_trans` varchar(20) DEFAULT NULL,
  `id_barang` varchar(20) DEFAULT NULL,
  `qty` int(6) DEFAULT NULL,
  `harga` double DEFAULT NULL,
  `jumlah` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `pembelian`
--

INSERT INTO `pembelian` (`id_pembelian`, `no_trans`, `id_barang`, `qty`, `harga`, `jumlah`) VALUES
(1, 'TRANS-PMB-001', 'BRG--001', 23, 1325000, 30475000),
(2, 'TRANS-PMB-002', 'BRG--002', 19, 2700000, 51300000),
(3, 'TRANS-PMB-003', 'BRG-013', 2, 40000, 80000),
(4, '201908014', 'BRG--001', 3, 300000, 900000),
(5, '13082019/43', 'BRG--001', 2, 300000, 600000),
(6, '13082019/43', 'BRG--002', 4, 200000, 800000),
(7, '13082019', 'BRG--002', 4, 200000, 800000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `pembelian_header`
--

CREATE TABLE `pembelian_header` (
  `no_trans` varchar(20) DEFAULT NULL,
  `id_supplier` varchar(20) DEFAULT NULL,
  `tgl` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `pembelian_header`
--

INSERT INTO `pembelian_header` (`no_trans`, `id_supplier`, `tgl`) VALUES
('TRANS-PMB-001', 'SUP-001', '2019-08-01'),
('TRANS-PMB-002', 'SUP-001', '2019-08-01'),
('TRANS-PMB-003', 'SUP-002', '2019-08-03'),
('201908014', 'SUP-002', '2019-08-01'),
('13082019/43', 'SUP-001', '2019-08-01'),
('13082019/43', 'SUP-004', '2019-08-01'),
('13082019', 'SUP-004', '2019-08-01');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penggajian`
--

CREATE TABLE `penggajian` (
  `no_transaksi` varchar(20) NOT NULL,
  `id_karyawan` varchar(20) DEFAULT NULL,
  `gaji` double DEFAULT NULL,
  `lemburan` double DEFAULT NULL,
  `transport` double DEFAULT NULL,
  `insentif` double DEFAULT NULL,
  `potongan` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `tgl` date DEFAULT NULL,
  `ket` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `penggajian`
--

INSERT INTO `penggajian` (`no_transaksi`, `id_karyawan`, `gaji`, `lemburan`, `transport`, `insentif`, `potongan`, `total`, `tgl`, `ket`) VALUES
('TRANS-PGJ-001', 'KRY-001', 1, 1, 1, 1, 0, 4, '2019-08-10', '-'),
('TRANS-PGJ-002', 'KRY-005', 1000000, 10000, 20000, 30000, 0, 1060000, '2019-08-12', '-'),
('TRANS-PGJ-003', 'KRY-007', 1000, 2000, 2000, 2000, 0, 7000, '2019-08-12', '-');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penjualan`
--

CREATE TABLE `penjualan` (
  `id_penjualan` int(11) NOT NULL,
  `no_trans` varchar(20) NOT NULL,
  `eartag` varchar(6) DEFAULT NULL,
  `sex` enum('Jantan','Betina') DEFAULT NULL,
  `bb` int(11) DEFAULT NULL,
  `harga` double DEFAULT NULL,
  `keterangan` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `penjualan`
--

INSERT INTO `penjualan` (`id_penjualan`, `no_trans`, `eartag`, `sex`, `bb`, `harga`, `keterangan`) VALUES
(1, 'TRANS-PNJ-001', '101', 'Jantan', 300, 18000000, 'Penjualan Sapi Fattening'),
(2, 'TRANS-PNJ-002', '113', 'Betina', 100, 5700000, 'Penjualan Sapi Afkir pedet'),
(3, 'TRANS-PNJ-003', 'K002', 'Betina', 300, 18000000, '-'),
(4, 'TRANS-PNJ-004', '-', 'Betina', 300, 12000000, '-'),
(5, 'TRANS-PNJ-005', 'k21', 'Betina', 300, 300000000, '-'),
(6, 'TRANS-PNJ-006', 'k23', 'Betina', 350, 18000000, '-'),
(7, 'TRANS-PNJ-007', '4', 'Jantan', 200, 30000000, 'i'),
(8, 'TRANS-PNJ-008', '8', 'Jantan', 250, 20000000, 's'),
(9, 'TRANS-PNJ-009', 'm12', 'Betina', 300, 20000000, 's');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penjualan_header`
--

CREATE TABLE `penjualan_header` (
  `no_trans` varchar(20) DEFAULT NULL,
  `nm_customer` varchar(20) DEFAULT NULL,
  `tgl` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `penjualan_header`
--

INSERT INTO `penjualan_header` (`no_trans`, `nm_customer`, `tgl`) VALUES
('TRANS-PNJ-001', 'CUS-002', '2019-08-05'),
('TRANS-PNJ-002', 'CUS-005', '2019-08-05'),
('TRANS-PNJ-003', 'null', '2019-08-05'),
('TRANS-PNJ-004', 'CUS-002', '2019-08-06'),
('TRANS-PNJ-005', 'budi', '2019-08-14'),
('TRANS-PNJ-006', 'milka', '2019-08-15'),
('TRANS-PNJ-007', 'm', '2019-08-15'),
('TRANS-PNJ-008', 'p', '2019-08-15'),
('TRANS-PNJ-009', 'd', '2019-08-16');

-- --------------------------------------------------------

--
-- Struktur dari tabel `perkiraan_akun`
--

CREATE TABLE `perkiraan_akun` (
  `id_akun` varchar(20) NOT NULL,
  `golongan` varchar(50) DEFAULT NULL,
  `tipe_perkiraan` varchar(50) DEFAULT NULL,
  `nm_perkiraan` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `perkiraan_akun`
--

INSERT INTO `perkiraan_akun` (`id_akun`, `golongan`, `tipe_perkiraan`, `nm_perkiraan`) VALUES
('1-1001', 'Harta', 'Detail', 'kas'),
('1-1002', 'Harta', 'Detail', 'Kas Kecil'),
('1-1011', 'Harta', 'Detail', 'Piutang'),
('2-0211', 'Hutang', 'Detail', 'Utang Dagang'),
('3-0002', 'Modal', 'Detail', 'Prive'),
('3-0099', 'Modal', 'Detail', 'Rugi-Laba'),
('3-3001', 'Modal', 'Detail', 'Modal'),
('4-1001', 'Pendapatan', 'Detail', 'Penjualan'),
('4-1002', 'Pendapatan', 'Detail', 'Return Penjualan'),
('4-1003', 'Pendapatan', 'Detail', 'Potongan Penjualan'),
('5-0001', 'Biaya', 'Detail', 'Biaya Sewa Kantor'),
('5-0002', 'Biaya', 'Detail', 'Biaya Asuransi'),
('5-0003', 'Biaya', 'Detail', 'Biaya Gaji'),
('5-0004', 'Biaya', 'Detail', 'Biaya Transportasi'),
('5-0005', 'Biaya', 'Detail', 'Biaya Listrik'),
('5-0006', 'Biaya', 'Detail', 'Biaya Telepon'),
('5-0007', 'Biaya', 'Detail', 'Biaya air PDAM'),
('5-0008', 'Biaya', 'Detail', 'Biaya Perlengkapan'),
('5-0009', 'Biaya', 'Detail', 'Potongan Pembelian'),
('5-1001', 'Biaya', 'Detail', 'Biaya Penyusutan Kendaraan'),
('5-1002', 'Biaya', 'Detail', 'Biaya Penyusutan Peralatan Kantor'),
('5-1003', 'Biaya', 'Detail', 'Biaya ijin pendirian perusahaan'),
('5-1006', 'Biaya', 'Detail', 'Pembelian'),
('5-1007', 'Biaya', 'Detail', 'Return Pembelian');

-- --------------------------------------------------------

--
-- Struktur dari tabel `piutang`
--

CREATE TABLE `piutang` (
  `id_piutang` varchar(20) NOT NULL,
  `id_karyawan` varchar(11) DEFAULT NULL,
  `jml_piutang` double DEFAULT NULL,
  `potongan` double DEFAULT NULL,
  `keterangan` text DEFAULT NULL,
  `tgl` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `piutang`
--

INSERT INTO `piutang` (`id_piutang`, `id_karyawan`, `jml_piutang`, `potongan`, `keterangan`, `tgl`) VALUES
('PIU-001', 'KRY-006', 2000000, 1000000, 'Piutang Karyawan', '2019-08-05'),
('PIU-002', 'KRY-004', 3000000, 500000, 'Piutang Karyawan Kandang', '2019-08-05');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `nm_user` varchar(30) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `deskripsi` text DEFAULT NULL,
  `level` enum('General Manager','Manager Keuangan','Staff Administrasi') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id_user`, `nm_user`, `username`, `password`, `deskripsi`, `level`) VALUES
(1, 'usersatu', 'usersatu', 'user', NULL, 'General Manager'),
(2, 'userdua', 'userdua', 'user', NULL, 'Manager Keuangan'),
(3, 'usertiga', 'usertiga', 'user', NULL, 'Staff Administrasi');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `angsuran`
--
ALTER TABLE `angsuran`
  ADD PRIMARY KEY (`id_angsuran`);

--
-- Indeks untuk tabel `dt_barang`
--
ALTER TABLE `dt_barang`
  ADD PRIMARY KEY (`id_barang`);

--
-- Indeks untuk tabel `dt_karyawan`
--
ALTER TABLE `dt_karyawan`
  ADD PRIMARY KEY (`id_karyawan`);

--
-- Indeks untuk tabel `dt_supplier`
--
ALTER TABLE `dt_supplier`
  ADD PRIMARY KEY (`id_supplier`);

--
-- Indeks untuk tabel `kas_keluar`
--
ALTER TABLE `kas_keluar`
  ADD PRIMARY KEY (`no_transaksi`);

--
-- Indeks untuk tabel `kas_masuk`
--
ALTER TABLE `kas_masuk`
  ADD PRIMARY KEY (`no_transaksi`);

--
-- Indeks untuk tabel `pembelian`
--
ALTER TABLE `pembelian`
  ADD PRIMARY KEY (`id_pembelian`);

--
-- Indeks untuk tabel `penggajian`
--
ALTER TABLE `penggajian`
  ADD PRIMARY KEY (`no_transaksi`);

--
-- Indeks untuk tabel `penjualan`
--
ALTER TABLE `penjualan`
  ADD PRIMARY KEY (`id_penjualan`);

--
-- Indeks untuk tabel `perkiraan_akun`
--
ALTER TABLE `perkiraan_akun`
  ADD PRIMARY KEY (`id_akun`);

--
-- Indeks untuk tabel `piutang`
--
ALTER TABLE `piutang`
  ADD PRIMARY KEY (`id_piutang`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `angsuran`
--
ALTER TABLE `angsuran`
  MODIFY `id_angsuran` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT untuk tabel `pembelian`
--
ALTER TABLE `pembelian`
  MODIFY `id_pembelian` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT untuk tabel `penjualan`
--
ALTER TABLE `penjualan`
  MODIFY `id_penjualan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
