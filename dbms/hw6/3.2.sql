SELECT
    StudentName,
    CourseName
FROM
    Students,
    Courses,
    (
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
    ) AS Pairs
WHERE
    Pairs.StudentId = Students.StudentId
    AND Pairs.CourseId = Courses.CourseId
