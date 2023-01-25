CREATE ASSERTION NoExtraMarks CHECK (
    NOT EXISTS (
        SELECT
            *
        FROM
            Marks
        WHERE
            NOT EXISTS (
                SELECT
                    *
                FROM
                    Students NATURAL
                    JOIN Plan
                WHERE
                    Students.StudentId = Marks.StudentId
                    AND Plan.CourseId = Marks.CourseId
            )
    )
);
