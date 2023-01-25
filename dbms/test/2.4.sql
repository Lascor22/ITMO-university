-- Задачи, решённые всеми сессиями, участвовашими в соревновании (ContestId, Letter).
SELECT
    P.ContestId,
    P.Letter
FROM
    Problems AS P
WHERE
    NOT EXISTS (
        SELECT
            RunId,
            max(SubmitTime)
        FROM
            Runs AS R,
            Sessions AS S
        WHERE
            P.ContestId = S.ContestId
            AND R.SessionId = S.SessionId
            AND R.Letter = P.Letter
            AND R.Accepted = 0
        GROUP BY
            SessionId
    )
    AND NOT EXISTS (
        SELECT
    )
