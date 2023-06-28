-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jun 23, 2023 at 03:03 PM
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
('Bryan', 23),
('JM', 25),
('Jason', 1);

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
(1, 'microbe'),
(1, 'penalizes'),
(3, 'deluxe'),
(1, 'ulterior'),
(3, 'exploit'),
(1, 'guillotine'),
(3, 'uplifting'),
(3, 'intricacy'),
(3, 'smokers'),
(3, 'affirmed'),
(3, 'pompom'),
(3, 'awaken'),
(3, 'outbid'),
(1, 'toxic'),
(1, 'codex'),
(3, 'welcome'),
(3, 'eyeful'),
(3, 'repay'),
(3, 'plaza'),
(1, 'farmhouse'),
(1, 'coughs'),
(2, 'patent'),
(3, 'clench'),
(3, 'pompon'),
(3, 'shave'),
(3, 'qualities'),
(1, 'touch'),
(1, 'catwalk'),
(3, 'bricked'),
(1, 'soaking'),
(1, 'expose'),
(1, 'skulk'),
(1, 'gabble'),
(3, 'entree'),
(3, 'effigy'),
(3, 'perishing'),
(3, 'children'),
(1, 'visit'),
(3, 'quilt'),
(1, 'ambush'),
(3, 'account'),
(3, 'quartz'),
(3, 'approve'),
(1, 'tactic'),
(1, 'joint'),
(1, 'savanna'),
(3, 'mousy'),
(3, 'buffed'),
(1, 'oxide'),
(3, 'lagoon'),
(3, 'quiff'),
(1, 'gallery'),
(3, 'rebate'),
(3, 'achieve'),
(3, 'giggle'),
(1, 'venom'),
(1, 'judge'),
(3, 'ground'),
(1, 'fatal'),
(3, 'brink'),
(3, 'phone'),
(3, 'enough'),
(3, 'billion'),
(1, 'carsick'),
(1, 'gazes'),
(1, 'acacia'),
(3, 'jackal'),
(1, 'gooses'),
(1, 'smoothing'),
(3, 'boxing'),
(3, 'upbeat'),
(3, 'boozy'),
(1, 'magic'),
(1, 'kitchen'),
(1, 'month'),
(1, 'bubbles'),
(1, 'bluejays'),
(3, 'however'),
(3, 'whoever'),
(1, 'boatman'),
(1, 'couch'),
(1, 'vouch'),
(1, 'fazing'),
(3, 'faint'),
(1, 'diffuse'),
(1, 'exile'),
(1, 'piggy'),
(1, 'buggy'),
(1, 'patient'),
(1, 'patient'),
(3, 'booked'),
(3, 'winner'),
(3, 'winner'),
(3, 'maxing'),
(3, 'serve');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
