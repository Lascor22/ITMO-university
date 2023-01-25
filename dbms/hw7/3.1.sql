UPDATE
    Students
SET
    Marks = COALESCE(
        (
            SELECT
                COUNT(Mark)
            FROM
                Marks
            WHERE
                Marks.StudentId = :StudentId
            GROUP BY
                Marks.StudentId
        ),
        0
    )
WHERE
    StudentId = :StudentId
