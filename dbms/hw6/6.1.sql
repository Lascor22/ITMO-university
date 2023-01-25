SELECT
    GlobalGroups.GroupId,
    GlobalCourses.CourseId
FROM
    Groups AS GlobalGroups,
    Courses AS GlobalCourses
WHERE
    NOT EXISTS (
        SELECT
            Groups.GroupId,
            Courses.CourseId
        FROM
            Groups,
            Courses,
            Students
        WHERE
            Students.GroupId = Groups.GroupId
            AND NOT EXISTS (
                SELECT
                    Mark
                FROM
                    Marks
                WHERE
                    Marks.StudentId = Students.StudentId
                    AND Marks.CourseId = Courses.CourseId
            )
            AND GlobalGroups.GroupId = Groups.GroupId
            AND GlobalCourses.CourseId = Courses.CourseId
    )
