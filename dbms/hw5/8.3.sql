SELECT GroupName, SumMark 
    FROM Groups
    LEFT JOIN (
        SELECT GroupId, SUM(SumMark) AS SumMark 
            FROM Students
            LEFT JOIN (
                SELECT StudentId, SUM(Mark) AS SumMark
                    FROM Marks
                GROUP BY StudentId
            ) AS StudentsSum ON StudentsSum.StudentId = Students.StudentId
        GROUP BY GroupId
    ) AS StudentGroupSum ON StudentGroupSum.GroupId = Groups.GroupId;
