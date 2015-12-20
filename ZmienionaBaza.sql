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
create database tas

create table if not exists `account_datas`(
	`DATA_ID` int(11) not null AUTO_INCREMENT,
    `LOGIN` varchar(35)  CHARACTER SET latin2 unique NOT NULL check(`LOGIN`not like '%[^a-zA-Z0-9ęóąśłżźćń]%'),
    `HASH_PASSWORD` varchar(30) CHARACTER SET latin2 NOT NULL,
	`EMAIL` varchar(40) CHARACTER SET latin2 NOT NULL check(`EMAIL` like '^[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$'),
    `PERMISSIONS` tinyint(4) NOT NULL,
    primary key(`DATA_ID`)
    
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;



CREATE TABLE IF NOT EXISTS `users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FIRST_NAME` varchar(15) CHARACTER SET latin2 NOT NULL check(`FIRST_NAME` not like '%[^a-zA-Zęóąśłżźćń]%'),
  `LAST_NAME` varchar(30) CHARACTER SET latin2 NOT NULL CHECK(`LAST_NAME` not like '%[^a-zA-ZęóąśłżźćńĘĄÓĄŚŁŻŹĆŃ]%'),
  `AGE` tinyint unsigned not null CHECK(`AGE` between 0 and 101),
  `CITY` varchar(30) CHARACTER SET latin2 NOT NULL,
  `ADDRESS` varchar(30) CHARACTER SET latin2 NOT NULL CHECK(`ADDRESS` like '%[a-zA-ZęóąśłżźćńĘĄÓĄŚŁŻŹĆŃ[:space:]]{1,}'),
  `PHONE` varchar(11) CHARACTER SET latin2 NOT NULL CHECK(length(`PHONE`)=9 or length(`PHONE`)=11),
  `ZIP_CODE` varchar(6) CHARACTER SET latin2 NOT NULL CHECK(`ZIP_CODE` like '[0-9][0-9]-[0-9][0-9][0-9]'),
  `CONFIRMED` varchar(1) CHARACTER SET latin2 NOT NULL DEFAULT 'N' CHECK(`CONFIRMED` IN ('Y','N')),
  `CREATED_AT` datetime NOT NULL,
  `CREDIT_CARD_NUMBER` varchar(16) NULL check(`CREDIT_CARD_NUMBER` like '^([0-9]{16})$'),
  `DELIVERY_ADDRESS` varchar(40) NULL check(`DELIVERY_ADDRESS` like '%[a-zA-ZęóąśłżźćńĘĄÓĄŚŁŻŹĆŃ[:space:]]{1,}'),
  `BANK_ACCOUNT` varchar(26) NULL check(`BANK_ACCOUNT` like '^([0-9]{26})$'),
  `SHIPPING_ADDRESS` varchar(40) NULL check(`SHIPPING_ADDRESS` like '%[a-zA-ZęóąśłżźćńĘĄÓĄŚŁŻŹĆŃ[:space:]]{1,}'),
  `ACCESS_DATA` int(11) UNIQUE NOT NULL,
  PRIMARY KEY (`ID`),
  foreign key(`ACCESS_DATA`) references account_datas(`DATA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;



CREATE table if not exists `categories` (
	`ID_CATEGORY` int(11) not null auto_increment,
    `NAME` varchar(25) not null unique check(`NAME` like '%[a-zA-ZęóąśłżźćńĘĄÓĄŚŁŻŹĆŃ-.]%'),
    `PARENT_CATEGORY` INT(11) not null,
    PRIMARY KEY(`ID_CATEGORY`),
    foreign key(`PARENT_CATEGORY`) references categories(`ID_CATEGORY`)

)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS `items`(
	`ITEM_ID` int(11) not null auto_increment,
    `ITEM_NAME` varchar(30) not null check(`ITEM_NAME` like '%[a-zA-Z0-9ęóąśłżźćńĘĄÓĄŚŁŻŹĆŃ-.]%'),
	`PICTURE_PATH` varchar(200) null check(`PICTURE_PATH` like '^(https?://|www\\.)[\.A-Za-z0-9\-]+\\.[a-zA-Z]{2,4}'),
    `SPECIFICATION` varchar(500) null,
    `STATUS` varchar(50) not null check(`STATUS` IN ('KUPIONY','WYSTAWIONY','WYCOFANY','MAGAZYNOWANY')),
    `PRODUCER` varchar(20) null check(`PRODUCER` not like '%[^a-zA-ZęóąśłżźćńĘĄÓĄŚŁŻŹĆŃ]%'),
	`CONDITION` varchar(20) not null check(`CONDITION` in ('NOWY','UŻYWANY')),
    `TYPE` varchar(15) null,
    `CATEGORY` int(11) not null,
    `ITEM_SELLER` int(11) not null,
    `ITEM_BUYER` int(11) null,
    primary key(`ITEM_ID`),
    foreign key(`CATEGORY`) references categories(`ID_CATEGORY`),
    foreign key(`ITEM_SELLER`) references users(`ID`),
    foreign key(`ITEM_BUYER`) references users(`ID`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;


create table IF NOT exists `auctions` (
	`AUCTION_ID` int(11) not null auto_increment,
    `TITLE` varchar(20) not null check(`TITLE` like '%[a-zA-Z0-9ęóąśłżźćńĘĄÓĄŚŁŻŹĆŃ[:space:]]{1,}'),
    `DESCRIPTION` varchar(1000) not null,
    `BUY_NOW_PRICE` DECIMAL(10,4) null check (`BUY_NOW_PRICE`>0), 
	`ACTIVE` varchar(1) not null default 'N' check (`ACTIVE` IN ('Y','N')),
    `CREATED_AT` datetime not null default NOW(),
    `FINISHED` varchar(1) not null default 'N' check(`FINISHED` in ('Y','N')),
    `FINISHED_AT` datetime null default null,
    `SHIPMENT` varchar(100) not null check(`SHIPMENT` like '([a-zA-Z0-9ęóąśłżźćńĘĄÓĄŚŁŻŹĆŃ[:space:]]){1,}'),
    `WEIGHT` decimal(4,2) not null check(`WEIGHT` >0),
    `SIZE` varchar(14) not null check(`SIZE` like '^([1-9]([0-9]){0,2}x[1-9]([0-9]){0,2}x[1-9]([0-9]){0,2})$'),
    `MINIMAL_PRICE` DECIMAL(10,4) null check (`MINIMAL_PRICE`>0),
    `BEST_PRICE` DECIMAL(10,4) not null check (`BEST_PRICE`>0),
    `ITEM` int(11) not null unique,
    primary key(`AUCTION_ID`),
    foreign key(`ITEM`) references items(`ITEM_ID`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;



CREATE TABLE IF NOT exists `comments` (
	`COMMENT_ID` int(11) not null auto_increment,
    `RECEIVER_ID` int(11) not null,
    `COMMENT` varchar(500) null check(`COMMENT` like '%[a-zA-Z0-9ęóąśłżźćńĘĄÓĄŚŁŻŹĆŃ-.]%'),
    `POSITIVE` varchar(1) not null default 'Y' CHECK(`POSITIVE` IN ('Y','N')),
    `CREATED_AT` datetime not null default NOW(),
    `COMMENTATOR_ID` int(11) not null,
    `AUCTION_COMMENTED` int(11) not null,
    primary key(`COMMENT_ID`),
    foreign key(`COMMENTATOR_ID`) references users(ID),
    foreign key(`RECEIVER_ID`) references users(ID),
    foreign key(`AUCTION_COMMENTED`) references auctions(`AUCTION_ID`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

CREATE table if not EXISTS `bids` (
	`BID_ID` int(11) not null auto_increment,
	`PRICE_OFFERED` DECIMAL(10,4) not null check (`PRICE_OFFERED`>0),
    `CREATED_AT` datetime not null check(`CREATED_AT`<=NOW()),
    `BUYER_ID` int(11) not null,
    `AUCTION` int(11) not null,
    primary key(`BID_ID`),
    foreign key(`BUYER_ID`) references users(`ID`),
    foreign key(`AUCTION`) references auctions(`AUCTION_ID`)    
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;







DELIMITER //
create procedure add_user
(
	IN IN_FIRST_NAME varchar(15),
    IN IN_LAST_NAME VARCHAR(30),
    IN IN_AGE  TINYINT unsigned,
    IN IN_CITY varchar(30),
    in IN_ADDRESS varchar(30),
    IN IN_PHONE varchar(11),
    in IN_ZIP_CODE VARCHAR(6),
    in IN_CONFIRMED varchar(1),
    IN IN_CREATED_AT datetime,
    IN IN_CREDIT_CARD_NUMBER VARCHAR(16),
    IN IN_DELIVERY_ADDRESS varchar(40),
    IN IN_BANK_ACCOUNT varchar(26),
    IN IN_SHIPPING_ADDRESS VARCHAR(40),
    IN IN_LOGIN varchar(35),
    IN IN_HASH_PASSWORD VARCHAR(30),
    IN IN_EMAIL VARCHAR(40),
    IN IN_PERMISSIONS tinyint(4)
)
begin 


DECLARE temp int(11) default 0;
declare exit handler for 1062
BEGIN
ROLLBACK;
SELECT 'Duplicate keys error encountered';
END;

declare exit handler for SQLEXCEPTION
BEGIN
ROLLBACK;
SELECT 'An error has occurred, operation rollbacked and the stored procedure was terminated';
END;

DECLARE EXIT HANDLER FOR SQLSTATE '23000'
BEGIN
ROLLBACK;
SELECT 'SQLSTATE 23000';
END;


	if not exists (select * from account_datas where account_datas.LOGIN=IN_LOGIN) THEN
		INSERT INTO account_datas(LOGIN,HASH_PASSWORD,EMAIL,PERMISSIONS)
				values(IN_LOGIN, IN_HASH_PASSWORD,IN_EMAIL,IN_PERMISSIONS);
				
		SELECT DATA_ID INTO temp from account_datas where account_datas.LOGIN=IN_LOGIN;
        
		IF ((IN_DELIVERY_ADDRESS IS NULL OR IN_CREDIT_CARD_NUMBER IS NULL) and (IN_BANK_ACCOUNT IS NULL OR IN_SHIPPING_ADDRESS IS NULL)) then
			INSERT INTO users(FIRST_NAME, LAST_NAME, AGE, CITY,ADDRESS, PHONE, ZIP_CODE, CONFIRMED, CREATED_AT, CREDIT_CARD_NUMBER, DELIVERY_ADDRESS, BANK_ACCOUNT, SHIPPING_ADDRESS,ACCESS_DATA)
				values(IN_FIRST_NAME,IN_LAST_NAME, IN_AGE, IN_CITY,IN_ADDRESS, IN_PHONE, IN_ZIP_CODE, IN_CONFIRMED, IN_CREATED_AT,null,null,null,null,temp);
                
		elseif ((IN_DELIVERY_ADDRESS IS NOT NULL AND IN_CREDIT_CARD_NUMBER IS NOT NULL) and (IN_BANK_ACCOUNT IS NULL OR IN_SHIPPING_ADDRESS IS NULL)) then
			INSERT INTO users(FIRST_NAME, LAST_NAME, AGE, CITY,ADDRESS, PHONE, ZIP_CODE, CONFIRMED, CREATED_AT, CREDIT_CARD_NUMBER, DELIVERY_ADDRESS, BANK_ACCOUNT, SHIPPING_ADDRESS,ACCESS_DATA)
				values(IN_FIRST_NAME,IN_LAST_NAME, IN_AGE, IN_CITY,IN_ADDRESS, IN_PHONE, IN_ZIP_CODE, IN_CONFIRMED, IN_CREATED_AT,IN_CREDIT_CARD_NUMBER,IN_DELIVERY_ADDRESS,null,null,temp);
			
        elseif ((IN_DELIVERY_ADDRESS IS NULL OR IN_CREDIT_CARD_NUMBER IS NULL) and (IN_BANK_ACCOUNT IS NOT NULL AND IN_SHIPPING_ADDRESS IS NOT NULL)) then
			INSERT INTO users(FIRST_NAME, LAST_NAME, AGE, CITY,ADDRESS, PHONE, ZIP_CODE, CONFIRMED, CREATED_AT, CREDIT_CARD_NUMBER, DELIVERY_ADDRESS, BANK_ACCOUNT, SHIPPING_ADDRESS,ACCESS_DATA)
				values(IN_FIRST_NAME,IN_LAST_NAME, IN_AGE, IN_CITY, IN_PHONE,IN_ADDRESS, IN_ZIP_CODE, IN_CONFIRMED, IN_CREATED_AT,null,null,IN_BANK_ACCOUNT,IN_SHIPPING_ADDRESS,temp);
			
        else
			INSERT INTO users(FIRST_NAME, LAST_NAME, AGE, CITY,ADDRESS, PHONE, ZIP_CODE, CONFIRMED, CREATED_AT, CREDIT_CARD_NUMBER, DELIVERY_ADDRESS, BANK_ACCOUNT, SHIPPING_ADDRESS,ACCESS_DATA)
				values(IN_FIRST_NAME,IN_LAST_NAME, IN_AGE, IN_CITY,IN_ADDRESS, IN_PHONE, IN_ZIP_CODE, IN_CONFIRMED, IN_CREATED_AT,IN_CREDIT_CARD_NUMBER,IN_DELIVERY_ADDRESS,IN_BANK_ACCOUNT,IN_SHIPPING_ADDRESS,temp);
			
        END IF;
	END IF;
	
end //

DELIMITER ;

DELIMITER //
create procedure add_comment
(
	IN IN_RECEIVER_ID int(11),
    IN IN_COMMENT varchar(500),
    IN IN_POSITIVE varchar(1),
    IN IN_CREATED_AT datetime,
    IN IN_COMMENTATOR_ID int(11),
    IN IN_AUCTION_COMMENTED int(11)

)
begin
declare exit handler for 1062
BEGIN
ROLLBACK;
SELECT 'Duplicate keys error encountered';
END;

declare exit handler for SQLEXCEPTION
BEGIN
ROLLBACK;
SELECT 'An error has occurred, operation rollbacked and the stored procedure was terminated';
END;

DECLARE EXIT HANDLER FOR SQLSTATE '23000'
BEGIN
ROLLBACK;
SELECT 'SQLSTATE 23000';
END;

IF exists (SELECT * FROM auctions where auctions.AUCTION_ID=IN_AUCTION_COMMENTED and auctions.FINISHED='Y')
	insert into comments(RECEIVER_ID,COMMENT, POSITIVE, CREATED_AT,COMMENTATOR_ID, AUCTION_COMMENTED)
		values(IN_RECEIVER_ID, IN_COMMENT, IN_POSITIVE, IN_CREATED_AT, IN_COMMENTATOR_ID, IN_AUCTION_COMMENTED);
else
	select 'That auction doesn\'t exist or isn\'t finished';
end if;
end //

DELIMITER ;

DELIMITER //
CREATE procedure add_category
(
	IN IN_NAME varchar(25),
    IN IN_PARENT int(11)

)
begin

declare exit handler for 1062
BEGIN
ROLLBACK;
SELECT 'Duplicate keys error encountered';
END;

declare exit handler for SQLEXCEPTION
BEGIN
ROLLBACK;
SELECT 'An error has occurred, operation rollbacked and the stored procedure was terminated';
END;

DECLARE EXIT HANDLER FOR SQLSTATE '23000'
BEGIN
ROLLBACK;
SELECT 'SQLSTATE 23000';
END;
if exists (SELECT * FROM categories where categories.ID_CATEGORY=IN_PARENT) then
	INSERT INTO categories(NAME,PARENT_CATEGORY)
		values(IN_NAME, IN_PARENT);
        
        
elseif IN_PARENT IS NULL THEN
	INSERT INTO categories(NAME,PARENT_CATEGORY)
		values(IN_NAME, IN_PARENT);
else
	select 'Parent category not found';

end if;

end //

DELIMITER ;



DELIMITER //

CREATE procedure add_auction
(
	 IN IN_TITLE varchar(20),
     IN IN_DESCRIPTION VARCHAR(1000),
     IN IN_BUY_NOW_PRICE decimal(10,4),
     IN IN_ACTIVE varchar(1),
     IN IN_CREATED_AT datetime,
     IN FINISHED varchar(1),
     IN FINISHED_AT datetime,
     IN IN_SHIPMENT varchar(100),
     IN IN_WEIGHT decimal(4,2),
     IN IN_SIZE varchar(14),
     IN IN_MINIMAL_PRICE decimal(10,4),
     IN IN_BEST_PRICE decimal(10,4),
     IN IN_ITEM int(11)
)
begin
declare exit handler for 1062
BEGIN
ROLLBACK;
SELECT 'Duplicate keys error encountered';
END;

declare exit handler for SQLEXCEPTION
BEGIN
ROLLBACK;
SELECT 'An error has occurred, operation rollbacked and the stored procedure was terminated';
END;

DECLARE EXIT HANDLER FOR SQLSTATE '23000'
BEGIN
ROLLBACK;
SELECT 'SQLSTATE 23000';
END;

if exists (SELECT * from items where items.ITEM_ID=IN_ITEM) then
	-- TEN IF PONIŻEJ CHYBA POWINIEN BYĆ W TRIGERRACH
	if (IN_CREATED_AT < IN_FINISHED_AT) then
		if (IN_BUY_NOW_PRICE is not null and IN_MINIMAL_PRICE is not null and IN_BUY_NOW_PRICE > MINIMAL_PRICE) then
			insert into auctions(TITLE, DESCRIPTION, BUY_NOW_PRICE, ACTIVE, FINISHED, FINISHED_AT, SHIPMENT, WEIGHT, SIZE, MINIMAL_PRICE,BEST_PRICE,ITEM)
				VALUES(IN_TITLE, IN_DESCRIPTION, IN_BUY_NOW_PRICE, IN_ACTIVE, IN_FINISHED, IN_FINISHED_AT, IN_SHIPMENT, IN_WEIGHT, IN_SIZE, IN_MINIMAL_PRICE,IN_BEST_PRICE,IN_ITEM);
			update items set items.STATUS='WYSTAWIONY' where items.ITEM_ID=IN_ITEM limit 1;
            
		elseif (IN_BUY_NOW_PRICE is null and IN_MINIMAL_PRICE is not null) then
			insert into auctions(TITLE, DESCRIPTION, BUY_NOW_PRICE, ACTIVE, FINISHED, FINISHED_AT, SHIPMENT, WEIGHT, SIZE, MINIMAL_PRICE,BEST_PRICE,ITEM)
				VALUES(IN_TITLE, IN_DESCRIPTION, NULL, IN_ACTIVE, IN_FINISHED, IN_FINISHED_AT, IN_SHIPMENT, IN_WEIGHT, IN_SIZE, IN_MINIMAL_PRICE,IN_BEST_PRICE,IN_ITEM);
                
			update items set items.STATUS='WYSTAWIONY' where items.ITEM_ID=IN_ITEM limit 1;
            
		elseif (IN_BUY_NOW_PRICE is not null and IN_MINIMAL_PRICE is null) then
			insert into auctions(TITLE, DESCRIPTION, BUY_NOW_PRICE, ACTIVE, FINISHED, FINISHED_AT, SHIPMENT, WEIGHT, SIZE, MINIMAL_PRICE,BEST_PRICE,ITEM)
				VALUES(IN_TITLE, IN_DESCRIPTION, NULL, IN_ACTIVE, IN_FINISHED, IN_FINISHED_AT, IN_SHIPMENT, IN_WEIGHT, IN_SIZE, IN_MINIMAL_PRICE,IN_BEST_PRICE,IN_ITEM);
                
			update items set items.STATUS='WYSTAWIONY' where items.ITEM_ID=IN_ITEM limit 1;
        else
			insert into auctions(TITLE, DESCRIPTION, BUY_NOW_PRICE, ACTIVE, FINISHED, FINISHED_AT, SHIPMENT, WEIGHT, SIZE, MINIMAL_PRICE,BEST_PRICE,ITEM)
				VALUES(IN_TITLE, IN_DESCRIPTION, NULL, IN_ACTIVE, IN_FINISHED, IN_FINISHED_AT, IN_SHIPMENT, IN_WEIGHT, IN_SIZE, NULL,IN_BEST_PRICE,IN_ITEM);
                
			update items set items.STATUS='WYSTAWIONY' where items.ITEM_ID=IN_ITEM limit 1;
		end if;
	else 
		select 'FINISHED_AT date is older than CREATED_AT date'; 
	END IF;
END IF;


end //

DELIMITER ;


DELIMITER //
create procedure add_item
(
	IN IN_ITEM_NAME varchar(30),
    IN IN_PICTURE_PATH VARCHAR(200),
    IN IN_SPECIFICATION varchar(500),
    IN IN_STATUS varchar(50),
    IN IN_PRODUCER VARCHAR(20),
    IN IN_CONDITION varchar(20),
    IN IN_TYPE varchar(15),
    IN IN_CATEGORY INT(11),
    IN IN_ITEM_SELLER INT(11)
)
begin
declare exit handler for 1062
BEGIN
ROLLBACK;
SELECT 'Duplicate keys error encountered';
END;

declare exit handler for SQLEXCEPTION
BEGIN
ROLLBACK;
SELECT 'An error has occurred, operation rollbacked and the stored procedure was terminated';
END;

DECLARE EXIT HANDLER FOR SQLSTATE '23000'
BEGIN
ROLLBACK;
SELECT 'SQLSTATE 23000';
END;

SET IN_STATUS='MAGAZYNOWANY';

if exists (select * from users where users.ID=IN_ITEM_SELLER) then

	insert into items(ITEM_NAME, PICTURE_PATH, SPECIFICATION, STATUS, PRODUCER, items.CONDITION, items.TYPE, CATEGORY, ITEM_SELLER, ITEM_BUYER)
		VALUES(IN_ITEM_NAME, IN_PICTURE_PATH, IN_SPECIFICATION, IN_STATUS, IN_PRODUCER, IN_CONDITION, IN_TYPE, IN_CATEGORY, IN_ITEM_SELLER, NULL);
else
	select 'Seller not found';

end if;

end //

DELIMITER ;


DELIMITER //
create procedure add_bid
(
	IN IN_PRICE_OFFERED DECIMAL(10,4),
    IN IN_CREATED_AT DATETIME,
    IN IN_BUYER_ID INT(11),
    IN IN_AUCTION INT(11)
)
BEGIN 
declare exit handler for 1062
BEGIN
ROLLBACK;
SELECT 'Duplicate keys error encountered';
END;

declare exit handler for SQLEXCEPTION
BEGIN
ROLLBACK;
SELECT 'An error has occurred, operation rollbacked and the stored procedure was terminated';
END;

DECLARE EXIT HANDLER FOR SQLSTATE '23000'
BEGIN
ROLLBACK;
SELECT 'SQLSTATE 23000';
END;
if (IN_PRICE_OFFERED > (select auctions.BEST_PRICE from auctions where auctions.AUCTION_ID=IN_AUCTION)) AND EXISTS (SELECT * FROM users WHERE users.ID=IN_BUYER_ID and users.DELIVERY_ADDRESS is not null and users.CREDIT_CARD_NUMBER is not null) then
	insert into bids(PRICE_OFFERED, CREATED_AT, BUYER_ID, AUCTION)
		values(PRICE_OFFERED, CREATED_AT, BUYER_ID, AUCTION)

end if;


END //
DELIMITER ;


call create_user('Alten','M',18,'Krakow','Jana Pawła 21','636900120','62-640','Y',now(),'1234567891234567','hfsahdgahsjdgh', null,null,'Altenfrost','heheh','janpawel@gmail.com',1)
select * from users




