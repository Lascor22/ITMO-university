SELECT
    Q.TeamId,
    COUNT(*) AS Opened
FROM
    (
        SELECT
            DISTINCT TeamId,
            Letter,
            ContestId
        FROM
            Sessions AS S 
            NATURAL JOIN Runs AS R
    ) AS Q
GROUP BY
    Q.TeamId;
