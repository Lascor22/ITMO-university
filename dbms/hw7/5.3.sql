CREATE VIEW Debts(StudentId, Debts) AS 
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
