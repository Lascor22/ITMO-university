SELECT GroupName, AvgMark 
    FROM Groups
    LEFT JOIN (
        SELECT GroupId, AVG(CAST(Mark AS REAL)) AS AvgMark 
            FROM Students
            LEFT JOIN (
                SELECT StudentId, Mark  
                    FROM Marks
            ) AS StudentsSum ON StudentsSum.StudentId = Students.StudentId
        GROUP BY GroupId
    ) AS StudentGroupSum ON StudentGroupSum.GroupId = Groups.GroupId;
