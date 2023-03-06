CREATE TABLE student (
  id INT NOT NULL AUTO_INCREMENT,
  name CHAR(40) NOT NULL,
  no CHAR(5) NOT NULL,
  grade int,
  team char(10),
  remark text,
  deleted_at datetime,
  PRIMARY KEY (id)
);