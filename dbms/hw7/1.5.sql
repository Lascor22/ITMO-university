DELETE FROM
    Students
WHERE
    StudentId IN (
        SELECT
            StudentId
        FROM
            Marks
        GROUP BY
            StudentId
        HAVING
            COUNT(Mark) <= 3
    )
    OR StudentId NOT IN (
        SELECT
            DISTINCT StudentId
        FROM
            Marks
    )
