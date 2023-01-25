SELECT
    StudentId,
    Total,
    Passed,
    (Total - Passed) AS Failed
FROM
    (
        SELECT
            StudentId,
            COALESCE(COUNT(DISTINCT CourseId), 0) AS Total
        FROM
            Students
            LEFT JOIN Plan ON Plan.GroupId = Students.GroupId
        GROUP BY
            StudentId
    ) AS Query2 NATURAL
    JOIN (
        SELECT
            Students.StudentId,
            COALESCE(Passed, 0) AS Passed
        FROM
            Students
            LEFT JOIN (
                SELECT
                    StudentId,
                    COUNT(Mark) AS Passed
                FROM
                    Marks NATURAL
                    JOIN Plan NATURAL
                    JOIN Students
                GROUP BY
                    StudentId
            ) AS Query3 ON Query3.StudentId = Students.StudentId
    ) AS Query4
