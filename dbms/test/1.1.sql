SELECT DISTINCT
    S.TeamId
FROM
    Sessions AS S
WHERE
    S.ContestId = :ContestId
