-- MySQL dump 10.13  Distrib 9.6.0, for Win64 (x86_64)
--
-- Host: localhost    Database: jwala_navdurga
-- ------------------------------------------------------
-- Server version	9.6.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `jwala_navdurga`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `jwala_navdurga` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `jwala_navdurga`;

--
-- Table structure for table `announcements`
--

DROP TABLE IF EXISTS `announcements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `announcements` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `festival_id` bigint unsigned DEFAULT NULL,
  `festival_day_id` bigint unsigned DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `priority` int NOT NULL DEFAULT '0',
  `status` varchar(30) NOT NULL DEFAULT 'DRAFT',
  `published_at` datetime(6) DEFAULT NULL,
  `expires_at` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by` bigint unsigned DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `updated_by` bigint unsigned DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_announcements_festival` (`festival_id`),
  KEY `idx_announcements_day` (`festival_day_id`),
  KEY `idx_announcements_status` (`status`),
  KEY `idx_announcements_published` (`published_at`),
  CONSTRAINT `fk_announcements_day` FOREIGN KEY (`festival_day_id`) REFERENCES `festival_days` (`id`),
  CONSTRAINT `fk_announcements_festival` FOREIGN KEY (`festival_id`) REFERENCES `festivals` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcements`
--

LOCK TABLES `announcements` WRITE;
/*!40000 ALTER TABLE `announcements` DISABLE KEYS */;
INSERT INTO `announcements` VALUES (1,1,4,'Day 5 Cultural Program Announcement','The Day 5 cultural program will begin at 7:00 PM.',2,'PUBLISHED','2026-10-05 22:04:55.987642','2026-10-20 23:59:59.000000','2026-09-06 11:52:57.957884',2,'2026-10-05 22:04:55.987642',NULL,NULL,NULL),(2,1,8,'Mahaprasad Announcement','Mahaprasad will be served to local residents at the Mandal premises.',20,'PUBLISHED','2026-09-15 10:00:00.000000','2026-09-22 23:59:00.000000','2026-09-06 11:52:57.957884',1,'2026-09-06 11:52:57.957884',NULL,NULL,NULL),(3,1,4,'Chess Competition Registration Open','Registration is now open for the Day 4 Chess Competition. Limited seats available.',1,'PUBLISHED','2026-09-15 00:00:00.000000','2026-09-20 23:59:59.000000','2026-10-04 10:47:59.130549',NULL,'2026-10-04 10:47:59.130549',NULL,NULL,NULL),(4,1,8,'Mahaprasad Announcement','Mahaprasad will be organized on Day 8. All residents are welcome.',2,'PUBLISHED','2026-09-15 00:00:00.000000','2026-09-23 23:59:59.000000','2026-10-04 10:47:59.130549',NULL,'2026-10-04 10:47:59.130549',NULL,NULL,NULL),(5,1,9,'Visarjan Procession','The Navdurga Visarjan procession will take place on Day 9.',3,'PUBLISHED','2026-09-15 00:00:00.000000','2026-09-23 23:59:59.000000','2026-10-04 10:47:59.130549',NULL,'2026-10-04 10:47:59.130549',NULL,NULL,NULL),(6,1,4,'Day 4 Cultural Evening Announcement','The Day 4 cultural evening will begin at 7:00 PM.',2,'DRAFT',NULL,'2026-10-20 23:59:59.000000','2026-10-05 21:50:34.708345',NULL,'2026-10-05 21:50:34.708345',NULL,NULL,NULL);
/*!40000 ALTER TABLE `announcements` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit_logs`
--

DROP TABLE IF EXISTS `audit_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit_logs` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned DEFAULT NULL,
  `action` varchar(50) NOT NULL,
  `entity_type` varchar(100) NOT NULL,
  `entity_id` bigint unsigned DEFAULT NULL,
  `old_value` json DEFAULT NULL,
  `new_value` json DEFAULT NULL,
  `ip_address` varchar(45) DEFAULT NULL,
  `user_agent` varchar(500) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  KEY `idx_audit_user` (`user_id`),
  KEY `idx_audit_entity` (`entity_type`,`entity_id`),
  KEY `idx_audit_created` (`created_at`),
  CONSTRAINT `fk_audit_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_logs`
--

LOCK TABLES `audit_logs` WRITE;
/*!40000 ALTER TABLE `audit_logs` DISABLE KEYS */;
INSERT INTO `audit_logs` VALUES (1,2,'UPDATE','EVENT',6,'{\"start_time\": \"17:00:00\", \"max_participants\": 30}','{\"start_time\": \"16:00:00\", \"max_participants\": 32}','192.168.1.25','Mozilla/5.0','2026-09-06 11:52:58.083997');
/*!40000 ALTER TABLE `audit_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blog_posts`
--

DROP TABLE IF EXISTS `blog_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blog_posts` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `festival_id` bigint unsigned DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `slug` varchar(300) NOT NULL,
  `summary` text,
  `content` longtext NOT NULL,
  `cover_drive_url` varchar(1000) DEFAULT NULL,
  `status` varchar(30) NOT NULL DEFAULT 'DRAFT',
  `published_at` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by` bigint unsigned DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `updated_by` bigint unsigned DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_blog_posts_slug` (`slug`),
  KEY `idx_blog_posts_festival` (`festival_id`),
  KEY `idx_blog_posts_status` (`status`),
  CONSTRAINT `fk_blog_posts_festival` FOREIGN KEY (`festival_id`) REFERENCES `festivals` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog_posts`
--

LOCK TABLES `blog_posts` WRITE;
/*!40000 ALTER TABLE `blog_posts` DISABLE KEYS */;
INSERT INTO `blog_posts` VALUES (1,1,'Jwala Navdurga Utsav 2026','jwala-navdurga-utsav-2026','Everything you need to know about the 2026 Navdurga celebration.','Jwala Navdurga Utsav Mandal welcomes everyone to the annual Navdurga celebration.','https://drive.google.com/REPLACE_WITH_COVER_IMAGE','PUBLISHED','2026-09-01 10:00:00.000000','2026-09-06 11:52:57.969799',1,'2026-09-06 11:52:57.969799',NULL,NULL,NULL);
/*!40000 ALTER TABLE `blog_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_messages`
--

DROP TABLE IF EXISTS `contact_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contact_messages` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `mobile_number` varchar(15) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `subject` varchar(255) NOT NULL,
  `message` text NOT NULL,
  `status` varchar(30) NOT NULL DEFAULT 'NEW',
  `resolved_at` datetime(6) DEFAULT NULL,
  `resolved_by` bigint unsigned DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  KEY `idx_contact_status` (`status`),
  KEY `idx_contact_created` (`created_at`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_messages`
--

LOCK TABLES `contact_messages` WRITE;
/*!40000 ALTER TABLE `contact_messages` DISABLE KEYS */;
INSERT INTO `contact_messages` VALUES (1,'Vikas Sharma','9898989898','vikas.sharma@example.com','Chess Competition','I would like to know whether registration is still open.','NEW',NULL,NULL,'2026-09-06 11:52:58.062467','2026-09-06 11:52:58.062467'),(2,'Uday Kpale','9226543210','rahul@example.com','Volunteer Query','I would like to volunteer for the festival.','NEW',NULL,NULL,'2026-10-04 12:02:29.690955','2026-10-04 12:02:29.690955'),(3,'Uday Kpale','9226543210','rahul@example.com','Volunteer Query','I would like to volunteer for the festival.','NEW',NULL,NULL,'2026-10-04 12:02:40.474069','2026-10-04 12:02:40.474069'),(4,'Uday Kpale','9226543210','rahul@example.com','Volunteer Query','I would like to volunteer for the festival.','NEW',NULL,NULL,'2026-10-04 12:02:44.148079','2026-10-04 12:02:44.148079'),(5,'Uday Kpale','9226543210','rahul@example.com','Volunteer Query','I would like to volunteer for the festival.','NEW',NULL,NULL,'2026-10-04 12:02:45.514603','2026-10-04 12:02:45.514603'),(6,'Uday Kpale','9226543210','rahul@example.com','Volunteer Query','I would like to volunteer for the festival.','NEW',NULL,NULL,'2026-10-04 12:02:46.998382','2026-10-04 12:02:46.998382'),(7,'Uday Ravindra Kaple','9322250836','udaykaple5@gmail.com','TEST','Hello test','NEW',NULL,NULL,'2026-10-11 01:35:44.002185','2026-10-11 01:35:44.002185');
/*!40000 ALTER TABLE `contact_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donation_campaigns`
--

DROP TABLE IF EXISTS `donation_campaigns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation_campaigns` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `festival_id` bigint unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text,
  `target_amount` decimal(15,2) DEFAULT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `status` varchar(30) NOT NULL DEFAULT 'DRAFT',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by` bigint unsigned DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `updated_by` bigint unsigned DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_campaign_festival` (`festival_id`),
  KEY `idx_campaign_status` (`status`),
  CONSTRAINT `fk_campaign_festival` FOREIGN KEY (`festival_id`) REFERENCES `festivals` (`id`),
  CONSTRAINT `chk_campaign_amount` CHECK (((`target_amount` is null) or (`target_amount` >= 0))),
  CONSTRAINT `chk_campaign_dates` CHECK ((`end_date` >= `start_date`))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donation_campaigns`
--

LOCK TABLES `donation_campaigns` WRITE;
/*!40000 ALTER TABLE `donation_campaigns` DISABLE KEYS */;
INSERT INTO `donation_campaigns` VALUES (1,1,'Navdurga Utsav 2026 - Mahaprasad Fund','Contributions towards organizing Mahaprasad for local residents.',200000.00,'2026-09-01','2026-10-31','ACTIVE','2026-09-06 11:52:57.979844',3,'2026-10-11 00:29:00.062142',NULL,NULL,NULL);
/*!40000 ALTER TABLE `donation_campaigns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donation_receipts`
--

DROP TABLE IF EXISTS `donation_receipts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation_receipts` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `donation_id` bigint unsigned NOT NULL,
  `receipt_number` varchar(100) NOT NULL,
  `receipt_date` datetime(6) NOT NULL,
  `receipt_document_url` varchar(1000) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_receipt_number` (`receipt_number`),
  UNIQUE KEY `uk_receipt_donation` (`donation_id`),
  CONSTRAINT `fk_receipt_donation` FOREIGN KEY (`donation_id`) REFERENCES `donations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donation_receipts`
--

LOCK TABLES `donation_receipts` WRITE;
/*!40000 ALTER TABLE `donation_receipts` DISABLE KEYS */;
INSERT INTO `donation_receipts` VALUES (1,1,'JNU-2026-000001','2026-09-05 12:30:05.000000','https://drive.google.com/REPLACE_WITH_RECEIPT_URL_1','2026-09-06 11:52:58.030165'),(2,2,'JNU-2026-000002','2026-09-06 09:15:05.000000','https://drive.google.com/REPLACE_WITH_RECEIPT_URL_2','2026-09-06 11:52:58.030165'),(3,3,'JNU-2026-000003','2026-09-06 10:20:05.000000','https://drive.google.com/REPLACE_WITH_RECEIPT_URL_3','2026-09-06 11:52:58.030165'),(4,4,'JNU-2026-CCF414E21A00','2026-10-04 18:11:37.928219',NULL,'2026-10-04 18:11:37.928219');
/*!40000 ALTER TABLE `donation_receipts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donations`
--

DROP TABLE IF EXISTS `donations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donations` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `campaign_id` bigint unsigned NOT NULL,
  `donor_id` bigint unsigned DEFAULT NULL,
  `amount` decimal(15,2) NOT NULL,
  `donation_type` varchar(30) NOT NULL,
  `note` varchar(500) DEFAULT NULL,
  `status` varchar(30) NOT NULL,
  `currency` char(3) NOT NULL DEFAULT 'INR',
  `donation_status` varchar(30) NOT NULL DEFAULT 'PENDING',
  `donated_at` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by` bigint unsigned DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `updated_by` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_donations_campaign` (`campaign_id`),
  KEY `idx_donations_donor` (`donor_id`),
  KEY `idx_donations_status` (`donation_status`),
  KEY `idx_donations_date` (`donated_at`),
  CONSTRAINT `fk_donations_campaign` FOREIGN KEY (`campaign_id`) REFERENCES `donation_campaigns` (`id`),
  CONSTRAINT `fk_donations_donor` FOREIGN KEY (`donor_id`) REFERENCES `donors` (`id`),
  CONSTRAINT `chk_donation_amount` CHECK ((`amount` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donations`
--

LOCK TABLES `donations` WRITE;
/*!40000 ALTER TABLE `donations` DISABLE KEYS */;
INSERT INTO `donations` VALUES (1,1,1,501.00,'',NULL,'','INR','SUCCESS','2026-09-05 12:30:00.000000','2026-09-06 11:52:58.004781',3,'2026-09-06 11:52:58.004781',NULL),(2,1,2,1100.00,'',NULL,'','INR','SUCCESS','2026-09-06 09:15:00.000000','2026-09-06 11:52:58.004781',3,'2026-09-06 11:52:58.004781',NULL),(3,1,3,501.00,'',NULL,'','INR','SUCCESS','2026-09-06 10:20:00.000000','2026-09-06 11:52:58.004781',3,'2026-09-06 11:52:58.004781',NULL),(4,1,4,501.00,'ONLINE','Testing donation flow','SUCCESS','INR','SUCCESS','2026-10-04 18:02:33.613224','2026-10-04 17:38:28.609254',NULL,'2026-10-04 17:38:28.609254',NULL),(5,1,5,101.00,'ONLINE','Navdurga Utsav donation','PENDING','INR','PENDING','2026-10-11 00:26:50.177547','2026-10-11 00:26:50.177547',NULL,'2026-10-11 00:26:50.177547',NULL),(6,1,5,101.00,'ONLINE','Navdurga Utsav donation','PENDING','INR','PENDING','2026-10-11 00:27:09.609412','2026-10-11 00:27:09.609412',NULL,'2026-10-11 00:27:09.609412',NULL),(7,1,5,101.00,'ONLINE','Navdurga Utsav donation','PENDING','INR','PENDING','2026-10-11 00:49:19.377708','2026-10-11 00:49:19.377708',NULL,'2026-10-11 00:49:19.377708',NULL);
/*!40000 ALTER TABLE `donations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donors`
--

DROP TABLE IF EXISTS `donors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donors` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `mobile_number` varchar(15) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_donors_mobile` (`mobile_number`),
  KEY `idx_donors_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donors`
--

LOCK TABLES `donors` WRITE;
/*!40000 ALTER TABLE `donors` DISABLE KEYS */;
INSERT INTO `donors` VALUES (1,'Rohan Deshmukh','9876543211','rohan.deshmukh@example.com','2026-09-06 11:52:57.991208','2026-09-06 11:52:57.991208',NULL,NULL),(2,'Priya Joshi','9823456789','priya.joshi@example.com','2026-09-06 11:52:57.991208','2026-09-06 11:52:57.991208',NULL,NULL),(3,'Sandeep Pawar','9765432108',NULL,'2026-09-06 11:52:57.991208','2026-09-06 11:52:57.991208',NULL,NULL),(4,'Test Donor','9876543210','testdonor@example.com','2026-10-04 17:25:58.958615','2026-10-04 17:25:58.958615',NULL,NULL),(5,'Uday Ravindra Kaple','9322250836','udaykaple5@gmail.com','2026-10-11 00:26:50.077719','2026-10-11 00:26:50.077719',NULL,NULL);
/*!40000 ALTER TABLE `donors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_categories`
--

DROP TABLE IF EXISTS `event_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_categories` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by` bigint unsigned DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `updated_by` bigint unsigned DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_event_categories_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_categories`
--

LOCK TABLES `event_categories` WRITE;
/*!40000 ALTER TABLE `event_categories` DISABLE KEYS */;
INSERT INTO `event_categories` VALUES (1,'Religious','Religious and devotional activities','2026-09-06 11:52:57.879663',NULL,'2026-09-06 11:52:57.879663',NULL,NULL,NULL),(2,'Cultural','Cultural programs and performances','2026-09-06 11:52:57.879663',NULL,'2026-09-06 11:52:57.879663',NULL,NULL,NULL),(3,'Competition','Competitions organized during the festival','2026-09-06 11:52:57.879663',NULL,'2026-09-06 11:52:57.879663',NULL,NULL,NULL),(4,'Entertainment','Entertainment and recreational activities','2026-09-06 11:52:57.879663',NULL,'2026-09-06 11:52:57.879663',NULL,NULL,NULL),(5,'Sports','Sports and games','2026-09-06 11:52:57.879663',NULL,'2026-09-06 11:52:57.879663',NULL,NULL,NULL),(6,'Community','Community-oriented activities','2026-09-06 11:52:57.879663',NULL,'2026-09-06 11:52:57.879663',NULL,NULL,NULL),(7,'Special','Special festival events','2026-09-06 11:52:57.879663',NULL,'2026-09-06 11:52:57.879663',NULL,NULL,NULL);
/*!40000 ALTER TABLE `event_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_registrations`
--

DROP TABLE IF EXISTS `event_registrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_registrations` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `event_id` bigint unsigned NOT NULL,
  `name` varchar(150) NOT NULL,
  `mobile_number` varchar(15) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `age` tinyint unsigned DEFAULT NULL,
  `registration_status` varchar(30) NOT NULL DEFAULT 'REGISTERED',
  `registered_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `deleted_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_event_registrations_event` (`event_id`),
  KEY `idx_event_registrations_mobile` (`mobile_number`),
  KEY `idx_event_registrations_status` (`registration_status`),
  CONSTRAINT `fk_event_registrations_event` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_registrations`
--

LOCK TABLES `event_registrations` WRITE;
/*!40000 ALTER TABLE `event_registrations` DISABLE KEYS */;
INSERT INTO `event_registrations` VALUES (1,6,'Aditya Kulkarni','9876123456','aditya.kulkarni@example.com',17,'REGISTERED','2026-09-06 11:52:58.072835','2026-09-06 11:52:58.072835','2026-09-06 11:52:58.072835',NULL),(2,6,'Omkar Patil','9822123456','omkar.patil@example.com',19,'REGISTERED','2026-09-06 11:52:58.072835','2026-09-06 11:52:58.072835','2026-09-06 11:52:58.072835',NULL),(3,6,'Test User','9123456789','test@example.com',25,'REGISTERED','2026-10-04 10:12:38.716678','2026-10-04 10:12:38.716678','2026-10-04 10:12:38.716678',NULL);
/*!40000 ALTER TABLE `event_registrations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `events` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `festival_day_id` bigint unsigned NOT NULL,
  `category_id` bigint unsigned NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text,
  `event_type` varchar(30) NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `registration_required` tinyint(1) NOT NULL DEFAULT '0',
  `max_participants` int unsigned DEFAULT NULL,
  `status` varchar(30) NOT NULL DEFAULT 'DRAFT',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by` bigint unsigned DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `updated_by` bigint unsigned DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_events_festival_day` (`festival_day_id`),
  KEY `idx_events_category` (`category_id`),
  KEY `idx_events_type` (`event_type`),
  KEY `idx_events_status` (`status`),
  CONSTRAINT `fk_events_category` FOREIGN KEY (`category_id`) REFERENCES `event_categories` (`id`),
  CONSTRAINT `fk_events_festival_day` FOREIGN KEY (`festival_day_id`) REFERENCES `festival_days` (`id`),
  CONSTRAINT `chk_event_capacity` CHECK (((`max_participants` is null) or (`max_participants` > 0))),
  CONSTRAINT `chk_event_times` CHECK (((`end_time` is null) or (`end_time` >= `start_time`))),
  CONSTRAINT `chk_event_type` CHECK ((`event_type` in (_cp850'DAY',_cp850'NIGHT',_cp850'SPECIAL')))
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` VALUES (1,1,1,'Morning Aarti','Morning devotional Aarti for the Navdurga installation day.','DAY','07:00:00','08:00:00','Mandal Premises',0,NULL,'PUBLISHED','2026-09-06 11:52:57.923291',2,'2026-09-06 11:52:57.923291',NULL,NULL,NULL),(2,1,1,'Mahaarti','Fun community musical chairs competition.','NIGHT','20:30:00','21:30:00','Mandal Ground',1,50,'PUBLISHED','2026-09-06 11:52:57.923291',2,'2026-10-11 02:02:12.571277',NULL,NULL,NULL),(3,2,4,'Sangeet Khurchi','Festival lucky draw event for registered participants.','DAY','17:00:00','22:00:00','Mandal Ground',1,200,'PUBLISHED','2026-09-06 11:52:57.923291',2,'2026-10-11 02:02:12.581493',NULL,NULL,NULL),(4,3,4,'Lucky Draw','Special evening Aarti.','DAY','17:00:00','19:15:00','Mandal Premises',0,NULL,'PUBLISHED','2026-09-06 11:52:57.923291',2,'2026-10-11 02:02:12.588029',NULL,NULL,NULL),(5,3,1,'Jagrata','Devotional night program with bhajans and devotional performances.','NIGHT','21:00:00','23:59:00','Mandal Premises',0,NULL,'DRAFT','2026-09-06 11:52:57.923291',2,'2026-10-11 02:04:49.431334',NULL,'2026-10-11 02:04:49.431334',NULL),(6,4,1,'Mahila Bhajan','Open chess competition for local participants.','DAY','16:00:00','19:00:00','Mandal Hall',1,32,'PUBLISHED','2026-09-06 11:52:57.923291',2,'2026-10-11 02:02:12.593201',NULL,NULL,NULL),(7,4,6,'Eating Stalls','Cultural performances by local participants.','NIGHT','19:00:00','22:30:00','Mandal Ground',0,NULL,'PUBLISHED','2026-09-06 11:52:57.923291',2,'2026-10-11 02:02:12.595784',NULL,NULL,NULL),(8,8,1,'Special Aarti','Special devotional Aarti before Mahaprasad.','DAY','10:00:00','11:00:00','Mandal Premises',0,NULL,'PUBLISHED','2026-09-06 11:52:57.923291',2,'2026-09-06 11:52:57.923291',NULL,NULL,NULL),(9,9,4,'Mimicry','Grand procession for Navdurga Visarjan.','NIGHT','20:00:00','20:00:00','Mandal Premises to River',0,NULL,'DRAFT','2026-09-06 11:52:57.923291',2,'2026-10-11 02:05:02.675767',NULL,'2026-10-11 02:05:02.675767',NULL),(10,9,2,'Lezim Performance','Traditional Lezim performance during the Visarjan procession.','DAY','17:00:00','18:00:00','Procession Route',0,NULL,'DRAFT','2026-09-06 11:52:57.923291',2,'2026-10-11 02:04:49.447409',NULL,'2026-10-11 02:04:49.447409',NULL),(11,9,7,'Visarjan','Special DJ celebration after the Visarjan procession.','DAY','10:00:00','23:00:00','Mandal Ground',0,NULL,'PUBLISHED','2026-09-06 11:52:57.923291',2,'2026-10-11 02:02:12.614226',NULL,NULL,NULL),(12,4,1,'Carrom Competition','Carrom competition for local participants','DAY','10:00:00','13:00:00','Jwala Navdurga Mandal',1,32,'DRAFT','2026-10-04 22:19:42.369424',NULL,'2026-10-11 02:02:12.626586',NULL,'2026-10-11 02:02:12.626586',NULL),(13,5,1,'Navdurga Bhojan','An evening filled with cultural performances and entertainment.','DAY','12:00:00','19:00:00','Jwala Navdurga Mandal',0,NULL,'PUBLISHED','2026-10-06 01:36:40.000000',NULL,'2026-10-11 02:02:12.598533',NULL,NULL,NULL),(14,5,2,'Garba','A community gathering for devotees and local residents.','NIGHT','20:00:00','21:30:00','Jwala Navdurga Mandal',0,NULL,'PUBLISHED','2026-10-06 01:36:40.000000',NULL,'2026-10-11 02:02:12.600754',NULL,NULL,NULL),(15,3,4,'Magical Show','Special magical show for festival visitors.','NIGHT','20:00:00',NULL,NULL,0,NULL,'PUBLISHED','2026-10-11 02:02:12.621179',NULL,'2026-10-11 02:02:12.621179',NULL,NULL,NULL),(16,6,2,'Dance','Dance performances during the festival.','NIGHT','20:00:00',NULL,NULL,0,NULL,'PUBLISHED','2026-10-11 02:02:12.621179',NULL,'2026-10-11 02:02:12.621179',NULL,NULL,NULL),(17,2,1,'Jagrata','Night devotional event.','NIGHT','21:00:00',NULL,NULL,0,NULL,'PUBLISHED','2026-10-11 02:04:49.418424',NULL,'2026-10-11 02:04:49.418424',NULL,NULL,NULL),(18,6,5,'Lemon Spoon Competition','Lemon and spoon competition.','DAY','16:00:00',NULL,NULL,0,NULL,'PUBLISHED','2026-10-11 02:04:49.438822',NULL,'2026-10-11 02:04:49.438822',NULL,NULL,NULL),(19,7,4,'Mimicry','Mimicry performance.','NIGHT','20:00:00',NULL,NULL,0,NULL,'PUBLISHED','2026-10-11 02:04:49.442371',NULL,'2026-10-11 02:04:49.442371',NULL,NULL,NULL),(20,8,6,'Mahaprasad','Community mahaprasad.','NIGHT','20:00:00',NULL,NULL,0,NULL,'PUBLISHED','2026-10-11 02:04:49.445566',NULL,'2026-10-11 02:04:49.445566',NULL,NULL,NULL),(21,9,7,'Miravnuk','Night procession.','NIGHT','20:00:00',NULL,NULL,0,NULL,'PUBLISHED','2026-10-11 02:04:49.451879',NULL,'2026-10-11 02:04:49.451879',NULL,NULL,NULL);
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `festival_day_themes`
--

DROP TABLE IF EXISTS `festival_day_themes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `festival_day_themes` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `festival_day_id` bigint unsigned NOT NULL,
  `primary_color` varchar(20) NOT NULL,
  `secondary_color` varchar(20) NOT NULL,
  `accent_color` varchar(20) NOT NULL,
  `accent_light_color` varchar(20) NOT NULL,
  `accent_dark_color` varchar(20) NOT NULL,
  `glow_color` varchar(20) NOT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by` bigint unsigned DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `updated_by` bigint unsigned DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_theme_festival_day` (`festival_day_id`),
  CONSTRAINT `fk_theme_festival_day` FOREIGN KEY (`festival_day_id`) REFERENCES `festival_days` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `festival_day_themes`
--

LOCK TABLES `festival_day_themes` WRITE;
/*!40000 ALTER TABLE `festival_day_themes` DISABLE KEYS */;
INSERT INTO `festival_day_themes` VALUES (1,1,'#E879B9','#A82D72','#F3A6D2','#FCE4F1','#7D2057','#FFB8E0','2026-09-06 11:52:57.868681',1,'2026-10-11 00:39:17.822275',NULL,NULL,NULL),(2,2,'#D93645','#8F1728','#F16B76','#FFE0E3','#68101D','#FF8C96','2026-09-06 11:52:57.868681',1,'2026-10-11 00:39:17.822275',NULL,NULL,NULL),(3,3,'#F07827','#A7470E','#FFA45E','#FFE8D4','#78300A','#FFC080','2026-09-06 11:52:57.868681',1,'2026-10-11 00:39:17.822275',NULL,NULL,NULL),(4,4,'#39965A','#1D6036','#71C98B','#E0F4E5','#164526','#91E5A7','2026-09-06 11:52:57.868681',1,'2026-10-11 00:39:17.822275',NULL,NULL,NULL),(5,5,'#8050B8','#4E287B','#B18AE2','#EFE3FC','#361858','#C9A3FF','2026-09-06 11:52:57.868681',1,'2026-10-11 00:39:17.822275',NULL,NULL,NULL),(6,6,'#D6C8A2','#9D8960','#E9DDBE','#FAF5E8','#71603F','#F5E8C7','2026-09-06 11:52:57.868681',1,'2026-10-11 00:39:17.822275',NULL,NULL,NULL),(7,7,'#D52B91','#8A145C','#F064B7','#FCE0F1','#5E0C3D','#FF9AD6','2026-09-06 11:52:57.868681',1,'2026-10-11 00:39:17.822275',NULL,NULL,NULL),(8,8,'#3976D2','#1E4382','#6AA5FF','#E0ECFF','#163563','#8AB8FF','2026-09-06 11:52:57.868681',1,'2026-10-11 00:39:17.822275',NULL,NULL,NULL),(9,9,'#29252C','#111014','#77717F','#E9E5EB','#080709','#AAA0B5','2026-09-06 11:52:57.868681',1,'2026-10-11 00:39:17.822275',NULL,NULL,NULL);
/*!40000 ALTER TABLE `festival_day_themes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `festival_days`
--

DROP TABLE IF EXISTS `festival_days`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `festival_days` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `festival_id` bigint unsigned NOT NULL,
  `day_number` tinyint unsigned NOT NULL,
  `festival_date` date NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text,
  `is_visarjan_day` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by` bigint unsigned DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `updated_by` bigint unsigned DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_festival_day_number` (`festival_id`,`day_number`),
  UNIQUE KEY `uk_festival_day_date` (`festival_id`,`festival_date`),
  KEY `idx_festival_days_festival` (`festival_id`),
  CONSTRAINT `fk_festival_days_festival` FOREIGN KEY (`festival_id`) REFERENCES `festivals` (`id`),
  CONSTRAINT `chk_festival_day_number` CHECK ((`day_number` between 1 and 9))
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `festival_days`
--

LOCK TABLES `festival_days` WRITE;
/*!40000 ALTER TABLE `festival_days` DISABLE KEYS */;
INSERT INTO `festival_days` VALUES (1,1,1,'2026-10-11','Day 1 - Navdurga Installation','Installation and opening day of the Navdurga festival.',0,'2026-09-06 11:52:57.854954',1,'2026-10-11 00:34:48.501538',NULL,NULL,NULL),(2,1,2,'2026-10-12','Day 2 - Cultural Evening','Religious and cultural activities.',0,'2026-09-06 11:52:57.854954',1,'2026-10-11 00:34:48.501538',NULL,NULL,NULL),(3,1,3,'2026-10-13','Day 3 - Jagrata','Special evening dedicated to devotional activities.',0,'2026-09-06 11:52:57.854954',1,'2026-10-11 00:34:48.501538',NULL,NULL,NULL),(4,1,4,'2026-10-14','Day 4 - Competition Day','Daytime competitions and evening cultural activities.',0,'2026-09-06 11:52:57.854954',1,'2026-10-11 00:34:48.501538',NULL,NULL,NULL),(5,1,5,'2026-10-15','Day 5 - Cultural Programs','Cultural programs and community activities.',0,'2026-09-06 11:52:57.854954',1,'2026-10-11 00:34:48.501538',NULL,NULL,NULL),(6,1,6,'2026-10-16','Day 6 - Community Day','Community and devotional activities.',0,'2026-09-06 11:52:57.854954',1,'2026-10-11 00:34:48.501538',NULL,NULL,NULL),(7,1,7,'2026-10-17','Day 7 - Celebration','Special celebration and cultural programs.',0,'2026-09-06 11:52:57.854954',1,'2026-10-11 00:34:48.501538',NULL,NULL,NULL),(8,1,8,'2026-10-18','Day 8 - Mahaprasad','Special devotional activities and Mahaprasad.',0,'2026-09-06 11:52:57.854954',1,'2026-10-11 00:34:48.501538',NULL,NULL,NULL),(9,1,9,'2026-10-19','Day 9 - Visarjan','Visarjan procession and final day celebration.',1,'2026-09-06 11:52:57.854954',1,'2026-10-11 00:34:48.501538',NULL,NULL,NULL);
/*!40000 ALTER TABLE `festival_days` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `festivals`
--

DROP TABLE IF EXISTS `festivals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `festivals` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `year` smallint unsigned NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `description` text,
  `status` varchar(30) NOT NULL DEFAULT 'DRAFT',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by` bigint unsigned DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `updated_by` bigint unsigned DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_festivals_year` (`year`),
  CONSTRAINT `chk_festival_dates` CHECK ((`end_date` >= `start_date`))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `festivals`
--

LOCK TABLES `festivals` WRITE;
/*!40000 ALTER TABLE `festivals` DISABLE KEYS */;
INSERT INTO `festivals` VALUES (1,'Jwala Navdurga Utsav',2026,'2026-10-11','2026-10-19','Annual Navdurga festival organized by Jwala Navdurga Utsav Mandal.','PUBLISHED','2026-09-06 11:52:57.841599',1,'2026-10-11 00:34:49.734351',NULL,NULL,NULL);
/*!40000 ALTER TABLE `festivals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gallery_albums`
--

DROP TABLE IF EXISTS `gallery_albums`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gallery_albums` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `festival_id` bigint unsigned NOT NULL,
  `festival_day_id` bigint unsigned DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `description` text,
  `drive_url` varchar(1000) NOT NULL,
  `display_order` int unsigned NOT NULL DEFAULT '0',
  `status` varchar(30) NOT NULL DEFAULT 'PUBLISHED',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by` bigint unsigned DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `updated_by` bigint unsigned DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_gallery_festival` (`festival_id`),
  KEY `idx_gallery_day` (`festival_day_id`),
  CONSTRAINT `fk_gallery_day` FOREIGN KEY (`festival_day_id`) REFERENCES `festival_days` (`id`),
  CONSTRAINT `fk_gallery_festival` FOREIGN KEY (`festival_id`) REFERENCES `festivals` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gallery_albums`
--

LOCK TABLES `gallery_albums` WRITE;
/*!40000 ALTER TABLE `gallery_albums` DISABLE KEYS */;
INSERT INTO `gallery_albums` VALUES (1,1,1,'Day 1 - Navdurga Installation','Photos from the Navdurga installation and opening ceremony.','https://drive.google.com/drive/folders/REPLACE_WITH_DAY_1_FOLDER_ID',1,'PUBLISHED','2026-09-06 11:52:57.944050',4,'2026-09-06 11:52:57.944050',NULL,NULL,NULL),(2,1,3,'Day 3 - Jagrata','Photos from the Jagrata and devotional programs.','https://drive.google.com/drive/folders/REPLACE_WITH_DAY_3_FOLDER_ID',2,'PUBLISHED','2026-09-06 11:52:57.944050',4,'2026-09-06 11:52:57.944050',NULL,NULL,NULL),(3,1,8,'Mahaprasad','Photos from the Mahaprasad arrangement.','https://drive.google.com/drive/folders/REPLACE_WITH_MAHAPRASAD_FOLDER_ID',3,'PUBLISHED','2026-09-06 11:52:57.944050',4,'2026-09-06 11:52:57.944050',NULL,NULL,NULL),(4,1,9,'Visarjan','Photos from the Visarjan procession and celebrations.','https://drive.google.com/drive/folders/REPLACE_WITH_VISARJAN_FOLDER_ID',4,'PUBLISHED','2026-09-06 11:52:57.944050',4,'2026-09-06 11:52:57.944050',NULL,NULL,NULL),(5,1,4,'Day 4 - Competition Day','Photos from the chess competition and cultural programs.','https://drive.google.com/drive/folders/REPLACE_WITH_DAY_4_FOLDER_ID',2,'PUBLISHED','2026-10-04 10:39:29.000000',NULL,'2026-10-04 10:39:29.253527',NULL,NULL,NULL);
/*!40000 ALTER TABLE `gallery_albums` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mahaprasad_events`
--

DROP TABLE IF EXISTS `mahaprasad_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mahaprasad_events` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `festival_id` bigint unsigned NOT NULL,
  `festival_day_id` bigint unsigned DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `description` text,
  `event_date` date NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time DEFAULT NULL,
  `location` varchar(255) NOT NULL,
  `estimated_people` int unsigned DEFAULT NULL,
  `status` varchar(30) NOT NULL DEFAULT 'DRAFT',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by` bigint unsigned DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `updated_by` bigint unsigned DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_mahaprasad_festival` (`festival_id`),
  KEY `idx_mahaprasad_day` (`festival_day_id`),
  KEY `idx_mahaprasad_date` (`event_date`),
  CONSTRAINT `fk_mahaprasad_day` FOREIGN KEY (`festival_day_id`) REFERENCES `festival_days` (`id`),
  CONSTRAINT `fk_mahaprasad_festival` FOREIGN KEY (`festival_id`) REFERENCES `festivals` (`id`),
  CONSTRAINT `chk_mahaprasad_people` CHECK (((`estimated_people` is null) or (`estimated_people` > 0)))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mahaprasad_events`
--

LOCK TABLES `mahaprasad_events` WRITE;
/*!40000 ALTER TABLE `mahaprasad_events` DISABLE KEYS */;
INSERT INTO `mahaprasad_events` VALUES (1,1,8,'Navdurga Mahaprasad','Mahaprasad arranged for local residents and devotees.','2026-09-22','12:00:00','15:00:00','Mandal Premises',1000,'ACTIVE','2026-09-06 11:52:58.041466',1,'2026-10-05 22:17:34.845271',NULL,NULL,NULL),(2,1,8,'Community Mahaprasad 2026 - Updated','Updated Mahaprasad information for devotees.','2026-09-22','12:30:00','15:30:00','Jwala Navdurga Mandal Ground',1200,'DELETED','2026-10-05 22:16:52.483905',NULL,'2026-10-05 22:24:44.815517',NULL,'2026-10-05 22:24:44.815517',NULL),(3,1,8,'Community Mahaprasad 2026','Mahaprasad distribution for devotees and local residents.','2026-09-22','12:00:00','15:00:00','Jwala Navdurga Mandal',1000,'DRAFT','2026-10-05 22:23:26.564303',NULL,'2026-10-05 22:23:26.564303',NULL,NULL,NULL);
/*!40000 ALTER TABLE `mahaprasad_events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_transactions`
--

DROP TABLE IF EXISTS `payment_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_transactions` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `donation_id` bigint unsigned NOT NULL,
  `gateway` varchar(50) NOT NULL,
  `gateway_transaction_id` varchar(255) DEFAULT NULL,
  `payment_method` varchar(50) DEFAULT NULL,
  `amount` decimal(15,2) NOT NULL,
  `currency` char(3) NOT NULL DEFAULT 'INR',
  `status` varchar(30) NOT NULL DEFAULT 'INITIATED',
  `initiated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `completed_at` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_gateway_transaction` (`gateway`,`gateway_transaction_id`),
  KEY `idx_payment_donation` (`donation_id`),
  KEY `idx_payment_status` (`status`),
  CONSTRAINT `fk_payment_donation` FOREIGN KEY (`donation_id`) REFERENCES `donations` (`id`),
  CONSTRAINT `chk_payment_amount` CHECK ((`amount` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_transactions`
--

LOCK TABLES `payment_transactions` WRITE;
/*!40000 ALTER TABLE `payment_transactions` DISABLE KEYS */;
INSERT INTO `payment_transactions` VALUES (1,1,'RAZORPAY','pay_demo_100001','UPI',501.00,'INR','SUCCESS','2026-09-05 12:29:40.000000','2026-09-05 12:30:00.000000','2026-09-06 11:52:58.019091','2026-09-06 11:52:58.019091'),(2,2,'RAZORPAY','pay_demo_100002','UPI',1100.00,'INR','SUCCESS','2026-09-06 09:14:30.000000','2026-09-06 09:15:00.000000','2026-09-06 11:52:58.019091','2026-09-06 11:52:58.019091'),(3,3,'RAZORPAY','pay_demo_100003','UPI',501.00,'INR','SUCCESS','2026-09-06 10:19:30.000000','2026-09-06 10:20:00.000000','2026-09-06 11:52:58.019091','2026-09-06 11:52:58.019091'),(4,4,'TEST_GATEWAY','test_txn_000004','UPI',501.00,'INR','SUCCESS','2026-10-04 17:46:12.656602','2026-10-04 18:02:33.613224','2026-10-04 17:46:12.656602','2026-10-04 18:02:33.613224');
/*!40000 ALTER TABLE `payment_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by` bigint unsigned DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `updated_by` bigint unsigned DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_permissions_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions` VALUES (1,'FESTIVAL_VIEW','View festival information','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(2,'FESTIVAL_CREATE','Create festival','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(3,'FESTIVAL_UPDATE','Update festival','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(4,'FESTIVAL_DELETE','Delete festival','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(5,'EVENT_VIEW','View events','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(6,'EVENT_CREATE','Create events','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(7,'EVENT_UPDATE','Update events','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(8,'EVENT_DELETE','Delete events','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(9,'EVENT_PUBLISH','Publish events','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(10,'REGISTRATION_VIEW','View event registrations','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(11,'REGISTRATION_EXPORT','Export event registrations','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(12,'DONATION_VIEW','View donations','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(13,'DONATION_CREATE','Create donation records','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(14,'DONATION_UPDATE','Update donation records','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(15,'DONATION_EXPORT','Export donation records','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(16,'PAYMENT_VIEW','View payment transactions','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(17,'RECEIPT_VIEW','View donation receipts','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(18,'RECEIPT_CREATE','Generate donation receipts','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(19,'GALLERY_VIEW','View gallery','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(20,'GALLERY_CREATE','Create gallery albums','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(21,'GALLERY_UPDATE','Update gallery albums','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(22,'GALLERY_DELETE','Delete gallery albums','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(23,'CONTENT_VIEW','View content','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(24,'CONTENT_CREATE','Create content','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(25,'CONTENT_UPDATE','Update content','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(26,'CONTENT_DELETE','Delete content','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(27,'CONTENT_PUBLISH','Publish content','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(28,'MAHAPRASAD_VIEW','View Mahaprasad information','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(29,'MAHAPRASAD_CREATE','Create Mahaprasad event','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(30,'MAHAPRASAD_UPDATE','Update Mahaprasad event','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(31,'VOLUNTEER_VIEW','View volunteer applications','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(32,'VOLUNTEER_UPDATE','Update volunteer applications','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(33,'USER_VIEW','View admin users','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(34,'USER_CREATE','Create admin users','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(35,'USER_UPDATE','Update admin users','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(36,'USER_DELETE','Delete admin users','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(37,'ROLE_VIEW','View roles','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(38,'ROLE_ASSIGN','Assign roles','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL),(39,'AUDIT_VIEW','View audit logs','2026-09-06 11:52:57.807307',NULL,'2026-09-06 11:52:57.807307',NULL,NULL,NULL);
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permissions`
--

DROP TABLE IF EXISTS `role_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permissions` (
  `role_id` bigint unsigned NOT NULL,
  `permission_id` bigint unsigned NOT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `fk_role_permissions_permission` (`permission_id`),
  CONSTRAINT `fk_role_permissions_permission` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`),
  CONSTRAINT `fk_role_permissions_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permissions`
--

LOCK TABLES `role_permissions` WRITE;
/*!40000 ALTER TABLE `role_permissions` DISABLE KEYS */;
INSERT INTO `role_permissions` VALUES (1,1,'2026-10-04 21:36:22.133477',NULL),(1,2,'2026-10-04 21:36:22.133477',NULL),(1,3,'2026-10-04 21:36:22.133477',NULL),(1,4,'2026-10-04 21:36:22.133477',NULL),(1,5,'2026-10-04 21:36:22.133477',NULL),(1,6,'2026-10-04 21:36:22.133477',NULL),(1,7,'2026-10-04 21:36:22.133477',NULL),(1,8,'2026-10-04 21:36:22.133477',NULL),(1,9,'2026-10-04 21:36:22.133477',NULL),(1,10,'2026-10-04 21:36:22.133477',NULL),(1,11,'2026-10-04 21:36:22.133477',NULL),(1,12,'2026-10-04 21:36:22.133477',NULL),(1,13,'2026-10-04 21:36:22.133477',NULL),(1,14,'2026-10-04 21:36:22.133477',NULL),(1,15,'2026-10-04 21:36:22.133477',NULL),(1,16,'2026-10-04 21:36:22.133477',NULL),(1,17,'2026-10-04 21:36:22.133477',NULL),(1,18,'2026-10-04 21:36:22.133477',NULL),(1,19,'2026-10-04 21:36:22.133477',NULL),(1,20,'2026-10-04 21:36:22.133477',NULL),(1,21,'2026-10-04 21:36:22.133477',NULL),(1,22,'2026-10-04 21:36:22.133477',NULL),(1,23,'2026-10-04 21:36:22.133477',NULL),(1,24,'2026-10-04 21:36:22.133477',NULL),(1,25,'2026-10-04 21:36:22.133477',NULL),(1,26,'2026-10-04 21:36:22.133477',NULL),(1,27,'2026-10-04 21:36:22.133477',NULL),(1,28,'2026-10-04 21:36:22.133477',NULL),(1,29,'2026-10-04 21:36:22.133477',NULL),(1,30,'2026-10-04 21:36:22.133477',NULL),(1,31,'2026-10-04 21:36:22.133477',NULL),(1,32,'2026-10-04 21:36:22.133477',NULL),(1,33,'2026-10-04 21:36:22.133477',NULL),(1,34,'2026-10-04 21:36:22.133477',NULL),(1,35,'2026-10-04 21:36:22.133477',NULL),(1,36,'2026-10-04 21:36:22.133477',NULL),(1,37,'2026-10-04 21:36:22.133477',NULL),(1,38,'2026-10-04 21:36:22.133477',NULL),(1,39,'2026-10-04 21:36:22.133477',NULL),(2,1,'2026-10-04 21:44:12.888488',NULL),(2,2,'2026-10-04 21:44:12.888488',NULL),(2,3,'2026-10-04 21:44:12.888488',NULL),(2,4,'2026-10-04 21:44:12.888488',NULL),(2,5,'2026-10-04 21:44:12.888488',NULL),(2,6,'2026-10-04 21:44:12.888488',NULL),(2,7,'2026-10-04 21:44:12.888488',NULL),(2,8,'2026-10-04 21:44:12.888488',NULL),(2,9,'2026-10-04 21:44:12.888488',NULL),(2,10,'2026-10-04 21:44:12.888488',NULL),(2,11,'2026-10-04 21:44:12.888488',NULL),(3,12,'2026-10-04 21:44:27.781819',NULL),(3,13,'2026-10-04 21:44:27.781819',NULL),(3,14,'2026-10-04 21:44:27.781819',NULL),(3,15,'2026-10-04 21:44:27.781819',NULL),(3,16,'2026-10-04 21:44:27.781819',NULL),(3,17,'2026-10-04 21:44:27.781819',NULL),(3,18,'2026-10-04 21:44:27.781819',NULL),(4,19,'2026-10-04 21:44:41.683972',NULL),(4,20,'2026-10-04 21:44:41.683972',NULL),(4,21,'2026-10-04 21:44:41.683972',NULL),(4,22,'2026-10-04 21:44:41.683972',NULL),(5,23,'2026-10-04 21:44:48.006960',NULL),(5,24,'2026-10-04 21:44:48.006960',NULL),(5,25,'2026-10-04 21:44:48.006960',NULL),(5,26,'2026-10-04 21:44:48.006960',NULL),(5,27,'2026-10-04 21:44:48.006960',NULL),(6,28,'2026-10-04 21:44:54.466298',NULL),(6,29,'2026-10-04 21:44:54.466298',NULL),(6,30,'2026-10-04 21:44:54.466298',NULL),(7,31,'2026-10-04 21:45:01.348170',NULL),(7,32,'2026-10-04 21:45:01.348170',NULL);
/*!40000 ALTER TABLE `role_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by` bigint unsigned DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `updated_by` bigint unsigned DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_roles_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'SUPER_ADMIN','Full access to all administrative functionality','2026-09-06 11:52:57.778127',NULL,'2026-09-06 11:52:57.778127',NULL,NULL,NULL),(2,'EVENT_ADMIN','Manages festivals, events and event registrations','2026-09-06 11:52:57.778127',NULL,'2026-09-06 11:52:57.778127',NULL,NULL,NULL),(3,'DONATION_ADMIN','Manages donations, payment records and receipts','2026-09-06 11:52:57.778127',NULL,'2026-09-06 11:52:57.778127',NULL,NULL,NULL),(4,'GALLERY_ADMIN','Manages gallery albums and Google Drive media links','2026-09-06 11:52:57.778127',NULL,'2026-09-06 11:52:57.778127',NULL,NULL,NULL),(5,'CONTENT_ADMIN','Manages announcements and blog posts','2026-09-06 11:52:57.778127',NULL,'2026-09-06 11:52:57.778127',NULL,NULL,NULL),(6,'MAHAPRASAD_ADMIN','Manages Mahaprasad information','2026-09-06 11:52:57.778127',NULL,'2026-09-06 11:52:57.778127',NULL,NULL,NULL),(7,'VOLUNTEER_ADMIN','Manages volunteer applications','2026-09-06 11:52:57.778127',NULL,'2026-09-06 11:52:57.778127',NULL,NULL,NULL);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint unsigned NOT NULL,
  `role_id` bigint unsigned NOT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_user_roles_role` (`role_id`),
  CONSTRAINT `fk_user_roles_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `fk_user_roles_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1,'2026-09-06 11:52:57.831304',NULL),(2,2,'2026-09-06 11:52:57.831304',NULL),(3,3,'2026-09-06 11:52:57.831304',NULL),(4,4,'2026-09-06 11:52:57.831304',NULL);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `mobile_number` varchar(15) DEFAULT NULL,
  `status` varchar(30) NOT NULL DEFAULT 'ACTIVE',
  `last_login_at` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by` bigint unsigned DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `updated_by` bigint unsigned DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_users_username` (`username`),
  UNIQUE KEY `uk_users_email` (`email`),
  KEY `idx_users_status` (`status`),
  KEY `idx_users_deleted_at` (`deleted_at`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'superadmin','admin@jwalanavdurga.org','$2a$10$F60rqnxZIvE9uxBw6wMSVe7MwHJ0K/7azlpt2Qd.Gkqenq/lBlNYe','Amit','Patil','9876543210','ACTIVE',NULL,'2026-09-06 11:52:57.823265',NULL,'2026-10-04 19:13:32.349910',NULL,NULL,NULL),(2,'eventadmin','events@jwalanavdurga.org','$2a$10$REPLACE_WITH_REAL_BCRYPT_HASH','Rahul','Shinde','9823456712','ACTIVE',NULL,'2026-09-06 11:52:57.823265',NULL,'2026-09-06 11:52:57.823265',NULL,NULL,NULL),(3,'donationadmin','donations@jwalanavdurga.org','$2a$10$REPLACE_WITH_REAL_BCRYPT_HASH','Sneha','Kulkarni','9765432189','ACTIVE',NULL,'2026-09-06 11:52:57.823265',NULL,'2026-09-06 11:52:57.823265',NULL,NULL,NULL),(4,'galleryadmin','gallery@jwalanavdurga.org','$2a$10$REPLACE_WITH_REAL_BCRYPT_HASH','Akash','Jadhav','9890123456','ACTIVE',NULL,'2026-09-06 11:52:57.823265',NULL,'2026-09-06 11:52:57.823265',NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `volunteer_applications`
--

DROP TABLE IF EXISTS `volunteer_applications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `volunteer_applications` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `festival_id` bigint unsigned DEFAULT NULL,
  `name` varchar(150) NOT NULL,
  `mobile_number` varchar(15) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `age` tinyint unsigned DEFAULT NULL,
  `preferred_area` varchar(150) DEFAULT NULL,
  `message` text,
  `status` varchar(30) NOT NULL DEFAULT 'PENDING',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_volunteer_festival` (`festival_id`),
  KEY `idx_volunteer_mobile` (`mobile_number`),
  KEY `idx_volunteer_status` (`status`),
  CONSTRAINT `fk_volunteer_festival` FOREIGN KEY (`festival_id`) REFERENCES `festivals` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `volunteer_applications`
--

LOCK TABLES `volunteer_applications` WRITE;
/*!40000 ALTER TABLE `volunteer_applications` DISABLE KEYS */;
INSERT INTO `volunteer_applications` VALUES (1,1,'Vivek More','9876501234','vivek.more@example.com',24,'Event Management','I can help with evening events and crowd coordination.','PENDING','2026-09-06 11:52:58.052438','2026-09-06 11:52:58.052438',NULL,NULL),(2,1,'Neha Bhosale','9823012345','neha.bhosale@example.com',22,'Photography','I can help with photography and social media content.','APPROVED','2026-09-06 11:52:58.052438','2026-09-06 11:52:58.052438',NULL,NULL),(3,1,'Rahul Patil','9876543210','rahul@example.com',24,'Event Management','I would like to help during the Navdurga festival.','PENDING','2026-10-04 11:04:22.138710','2026-10-04 11:04:22.138710',NULL,NULL),(4,1,'Uday Patil','9876543211','rahul@example.com',24,'Event Management','I would like to help.','PENDING','2026-10-04 11:19:14.817845','2026-10-04 11:19:14.817845',NULL,NULL);
/*!40000 ALTER TABLE `volunteer_applications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'jwala_navdurga'
--

--
-- Dumping routines for database 'jwala_navdurga'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-11  8:25:57
