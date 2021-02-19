# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uid` int NOT NULL AUTO_INCREMENT,
  `email` varchar(128) DEFAULT NULL,
  `nickname` varchar(128) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `create_date` datetime DEFAULT current_timestamp(),
  `certified` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `user_idx_unique_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `background`;

CREATE TABLE `background` (
  `backgroundNo` int NOT NULL AUTO_INCREMENT,
  `path` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`backgroundNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `bgm`;

CREATE TABLE `bgm` (
  `bgmNo` int NOT NULL AUTO_INCREMENT,
  `path` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`bgmNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `room`;

CREATE TABLE `room` (
  `roomNo` int NOT NULL AUTO_INCREMENT,
  `backgroundNo` int DEFAULT NULL,
  `bgmNo` int DEFAULT NULL,
  `roomName` varchar(128) DEFAULT NULL,
  `roomURL` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`roomNo`),
  FOREIGN KEY (`backgroundNo`) references background (`backgroundNo`),
  FOREIGN KEY (`bgmNo`) references bgm (`bgmNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `roomJoin`;

CREATE TABLE `roomJoin` (
  `roomJoinNo` int NOT NULL AUTO_INCREMENT,
  `roomNo` int DEFAULT NULL,
  `uid` int DEFAULT NULL,
  PRIMARY KEY (`roomJoinNo`),
  FOREIGN KEY (`roomNo`) references room (`roomNo`),
  FOREIGN KEY (`uid`) references user (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

