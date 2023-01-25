SELECT
    GroupName,
    AVG(CAST(Mark AS REAL)) AS AvgMark
FROM
    Students
    NATURAL JOIN Marks
    NATURAL JOIN Groups
WHERE CourseId = :CourseId
GROUP BY
    GroupId
