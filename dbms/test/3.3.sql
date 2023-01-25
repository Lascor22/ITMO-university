INSERT INTO
    Sessions (SessionId, TeamId, ContestId, START)
SELECT
    Q.NewId,
    S.TeamId,
    S.ContestId,
    CURRENT_TIMESTAMP
FROM
    Sessions AS S,
    (
        SELECT
            max(SessionId) + 1 AS NewId
        FROM
            Sessions
    ) AS Q
WHERE
    S.ContestId = :ContestId
