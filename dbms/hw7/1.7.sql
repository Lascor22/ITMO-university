DELETE FROM
    Students
WHERE
    StudentId IN (
        SELECT
            StudentId
        FROM
            Students 
            NATURAL JOIN Plan
        WHERE
            NOT EXISTS (
                SELECT
                    Mark
                FROM
                    Marks
                WHERE
                    Marks.StudentId = Students.StudentId
                    AND Marks.CourseId = Plan.CourseId
            )
        GROUP BY
            StudentId
        HAVING
            COUNT(DISTINCT CourseId) >= 2
    )
