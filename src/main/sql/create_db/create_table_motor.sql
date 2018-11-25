/*
SQLyog Community v12.09 (64 bit)
MySQL - 5.5.47-MariaDB-log 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table if not exists `motor` (
	`ID` int (11),
	`CertOrgID` int (11),
	`Designation` varchar (192),
	`DiameterID` int (11),
	`ExternalID` varchar (192),
	`ImpulseID` int (11),
	`LastUpdated` date ,
	`ManufacturerID` int (11),
	`NameID` int (11),
	`PropellantID` int (11),
	`TypeID` int (11),
	`BrandName` varchar (192),
	`BurnTime` double ,
	`CaseId` int (11),
	`GrossWeight` double ,
	`Length` double ,
	`ThrustAvg` double ,
	`ThrustMax` double ,
	`TotalImpulseNs` double ,
	`Weight` double
); 
