INSERT INTO `author` (`name`)
VALUES
("Gail Terrell"),
("Nathan Roberson"),
("Hayley Cruz"),
("Nasim Glover"),
("Kim Guerrero"),
("Cleo Contreras"),
("Gavin Houston"),
("Sacha Bolton"),
("Nicholas Roberson"),
("Alden Fowler"),
("Conan York"),
("Karleigh Berger"),
("Rhoda Mccormick"),
("Cade Fischer"),
("Steel Blankenship"),
("Jaden Lara"),
("Hammett Kennedy"),
("Maile Watson"),
("Emery Tillman"),
("Ethan Gates");

INSERT INTO `reader` (`first_name`,`last_name`,`email`,`gender`,`phone`,`date_of_registration`)
VALUES
("Vance","Kirby","ut@Vivamusmolestie.co.uk","0","790467801","2019-09-28 15:43:26"),
("Vielka","Howell","non@sapienCrasdolor.ca","1","650807649","2020-05-24 14:32:10"),
("Sigourney","Price","Duis@Suspendisse.com","0","969608500","2019-10-17 01:59:33"),
("Melodie","Gross","amet.diam.eu@anteMaecenasmi.com","0","005455747","2021-01-23 06:01:23"),
("Sonia","Wagner","Fusce@ornareegestas.edu","0","821220795","2021-05-15 15:22:40"),
("Ulric","Mcdowell","lobortis.tellus.justo@ornaresagittis.com","0","283026732","2020-10-31 20:05:59"),
("Germaine","Klein","dui.semper.et@adipiscingnon.ca","0","882210797","2020-04-07 07:03:25"),
("Kitra","Manning","tincidunt@tellusfaucibusleo.org","1","179678470","2020-11-21 15:57:07"),
("Aspen","Gilbert","quis@suscipit.edu","0","604140254","2020-04-18 08:25:31"),
("Alfreda","Reeves","nec.ante.blandit@ultrices.com","0","492476829","2019-10-16 01:29:50");

INSERT INTO `genre` (`genre`)
VALUES
("fantasy"),
("thriller"),
("romance"),
("drama"),
("detective"),
("Phasellus dapibus"),
("dolor. Nulla semper tellus"),
("luctus vulputate, nisi sem semper erat, in consectetuer"),
("Nam tempor diam dictum sapien.");

INSERT INTO `book`
(`title`,`publisher`,`publish_date`,`page_count`,`description`,`total_amount`,`remaining_amount`,`ISBN`,`status`)
VALUES
("tellus faucibus","Wade Melendez","2020-04-20 00:27:17",101,"Nullam suscipit, est ac facilisis facilisis, magna tellus faucibus",85,85,"038-928-3139","1"),
("morbi tristique senectus","Curran Kim","2020-07-14 12:50:49",198,"est arcu ac orci. Ut",84,84,"068-533-5011","1"),
("vulputate, posuere","Nero Valencia","2019-12-20 03:59:37",344,"tortor at risus.",62,62,"088-651-5002","0"),
("elit, pellentesque","Flynn Walters","2020-05-19 11:59:30",299,"nec, diam. Duis mi enim, condimentum",81,81,"001-174-9944","0"),
("Vivamus","Elvis Holcomb","2020-01-21 04:31:16",78,"semper. Nam tempor diam dictum sapien.",78,78,"013-482-8151","0"),
("pede. Cras vulputate","Timon England","2020-06-10 21:08:30",402,"dui quis accumsan",95,95,"017-078-6257","1"),
("sapien imperdiet","Burton Jimenez","2020-08-31 14:41:23",42,"amet ornare lectus justo eu arcu. Morbi sit",45,45,"063-792-8805","1"),
("in consequat","Kermit Torres","2021-02-08 17:44:39",352,"felis,",97,97,"053-644-5814","1"),
("Integer eu","Brody Duncan","2019-09-05 07:50:45",23,"molestie tortor nibh sit amet",53,53,"062-331-2666","1"),
("libero.","Emerson Wood","2020-11-03 07:03:25",39,"vitae diam. Proin dolor. Nulla semper tellus id nunc",75,75,"079-997-3345","0");

INSERT INTO `book_author` (`book_id`,`author_id`)
VALUES
(8,6),(3,9),(10,10),(10,1),(7,8),(6,5),(10,4),(6,6),(9,2),(4,5),
(7,10),(1,3),(9,8),(2,8),(10,8),(2,3),(4,1),(10,7),(10,9),(7,1),
(4,5),(3,2),(1,7),(4,5),(2,7),(2,7),(9,4),(5,3),(4,3),(8,8);

INSERT INTO `book_genre` (`book_id`,`genre_id`)
VALUES
(7,8),(9,6),(2,1),(4,8),(9,2),(9,1),(5,1),(10,6),(1,6),(1,4),
(8,4),(5,5),(5,2),(9,3),(3,4),(3,3),(10,9),(2,9),(8,2),(5,4),
(9,9),(2,3),(2,5),(8,7),(5,7),(2,4),(8,9),(1,4),(10,6),(9,3);


INSERT INTO `book_order` (`book_id`,`reader_id`,`borrow_date`,`borrow_period`, `status`,`comment`,`due_date`,`return_date`)
VALUES
(7,7,"2020-01-25 12:06:38","ONE","RETURNED_AND_DAMAGED","per conubia nostra,","2020-12-29 05:06:49","2020-11-09 07:38:21"),(4,9,"2020-01-01 04:29:19","TWO","RETURNED_AND_DAMAGED","erat neque non quam. Pellentesque habitant morbi tristique senectus et","2021-07-31 10:02:48","2021-03-17 14:58:16"),(10,10,"2020-12-11 02:03:20","THREE","RETURNED_AND_DAMAGED","feugiat nec, diam. Duis mi enim,","2020-12-09 22:46:09","2019-09-24 22:17:52"),(5,5,"2020-06-03 15:58:27","SIX","LOST","dui nec urna","2021-06-18 12:53:32","2019-09-28 15:13:25"),(3,2,"2020-07-15 22:42:00","TWELVE","LOST","libero. Proin sed turpis nec mauris blandit mattis. Cras","2021-04-11 02:59:20","2021-05-01 01:36:07"),(4,3,"2021-01-07 10:31:54","TWELVE","RETURNED_AND_DAMAGED","dapibus id, blandit","2021-07-08 04:19:23","2020-01-25 02:17:44"),(2,7,"2021-07-25 08:23:11","SIX","LOST","et magnis dis parturient montes, nascetur ridiculus","2021-01-16 03:44:29","2021-06-10 21:28:41"),(3,5,"2021-01-14 21:44:27","ONE","RETURNED_AND_DAMAGED","Donec sollicitudin adipiscing ligula. Aenean gravida nunc","2021-06-01 05:53:10","2020-02-24 19:28:09"),(2,3,"2019-12-05 07:22:46","TWELVE","RETURNED","montes, nascetur ridiculus mus. Proin vel arcu eu odio tristique","2021-06-27 23:45:15","2019-09-22 06:49:03"),(5,4,"2020-10-30 12:49:57","ONE","RETURNED_AND_DAMAGED","enim. Curabitur massa. Vestibulum accumsan neque et","2021-05-03 11:46:18","2020-11-16 16:54:52");
