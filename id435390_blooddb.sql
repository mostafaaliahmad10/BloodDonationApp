-- phpMyAdmin SQL Dump
-- version 4.6.5.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jan 22, 2017 at 04:07 PM
-- Server version: 10.1.20-MariaDB
-- PHP Version: 7.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id435390_blooddb`
--

-- --------------------------------------------------------

--
-- Table structure for table `bloodtype`
--

CREATE TABLE `bloodtype` (
  `bloodtypeId` int(11) NOT NULL,
  `bloodname` varchar(3) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bloodtype`
--

INSERT INTO `bloodtype` (`bloodtypeId`, `bloodname`) VALUES
(3, 'A+'),
(4, 'A-'),
(5, 'B+'),
(6, 'B-'),
(7, 'O+'),
(8, 'O-'),
(9, 'AB+'),
(10, 'AB-');

-- --------------------------------------------------------

--
-- Table structure for table `company`
--

CREATE TABLE `company` (
  `companyId` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `phone1` varchar(50) DEFAULT NULL,
  `phone2` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  `xcoor` varchar(50) DEFAULT NULL,
  `ycoor` varchar(50) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `company`
--

INSERT INTO `company` (`companyId`, `name`, `phone1`, `phone2`, `address`, `flag`, `xcoor`, `ycoor`) VALUES
(15, 'companyhhh', '22222344', '1111113', 'nabatieh3', 1, '33.3897', '35.4828'),
(16, 'company2', '666666', '777777', 'beirut', 1, '66', '77'),
(17, 'compnnnay3', '7777877', '888788', 'beyrouth', 1, '33.3897', '35.4828');

-- --------------------------------------------------------

--
-- Table structure for table `companyusers`
--

CREATE TABLE `companyusers` (
  `companyuserId` int(11) NOT NULL,
  `companyId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `companyusers`
--

INSERT INTO `companyusers` (`companyuserId`, `companyId`, `userId`) VALUES
(18, 17, 89),
(17, 16, 88),
(16, 15, 85),
(19, 18, 93),
(20, 19, 100),
(21, 20, 101),
(22, 21, 102);

-- --------------------------------------------------------

--
-- Table structure for table `district`
--

CREATE TABLE `district` (
  `districtId` int(11) NOT NULL,
  `districtname` varchar(50) DEFAULT NULL,
  `govId` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `district`
--

INSERT INTO `district` (`districtId`, `districtname`, `govId`) VALUES
(1, 'Beirut', 1),
(2, 'Rashaya', 2),
(3, 'WesternBeqaa', 2),
(4, 'Zahle', 2),
(5, 'Aley', 3),
(6, 'Baabda', 3),
(7, 'Chouf', 3),
(8, 'Jbeil', 3),
(9, 'Keserwan', 3),
(10, 'Matn', 3),
(11, 'Batroun', 4),
(12, 'Bsharri', 4),
(13, 'Koura', 4),
(14, 'Minyeh', 4),
(15, 'Tripoli', 4),
(16, 'Zgharta', 4),
(17, 'BintJbeil', 5),
(18, 'Hasbaya', 5),
(19, 'Marjeyoun', 5),
(20, 'Nabatiyeh', 5),
(21, 'Sidon', 6),
(22, 'Jezzine', 6),
(23, 'Tyre', 6);

-- --------------------------------------------------------

--
-- Table structure for table `donor`
--

CREATE TABLE `donor` (
  `donorId` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `phone1` varchar(50) DEFAULT NULL,
  `phone2` varchar(50) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  `rate` double DEFAULT NULL,
  `lastdonation` date DEFAULT NULL,
  `seconddonationdate` date DEFAULT NULL,
  `xcoor` varchar(100) DEFAULT NULL,
  `ycoor` varchar(100) DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  `bloodtypeId` int(11) DEFAULT NULL,
  `districtId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `donor`
--

INSERT INTO `donor` (`donorId`, `name`, `phone1`, `phone2`, `flag`, `rate`, `lastdonation`, `seconddonationdate`, `xcoor`, `ycoor`, `status`, `bloodtypeId`, `districtId`, `userId`) VALUES
(80, 'hadi', '71357843', '333', 1, 1.5, '2017-01-22', NULL, '33.3872304', '35.4997306', '1', 3, 1, 87),
(81, 'lamaa', '66666', '6666', 1, 1, '2017-01-22', '2017-07-22', '33.4000326', '35.5122881', '1', 9, 8, 90),
(79, 'mostafa', '111111', '222222', 1, 3, '2017-01-20', NULL, '33.3872299', '35.499727', '1', 5, 20, 86),
(82, 'hala', '7777', '8888', 1, 0, '2017-01-22', '2017-01-22', '33.3872102', '35.4996884', '1', 6, 15, 91);

-- --------------------------------------------------------

--
-- Table structure for table `governorate`
--

CREATE TABLE `governorate` (
  `govId` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `governorate`
--

INSERT INTO `governorate` (`govId`, `name`) VALUES
(1, 'Beirut'),
(2, 'Beqaa'),
(3, 'MountLebanon'),
(4, 'North'),
(5, 'Nabatiye'),
(6, 'South');

-- --------------------------------------------------------

--
-- Table structure for table `permission`
--

CREATE TABLE `permission` (
  `permId` int(11) NOT NULL,
  `flag` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `permission`
--

INSERT INTO `permission` (`permId`, `flag`, `name`) VALUES
(1, 1, 'admin'),
(2, 1, 'staff'),
(3, 1, 'donor');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userId` int(11) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  `lastlogin` datetime DEFAULT NULL,
  `permId` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userId`, `username`, `password`, `email`, `flag`, `lastlogin`, `permId`) VALUES
(51, 'admin', 'admin', 'email', 1, NULL, 1),
(89, 'staff5', 'staff5', 'ejjjjjvbj@', 1, NULL, 2),
(88, 'staff4', 'staff4', 'bnnb@', 1, NULL, 2),
(86, 'mostafa', 'mostafa', 'mostafaaliahmad10@gmail.com', 1, NULL, 3),
(87, 'hadi', 'hadi', 'hadihh6email@', 1, NULL, 3),
(85, 'staff1', 'staff1', 'email3@', 1, NULL, 2),
(90, 'lama', 'lama', 'lama@emai', 1, NULL, 3),
(91, 'hala', 'hala', 'hala@mail', 1, NULL, 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bloodtype`
--
ALTER TABLE `bloodtype`
  ADD PRIMARY KEY (`bloodtypeId`);

--
-- Indexes for table `company`
--
ALTER TABLE `company`
  ADD PRIMARY KEY (`companyId`);

--
-- Indexes for table `companyusers`
--
ALTER TABLE `companyusers`
  ADD PRIMARY KEY (`companyuserId`),
  ADD KEY `companyId` (`companyId`,`userId`),
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `district`
--
ALTER TABLE `district`
  ADD PRIMARY KEY (`districtId`),
  ADD KEY `govId` (`govId`);

--
-- Indexes for table `donor`
--
ALTER TABLE `donor`
  ADD PRIMARY KEY (`donorId`),
  ADD KEY `bloodtypeId` (`districtId`,`userId`),
  ADD KEY `userId` (`userId`),
  ADD KEY `bloodtypeId_2` (`bloodtypeId`);

--
-- Indexes for table `governorate`
--
ALTER TABLE `governorate`
  ADD PRIMARY KEY (`govId`);

--
-- Indexes for table `permission`
--
ALTER TABLE `permission`
  ADD PRIMARY KEY (`permId`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userId`),
  ADD KEY `permId` (`permId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bloodtype`
--
ALTER TABLE `bloodtype`
  MODIFY `bloodtypeId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `company`
--
ALTER TABLE `company`
  MODIFY `companyId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT for table `companyusers`
--
ALTER TABLE `companyusers`
  MODIFY `companyuserId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT for table `district`
--
ALTER TABLE `district`
  MODIFY `districtId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;
--
-- AUTO_INCREMENT for table `donor`
--
ALTER TABLE `donor`
  MODIFY `donorId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=88;
--
-- AUTO_INCREMENT for table `governorate`
--
ALTER TABLE `governorate`
  MODIFY `govId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `permission`
--
ALTER TABLE `permission`
  MODIFY `permId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `userId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=103;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
