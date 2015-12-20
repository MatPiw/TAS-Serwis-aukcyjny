-- phpMyAdmin SQL Dump
-- version home.pl
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 20, 2015 at 02:25 PM
-- Server version: 5.5.44-37.3-log
-- PHP Version: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `14622709_tasy`
--

-- --------------------------------------------------------

--
-- Table structure for table `bids`
--

CREATE TABLE IF NOT EXISTS `bids` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `OFFER_ID` int(11) NOT NULL,
  `BIDDER_ID` int(11) NOT NULL,
  `PRICE` double NOT NULL,
  `CREATED_AT` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin2 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE IF NOT EXISTS `comments` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `GIVER_ID` int(11) NOT NULL,
  `RECIEVER_ID` int(11) NOT NULL,
  `OFFER_ID` int(11) NOT NULL,
  `COMMENT` text NOT NULL,
  `POSITIVE` tinyint(4) NOT NULL,
  `CREATED_AT` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin2 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `offers`
--

CREATE TABLE IF NOT EXISTS `offers` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` text NOT NULL,
  `PICTURE_PATH` varchar(255) NOT NULL,
  `OWNER_ID` int(11) NOT NULL,
  `BUY_NOW_PRICE` double NOT NULL,
  `ACTIVE` tinyint(4) NOT NULL,
  `CREATED_AT` datetime NOT NULL,
  `FINISHED_AT` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin2 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LOGIN` varchar(255) CHARACTER SET latin2 NOT NULL,
  `HASH_PASSWORD` varchar(255) CHARACTER SET latin2 NOT NULL,
  `PERMISSIONS` tinyint(4) NOT NULL,
  `FIRST_NAME` varchar(255) CHARACTER SET latin2 NOT NULL,
  `LAST_NAME` varchar(255) CHARACTER SET latin2 NOT NULL,
  `EMAIL` varchar(255) CHARACTER SET latin2 NOT NULL,
  `CITY` varchar(255) CHARACTER SET latin2 NOT NULL,
  `ADDRESS` varchar(255) CHARACTER SET latin2 NOT NULL,
  `PHONE` varchar(9) CHARACTER SET latin2 NOT NULL,
  `ZIP_CODE` varchar(6) CHARACTER SET latin2 NOT NULL,
  `CONFIRMED` varchar(1) CHARACTER SET latin2 NOT NULL,
  `CREATED_AT` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
