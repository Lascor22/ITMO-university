SELECT DISTINCT
    TeamId
FROM
    Runs AS R,
    Sessions AS S
WHERE
    R.SessionId = S.SessionId
    AND S.ContestId = :ContestId
    AND R.Accepted = 0
