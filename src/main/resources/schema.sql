DROP TABLE IF EXISTS course;

CREATE TABLE course(
   course_id INT AUTO_INCREMENT  PRIMARY KEY,
   course_name VARCHAR(250) NOT NULL,
   course_description VARCHAR(250) NOT NULL,
   UNIQUE (course_name)
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

DROP TABLE IF EXISTS course_student;

CREATE TABLE course_student(
id INT AUTO_INCREMENT PRIMARY KEY,
student_id INT NOT NULL,
course_id VARCHAR(250) NOT NULL,
FOREIGN KEY (student_id) REFERENCES student(student_id),
FOREIGN KEY (course_id) REFERENCES course(course_id),
UNIQUE (student_id,course_id)
);
