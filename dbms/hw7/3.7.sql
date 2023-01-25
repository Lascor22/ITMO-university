UPDATE
    Students
SET
    Debts = COALESCE(
        (
            SELECT
                COUNT(DISTINCT CourseId) AS Debts
            FROM
                Students AS S 
                NATURAL JOIN Plan
            WHERE
                S.StudentId = Students.StudentId
                AND NOT EXISTS (
                    SELECT
                        Mark
                    FROM
                        Marks
                    WHERE
                        Marks.StudentId = Students.StudentId
                        AND Marks.CourseId = Plan.CourseId
                )
            GROUP BY
                S.StudentId
        ),
        0
    )
WHERE
    GroupId IN (
        SELECT
            GroupId
        FROM
            Groups
        WHERE
            GroupName = :GroupName
    )
