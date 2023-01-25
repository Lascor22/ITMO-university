SELECT StudentId, StudentName, Students.GroupId
    FROM Students
    NATURAL JOIN Marks    
    INNER JOIN Plan USING(CourseId)
    INNER JOIN Lecturers ON Lecturers.LecturerId = Plan.LecturerId
WHERE Mark = :Mark AND
      LecturerName = :LecturerName;