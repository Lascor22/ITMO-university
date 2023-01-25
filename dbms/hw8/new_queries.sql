SELECT
    StudentId,
    StudentName,
    GroupId
FROM
    Students
WHERE
    StudentName LIKE :StudentName;

CREATE INDEX Students_StudentName_Prefix ON Students USING Btree(StudentName);

SELECT
    LecturerId
FROM
    Lecturers
WHERE
    LecturerName LIKE :LecturerName;

CREATE INDEX Lecturers_LecturerName_Prefix ON Lecturers USING Btree(LecturerName);

SELECT
    CourseId
FROM
    Courses
WHERE
    CourseName LIKE :CourseName;

CREATE INDEX Courses_CourseName_Prefix ON Courses USING HASH(CourseName);
