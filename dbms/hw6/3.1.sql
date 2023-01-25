SELECT
    StudentId,
    CourseId
FROM
    Marks
UNION
SELECT
    StudentId,
    CourseId
FROM
    Students,
    Plan
WHERE
    Plan.GroupId = Students.GroupId
