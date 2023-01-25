DELETE FROM
    Students
WHERE
    EXISTS (
        SELECT
            CourseId
        FROM
            Plan
        WHERE
            Plan.GroupId = Students.GroupId
            AND NOT EXISTS (
                SELECT
                    DISTINCT Mark
                FROM
                    Marks
                WHERE
                    Marks.StudentId = Students.StudentId
                    AND Marks.CourseId = Plan.CourseId
            )
    )
