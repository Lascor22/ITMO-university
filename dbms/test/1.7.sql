SELECT 
    S.SessionId
FROM
    Sessions AS S
EXCEPT
SELECT 
    Q.SessionId
FROM
    (
        SELECT
            S.SessionId,
            P.Letter
        FROM
            Sessions AS S 
            NATURAL JOIN Problems AS P
        EXCEPT
        SELECT
            S.SessionId,
            P.Letter
        FROM
            Sessions AS S
            NATURAL JOIN Problems AS P 
            NATURAL JOIN Runs AS R
    ) AS Q
