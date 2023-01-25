SELECT
    StudentName,
    CourseName
FROM
    (
        SELECT
            DISTINCT Students.StudentId,
            StudentName,
            Courses.CourseId,
            CourseName
        FROM
            Students,
            Plan,
            Courses
        WHERE
            Students.GroupId = Plan.GroupId
            AND Plan.CourseId = Courses.CourseId
            AND EXISTS (
                SELECT
                    Marks.StudentId,
                    Marks.CourseId
                FROM
                    Marks
                WHERE
                    Marks.StudentId = Students.StudentId
                    AND Marks.CourseId = Courses.CourseId
                    AND Mark < 3
            )
    ) AS Core
