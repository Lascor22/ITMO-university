-- b-tree index
-- ДЗ-5.3.2. Информацию о студентах с заданной оценкой (:Mark) по
-- предмету С заданным названием (:CourseName).
-- ДЗ-5.4.1. Информацию о студентах не имеющих оценки по предмету
-- :CourseName Среди всех студентов.
-- ДЗ-5.4.2. Информацию о студентах не имеющих оценки по предмету
-- :CourseName Среди студентов, у которых есть этот предмет.
CREATE UNIQUE INDEX Courses_CourseId ON Courses USING Btree (CourseId);

-- hash index
-- ДЗ-5.3.2. Информацию о студентах с заданной оценкой (:Mark) по
-- предмету С заданным названием (:CourseName).
-- ДЗ-5.4.1. Информацию о студентах не имеющих оценки по предмету
-- :CourseName Среди всех студентов.
-- ДЗ-5.4.2. Информацию о студентах не имеющих оценки по предмету
-- :CourseName Среди студентов, у которых есть этот предмет.
CREATE INDEX Courses_CourseName_CourseId ON Courses USING HASH (CourseName, CourseId);
