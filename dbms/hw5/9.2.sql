SELECT StudentName, AvgMark 
    FROM Students
    LEFT JOIN (
        SELECT StudentId, AVG(CAST(Mark AS REAL)) AS AvgMark
            FROM Marks
        GROUP BY StudentId
    ) AS StudentsSum ON StudentsSum.StudentId = Students.StudentId;
