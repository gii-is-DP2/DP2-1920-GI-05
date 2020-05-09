-- ADMIN --
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
INSERT INTO admins(id,first_name,last_name,address,city,telephone,username)  VALUES (1, 'Marc', 'Oliver', '128 W. Santana St.', 'Detrroit', '6085551043', 'admin1');
-- OWNERS
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

--GUIDES--
INSERT INTO users(username,password,enabled) VALUES ('guide1','guid3',TRUE);
INSERT INTO authorities VALUES ('guide1','guide');

INSERT INTO guides(id,first_name,last_name,address,city,telephone,username)  VALUES (1, 'Carmelo', 'Justice', '89 W. Wings St.', 'Sky City', '6081251012', 'guide1');

--JUDGES--
INSERT INTO users(username,password,enabled) VALUES ('judge1','jugd3',TRUE);
INSERT INTO authorities VALUES ('judge1','judge');
INSERT INTO users(username,password,enabled) VALUES ('judge2','jugd3',TRUE);
INSERT INTO authorities VALUES ('judge2','judge');

INSERT INTO judges(id,first_name,last_name,address,city,telephone,username)  VALUES (1, 'Michael', 'Dredd', '60 W. Liberty St.', 'Mega City', '6081251023', 'judge1');
INSERT INTO judges(id,first_name,last_name,address,city,telephone,username)  VALUES (2, 'Travis', 'Dacon', '10 W. Liberty St.', 'St Destroy', '6085554523', 'judge2');


--FIELDS--
INSERT INTO fields (id, name, photoURL, lenght, width)  VALUES (1, 'Map 1', 'https://helgehimleagilitycourses.files.wordpress.com/2019/09/dm-jump-team.gif?w=676', 200.00, 120.00);
INSERT INTO fields (id, name, photoURL, lenght, width)  VALUES (2, 'Map 10', 'https://data.agilitynerd.com/images/courses/robertson001.jpg', 100.00, 300.00);


--PET TYPES--
INSERT INTO types VALUES (1, 'Cat');
INSERT INTO types VALUES (2, 'Dog');
INSERT INTO types VALUES (3, 'Lizard');
INSERT INTO types VALUES (4, 'Snake');
INSERT INTO types VALUES (5, 'Bird');
INSERT INTO types VALUES (6, 'Hamster');
INSERT INTO types VALUES (7, 'Horse');


--CATEGORIES--
INSERT INTO categories(id,name) VALUES (1,'Agility');
INSERT INTO categories(id,name) VALUES (2,'Speed');
INSERT INTO categories(id,name) VALUES (3,'Obstacles');
INSERT INTO categories(id,name) VALUES (4,'Beauty');
INSERT INTO categories(id,name) VALUES (5,'Obedience');
INSERT INTO categories(id,name) VALUES (6,'Disc');


--TOURNAMENTS--
INSERT INTO tournaments(id, name, apply_date, start_date, end_date, location, amount, currency , category_id, pet_type_id, field_id, judge_id) 
VALUES (1, 'Cats beauty contest 2019', '2019-10-10', '2019-12-11',  '2019-12-13', 'Sevilla', 120.00, '€', 4, 1, 1, 1); -- old cats tournament --
INSERT INTO tournaments(id, name, apply_date, start_date, end_date, location, amount, currency , category_id, pet_type_id, field_id, judge_id) 
VALUES (2, 'Dogs obedience contest 2019', '2019-12-04', '2019-01-05',  '2019-01-07', 'Sevilla', 250.00, '€', 5, 2, 2, 1); -- old dogs tournament -- 
INSERT INTO tournaments(id, name, apply_date, start_date, end_date, location, amount, currency , category_id, pet_type_id, field_id, judge_id) 
VALUES (3, 'Cats  agility tournament 2020', '2020-11-10', '2020-12-01',  '2020-12-03', 'Sevilla', 100.00, '€', 1, 1, null, null); -- new cats tournament 1 --
INSERT INTO tournaments(id, name, apply_date, start_date, end_date, location, amount, currency , category_id, pet_type_id, field_id, judge_id) 
VALUES (4, 'Dogs speed tournament 2020 ', '2020-11-10', '2020-12-01',  '2020-12-03',  'Sevilla', 100.00, '€', 2, 2, null, null); -- new dogs tournament 1 --
INSERT INTO tournaments(id, name, apply_date, start_date, end_date, location, amount, currency , category_id, pet_type_id, field_id, judge_id) 
VALUES (5, 'Hamster obstacle tournament 2020', '2020-11-10', '2020-12-01',  '2020-12-03',  'Sevilla', 100.00, '€', 3, 6, null, null); -- new hamsters tournament --
INSERT INTO tournaments(id, name, apply_date, start_date, end_date, location, amount, currency , category_id, pet_type_id, field_id, judge_id) 
VALUES (6, 'Cats obedience tournament 2020', '2020-11-10', '2020-12-01',  '2020-12-03',  'Sevilla', 100.00, '€', 5, 1, null, null); -- new cats tournament 2 --
INSERT INTO tournaments(id, name, apply_date, start_date, end_date, location, amount, currency , category_id, pet_type_id, field_id, judge_id) 
VALUES (7, 'Dog puller  tournament 2020', '2020-11-10', '2020-12-01',  '2020-12-03',  'Sevilla', 100.00, '€', 6, 2, null, null); -- new dogs tournament 2 --
INSERT INTO tournaments(id, name, apply_date, start_date, end_date, location, amount, currency , category_id, pet_type_id, field_id, judge_id) 
VALUES (8, 'Horses speed contest 2020', '2020-11-10', '2020-12-01',  '2020-12-03', 'Sevilla', 100.00, '€', 1, 7, null, null); -- speed horses tournament --
INSERT INTO tournaments(id, name, apply_date, start_date, end_date, location, amount, currency , category_id, pet_type_id, field_id, judge_id) 
VALUES (9, 'Lovebirds speed contest  2020', '2020-11-10', '2020-12-01',  '2020-12-03',  'Sevilla', 100.00, '€', 2, 5, null, null); -- speed birds tournament --
INSERT INTO tournaments(id, name, apply_date, start_date, end_date, location, amount, currency , category_id, pet_type_id, field_id, judge_id) 
VALUES (10, 'Lizards  speed contest 2020', '2020-11-10', '2020-12-01',  '2020-12-03',  'Sevilla', 100.00, '€', 2, 3, null, null); -- speed lizards tournament --

--PETS--
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);  -- cat from owner1 --
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 1); -- hamster from owner1 --
INSERT INTO pets(id,name,birth_date,type_id,owner_id,guide_id) VALUES (3, 'Rosy', '2011-04-17', 2, 2, 1); -- dog from owner2 --
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 5, 2); -- bird from owner2 --
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 3); -- lizard from owner3 --
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 3); -- snake from owner3 --
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 4, 4); -- snake from owner4 --
INSERT INTO pets(id,name,birth_date,type_id,owner_id,guide_id) VALUES (8, 'Max', '2012-09-04', 2, 4,1); -- dog from owner4 --
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Garp', '2011-08-06', 5, 5); -- bird from owner5 --
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 3, 5); -- snake from owner5 --
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 6, 6); -- hamster from owner6 -- 
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 1, 6); -- cat from owner6 -- 
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 2, 7); -- dog from owner 7 --
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (14, 'Drummond', '2015-09-04', 7, 8); -- horse from owner8 --
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (15, 'JoJo', '2017-06-12', 7, 9); -- horse from owner9 --

--APPLICATIONS--
INSERT INTO applications(id, status, moment, credit_card, owner_id, pet_id, tournament_id) -- old cat accepted pplication from owner1 --
VALUES (1,'ACCEPTED', '2019-09-22', '352571631239294', 1, 1, 1);
INSERT INTO applications(id, status, moment, credit_card, owner_id, pet_id, tournament_id) -- old dog accepted application from owner2 --
VALUES (2,'ACCEPTED', '2019-09-18', '363017956100486', 2, 3, 2);
INSERT INTO applications(id, status, moment, credit_card, owner_id, pet_id, tournament_id)  -- new hamster pending application from owner1 --
VALUES (3,'PENDING', '2020-11-01', '352571631239294', 1, 2, 5);
INSERT INTO applications(id, status, moment, credit_card, owner_id, pet_id, tournament_id)  -- new bird pending application from owner 2 --
VALUES (4,'PENDING', '2020-11-02', '363017956100486', 2, 4, 9);
INSERT INTO applications(id, status, moment, credit_card, owner_id, pet_id, tournament_id)  -- new lizard rejected application from owner3 -- 
VALUES (5,'REJECTED', '2020-11-03', '379254492621186', 3, 5, 10);
INSERT INTO applications(id, status, moment, credit_card, owner_id, pet_id, tournament_id)  -- new cat accepted application from owwer6 -- 
VALUES (6,'ACCEPTED', '2019-09-20', '379254492621186', 6, 12, 1);

--VETS--
INSERT INTO users(username,password,enabled) VALUES ('vet1','ve3t1',FALSE);
INSERT INTO authorities VALUES ('vet1','vet');

INSERT  INTO vets VALUES (1, '563 Friendly St.', 'Boston', 'James', 'Carter', '6085551093','vet1');
INSERT  INTO vets VALUES (2, '638 Cardinal Ave.', 'York', 'Helen', 'Leary' , '6089552765', 'vet1');
INSERT  INTO vets VALUES (3, '2387 S. Fair Way', 'Madrid', 'Linda', 'Douglas', '6095552654', 'vet1');
INSERT  INTO vets VALUES (4, '2749 Blackhawk Trail' ,'Barcelona', 'Rafael', 'Ortega', '6085855487', 'vet1');
INSERT  INTO vets VALUES (5, '105 N. Lake St', 'Asturias', 'Henry', 'Stevens', '6985553898', 'vet1');
INSERT  INTO vets VALUES (6, '2335 Independence La', 'Munich', 'Sharon', 'Jenkins','6087555387', 'vet1');

--VET SPECIALITIES--
INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT  INTO vet_specialties VALUES (2, 1);
INSERT  INTO vet_specialties VALUES (3, 2);
INSERT  INTO vet_specialties VALUES (3, 3);
INSERT  INTO vet_specialties VALUES (4, 2);
INSERT  INTO vet_specialties VALUES (5, 1);

--VISITS--
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

--REPORTS--
INSERT INTO reports(id, points, comments, judge_id, tournament_id, pet)  -- Report from judge 1, tournament 1, pet 1 --
VALUES (1, 20, 'Sin comentarios', 1, 1, 1);
INSERT INTO reports(id, points, comments, judge_id, tournament_id, pet) -- Report from judge 1, tournament 2, pet 3 --
VALUES (2, 30, 'Sin comentarios', 1, 2, 3);
INSERT INTO reports(id, points, comments, judge_id, tournament_id, pet) -- Report from judge 1, tournament 10, pet 5 --
VALUES (3, 60, 'Sin comentarios', 1, 10, 5);
INSERT INTO reports(id, points, comments, judge_id, tournament_id, pet)  -- Report from judge 2, tournament 1, pet 1 --
VALUES (4, 40, 'Sin comentarios', 2, 1, 1);
INSERT INTO reports(id, points, comments, judge_id, tournament_id, pet)  -- Report from judge 2, tournament 1, pet 3 --
VALUES (5, 70, 'Sin comentarios', 2, 2, 3);
INSERT INTO reports(id, points, comments, judge_id, tournament_id, pet)  -- Report from judge 2, tournament 1, pet 5 --
VALUES (6, 90, 'Sin comentarios', 2, 10, 5);


