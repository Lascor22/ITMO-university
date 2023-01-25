SELECT StudentId, StudentName, GroupName
    FROM Students
    JOIN Groups USING(GroupId)
WHERE StudentId = :StudentId;