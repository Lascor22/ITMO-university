SELECT
    Students.StudentId,
    Students.StudentName,
    Students.GroupId
FROM
    Students,
    Groups
WHERE
    Students.GroupId = Groups.GroupId
    AND GroupName = :GroupName;
