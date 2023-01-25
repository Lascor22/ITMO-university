SELECT
    TeamName
FROM
    Teams 
    NATURAL JOIN (
        SELECT TeamId FROM Teams
        EXCEPT
        SELECT
            DISTINCT TeamId
        FROM
            Sessions AS S 
            NATURAL JOIN Runs AS R
        WHERE
            S.ContestId = :ContestId
    ) AS Q
WHERE
    TRUE
