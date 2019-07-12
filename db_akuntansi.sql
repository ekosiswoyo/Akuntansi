-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 12 Jul 2019 pada 10.18
-- Versi server: 10.1.37-MariaDB
-- Versi PHP: 7.3.1

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
  `id_angsuran` char(20) NOT NULL,
  `id_piutang` varchar(11) DEFAULT NULL,
  `angsuran_ke` int(4) DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `angsuran`
--

INSERT INTO `angsuran` (`id_angsuran`, `id_piutang`, `angsuran_ke`, `jumlah`) VALUES
('1', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `dt_barang`
--

CREATE TABLE `dt_barang` (
  `id_barang` varchar(20) NOT NULL,
  `nm_barang` varchar(30) DEFAULT NULL,
  `spesifikasi` text,
  `harga_beli` int(11) DEFAULT NULL,
  `harga_jual` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `dt_barang`
--

INSERT INTO `dt_barang` (`id_barang`, `nm_barang`, `spesifikasi`, `harga_beli`, `harga_jual`) VALUES
('1', 'yuyt', 'sda12', 20000, 30000),
('BR2', 'asd', 'das', 10000, 20000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `dt_customer`
--

CREATE TABLE `dt_customer` (
  `id_customer` varchar(20) DEFAULT NULL,
  `nm_customer` varchar(30) DEFAULT NULL,
  `alamat` text,
  `no_telepon` varchar(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `dt_customer`
--

INSERT INTO `dt_customer` (`id_customer`, `nm_customer`, `alamat`, `no_telepon`) VALUES
('CS-002', 'asd', 'da', '123'),
('CS-003', 'as', 'da', '4'),
('CS-004', 'iut', 'wwww', '123');

-- --------------------------------------------------------

--
-- Struktur dari tabel `dt_karyawan`
--

CREATE TABLE `dt_karyawan` (
  `id_karyawan` varchar(20) NOT NULL,
  `nama` varchar(30) DEFAULT NULL,
  `alamat` text,
  `no_telepon` varchar(12) DEFAULT NULL,
  `jabatan` varchar(30) DEFAULT NULL,
  `status` varchar(14) DEFAULT NULL,
  `keterangan` text,
  `tgl_masuk` date DEFAULT NULL,
  `masa_kerja` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `dt_karyawan`
--

INSERT INTO `dt_karyawan` (`id_karyawan`, `nama`, `alamat`, `no_telepon`, `jabatan`, `status`, `keterangan`, `tgl_masuk`, `masa_kerja`) VALUES
('KRY-001', 'eko', 'ssmcv', '643', '1', '1', 'rett', '2019-06-24', '2019-06-25');

-- --------------------------------------------------------

--
-- Struktur dari tabel `dt_supplier`
--

CREATE TABLE `dt_supplier` (
  `id_supplier` varchar(20) NOT NULL,
  `nm_supplier` varchar(30) DEFAULT NULL,
  `alamat` text,
  `no_telepon` varchar(13) DEFAULT NULL,
  `no_rekening` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `dt_supplier`
--

INSERT INTO `dt_supplier` (`id_supplier`, `nm_supplier`, `alamat`, `no_telepon`, `no_rekening`) VALUES
('SUP-002', 'ds', 'dfs', '12', '54'),
('SUP-003', 'qwe', 'qweqe', '123', '432');

-- --------------------------------------------------------

--
-- Struktur dari tabel `jurnal_umum`
--

CREATE TABLE `jurnal_umum` (
  `no_transaksi` varchar(20) NOT NULL,
  `id_akun` varchar(11) DEFAULT NULL,
  `debet` int(11) DEFAULT NULL,
  `kredit` int(11) DEFAULT NULL,
  `tgl_transaksi` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `jurnal_umum`
--

INSERT INTO `jurnal_umum` (`no_transaksi`, `id_akun`, `debet`, `kredit`, `tgl_transaksi`) VALUES
('JUR-022', '3', 120000, 10000, '2019-07-09');

-- --------------------------------------------------------

--
-- Struktur dari tabel `kas_keluar`
--

CREATE TABLE `kas_keluar` (
  `no_transaksi` varchar(20) NOT NULL,
  `tgl_nota` date DEFAULT NULL,
  `id_akun` varchar(11) DEFAULT NULL,
  `keterangan` text,
  `nominal` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `kas_keluar`
--

INSERT INTO `kas_keluar` (`no_transaksi`, `tgl_nota`, `id_akun`, `keterangan`, `nominal`) VALUES
('TRANS-KK-003', '2019-07-09', '3', 'das', 10000),
('TRANS-KK-004', '2019-07-12', '3', 'sa', 12000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `kas_masuk`
--

CREATE TABLE `kas_masuk` (
  `no_transaksi` varchar(20) NOT NULL,
  `tgl_nota` date DEFAULT NULL,
  `id_akun` varchar(11) DEFAULT NULL,
  `keterangan` text,
  `nominal` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `kas_masuk`
--

INSERT INTO `kas_masuk` (`no_transaksi`, `tgl_nota`, `id_akun`, `keterangan`, `nominal`) VALUES
('TRANS-KM-013', '2019-06-25', '3', 'asd', 2000),
('TRANS-KM-014', '2019-07-08', '3', 'jdsa', 90000),
('TRANS-KM-015', '2019-07-12', 'AKUN-004', 'asd', 12000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `pembelian`
--

CREATE TABLE `pembelian` (
  `no_transaksi` varchar(20) NOT NULL,
  `id_supplier` varchar(11) DEFAULT NULL,
  `id_barang` varchar(11) DEFAULT NULL,
  `qty` int(6) DEFAULT NULL,
  `harga` int(11) DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  `tgl` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `pembelian`
--

INSERT INTO `pembelian` (`no_transaksi`, `id_supplier`, `id_barang`, `qty`, `harga`, `jumlah`, `tgl`) VALUES
('TRANS-PMB-001', 'SUP-002', '1', 3, 20000, 60000, '2019-07-08'),
('TRANS-PMB-002', 'SUP-002', '1', 2, 12000, 24000, '2019-07-12'),
('TRANS-PMB-003', 'SUP-002', '1', 2, 20000, 40000, '2019-07-13');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penggajian`
--

CREATE TABLE `penggajian` (
  `no_transaksi` varchar(20) NOT NULL,
  `id_karyawan` varchar(11) DEFAULT NULL,
  `gaji` int(11) DEFAULT NULL,
  `lemburan` int(11) DEFAULT NULL,
  `transport` int(11) DEFAULT NULL,
  `insentif` int(11) DEFAULT NULL,
  `potongan` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `tgl` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `penggajian`
--

INSERT INTO `penggajian` (`no_transaksi`, `id_karyawan`, `gaji`, `lemburan`, `transport`, `insentif`, `potongan`, `total`, `tgl`) VALUES
('TRANS-PGJ-003', '1', 120000, 23000, 12000, 89000, 12000, 232000, '2019-07-08'),
('TRANS-PGJ-004', 'KRY-001', 2, 3, 4, 1, 1, 9, '2019-07-13');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penjualan`
--

CREATE TABLE `penjualan` (
  `no_transaksi` varchar(20) NOT NULL,
  `id_customer` varchar(11) DEFAULT NULL,
  `eartag` varchar(6) DEFAULT NULL,
  `sex` enum('JANTAN','BETINA') DEFAULT NULL,
  `bb` int(11) DEFAULT NULL,
  `harga` int(11) DEFAULT NULL,
  `keterangan` text,
  `tgl` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `penjualan`
--

INSERT INTO `penjualan` (`no_transaksi`, `id_customer`, `eartag`, `sex`, `bb`, `harga`, `keterangan`, `tgl`) VALUES
('TRANS-PNJ-002', '1', '24', '', 22, 30000, 'ok', '2019-06-24'),
('TRANS-PNJ-003', 'CS-004', 'sad', 'BETINA', 12, 1200, 'gfh', '2019-07-08'),
('TRANS-PNJ-004', 'CS-002', 'd', 'JANTAN', 12, 12000, 'dfs', '2019-07-12');

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
('3', 'Hutang', 'Detail', 'a'),
('AKUN-004', 'Harta', 'Header', 'dada');

-- --------------------------------------------------------

--
-- Struktur dari tabel `piutang`
--

CREATE TABLE `piutang` (
  `id_piutang` varchar(20) NOT NULL,
  `id_karyawan` varchar(11) DEFAULT NULL,
  `jml_piutang` int(11) DEFAULT NULL,
  `potongan` int(11) DEFAULT NULL,
  `keterangan` text,
  `tgl` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `piutang`
--

INSERT INTO `piutang` (`id_piutang`, `id_karyawan`, `jml_piutang`, `potongan`, `keterangan`, `tgl`) VALUES
('1', '1', 10000, 20000, 'a', '2019-06-25'),
('PIU-002', 'KRY-001', 12, 1, 'as', '2019-07-13'),
('PIU003', '1', 3333, 1, 'sa', '2019-07-08');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `nm_user` varchar(30) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `deskripsi` text,
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
-- Indeks untuk tabel `jurnal_umum`
--
ALTER TABLE `jurnal_umum`
  ADD PRIMARY KEY (`no_transaksi`);

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
  ADD PRIMARY KEY (`no_transaksi`);

--
-- Indeks untuk tabel `penggajian`
--
ALTER TABLE `penggajian`
  ADD PRIMARY KEY (`no_transaksi`);

--
-- Indeks untuk tabel `penjualan`
--
ALTER TABLE `penjualan`
  ADD PRIMARY KEY (`no_transaksi`);

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
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
