UPDATE
    Runs
SET
    Accepted = 1
WHERE
    RunId IN (
        SELECT
            R.RunId
        FROM Runs as R
        NATURAL JOIN (
                SELECT
                    SessionId,
                    max(SubmitTime) AS SubmitTime
                FROM
                    Runs
                WHERE Accepted = 0
                GROUP BY
                    SessionId
            ) AS Q
    )
