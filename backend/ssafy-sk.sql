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

