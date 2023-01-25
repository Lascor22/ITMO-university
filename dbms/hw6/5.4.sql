SELECT
    DISTINCT StudentId
FROM
    Students AS GlobalStudents
WHERE
    NOT EXISTS (
        SELECT
            StudentId
        FROM
            Students,
            (
                SELECT
                    CourseId,
                    GroupId
                FROM
                    Plan,
                    Lecturers
                WHERE
                    Plan.LecturerId = Lecturers.LecturerId
                    AND LecturerName = :LecturerName
            ) AS LecturerCourses
        WHERE
            GlobalStudents.StudentId = Students.StudentId
            AND Students.GroupId = LecturerCourses.GroupId
            AND NOT EXISTS (
                SELECT
                    Mark
                FROM
                    Marks
                WHERE
                    Marks.StudentId = Students.StudentId
                    AND Marks.CourseId = LecturerCourses.CourseId
            )
    )
