UPDATE
    Students
SET
    Debts = COALESCE(
        (
            SELECT
                COUNT(DISTINCT CourseId) AS Debts
            FROM
                Students
                NATURAL JOIN Plan
            WHERE
                Students.StudentId = :StudentId
                AND NOT EXISTS (
                    SELECT
                        Mark
                    FROM
                        Marks
                    WHERE
                        Marks.StudentId = :StudentId
                        AND Marks.CourseId = Plan.CourseId
                )
            GROUP BY
                Students.StudentId
        ),
        0
    )
WHERE
    StudentId = :StudentId
