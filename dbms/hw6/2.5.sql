SELECT
    StudentId,
    StudentName,
    GroupName
FROM
    Students,
    Groups
WHERE
    Students.GroupId = Groups.GroupId
    AND StudentId IN (
        SELECT
            StudentId
        FROM
            Students,
            Plan,
            Courses
        WHERE
            Students.GroupId = Plan.GroupId
            AND Courses.CourseId = Plan.CourseId
            AND CourseName = :CourseName
    )
    AND StudentId NOT IN (
        SELECT
            StudentId
        FROM
            Marks,
            Courses
        WHERE
            Courses.CourseId = Marks.CourseId
            AND CourseName = :CourseName
    )
