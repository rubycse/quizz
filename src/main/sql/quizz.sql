CREATE TABLE user
(
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(64) NOT NULL UNIQUE,
  password VARCHAR(64) NOT NULL,
  first_name VARCHAR(64) NOT NULL,
  last_name VARCHAR(64) NOT NULL,
  email VARCHAR(64) NOT NULL UNIQUE,
  birth_date DATE NOT NULL,
  gender VARCHAR(16) NOT NULL,
  phone VARCHAR(16),
  student BOOLEAN NOT NULL,
  email_verification_id VARCHAR(38) NOT NULL,
  email_verified BOOLEAN NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE quiz_template
(
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  created_by_id INT NOT NULL,
  published BOOLEAN NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (created_by_id) REFERENCES user(id)
);

CREATE TABLE question_template
(
  id INT NOT NULL AUTO_INCREMENT,
  label VARCHAR(255) NOT NULL,
  max_duration_in_min INT,
  required BOOLEAN NOT NULL,
  quiz_template_id INT NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (quiz_template_id) REFERENCES quiz_template(id)
);

CREATE TABLE option_template
(
  id INT NOT NULL AUTO_INCREMENT,
  label VARCHAR(255) NOT NULL,
  right_answer BOOLEAN NOT NULL,
  question_template_id INT NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (question_template_id) REFERENCES question_template(id)
);

CREATE TABLE publication
(
  id INT NOT NULL AUTO_INCREMENT,
  quiz_template_id INT NOT NULL,
  publish_for VARCHAR(20) NOT NULL,
  duration_in_min INT NOT NULL,
  schedule_from TIMESTAMP NULL,
  schedule_to TIMESTAMP NULL,
  result_publication_time TIMESTAMP NULL,
  published_on TIMESTAMP NULL,
  published_by_id INT NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (quiz_template_id) REFERENCES quiz_template(id),
  FOREIGN KEY (published_by_id) REFERENCES user(id)
);

CREATE TABLE publish_to
(
  publication_id INT NOT NULL,
  email VARCHAR(64) NOT NULL,
  FOREIGN KEY (publication_id) REFERENCES publication(id)
);

CREATE TABLE quiz
(
  id INT NOT NULL AUTO_INCREMENT,
  publication_id INT NOT NULL,
  quiz_template_id INT NOT NULL,
  answered_by_id INT NOT NULL,
  start_time TIMESTAMP NOT NULL,
  end_time TIMESTAMP NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (quiz_template_id) REFERENCES quiz_template(id),
  FOREIGN KEY (answered_by_id) REFERENCES user(id)
);

CREATE TABLE question
(
  id INT NOT NULL AUTO_INCREMENT,
  label VARCHAR(255) NOT NULL,
  max_duration_in_min INT,
  required BOOLEAN NOT NULL,
  quiz_id INT NOT NULL,
  start_time TIMESTAMP NULL,
  end_time TIMESTAMP NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (quiz_id) REFERENCES quiz(id)
);

CREATE TABLE ques_option
(
  id INT NOT NULL AUTO_INCREMENT,
  label VARCHAR(255) NOT NULL,
  right_answer BOOLEAN NOT NULL,
  answered BOOLEAN NOT NULL,
  question_id INT NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (question_id) REFERENCES question(id)
);

insert into user values(1, 'rubycse', 'rubycse', 'Teacher', 'Teacher', 'rubycse@gmail.com', '2014-12-01', 'FEMALE', null, 0, '0', 1);
insert into user values(2, 'nahar', 'rubycse', 'Student', 'Student', 'rubycse@yahoo.com', '2014-12-01', 'FEMALE', null, 1, '0', 1);
