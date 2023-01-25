CREATE ASSERTION SameMarks CHECK (
    NOT EXISTS (
        SELECT
            *
        FROM
            Students 
            NATURAL JOIN Marks
        WHERE
            CourseId NOT IN (
                SELECT
                    DISTINCT CourseId
                FROM
                    Plan
                WHERE
                    Plan.GroupId = Students.GroupId
            )
    )
)
