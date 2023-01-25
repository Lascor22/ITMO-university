CREATE VIEW AllMarks(StudentId, Marks) AS
SELECT
    Students.StudentId,
    COALESCE(SUM(MarksCount), 0)
FROM
    Students
    LEFT JOIN (
        SELECT
            StudentId,
            COUNT(Mark) AS MarksCount
        FROM
            Marks
        GROUP BY
            StudentId
        UNION
        ALL
        SELECT
            StudentId,
            COUNT(Mark) AS MarksCount
        FROM
            NewMarks
        GROUP BY
            StudentId
    ) SumMarks ON SumMarks.StudentId = Students.StudentId
GROUP BY
    Students.StudentId
