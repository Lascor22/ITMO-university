-- psql (PostgreSQL) 13.4
CREATE OR REPLACE FUNCTION TriggerFunction() 
RETURNS TRIGGER AS $TriggerFunction$
BEGIN 
    IF NEW.Mark < OLD.Mark THEN
        NEW.Mark = OLD.Mark;
    END IF;
    RETURN NEW;
END;
$TriggerFunction$ LANGUAGE plpgsql;

CREATE TRIGGER PreserverMarks 
BEFORE UPDATE
ON Marks 
FOR EACH ROW
EXECUTE PROCEDURE TriggerFunction();
