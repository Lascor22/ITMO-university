UPDATE
    Students
SET
    Marks = Marks + (
        SELECT
            COUNT(Mark)
        FROM
            NewMarks
        WHERE
            NewMarks.StudentId = Students.StudentId
        GROUP BY
            NewMarks.StudentId
    )
WHERE
    EXISTS (
        SELECT
            *
        FROM
            NewMarks
        WHERE
            NewMarks.StudentId = Students.StudentId
    )
