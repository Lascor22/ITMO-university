SELECT StudentId
    FROM (
        SELECT StudentId, CourseId
            FROM Marks
    ) AS Query1
EXCEPT
SELECT StudentId
    FROM (
        SELECT StudentId, CourseId
            FROM (
                SELECT StudentId
                    FROM (
                        SELECT StudentId, CourseId
                            FROM Marks
                    ) AS Query2
            ) AS Query3
            CROSS JOIN (
                SELECT CourseId
                    FROM Plan
                    NATURAL JOIN Lecturers
                WHERE LecturerName = :LecturerName
            ) AS Query4
        EXCEPT
        SELECT StudentId, CourseId
            FROM Marks
    ) AS Query5;
