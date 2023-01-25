UPDATE
    Marks
SET
    Mark = (
        SELECT
            Mark
        FROM
            NewMarks
        WHERE
            NewMarks.StudentId = Marks.StudentId
            AND NewMarks.CourseId = Marks.CourseId
    )
WHERE
    EXISTS (
        SELECT
            *
        FROM
            NewMarks
        WHERE
            NewMarks.StudentId = Marks.StudentId
            AND NewMarks.CourseId = Marks.CourseId
            AND Marks.Mark < NewMarks.Mark
    )
