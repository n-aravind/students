insert into course (course_name,course_description)
values
('Biology','Study Of Living Organisms'),
('Statistics','Study of the collection, organization, and interpretation of data'),
('Physics','Study Of Matter');

insert into student (first_name,last_name)
values
('Jason','Pike'),
('Marco','Keenan'),
('Elina','Dodson');

insert into email_addresses (student_id,email_address,email_type)
values
(1,'jpike@edu.in','ACADEMIC'),
(1,'jasonpike@gmail.com','PERSONAL'),
(2,'mkeenan@edu.in','ACADEMIC'),
(2,'marcok@gmail.com','PERSONAL'),
(3,'edodson@wdu.in','ACADEMIC');

insert into course_student (student_id,course_id)
values
(1,1),
(1,2),
(2,1),
(2,2),
(2,3);
