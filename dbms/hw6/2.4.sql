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
            Plan
        WHERE
            Students.GroupId = Plan.GroupId
            AND CourseId = :CourseId
    )
    AND StudentId NOT IN (
        SELECT
            StudentId
        FROM
            Marks
        WHERE
            CourseId = :CourseId
    )
