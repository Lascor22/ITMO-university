CREATE TABLE Flights(
    FlightId INT PRIMARY KEY,
    FlightTime TIMESTAMP NOT NULL,
    PlaneId INT NOT NULL
);

CREATE TABLE Seats(
    SeatId INT PRIMARY KEY,
    PlaneId INT,
    SeatNo VARCHAR(4) NOT NULL
);

-- информация о пользователе UserId и его пароле UserPassword
CREATE TABLE Users(
    UserId INT PRIMARY KEY,
    UserPassword VARCHAR(64) NOT NULL
);

-- информация о купленном месте SeatId в рейсе FlightId
CREATE TABLE PurchasedSeats(
    FlightId INT NOT NULL,
    SeatId INT NOT NULL,
    PRIMARY KEY (FlightId, SeatId)
);

-- информация о зарезервированном месте SeatId 
-- пользователем UserId в рейсе FlightId в момент ReservedTime
CREATE TABLE ReservedSeats(
    FlightId INT NOT NULL,
    SeatId INT NOT NULL,
    UserId INT NOT NULL,
    ReservedTime TIMESTAMP NOT NULL,
    PRIMARY KEY(FlightId, SeatId)
);
