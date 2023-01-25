MERGE INTO Marks M
USING NewMarks AS N 
ON M.StudentId = N.StudentId
AND M.CourseId = N.CourseId
WHEN MATCHED THEN
UPDATE
SET
    M.Mark = (
        CASE
            WHEN N.Mark > M.Mark THEN N.Mark
            ELSE M.Mark
        END
    )
WHEN NOT MATCHED THEN
INSERT
    (StudentId, CourseId, Mark)
VALUES
    (N.StudentId, N.CourseId, N.Mark)
