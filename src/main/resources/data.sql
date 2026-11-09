-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO authorities(id,authority) VALUES (1,'ADMIN');
INSERT INTO appusers(id,username,password,authority) VALUES (1,'admin1','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',1);

-- Ten player users, named player1 with passwor 0wn3r
INSERT INTO authorities(id,authority) VALUES (2,'PLAYER');
INSERT INTO appusers(id,username,password,authority) VALUES (4,'player1','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (5,'player2','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (6,'player3','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (7,'player4','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (8,'player5','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (9,'player6','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (10,'player7','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (11,'player8','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (12,'player9','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (13,'player10','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);

-- Matches for the lobby (live coding session 1)
--   start = NULL                      -> waiting for players (can be joined)
--   start != NULL and finish = NULL   -> being played (can be watched as a spectator)
--   finish != NULL                    -> finished (not shown in the lobby)
--   code != NULL                      -> private match (players need the code to join)
INSERT INTO matches(id,name,code,start,finish) VALUES (1,'Fiesta para todos!!!! UNETE!',NULL,NULL,NULL);
INSERT INTO matches(id,name,code,start,finish) VALUES (2,'Solo para amigos','super-secret',NULL,NULL);
INSERT INTO matches(id,name,code,start,finish) VALUES (3,'Partida abierta ya comenzada',NULL,'2026-10-01 15:20:00',NULL);
INSERT INTO matches(id,name,code,start,finish) VALUES (4,'Partida privada ya comenzada','1234','2026-10-01 16:00:00',NULL);
INSERT INTO matches(id,name,code,start,finish) VALUES (5,'Partida abierta ya terminada',NULL,'2026-09-30 18:20:00','2026-09-30 19:20:00');

-- Players of each match (live coding session 2)
--   player1 (id 4): only in matches that are waiting (1 and 2)
--   player2 (id 5): in a waiting match (2) and in a match being played (3)
--   player3 (id 6): in two matches being played (3 and 4). This breaks the rule of
--                   no concurrent matches on purpose, to test the query with several results
--   player4 (id 7) and player5 (id 8): only in a finished match (5)
--   player6 (id 9): in no match
INSERT INTO matches_players(match_id,players_id) VALUES (1,4),(2,4),(2,5),(3,5),(3,6),(4,6),(5,7),(5,8);
