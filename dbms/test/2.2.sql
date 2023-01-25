SELECT T.TeamName
  FROM Teams AS T
 WHERE T.TeamId NOT IN (
           SELECT DISTINCT S.TeamId
             FROM Runs AS R,
                  Sessions AS S
            WHERE R.SessionId = S.SessionId AND 
                  R.Letter = :Letter AND 
                  S.ContestId = :ContestId AND 
                  R.Accepted = 1
       );
