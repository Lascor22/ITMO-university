SELECT
    GroupName,
    AvgAvgMark
FROM
    Groups
    LEFT JOIN (
        SELECT
            GroupId,
            AVG(CAST(AvgMark AS REAL)) AS AvgAvgMark
        FROM
            Students
            LEFT JOIN (
                SELECT
                    StudentId,
                    AVG(CAST(Mark AS REAL)) AS AvgMark
                FROM
                    Marks
                GROUP BY
                    StudentId
            ) AS StudentsSum ON StudentsSum.StudentId = Students.StudentId
        GROUP BY
            GroupId
    ) AS StudentGroupSum ON StudentGroupSum.GroupId = Groups.GroupId
