-- b-tree index
-- ДЗ-5.1.1. Информацию о студентах (StudentId, StudentName, GroupId)
-- С заданным идентификатором (:StudentId).
-- ДЗ-5.2.1. Полную информацию о студентах (StudentId, StudentName,
-- GroupName) С заданным идентификатором (:StudentId).
-- ДЗ-5.3.1. Информацию о студентах с заданной оценкой (:Mark) по
-- предмету С заданным идентификатором (:CourseId);
CREATE UNIQUE INDEX Students_StudentId ON Students USING Btree (StudentId);

-- hash index
-- ДЗ-7.1.1. Напишите запросы, удаляющие студентов:
-- Учащихся в группе :GroupId;
-- ДЗ-7.1.2. Напишите запросы, удаляющие студентов:
-- Учащихся в группе :GroupName;
-- ДЗ-7.2.3. Напишите запросы, обновляющие данные студентов
-- Перевод всех студентов из группы :FromGroupId в группу :GroupId;
CREATE INDEX Students_GroupId ON Students USING HASH (GroupId);
 
-- hash index 
-- ДЗ-5.1.2. Информацию о студентах (StudentId, StudentName, GroupId) 
-- С заданным ФИО (:StudentName).
-- ДЗ-5.2.2. Полную информацию о студентах (StudentId, StudentName, 
-- GroupName) С заданным ФИО (:StudentName).
-- ДЗ-6.1.1. Информацию о студентах (StudentId, StudentName, GroupId)
-- С заданным ФИО (:StudentName).
CREATE INDEX Students_StudentName_StudentId ON Students USING HASH (StudentName, StudentId);
