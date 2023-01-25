CREATE OR REPLACE FUNCTION Reserve(
    userIdArg INT, 
    userPasswordArg VARCHAR(64), 
    flightIdArg INT, 
    seatNoArg VARCHAR(4)
) 
RETURNS BOOLEAN AS 
$$ 
DECLARE 
    currTime TIMESTAMP := NOW();
    diffTime INTERVAL := INTERVAL '3 days';
    lastReserveTime TIMESTAMP;
    fTime TIMESTAMP;
    seatIdArg INT;
BEGIN 
    fTime := (SELECT FlightTime FROM Flights WHERE FlightId = flightIdArg);

    IF fTime < currTime THEN 
        RETURN FALSE;
    END IF;

    IF seatNoArg NOT IN (SELECT SeatNo FROM Seats NATURAL JOIN Flights WHERE FlightId = flightIdArg) THEN
        return FALSE;
    END IF;

    IF seatNoArg IN (SELECT SeatNo FROM Seats NATURAL JOIN Flights NATURAL JOIN PurchasedSeats WHERE FlightId = flightIdArg) THEN
        return FALSE;
    END IF;

    seatIdArg := (SELECT SeatId FROM Seats NATURAL JOIN Flights WHERE FlightId = flightIdArg AND SeatNo = seatNoArg);

    IF flightIdArg IN (SELECT FlightId FROM ReservedSeats WHERE UserId = userIdArg AND SeatId = seatIdArg) THEN 
        lastReserveTime := (SELECT ReservedTime FROM ReservedSeats WHERE FlightId = flightIdArg AND SeatId = seatIdArg);
        IF lastReserveTime + diffTime < currTime THEN
            DELETE FROM ReservedSeats WHERE FlightId = flightIdArg AND SeatId = seatIdArg; 
            INSERT INTO ReservedSeats(FlightId, SeatId, UserId, ReservedTime) VALUES (flightIdArg, seatIdArg, userIdArg, currTime);
            return TRUE;
        ELSE 
            RETURN FALSE;
        END IF;
    ELSE
        if userPasswordArg NOT IN (SELECT UserPassword FROM Users WHERE UserId = userIdArg) THEN
            RETURN FALSE;
        END IF;
        DELETE FROM ReservedSeats WHERE FlightId = flightIdArg AND SeatId = seatIdArg;
        INSERT INTO ReservedSeats(FlightId, SeatId, UserId, ReservedTime) VALUES (flightIdArg, seatIdArg, userIdArg, currTime);
        RETURN TRUE;
    END IF;
END;
$$ LANGUAGE plpgsql;
