DELETE FROM
    Runs
WHERE
    SessionId IN (
        SELECT
            S.SessionId
        FROM
            Sessions AS S
        WHERE
            S.TeamId = :TeamId
    )
