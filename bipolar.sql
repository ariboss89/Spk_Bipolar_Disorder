# Host: localhost  (Version: 5.5.8)
# Date: 2019-12-09 00:59:51
# Generator: MySQL-Front 5.3  (Build 4.81)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "detailhistory"
#

DROP TABLE IF EXISTS `detailhistory`;
CREATE TABLE `detailhistory` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `idriwayat` varchar(255) DEFAULT NULL,
  `penyakit` varchar(255) DEFAULT NULL,
  `gejala` varchar(255) DEFAULT NULL,
  `jawaban` varchar(255) DEFAULT NULL,
  `nilaicf` float(5,2) DEFAULT NULL,
  `cfold` float(5,2) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

#
# Data for table "detailhistory"
#

/*!40000 ALTER TABLE `detailhistory` DISABLE KEYS */;
INSERT INTO `detailhistory` VALUES (14,'R0001',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `detailhistory` ENABLE KEYS */;

#
# Structure for table "gejala"
#

DROP TABLE IF EXISTS `gejala`;
CREATE TABLE `gejala` (
  `Id` varchar(11) NOT NULL DEFAULT '',
  `nama` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "gejala"
#

INSERT INTO `gejala` VALUES ('G001','Merasa bahagia dan terlalu bersemangat'),('G002','Sangat sensitif dan mudah tersinggung'),('G003','Banyak makan'),('G004','Kurang Tidur'),('G005','Bersikap Gegabah'),('G006','Melakukan kegiatan berisiko'),('G007','Berbicara dengan cepat dan mengbah objek pembicaraan'),('G008','Mengalami penurunan kemampuan untuk suatu keputusan '),('G009','Melihat hal-hal aneh dan misterius '),('G010','Frustasi'),('G011','Stress berkelanjutan'),('G012','Perasaan yang tidak teratur dan tidak menentu'),('G013','Merasa sangat sedih dan kehilangan harapan pada jangka waktu yang panjang '),('G014','Kehilangan ketertarikan dalam melakukan kegiatan sehari-hari ');

#
# Structure for table "history"
#

DROP TABLE IF EXISTS `history`;
CREATE TABLE `history` (
  `idriwayat` varchar(255) NOT NULL DEFAULT '',
  `penyakit` varchar(255) DEFAULT NULL,
  `jumlah` varchar(255) DEFAULT NULL,
  `nilai` varchar(255) DEFAULT NULL,
  `kesimpulan` varchar(255) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL,
  `solusi` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idriwayat`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

#
# Data for table "history"
#

/*!40000 ALTER TABLE `history` DISABLE KEYS */;
INSERT INTO `history` VALUES ('R0001',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `history` ENABLE KEYS */;

#
# Structure for table "pengetahuan"
#

DROP TABLE IF EXISTS `pengetahuan`;
CREATE TABLE `pengetahuan` (
  `myid` int(11) NOT NULL DEFAULT '0',
  `Id` varchar(11) NOT NULL DEFAULT '',
  `idpenyakit` varchar(255) DEFAULT NULL,
  `penyakit` varchar(255) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL,
  `solusi` varchar(255) DEFAULT NULL,
  `idgejala` varchar(255) DEFAULT NULL,
  `gejala` varchar(255) DEFAULT NULL,
  `nilaicf` float(5,2) DEFAULT NULL,
  PRIMARY KEY (`myid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "pengetahuan"
#

INSERT INTO `pengetahuan` VALUES (1,'K001','P001','Manic Depressif','aa','aaa','G002','Sangat sensitif dan mudah tersinggung',0.60),(2,'K002','P001','Manic Depressif','aa','aaa','G004','Kurang tidur',0.30),(3,'K003','P001','Manic Depressif','aa','aaa','G008','Mengalami penurunan kemampuan untuk suatu keputusan',0.40),(4,'K004','P001','Manic Depressif','aa','aaa','G009','Melihat hal-hal aneh dan misterius',0.40),(5,'K005','P002','Hipomanik','bb','bbb','G002','Sangat sensitif dan mudah tersinggung',0.40),(6,'K006','P002','Hipomanik','bb','bbb','G005','Bersikap gegabah',0.30),(7,'K007','P002','Hipomanik','bb','bbb','G006','Melakukan kegiatan beresiko',0.30),(8,'K008','P002','Hipomanik','bb','bbb','G007','Berbicara dengan cepat dan mengbah objek pembicaraan',0.40),(9,'K009','P002','Hipomanik','bb','bbb','G008','Mengalami penurunan kemampuan untuk suatu keputusan',0.40),(10,'K010','P003','Depressif','CC','ccc','G002','Sangat sensitif dan mudah tersinggung',0.50),(11,'K011','P003','Depressif','CC','ccc','G004','Kurang tidur',0.40),(12,'K012','P003','Depressif','CC','ccc','G009','Melihat hal-hal aneh dan misterius',0.50),(13,'K013','P003','Depressif','CC','ccc','G011','Stress berkelanjutan',0.60),(14,'K014','P003','Depressif','CC','ccc','G014','Kehilangan ketertarikan dalam melakukan kegiatan sehari-hari',0.50),(15,'K015','P004','Campuran','dd','ddd','G001','Merasa bahagia dan terlalu bersemangat',0.30),(16,'K016','P004','Campuran','dd','ddd','G002','Sangat sensitif dan mudah tersinggung',0.30),(17,'K017','P004','Campuran','dd','ddd','G003','Banyak makan',0.30),(18,'K018','P004','Campuran','dd','ddd','G010','Frustasi',0.50),(19,'K019','P004','Campuran','dd','ddd','G013','Merasa sangat sedih dan kehilangan harapan pada jangka waktu yang panjang',0.60);

#
# Structure for table "penyakit"
#

DROP TABLE IF EXISTS `penyakit`;
CREATE TABLE `penyakit` (
  `Id` varchar(11) NOT NULL DEFAULT '',
  `nama` varchar(255) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL,
  `solusi` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "penyakit"
#

INSERT INTO `penyakit` VALUES ('P001','Manic Depressif','aa','aaa'),('P002','Hipomanik','bb','bbb'),('P003','Depressif','CC','ccc'),('P004','Campuran','dd','ddd');

#
# Structure for table "user"
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `nama` varchar(255) DEFAULT NULL,
  `alamat` varchar(255) DEFAULT NULL,
  `username` varchar(11) NOT NULL DEFAULT '',
  `password` varchar(255) DEFAULT NULL,
  `konfirmasi` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "user"
#

INSERT INTO `user` VALUES ('Admin','admin','admin','21232f297a57a5a743894a0e4a801fc3','21232f297a57a5a743894a0e4a801fc3','Pengguna'),('Ari Ramadhan','Jl. Gudang Minyak No.28','ariboss89','e10adc3949ba59abbe56e057f20f883e','e10adc3949ba59abbe56e057f20f883e','Admin');
