SELECT
    T.TeamName
FROM
    Teams AS T 
    NATURAL JOIN (
        SELECT DISTINCT
            TeamId  
        FROM 
            (
                SELECT
                    C.ContestId,
                    T.TeamId
                FROM
                    Teams AS T,
                    Contests AS C
                WHERE
                    TRUE
                EXCEPT
                SELECT
                    S.ContestId,
                    T.TeamId
                FROM
                    Teams AS T 
                    NATURAL JOIN Sessions AS S 
                    NATURAL JOIN Runs AS R
                WHERE
                    TRUE
            ) AS Q1
    ) AS Q2
