/*
SQLyog Community
MySQL - 8.0.34 : Database - utic2025com_stilver_scavone
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

USE `utic2025com_stilver_scavone`;

/*Table structure for table `corredor` */

DROP TABLE IF EXISTS `corredor`;

CREATE TABLE `corredor` (
  `idCorredor` int NOT NULL AUTO_INCREMENT,
  `documento` varchar(15) NOT NULL,
  `razonSocial` varchar(150) NOT NULL,
  `contacto` varchar(45) NOT NULL,
  `pin` varchar(5) NOT NULL,
  `activo` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`idCorredor`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

/*Data for the table `corredor` */

LOCK TABLES `corredor` WRITE;

insert  into `corredor`(`idCorredor`,`documento`,`razonSocial`,`contacto`,`pin`,`activo`) values (1,'3413973','Juan Perez','0971147147','54245',1);

UNLOCK TABLES;

/*Table structure for table `corredor_login` */

DROP TABLE IF EXISTS `corredor_login`;

CREATE TABLE `corredor_login` (
  `idLogin` int NOT NULL AUTO_INCREMENT,
  `idCorredor` int NOT NULL,
  `fecha` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `token` text NOT NULL,
  `vencimiento` datetime NOT NULL,
  PRIMARY KEY (`idLogin`),
  KEY `idCorredor` (`idCorredor`),
  CONSTRAINT `corredor_login_ibfk_1` FOREIGN KEY (`idCorredor`) REFERENCES `corredor` (`idCorredor`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;

/*Data for the table `corredor_login` */

LOCK TABLES `corredor_login` WRITE;

insert  into `corredor_login`(`idLogin`,`idCorredor`,`fecha`,`token`,`vencimiento`) values (9,1,'2024-09-02 14:51:42','66d6093ed9e3b','0000-00-00 00:00:00'),(10,1,'2024-09-02 14:53:20','66d609a03a042','2024-09-03 06:53:20'),(11,1,'2024-09-02 21:32:24','66d667283ea6a','2024-09-03 09:32:24'),(12,1,'2024-09-09 11:41:38','66df173237de8','2024-09-09 23:41:38'),(13,1,'2024-09-09 11:41:38','66df173237de8','2024-09-09 23:41:38'),(14,1,'2024-09-09 20:13:00','66df8f0c6d88b','2024-09-10 08:13:00'),(15,1,'2024-09-10 14:25:46','66e08f2aeee9d','2024-09-11 02:25:46'),(16,1,'2024-09-10 17:46:37','66e0be3dc428c','2024-09-11 05:46:37'),(17,1,'2024-09-11 10:28:22','66e1a906ad887','2024-09-11 22:28:22'),(18,1,'2024-09-11 16:09:52','66e1f910ee299','2024-09-12 04:09:52'),(19,1,'2024-09-12 18:45:18','66e36efea2447','2024-09-13 06:45:18'),(20,1,'2024-09-12 19:44:13','66e37ccd9a3b1','2024-09-13 07:44:13');

UNLOCK TABLES;

/*Table structure for table `sorteo` */

DROP TABLE IF EXISTS `sorteo`;

CREATE TABLE `sorteo` (
  `idSorteo` int NOT NULL AUTO_INCREMENT,
  `nombreSorteo` varchar(45) NOT NULL,
  `idTipoSorteo` int NOT NULL,
  `fecha` date NOT NULL,
  `finalizado` tinyint NOT NULL DEFAULT '0',
  `idUsuario` int NOT NULL,
  PRIMARY KEY (`idSorteo`),
  KEY `fk_sorteo_1_idx` (`idTipoSorteo`),
  KEY `fk_sorteo_2_idx` (`idUsuario`),
  CONSTRAINT `fk_sorteo_1` FOREIGN KEY (`idTipoSorteo`) REFERENCES `tipo_sorteo` (`idTipoSorteo`),
  CONSTRAINT `fk_sorteo_2` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

/*Data for the table `sorteo` */

LOCK TABLES `sorteo` WRITE;

insert  into `sorteo`(`idSorteo`,`nombreSorteo`,`idTipoSorteo`,`fecha`,`finalizado`,`idUsuario`) values (1,'Vespertino',1,'2024-09-12',1,1),(2,'TEST',4,'2024-09-12',1,1),(3,'SORTEAMOS UNA VACA',4,'2024-09-12',1,1);

UNLOCK TABLES;

/*Table structure for table `sorteo_resultado` */

DROP TABLE IF EXISTS `sorteo_resultado`;

CREATE TABLE `sorteo_resultado` (
  `idResultado` int NOT NULL AUTO_INCREMENT,
  `idSorteo` int NOT NULL,
  `posicion` int NOT NULL,
  `numero` int NOT NULL,
  PRIMARY KEY (`idResultado`),
  KEY `fk_sorteo_resultado_1_idx` (`idSorteo`),
  CONSTRAINT `fk_sorteo_resultado_1` FOREIGN KEY (`idSorteo`) REFERENCES `sorteo` (`idSorteo`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb3;

/*Data for the table `sorteo_resultado` */

LOCK TABLES `sorteo_resultado` WRITE;

insert  into `sorteo_resultado`(`idResultado`,`idSorteo`,`posicion`,`numero`) values (3,1,1,333),(4,1,2,438),(5,1,3,742),(6,1,4,233),(7,1,5,228),(8,1,6,406),(9,1,7,322),(10,1,8,428),(11,1,9,898),(12,1,10,969),(13,1,11,447),(14,1,12,818),(15,1,13,853),(16,1,14,966),(17,2,1,599),(18,2,2,675),(19,2,3,603),(20,2,4,502),(21,2,5,13),(22,2,6,825),(23,2,7,883),(24,2,8,490),(25,2,9,815),(26,2,10,750),(27,2,11,864),(28,2,12,414),(29,2,13,193),(30,2,14,108),(31,3,1,69),(32,3,2,617),(33,3,3,223),(34,3,4,994),(35,3,5,650),(36,3,6,284),(37,3,7,851),(38,3,8,84),(39,3,9,991),(40,3,10,656),(41,3,11,478),(42,3,12,826),(43,3,13,588),(44,3,14,334);

UNLOCK TABLES;

/*Table structure for table `ticket` */

DROP TABLE IF EXISTS `ticket`;

CREATE TABLE `ticket` (
  `idTicket` int NOT NULL AUTO_INCREMENT,
  `idSorteo` int NOT NULL,
  `idCorredor` int NOT NULL,
  `fecha` datetime NOT NULL,
  `ubicacion` varchar(45) NOT NULL,
  `anulado` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`idTicket`),
  KEY `fk_ticket_1_idx` (`idSorteo`),
  KEY `fk_ticket_2_idx` (`idCorredor`),
  CONSTRAINT `fk_ticket_1` FOREIGN KEY (`idSorteo`) REFERENCES `sorteo` (`idSorteo`),
  CONSTRAINT `fk_ticket_2` FOREIGN KEY (`idCorredor`) REFERENCES `corredor` (`idCorredor`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb3;

/*Data for the table `ticket` */

LOCK TABLES `ticket` WRITE;

insert  into `ticket`(`idTicket`,`idSorteo`,`idCorredor`,`fecha`,`ubicacion`,`anulado`) values (20,1,1,'2024-09-02 15:41:38','-25.1235454;-55.1232135',0),(21,1,1,'2024-09-10 16:16:35','-55.123241;-22.45657',0),(22,1,1,'2024-09-10 16:16:35','-55.123241;-22.45657',0),(23,1,1,'2024-09-10 16:23:01','-55.123241;-22.45657',0),(24,1,1,'2024-09-10 16:23:01','-55.123241;-22.45657',0),(25,1,1,'2024-09-10 16:25:18','-55.123241;-22.45657',0),(26,1,1,'2024-09-10 16:25:18','-55.123241;-22.45657',0),(27,1,1,'2024-09-10 16:26:31','-55.123241;-22.45657',0),(28,1,1,'2024-09-10 16:26:31','-55.123241;-22.45657',0),(29,1,1,'2024-09-10 16:26:51','-55.123241;-22.45657',0),(30,1,1,'2024-09-10 16:26:51','-55.123241;-22.45657',0),(31,1,1,'2024-09-10 16:27:21','-55.123241;-22.45657',0),(32,1,1,'2024-09-10 16:27:21','-55.123241;-22.45657',0),(33,1,1,'2024-09-10 16:28:14','-55.123241;-22.45657',0),(34,1,1,'2024-09-10 16:28:15','-55.123241;-22.45657',0),(35,1,1,'2024-09-10 16:28:17','-55.123241;-22.45657',0),(36,1,1,'2024-09-10 16:28:17','-55.123241;-22.45657',0),(37,1,1,'2024-09-10 16:29:06','-55.123241;-22.45657',0),(38,1,1,'2024-09-10 16:29:06','-55.123241;-22.45657',0),(39,1,1,'2024-09-10 16:41:54','-55.123241;-22.45657',0),(40,1,1,'2024-09-10 16:41:54','-55.123241;-22.45657',0),(41,1,1,'2024-09-10 16:46:03','-55.123241;-22.45657',0),(42,1,1,'2024-09-10 16:46:03','-55.123241;-22.45657',0),(43,1,1,'2024-09-10 17:44:44','-55.123241;-22.45657',0),(44,1,1,'2024-09-10 17:48:00','-55.123241;-22.45657',0),(45,1,1,'2024-09-10 18:03:56','-55.123241;-22.45657',0),(46,1,1,'2024-09-10 18:04:42','-55.123241;-22.45657',0),(47,1,1,'2024-09-10 18:05:40','-55.123241;-22.45657',1),(48,1,1,'2024-09-11 16:15:46','-25.471764999999998,-56.017253333333336',0),(49,1,1,'2024-09-11 16:19:19','-25.471764999999998,-56.017253333333336',0),(50,1,1,'2024-09-11 16:27:15','0.0,0.0',0),(51,1,1,'2024-09-11 16:27:40','0.0,0.0',0),(52,1,1,'2024-09-11 16:29:56','-25.482201666666665,-56.012240000000006',0),(53,1,1,'2024-09-11 16:33:07','0.0,0.0',0),(54,1,1,'2024-09-11 16:33:32','-25.48228166666667,-56.012251666666664',0),(55,1,1,'2024-09-11 18:02:22','-25.471476666666664,-56.01724333333333',0),(56,1,1,'2024-09-11 18:04:15','-25.471566666666664,-56.017266666666664',0),(57,1,1,'2024-09-11 18:05:57','-25.471703333333334,-56.017039999999994',0),(58,1,1,'2024-09-11 18:06:18','-25.471623333333334,-56.017095000000005',0),(59,1,1,'2024-09-11 18:09:54','-25.471490000000003,-56.01723666666667',0),(60,1,1,'2024-09-11 18:10:26','-25.471521666666664,-56.017395',0),(61,1,1,'2024-09-11 18:10:53','-25.471551666666663,-56.01734499999999',0),(62,1,1,'2024-09-11 18:13:51','-25.471535000000003,-56.01734499999999',0),(63,1,1,'2024-09-11 18:15:07','-25.47156,-56.017316666666666',0),(64,1,1,'2024-09-11 18:18:38','-25.471563333333332,-56.017083333333325',0),(65,1,1,'2024-09-11 18:20:39','-25.471569999999996,-56.017263333333325',0),(66,1,1,'2024-09-12 18:47:36','-25.482046666666665,-56.012316666666656',0),(67,1,1,'2024-09-12 19:32:03','0.0,0.0',0),(68,1,1,'2024-09-12 19:40:09','0.0,0.0',0),(69,1,1,'2024-09-12 19:40:31','-25.482233333333337,-56.01209666666667',1),(70,3,1,'2024-09-12 20:57:33','-25.482264999999998,-56.01218666666667',0),(71,3,1,'2024-09-12 20:58:10','-25.48226833333333,-56.01222833333333',0),(72,3,1,'2024-09-12 20:58:44','-25.48219,-56.012298333333334',0),(73,3,1,'2024-09-12 20:59:36','-25.482081666666666,-56.01251166666666',0);

UNLOCK TABLES;

/*Table structure for table `ticket_det` */

DROP TABLE IF EXISTS `ticket_det`;

CREATE TABLE `ticket_det` (
  `idTicketDet` int NOT NULL AUTO_INCREMENT,
  `idTicket` int NOT NULL,
  `posicion` int NOT NULL,
  `numero` int NOT NULL,
  `monto` int NOT NULL,
  `tipo` tinyint NOT NULL,
  PRIMARY KEY (`idTicketDet`),
  KEY `fk_ticket_det_1_idx` (`idTicket`),
  CONSTRAINT `fk_ticket_det_1` FOREIGN KEY (`idTicket`) REFERENCES `ticket` (`idTicket`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb3;

/*Data for the table `ticket_det` */

LOCK TABLES `ticket_det` WRITE;

insert  into `ticket_det`(`idTicketDet`,`idTicket`,`posicion`,`numero`,`monto`,`tipo`) values (2,20,1,333,1000,3),(3,20,1,33,1000,2),(4,20,3,3,1000,1),(5,43,1,123,15000,3),(6,43,1,23,5000,2),(7,44,1,352,5000,1),(8,45,1,325,22500,1),(9,46,1,325,22500,1),(10,47,1,255,2000,1),(11,48,1,321,5000,1),(12,49,1,258,22555,1),(13,50,1,555,55200,1),(14,51,1,555,55200,1),(15,52,1,999,55000,1),(16,53,1,322,2200,1),(17,54,1,322,2200,1),(18,55,3,123,25000,3),(19,55,1,233,2000,3),(20,56,3,123,25000,3),(21,56,1,233,2000,3),(22,57,3,123,25000,3),(23,57,1,233,2000,3),(24,58,3,123,25000,3),(25,58,1,233,2000,3),(26,59,3,123,25000,3),(27,59,1,233,2000,3),(28,60,3,123,25000,3),(29,60,1,233,2000,3),(30,61,3,123,25000,3),(31,61,1,233,2000,3),(32,62,3,123,25000,3),(33,62,1,233,2000,3),(34,63,3,123,25000,3),(35,63,1,233,2000,3),(36,64,5,250,2000,3),(37,64,1,325,2500,1),(38,65,1,225,3000,3),(39,65,1,322,2000,3),(40,66,1,123,3500,3),(41,67,1,333,2500,3),(42,68,9,321,22500,3),(43,69,1,12,1000,2),(44,70,1,23,5000,2),(45,70,1,132,5000,3),(46,71,1,69,5000,2),(47,71,1,108,5000,3),(48,72,1,666,5000,3),(49,72,1,322,5000,3),(50,73,1,777,5000,3),(51,73,1,256,5000,3);

UNLOCK TABLES;

/*Table structure for table `tipo_sorteo` */

DROP TABLE IF EXISTS `tipo_sorteo`;

CREATE TABLE `tipo_sorteo` (
  `idTipoSorteo` int NOT NULL AUTO_INCREMENT,
  `nombreTipoSorteo` varchar(45) NOT NULL,
  PRIMARY KEY (`idTipoSorteo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

/*Data for the table `tipo_sorteo` */

LOCK TABLES `tipo_sorteo` WRITE;

insert  into `tipo_sorteo`(`idTipoSorteo`,`nombreTipoSorteo`) values (1,'El tempranero'),(2,'El matutino'),(3,'El vespertino'),(4,'El nocturno');

UNLOCK TABLES;

/*Table structure for table `usuario` */

DROP TABLE IF EXISTS `usuario`;

CREATE TABLE `usuario` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `usuario` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

/*Data for the table `usuario` */

LOCK TABLES `usuario` WRITE;

insert  into `usuario`(`idUsuario`,`usuario`,`password`) values (1,'admin','123456');

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
