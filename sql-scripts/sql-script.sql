CREATE DATABASE  IF NOT EXISTS `employee_directory`;
USE `employee_directory`;

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL DEFAULT NULL,
  `last_name` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `image` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 115
DEFAULT CHARACTER SET = latin1;

DROP TABLE IF EXISTS `sales`;

CREATE TABLE `sales` (
  `sale_id` INT NOT NULL AUTO_INCREMENT,
  `employee_id` INT NULL DEFAULT NULL,
  `date` DATE NOT NULL,
  `amount` DECIMAL(10,2) NOT NULL,
  `full_name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`sale_id`),
  INDEX `sales_ibfk_1` (`employee_id` ASC),
  CONSTRAINT `sales_ibfk_1`
    FOREIGN KEY (`employee_id`)
    REFERENCES `employee_directory`.`employee` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 89;

DROP TABLE IF EXISTS `prospects`;

CREATE TABLE `prospects` (
  `prospect_id` INT NOT NULL AUTO_INCREMENT,
  `employee_id` INT NULL DEFAULT NULL,
  `full_name` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(65) NULL DEFAULT NULL,
  PRIMARY KEY (`prospect_id`),
  INDEX `sales_ibfk_2` (`employee_id` ASC),
  CONSTRAINT `sales_ibfk_2`
    FOREIGN KEY (`employee_id`)
    REFERENCES `employee_directory`.`employee` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 48;

DROP TABLE IF EXISTS `prospect_links`;

CREATE TABLE `prospect_links` (
  `link_id` INT NOT NULL AUTO_INCREMENT,
  `prospect_id` INT NULL DEFAULT NULL,
  `linkedin` VARCHAR(2083) NOT NULL,
  `instagram` VARCHAR(2083) NOT NULL,
  `facebook` VARCHAR(2083) NOT NULL,
  PRIMARY KEY (`link_id`),
  INDEX `links_ibfk_1` (`prospect_id` ASC),
  CONSTRAINT `links_ibfk_1`
    FOREIGN KEY (`prospect_id`)
    REFERENCES `employee_directory`.`prospects` (`prospect_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 89;

DROP VIEW IF EXISTS monthly_sales;

CREATE VIEW monthly_sales AS
    SELECT e.employee_id, d.day, m.month, y.year, SUM(s.amount) AS total_sales, e.employee_id * 10000 + m.month * 100 + y.year + d.day AS id
    FROM (SELECT DISTINCT employee_id FROM sales) e
    CROSS JOIN (SELECT DISTINCT DAY(date) AS day FROM sales) d
    CROSS JOIN (SELECT DISTINCT MONTH(date) AS month FROM sales) m
    CROSS JOIN (SELECT DISTINCT YEAR(date) AS year FROM sales) y
    INNER JOIN sales s ON s.employee_id = e.employee_id AND DAY(s.date) = d.day AND MONTH(s.date) = m.month AND YEAR(s.date) = y.year
    GROUP BY e.employee_id, d.day, m.month, y.year;

DROP VIEW IF EXISTS yearly_sales;

CREATE VIEW yearly_sales AS
    SELECT e.employee_id, s.full_name, m.month, y.year, SUM(s.amount) AS total_sales, e.employee_id * y.year + 1000 * m.month AS id
    FROM (SELECT DISTINCT employee_id FROM sales) e
    CROSS JOIN (SELECT DISTINCT MONTH(date) AS month FROM sales) m
    CROSS JOIN (SELECT DISTINCT YEAR(date) AS year FROM sales) y
    INNER JOIN sales s ON s.employee_id = e.employee_id AND MONTH(s.date) = m.month AND YEAR(s.date) = y.year
    GROUP BY e.employee_id, m.month, y.year;
  
DROP VIEW IF EXISTS total_yearly_sales;
  
CREATE VIEW total_yearly_sales AS
    SELECT e.employee_id, s.full_name, y.year, SUM(s.amount) AS total_sales, e.employee_id * y.year AS id
    FROM (SELECT DISTINCT employee_id FROM sales) e
    CROSS JOIN (SELECT DISTINCT YEAR(date) AS year FROM sales) y
    INNER JOIN sales s ON s.employee_id = e.employee_id AND YEAR(s.date) = y.year
    GROUP BY e.employee_id, y.year;


USE `employee_directory`;

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` char(68) NOT NULL,
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `users`
--
-- NOTE: The passwords are encrypted using BCrypt
--
-- A generation tool is avail at: https://www.luv2code.com/generate-bcrypt-password
--
-- Default passwords here are: test123
--

INSERT INTO `users` 
VALUES 
('employee','{bcrypt}$2a$12$8oVA77lBL.ZfMwfyAZ66iu59ylWMVcBbkIaQSYpLh7nEL67xpKL9m',1),
('manager','{bcrypt}$2a$12$8oVA77lBL.ZfMwfyAZ66iu59ylWMVcBbkIaQSYpLh7nEL67xpKL9m',1),
('admin','{bcrypt}$2a$12$8oVA77lBL.ZfMwfyAZ66iu59ylWMVcBbkIaQSYpLh7nEL67xpKL9m',1);


--
-- Table structure for table `authorities`
--

CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities4_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities4_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `authorities`
--

INSERT INTO `authorities` 
VALUES 
('employee','ROLE_EMPLOYEE'),
('manager','ROLE_EMPLOYEE'),
('manager','ROLE_MANAGER'),
('admin','ROLE_EMPLOYEE'),
('admin','ROLE_MANAGER'),
('admin','ROLE_ADMIN');