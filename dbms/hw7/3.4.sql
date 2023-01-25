UPDATE
    Students
SET
    Marks = (
        SELECT
            COUNT(CourseId)
        FROM
            (
                SELECT
                    DISTINCT StudentId,
                    CourseId
                FROM
                    Marks
            ) AS StudentCourses
        WHERE
            StudentCourses.StudentId = Students.StudentId
    )
