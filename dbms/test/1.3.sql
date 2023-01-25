SELECT DISTINCT
    RunId,
    SessionId,
    Letter,
    SubmitTime
FROM
    Runs AS R 
    NATURAL JOIN Sessions AS S
WHERE
    ContestId = :ContestId
    AND Accepted = 0
