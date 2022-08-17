DROP SCHEMA IF EXISTS patientdb;

CREATE SCHEMA patientdb;

use patientdb;

create table IF NOT EXISTS `patient`
(
    `id`       bigint       not null auto_increment,
    `family`    varchar(255) not null,
    `given`     varchar(255),
    `dob` datetime,
    `sex` varchar(2),
    `address` varchar(255),
    `phone` varchar(255),
    PRIMARY KEY (`id`)
);

INSERT INTO patient (id, family, given, dob, sex, address, phone)
VALUES (1, 'TestNone', 'Test', '1966-12-31 06:41:05', 'F', '1 Brookside St', '100-222-3333');
INSERT INTO patient (id, family, given, dob, sex, address, phone)
VALUES (2, 'TestBorderline', 'Test', '1945-06-24 16:11:25', 'M', '2 High St', '200-333-4444');
INSERT INTO patient (id, family, given, dob, sex, address, phone)
VALUES (3, 'TestInDanger', 'Test', '2004-06-18 05:43:32', 'M', '3 Club Road', '300-444-5555');
INSERT INTO patient (id, family, given, dob, sex, address, phone)
VALUES (4, 'TestEarlyOnset', 'Test', '2002-06-28 10:11:27', 'F', '4 Valley Dr', '400-555-6666');
INSERT INTO patient (id, family, given, dob, sex, address, phone)
VALUES (5, 'TestNone', 'Test', '2002-06-28 10:11:27', 'F', '4 Valley Dr', '400-555-6666');
INSERT INTO patient (id, family, given, dob, sex, address, phone)
VALUES (6, 'TestNone', 'Test', '2002-06-28 10:11:27', 'F', '4 Valley Dr', '400-555-6666');
INSERT INTO patient (id, family, given, dob, sex, address, phone)
VALUES (7, 'TestNone', 'Test', '2002-06-28 10:11:27', 'F', '4 Valley Dr', '400-555-6666');