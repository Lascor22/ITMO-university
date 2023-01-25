CREATE OR REPLACE FUNCTION FreeSeats(flightIdArg INT) 
RETURNS VARCHAR(4)[] AS 
$$ 
DECLARE 
    currTime TIMESTAMP := NOW();
    fTime TIMESTAMP;
    freePlaces VARCHAR(4)[] := ARRAY[]::VARCHAR(4)[];
    seatNoIter VARCHAR(4);
BEGIN 
    fTime := (SELECT FlightTime FROM Flights WHERE FlightId = flightIdArg);

    IF fTime < currTime THEN 
        RETURN freePlaces;
    END IF;

    FOR seatNoIter IN 
        SELECT 
            SeatNo
        FROM
            Seats
            NATURAL JOIN Flights
        WHERE
            FlightId = flightIdArg
            AND SeatId NOT IN (
                SELECT SeatId FROM ReservedSeats WHERE FlightId = flightIdArg 
                UNION
                SELECT SeatId FROM PurchasedSeats WHERE FlightId = flightIdArg    
            )
    LOOP 
        freePlaces := array_append(freePlaces, seatNoIter);
    END LOOP;
    RETURN freePlaces;
END;
$$ LANGUAGE plpgsql;
