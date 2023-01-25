CREATE OR REPLACE FUNCTION FlightStat(
    userIdArg INT, 
    userPasswordArg VARCHAR(64),
    flightIdArg INT
) 
RETURNS TABLE (
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
            canReserveQuery.canReserve,
            canBuyQuery.canBuy,
            freeCountQuery.freeCount,
            reservedCountQuery.reservedCount,
            boughtCountQuery.boughtCount
        FROM
            (
                SELECT
                    count(unnest) > 0 AS canReserve
                FROM
                    unnest(FreeSeats(FlightIdArg))
            ) AS canReserveQuery,
            (
                SELECT
                    count(unnest) > 0 AS canBuy
                FROM
                    (
                        SELECT
                            unnest
                        FROM
                            unnest(FreeSeats(FlightIdArg))
                        UNION
                        SELECT
                            SeatNo AS unnest
                        FROM
                            ReservedSeats NATURAL
                            JOIN Users NATURAL
                            JOIN Seats
                        WHERE
                            FlightId = FlightIdArg
                            AND ReservedTime + diffTime > currTime
                            AND UserId = UserIdArg
                            AND UserPassword = userPasswordArg
                    ) q1
            ) AS canBuyQuery,
            (
                SELECT
                    count(unnest) AS FreeCount
                FROM
                    unnest(FreeSeats(FlightIdArg))
            ) AS freeCountQuery,
            (
                SELECT
                    count(SeatId) AS reservedCount
                FROM
                    ReservedSeats
                WHERE
                    FlightId = FlightIdArg
                    AND ReservedTime + diffTime > currTime
            ) AS reservedCountQuery,
            (
                SELECT
                    count(SeatId) AS boughtCount
                FROM
                    PurchasedSeats
                WHERE
                    FlightId = FlightIdArg
            ) AS boughtCountQuery;
END;
$$ LANGUAGE plpgsql;
