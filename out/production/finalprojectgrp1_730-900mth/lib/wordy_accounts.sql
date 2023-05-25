-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 25, 2023 at 11:31 AM
-- Server version: 8.0.31
-- PHP Version: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `wordy_accounts`
--

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_username` varchar(50) NOT NULL,
  `user_password` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `user_username`, `user_password`) VALUES
(1, 'Bryan', '123'),
(2, 'Jason', '123'),
(3, 'JM', '321');

-- --------------------------------------------------------

--
-- Table structure for table `wincount`
--

DROP TABLE IF EXISTS `wincount`;
CREATE TABLE IF NOT EXISTS `wincount` (
  `username` varchar(50) NOT NULL,
  `wins` int NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `wincount`
--

INSERT INTO `wincount` (`username`, `wins`) VALUES
('Bryan', 4),
('JM', 2);

-- --------------------------------------------------------

--
-- Table structure for table `wordlist`
--

DROP TABLE IF EXISTS `wordlist`;
CREATE TABLE IF NOT EXISTS `wordlist` (
  `user_id` int NOT NULL,
  `word` varchar(17) NOT NULL,
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `wordlist`
--

INSERT INTO `wordlist` (`user_id`, `word`) VALUES
(1, 'Fabric'),
(3, 'waxes'),
(3, 'whops'),
(3, 'heatwave'),
(3, 'nicknames'),
(1, 'chancy'),
(1, 'cicada'),
(1, 'candy'),
(3, 'skive'),
(3, 'stills'),
(3, 'queer'),
(1, 'heaven'),
(1, 'crowd'),
(1, 'microbe');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
