UPDATE
    Runs
SET
    Accepted = 0
WHERE
    RunId IN (
        SELECT
            R.RunId
        FROM Runs AS R
        NATURAL JOIN
            (
                SELECT
                    SessionId,
                    min(SubmitTime) AS SubmitTime,
                    Letter
                FROM
                    Runs
                GROUP BY
                    SessionId,
                    Letter
            ) AS Q
    )
update Runs
set Accepted = 0
where RunId in (
    select RunId
    from Runs
             inner join
         (
             select SessionId, MIN(SubmitTime) as SubmitTime, Letter
             from Runs
             group by SessionId, Letter
         ) as SessionMaxs
         on Runs.SessionId = SessionMaxs.SessionId
             and Runs.SubmitTime = SessionMaxs.SubmitTime
             and Runs.Letter = SessionMaxs.Letter
);
