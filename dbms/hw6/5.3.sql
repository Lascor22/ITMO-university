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
            (
                SELECT
                    CourseId
                FROM
                    Plan,
                    Lecturers
                WHERE
                    Plan.LecturerId = Lecturers.LecturerId
                    AND LecturerName = :LecturerName
            ) AS LecturerCourses
        WHERE
            NOT EXISTS (
                SELECT
                    Mark
                FROM
                    Marks
                WHERE
                    Marks.StudentId = Students.StudentId
                    AND Marks.CourseId = LecturerCourses.CourseId
            )
    )
