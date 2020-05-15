DROP TABLE IF EXISTS class;

CREATE TABLE class(
   class_id INT AUTO_INCREMENT  PRIMARY KEY,
   class_name VARCHAR(250) NOT NULL,
   class_description VARCHAR(250) NOT NULL,
   UNIQUE (class_name)
);

DROP TABLE IF EXISTS student;

CREATE TABLE student(
student_id INT AUTO_INCREMENT NOT NULL,
first_name VARCHAR(40) NOT NULL,
last_name VARCHAR(40) NOT NULL
);

DROP TABLE IF EXISTS email_addresses;

CREATE TABLE email_addresses(
id INT AUTO_INCREMENT PRIMARY KEY,
student_id INT NOT NULL,
email_address VARCHAR(250) NOT NULL,
email_type VARCHAR(20) NOT NULL,
FOREIGN KEY (student_id) REFERENCES student(student_id)
);

DROP TABLE IF EXISTS class_student;

CREATE TABLE class_student(
id INT AUTO_INCREMENT PRIMARY KEY,
student_id INT NOT NULL,
class_id VARCHAR(250) NOT NULL,
FOREIGN KEY (student_id) REFERENCES student(student_id),
FOREIGN KEY (class_id) REFERENCES class(class_id),
UNIQUE (student_id,class_id)
);
