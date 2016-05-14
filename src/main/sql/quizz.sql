CREATE TABLE quiz
(
id int NOT NULL AUTO_INCREMENT,
name varchar(255) NOT NULL,
max_duration_in_min int,
PRIMARY KEY (ID)
)

CREATE TABLE user
(
id INT NOT NULL AUTO_INCREMENT,
username VARCHAR(64) NOT NULL,
password VARCHAR(64) NOT NULL,
first_name VARCHAR(64) NOT NULL,
last_name VARCHAR(64) NOT NULL,
email VARCHAR(64) NOT NULL,
birth_date DATE NOT NULL,
gender VARCHAR(16) NOT NULL,
phone VARCHAR(16),
email_verification_id VARCHAR(38) NOT NULL,
email_verified BOOLEAN NOT NULL,
PRIMARY KEY (ID)
)
