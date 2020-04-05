-- One admin user, named admin1 with password 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
-- Ten owner users, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner2','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner3','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner4','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner5','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner6','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner7','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner8','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner9','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('owner10','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner1','owner');
INSERT INTO authorities VALUES ('owner2','owner');
INSERT INTO authorities VALUES ('owner3','owner');
INSERT INTO authorities VALUES ('owner4','owner');
INSERT INTO authorities VALUES ('owner5','owner');
INSERT INTO authorities VALUES ('owner6','owner');
INSERT INTO authorities VALUES ('owner7','owner');
INSERT INTO authorities VALUES ('owner8','owner');
INSERT INTO authorities VALUES ('owner9','owner');
INSERT INTO authorities VALUES ('owner10','owner');
-- One guide user, named guide1 with password guid3
INSERT INTO users(username,password,enabled) VALUES ('guide1','guid3',TRUE);
INSERT INTO authorities VALUES ('guide1','guide');
-- Two judge users, named judge1 and judge2 with password jugd3
INSERT INTO users(username,password,enabled) VALUES ('judge1','jugd3',TRUE);
INSERT INTO authorities VALUES ('judge1','judge');
INSERT INTO users(username,password,enabled) VALUES ('judge2','jugd3',TRUE);
INSERT INTO authorities VALUES ('judge2','judge');
-- A veterinary test user but does not have any functionality
INSERT INTO users(username,password,enabled) VALUES ('vet1','ve3t1',FALSE);
INSERT INTO authorities VALUES ('vet1','vet');

INSERT INTO guides(id,first_name,last_name,address,city,telephone,username)  VALUES (1, 'Carmelo', 'Justice', '89 W. Wings St.', 'Sky City', '6081251012', 'guide1');

--JUDGE--
INSERT INTO judges(id,first_name,last_name,address,city,telephone,username)  VALUES (1, 'Michael', 'Dredd', '60 W. Liberty St.', 'Mega City', '6081251023', 'judge1');
INSERT INTO judges(id,first_name,last_name,address,city,telephone,username)  VALUES (2, 'Travis', 'Dacon', '10 W. Liberty St.', 'St Destroy', '6085554523', 'judge2');

INSERT INTO types VALUES (1, 'Cat');
INSERT INTO types VALUES (2, 'Dog');
INSERT INTO types VALUES (3, 'Lizard');
INSERT INTO types VALUES (4, 'Snake');
INSERT INTO types VALUES (5, 'Bird');
INSERT INTO types VALUES (6, 'Hamster');

INSERT INTO owners(id,first_name,last_name,address,city,telephone,username)  VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners(id,first_name,last_name,address,city,telephone,username) VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner2');
INSERT INTO owners(id,first_name,last_name,address,city,telephone,username) VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner3');
INSERT INTO owners(id,first_name,last_name,address,city,telephone,username) VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner4');
INSERT INTO owners(id,first_name,last_name,address,city,telephone,username) VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner5');
INSERT INTO owners(id,first_name,last_name,address,city,telephone,username)VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner6');
INSERT INTO owners(id,first_name,last_name,address,city,telephone,username) VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner7');
INSERT INTO owners(id,first_name,last_name,address,city,telephone,username) VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner8');
INSERT INTO owners(id,first_name,last_name,address,city,telephone,username) VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner9');
INSERT INTO owners(id,first_name,last_name,address,city,telephone,username) VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner10');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

INSERT INTO categories(id,name) VALUES (1,'Dog Puller');
INSERT INTO categories(id,name) VALUES (2,'Speed');
INSERT INTO categories(id,name) VALUES (3,'Obstacles');

INSERT INTO tournaments VALUES (1, 'Winbendoll tournament 3', '2013-01-04', '2013-01-04', 'Sevilla', 100.00, '€', '2013-01-04', 1, null, null, 1 );
INSERT INTO tournaments VALUES (2, 'Winbendoll tournament 4', '2014-01-04', '2014-01-04', 'Sevilla', 100.00, '€', '2014-01-04', 1, null, null, 1 );
INSERT INTO tournaments VALUES (3, 'Winbendoll tournament 5', '2015-01-04', '2015-01-04', 'Sevilla', 100.00, '€', '2015-01-04', 1, null, null, 1 );
INSERT INTO tournaments VALUES (4, 'Winbendoll tournament 7', '2020-05-01', '2020-05-22', 'Sevilla', 100.00, '€', '2020-05-20', 1, null, null, 1 );
INSERT INTO tournaments VALUES (5, 'Winbendoll tournament 8', '2020-05-01', '2020-05-22', 'Sevilla', 100.00, '€', '2020-05-20', 1, null, null, 1 );
INSERT INTO tournaments VALUES (6, 'Winbendoll tournament 9', '2020-05-01', '2020-05-22', 'Sevilla', 100.00, '€', '2020-05-20', 1, null, null, 1 );

INSERT INTO applications(id, status, moment, credit_card, owner_id, pet_id, tournament_id) VALUES (1,'PENDING', '2020-07-25', '352571631239294', 1, 1, 4);
INSERT INTO applications(id, status, moment, credit_card, owner_id, pet_id, tournament_id)  VALUES (2,'APPROVED', '2020-05-27', '352571631239294', 1, 1, 2);
INSERT INTO applications(id, status, moment, credit_card, owner_id, pet_id, tournament_id)  VALUES (3,'PENDING', '2020-07-25', '363017956100486', 2, 2, 1);
INSERT INTO applications(id, status, moment, credit_card, owner_id, pet_id, tournament_id)  VALUES (4,'REJECTED', '2020-05-27', '363017956100486', 2, 2, 2);
INSERT INTO applications(id, status, moment, credit_card, owner_id, pet_id, tournament_id)  VALUES (5,'PENDING', '2020-07-25', '379254492621186', 3, 3, 1);

INSERT INTO fields (id, name, photoURL, lenght, breadth)  VALUES (1, 'Map 1', 'https://helgehimleagilitycourses.files.wordpress.com/2019/09/dm-jump-team.gif?w=676', 200.00, 120.00);
INSERT INTO fields (id, name, photoURL, lenght, breadth)  VALUES (2, 'Map 10', 'https://helgehimleagilitycourses.files.wordpress.com/2019/09/dm-jump-team.gif?w=676', 100.00, 300.00);

INSERT  INTO vets VALUES (1, '563 Friendly St.', 'Boston', 'James', 'Carter', '6085551093','vet1');
INSERT  INTO vets VALUES (2, '638 Cardinal Ave.', 'York', 'Helen', 'Leary' , '6089552765', 'vet1');
INSERT  INTO vets VALUES (3, '2387 S. Fair Way', 'Madrid', 'Linda', 'Douglas', '6095552654', 'vet1');
INSERT  INTO vets VALUES (4, '2749 Blackhawk Trail' ,'Barcelona', 'Rafael', 'Ortega', '6085855487', 'vet1');
INSERT  INTO vets VALUES (5, '105 N. Lake St', 'Asturias', 'Henry', 'Stevens', '6985553898', 'vet1');
INSERT  INTO vets VALUES (6, '2335 Independence La', 'Munich', 'Sharon', 'Jenkins','6087555387', 'vet1');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT  INTO vet_specialties VALUES (2, 1);
INSERT  INTO vet_specialties VALUES (3, 2);
INSERT  INTO vet_specialties VALUES (3, 3);
INSERT  INTO vet_specialties VALUES (4, 2);
INSERT  INTO vet_specialties VALUES (5, 1);

