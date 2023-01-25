SELECT
    DISTINCT Students.StudentId,
    Students.StudentName,
    Students.GroupId
FROM
    Students,
    Marks,
    Courses
WHERE
    Students.StudentId = Marks.StudentId
    AND Marks.CourseId = Courses.CourseId
    AND Mark = :Mark
    AND CourseName = :CourseName;
