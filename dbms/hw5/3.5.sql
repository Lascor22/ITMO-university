SELECT StudentId, StudentName, Students.GroupId
    FROM Students
    NATURAL JOIN Marks    
    INNER JOIN Plan USING(CourseId)
WHERE Mark = :Mark AND
      LecturerId = :LecturerId;