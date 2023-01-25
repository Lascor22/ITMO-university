SELECT StudentId 
    FROM (
        SELECT StudentId, SId 
            FROM (
                SELECT DISTINCT StudentId, CourseId
                    FROM Marks
            ) AS Query1
            CROSS JOIN (
                SELECT DISTINCT StudentId as SId 
                    FROM Lecturers
                    NATURAL JOIN Plan
                    NATURAL JOIN Students
                WHERE LecturerName = :LecturerName
            ) AS Query2
        EXCEPT
        SELECT StudentId, SId
            FROM (
                SELECT StudentId, Query4.CourseId, SId 
                    FROM (
                        SELECT DISTINCT StudentId, CourseId
                            FROM Marks
                    ) AS Query3
                    CROSS JOIN (
                        SELECT DISTINCT CourseId, StudentId as SId 
                            FROM Lecturers
                            NATURAL JOIN Plan
                            NATURAL JOIN Students
                        WHERE LecturerName = :LecturerName
                    ) AS Query4
                EXCEPT
                SELECT StudentId, CourseId, SId
                    FROM Marks
                    NATURAL JOIN (
                        SELECT CourseId, StudentId as SId 
                            FROM Lecturers
                            NATURAL JOIN Plan
                            NATURAL JOIN Students
                        WHERE LecturerName = :LecturerName
                    ) AS Query5
            ) AS Query6
    ) AS Query7
WHERE SId = StudentId;
