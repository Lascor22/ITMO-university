DELETE FROM
    Students
WHERE
    StudentId NOT IN (
        SELECT
            S.StudentId
        FROM
            Students AS S
            NATURAL JOIN Plan
        WHERE
            NOT EXISTS (
                SELECT
                    Mark
                FROM
                    Marks
                WHERE
                    Marks.StudentId = S.StudentId
                    AND Marks.CourseId = Plan.CourseId
            )
        GROUP BY
            S.StudentId
        HAVING
            COUNT(DISTINCT Plan.CourseId) > 2
    )
