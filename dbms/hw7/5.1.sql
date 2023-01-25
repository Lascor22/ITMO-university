CREATE VIEW StudentMarks(StudentId, Marks) AS
SELECT
    S.StudentId,
    COALESCE(MarksCount, 0)
FROM
    Students AS S
    LEFT JOIN (
        SELECT
            StudentId,
            COUNT(Mark) AS MarksCount
        FROM
            Marks
        GROUP BY
            StudentId
    ) AS SM ON S.StudentId = SM.StudentId
