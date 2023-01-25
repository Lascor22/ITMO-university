SELECT
    T.TeamName
FROM
    Teams AS T 
    NATURAL JOIN (
        SELECT
            DISTINCT S.TeamId
        FROM
            Sessions AS S
        WHERE
            S.ContestId = :ContestId
    ) AS Q
WHERE TRUE
