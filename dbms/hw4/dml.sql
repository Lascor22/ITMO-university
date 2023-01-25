SET client_encoding TO 'UTF8';

insert into Groups 
    (GroupId, GroupName) values
    (1, 'M3134'),
    (2, 'M3135'),
    (3, 'M3139');

insert into Students 
    (StudentId, StudentName, GroupId) values
    (1, 'Иванов Иван Иванович', 1),
    (2, 'Сидоров Кантемир Ананьевич', 2),
    (3, 'Акакьев Акакий Акакьевич', 3);

insert into Lecturers 
    (LecturerId, LecturerName) values
    (1, 'Корнеев Георгий'),
    (2, 'Станкевич Андрей'),
    (3, 'Васильев Артём'),
    (4, 'Кохась Константин');

insert into Courses 
    (CourseId, CourseName) values
    (1, 'Введение в программирование'),
    (2, 'Дискретная математика'),
    (3, 'Математический анализ'),
    (4, 'Парадигмы программирования');

insert into Plan 
    (GroupId, CourseId, LecturerId) values
    (1, 1, 1),
    (2, 1, 1),
    (3, 1, 1),
    (1, 2, 3),
    (2, 2, 3),
    (3, 2, 2),
    (3, 3, 4),
    (1, 4, 1),
    (2, 4, 1),
    (3, 4, 1);

insert into Marks 
    (StudentId, CourseId, Mark) values
    (1, 1, 5),
    (1, 2, 4),
    (1, 3, 4),
    (2, 1, 5),
    (2, 2, 3),
    (2, 3, 3),
    (3, 1, 4),
    (3, 2, 5),
    (3, 3, 3);
