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
                Marks.StudentId = Students.StudentId
            GROUP BY
                Marks.StudentId
        ),
        0
    )
