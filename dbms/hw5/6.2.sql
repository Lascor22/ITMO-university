SELECT StudentId
    FROM Students
EXCEPT
SELECT DISTINCT StudentId
    FROM Students
    NATURAL JOIN Marks
    NATURAL JOIN PLAN
    NATURAL JOIN Lecturers
WHERE LecturerName = :LecturerName;
