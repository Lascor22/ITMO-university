SELECT StudentName, CourseName
    FROM Plan
    NATURAL JOIN Students
    NATURAL JOIN Courses;    