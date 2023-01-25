SELECT GroupId, CourseId
    FROM (
        SELECT CourseId, GroupId
            FROM (
                SELECT DISTINCT CourseId
                    FROM Marks
            ) AS Query1
            CROSS JOIN (
                SELECT DISTINCT GroupId
                    FROM Students
            ) AS Query2
        EXCEPT
        SELECT CourseId, GroupId
            FROM (
                SELECT CourseId, GroupId, StudentId
                    FROM (
                        SELECT DISTINCT CourseId
                            FROM Marks
                    ) AS Query3
                    CROSS JOIN (
                        SELECT StudentId, GroupId
                            FROM Students
                    ) AS Query4
                EXCEPT
                SELECT CourseId, GroupId, StudentId
                    FROM (
                        SELECT StudentId, GroupId
                            FROM Students
                    ) AS Query5
                    NATURAL JOIN (
                        SELECT CourseId, StudentId
                            FROM Marks
                    ) AS Query6
            ) AS Query7
    ) AS Query8
