CREATE OR REPLACE FUNCTION FlightsStatistics(
    userIdArg INT, 
    userPasswordArg VARCHAR(64)
) 
RETURNS TABLE (
    fId INT,
    canReserve BOOLEAN,
    canBuy BOOLEAN,
    freeCount BIGINT,
    reservedCount BIGINT,
    boughtCount BIGINT
) AS
$$ 
DECLARE 
    currTime TIMESTAMP := NOW();
    diffTime INTERVAL := INTERVAL '3 days';
BEGIN 
    RETURN QUERY 
        SELECT
        FlightId AS fId,
        (
            SELECT
                count(unnest) > 0
            FROM
                unnest(FreeSeats(Flights.FlightId))
        ) AS canReserve,
        (
            SELECT
                count(unnest) > 0
            FROM
                (
                    SELECT
                        unnest
                    FROM
                        unnest(FreeSeats(Flights.FlightId))
                    UNION
                    SELECT
                        SeatNo AS unnest
                    FROM
                        ReservedSeats 
                        NATURAL JOIN Users
                        NATURAL JOIN Seats
                    WHERE
                        FlightId = Flights.FlightId
                        AND ReservedTime + diffTime > currTime
                        AND UserId = userIdArg
                        AND UserPassword = userPasswordArg
                ) subreq
        ) AS canBuy,
        (
            SELECT
                count(unnest)
            FROM
                unnest(FreeSeats(Flights.FlightId))
        ) AS freeCount,
        (
            SELECT
                count(seatId)
            FROM
                ReservedSeats
            WHERE
                FlightId = Flights.FlightId
                AND ReservedTime + diffTime > currTime
        ) AS reservedCount,
        (
            SELECT
                count(SeatId)
            FROM
                PurchasedSeats
            WHERE
                FlightId = Flights.FlightId
        ) AS boughtCount
        FROM
            Flights;
END;
$$ LANGUAGE plpgsql;
