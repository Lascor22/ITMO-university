-- b-tree index
-- ДЗ-6.1.2. Информацию о студентах (StudentId, StudentName, GroupId)
-- Учащихся в заданной группе (:GroupName).
-- ДЗ-5.2.1. Полную информацию о студентах (StudentId, StudentName,
-- GroupName) С заданным идентификатором (:StudentId).
-- ДЗ-5.2.2. Полную информацию о студентах (StudentId, StudentName, 
-- GroupName) С заданным ФИО (:StudentName).
CREATE UNIQUE INDEX Groups_GroupId ON Groups USING Btree (GroupId);

-- b-tree index
-- ДЗ-6.1.2. Информацию о студентах (StudentId, StudentName, GroupId)
-- С заданным ФИО (:StudentName).
-- ДЗ-7.1.2. Напишите запросы, удаляющие студентов:
-- Учащихся в группе :GroupName.
-- ДЗ-7.2.4. Напишите запросы, обновляющие данные студентов Перевод
-- всех студентов из группы :FromGroupName в группу :GroupName.
CREATE INDEX Group_GroupName_GroupId ON Groups USING Btree (GroupName, GroupId);
