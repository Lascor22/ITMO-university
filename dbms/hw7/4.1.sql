INSERT INTO
    Marks (StudentId, CourseId, Mark)
SELECT
    StudentId,
    CourseId,
    Mark
FROM
    NewMarks
WHERE
    NOT EXISTS (
        SELECT
            *
        FROM
            Marks
        WHERE
            NewMarks.StudentId = Marks.StudentId
            AND NewMarks.CourseId = Marks.CourseId
    )
