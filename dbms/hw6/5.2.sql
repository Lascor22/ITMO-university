SELECT
    DISTINCT StudentId
FROM
    Students
WHERE
    StudentId NOT IN (
        SELECT
            DISTINCT Students.StudentId
        FROM
            Students,
            Plan,
            Lecturers,
            Marks
        WHERE
            Students.StudentId = Marks.StudentId
            AND Students.GroupId = Plan.GroupId
            AND Plan.CourseId = Marks.CourseId
            AND Plan.LecturerId = Lecturers.LecturerId
            AND LecturerName = :LecturerName
    )
