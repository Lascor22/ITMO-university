DELETE FROM
    Runs
WHERE
    SessionId IN (
        SELECT
            S.SessionId
        FROM
            Sessions AS S,
            Contests AS C
        WHERE
            S.ContestId = C.ContestId
            AND C.ContestName = :ContestName
    )
