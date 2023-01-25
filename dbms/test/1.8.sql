SELECT
    S.SessionId
FROM
    Sessions AS S
WHERE 
    TRUE
EXCEPT
SELECT
    SessionId
FROM
    (
        SELECT
            S.SessionId,
            P.Letter
        FROM
            Sessions AS  S
            NATURAL JOIN Problems AS P
        WHERE
            TRUE
        EXCEPT
        SELECT
            S.SessionId,
            P.Letter
        FROM
            Sessions AS S
            NATURAL JOIN Problems AS P
            NATURAL JOIN Runs AS R
        WHERE
            R.Accepted = 1
    ) AS Q
