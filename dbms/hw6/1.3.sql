SELECT
    DISTINCT Students.StudentId,
    Students.StudentName,
    Students.GroupId
FROM
    Students,
    Marks
WHERE
    Students.StudentId = Marks.StudentId
    AND Mark = :Mark
    AND CourseId = :CourseId;
