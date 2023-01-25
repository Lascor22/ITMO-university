INSERT INTO
    Flights(FlightId, FlightTime, PlaneId)
VALUES
    (1, '2022-03-01 03:30:00-03', 1),
    (2, '2021-12-17 05:20:00-03', 1),
    (3, '2022-03-01 04:30:00-03', 2);

INSERT INTO
    Seats(SeatId, PlaneId, SeatNo)
VALUES
    (1, 1, '1A'),
    (2, 1, '1B'),
    (3, 1, '1C'),
    (4, 1, '1D'),
    (5, 1, '1E'),
    (6, 1, '1F'),
    (7, 2, '1A'),
    (8, 2, '1B'),
    (9, 2, '1C'),
    (10, 2, '1D'),
    (11, 2, '1E'),
    (12, 2, '1F'),
    (13, 2, '2A');

INSERT INTO
    Users(UserId, UserPassword)
VALUES
    (1, 'qwerty123'),
    (2, 'pass'),
    (3, '123456');

INSERT INTO
    PurchasedSeats(FlightId, SeatId)
VALUES
    (1, 3),
    (3, 10),
    (1, 5);

INSERT INTO
    ReservedSeats(FlightId, SeatId, UserId, ReservedTime)
VALUES
    (1, 2, 1, '2021-02-24 11:35:20-03'),
    (3, 13, 1, '2021-02-24 13:34:34-03'),
    (1, 4, 2, '2021-02-24 19:19:25-03');
