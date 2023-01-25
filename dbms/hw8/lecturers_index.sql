-- b-tree index
-- ДЗ-5.3.4. Информацию о студентах с заданной оценкой (:Mark) по
-- предмету Который у него вёл (:LecturerName).
-- ДЗ-5.3.6. Информацию о студентах с заданной оценкой (:Mark) по
-- предмету Который вёл (:LecturerName).
-- ДЗ-5.6.1. Идентификаторы студентов по преподавателю
-- (:LecturerName) Имеющих хотя бы одну оценку у преподавателя.
CREATE UNIQUE INDEX Lecturers_LecturerId ON Lecturers USING Btree (LecturerId);

-- hash index
-- ДЗ-5.3.4. Информацию о студентах с заданной оценкой (:Mark) по
-- предмету Который у него вёл (:LecturerName).
-- ДЗ-5.3.6. Информацию о студентах с заданной оценкой (:Mark) по 
-- предмету Который вёл (:LecturerName).
-- ДЗ-5.6.1. Идентификаторы студентов по преподавателю 
-- (:LecturerName) Имеющих хотя бы одну оценку у преподавателя.
CREATE INDEX Lecturers_LecturerName_LecturerId ON Lecturers USING HASH (LecturerName, LecturerId);
