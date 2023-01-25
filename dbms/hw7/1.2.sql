DELETE FROM
    Students
WHERE
    GroupId IN (
        SELECT
            GroupId
        FROM
            Groups
        WHERE
            GroupName = :GroupName
    )
