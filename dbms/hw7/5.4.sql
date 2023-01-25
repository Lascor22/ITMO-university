CREATE VIEW StudentDebts(StudentId, Debts) AS
SELECT
    S.StudentId,
    COALESCE(SD.Debts, 0)
FROM
    Students AS S
    LEFT JOIN (
        SELECT
            StudentId,
            COUNT(DISTINCT CourseId) AS Debts
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
    ) AS SD ON S.StudentId = SD.StudentId
