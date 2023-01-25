SELECT StudentName, SumMark 
    FROM Students
    LEFT JOIN (
        SELECT StudentId, SUM(Mark) AS SumMark
            FROM Marks
        GROUP BY StudentId
    ) AS StudentsSum ON StudentsSum.StudentId = Students.StudentId;
