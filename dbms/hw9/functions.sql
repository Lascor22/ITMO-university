create or replace function freeSeats(FlightIdArr int) 
    returns int[]
as
$$
declare
    currentTime timestamp := now();
    fTime timestamp;
    freePlaces int[] := array[]::int[];
    iter int := 1;
    seatNoTmp int;
    flightAmountSeats int := 0;
begin
    fTime := (select FligtTime from Flights where FlightId=FlightIdArr);
    if fTime < currentTime then
        return freePlaces;
    end if;

    flightAmountSeats := (select SeatNo from Seats where PlaneId in (select (PlaneId) from Flights where FlightId=FlightIdArr));
    while iter <= flightAmountSeats loop
        freePlaces := array_append(freePlaces, iter);
        iter := iter + 1;
    end loop;

    for seatNoTmp in select (SeatNo) from BoughtSeats where FlightId=FlightIdArr loop
        freePlaces := array_remove(freePlaces, seatNoTmp);    
    end loop;

    for seatNoTmp in select (SeatNo) from ReservedSeats where FlightId=FlightIdArr loop
        freePlaces := array_remove(freePlaces, seatNoTmp);    
    end loop;

    return freePlaces;
end;
$$
language plpgsql;








create or replace function reserve(UserIdArr int, PassArr varchar(60), FlightIdArr int, SeatNoArr int) 
    returns boolean
as
$$
declare
    currentTime timestamp := now();
    diffTime interval := interval '3 Hours';
    bron timestamp;
    fTime timestamp;
    res boolean;
    flightAmountSeats int := 0;
begin
    fTime := (select FligtTime from Flights where FlightId=FlightIdArr);
    if fTime < currentTime then
        return false;
    end if;

    flightAmountSeats := (select SeatNo from Seats where PlaneId in (select (PlaneId) from Flights where FlightId=FlightIdArr));
    if SeatNoArr > flightAmountSeats then  
        return false;
    end if;

    if FlightIdArr in (select FlightId from BoughtSeats where FlightId=FlightIdArr and SeatNo=SeatNoArr) then 
        return false;
    end if;

    if FlightIdArr in (select FlightId from ReservedSeats where FlightId=FlightIdArr and UserId=UserIdArr and SeatNo=SeatNoArr) then 
        bron := (select ReservedTime from ReservedSeats where FlightId=FlightIdArr and SeatNo=SeatNoArr);
        if bron + diffTime < currentTime then
            delete from ReservedSeats where FlightId=FlightIdArr and SeatNo=SeatNoArr;
            insert into ReservedSeats(FlightId, SeatNo, UserId, ReservedTime) values (FlightIdArr, SeatNoArr, UserIdArr, currentTime);
            return true;
        else
            return false;
        end if;
    else
        if PassArr not in (select UserPassword from Users where UserId=UserIdArr) then
            return false;
        end if;
        delete from ReservedSeats where FlightId=FlightIdArr and SeatNo=SeatNoArr;
        insert into ReservedSeats(FlightId, SeatNo, UserId, ReservedTime) values (FlightIdArr, SeatNoArr, UserIdArr, currentTime);
        return true;
    end if;
end;
$$
language plpgsql;








create or replace function extendReservation(UserIdArr int, PassArr varchar(60), FlightIdArr int, SeatNoArr int) 
    returns boolean
as
$$
declare
    currentTime timestamp := now();
    diffTime interval := interval '3 Hours';
    bron timestamp;
    fTime timestamp;
    res boolean;
    flightAmountSeats int := 0;
begin
    fTime := (select FligtTime from Flights where FlightId=FlightIdArr);
    if fTime < currentTime then
        return false;
    end if; 

    flightAmountSeats := (select SeatNo from Seats where PlaneId in (select (PlaneId) from Flights where FlightId=FlightIdArr));
    if SeatNoArr > flightAmountSeats then  
        return false;
    end if;


    if FlightIdArr in (select FlightId from BoughtSeats where FlightId=FlightIdArr and SeatNo=SeatNoArr) then 
        return false;
    end if;

    if FlightIdArr in (select FlightId from ReservedSeats where FlightId=FlightIdArr and UserId=UserIdArr and SeatNo=SeatNoArr) then 
        bron := (select ReservedTime from ReservedSeats where FlightId=FlightIdArr and SeatNo=SeatNoArr);
        if bron + diffTime > currentTime then
            delete from ReservedSeats where FlightId=FlightIdArr and SeatNo=SeatNoArr;
            insert into ReservedSeats(FlightId, SeatNo, UserId, ReservedTime) values (FlightIdArr, SeatNoArr, UserIdArr, currentTime);
            return true;
        else
            return false;
        end if;
    else
        return false;
    end if;
end;
$$
language plpgsql;








create or replace function buyFree(FlightIdArr int, SeatNoArr int) 
    returns boolean
as
$$
declare
    currentTime timestamp := now();
    diffTime interval := interval '3 Hours';
    bron timestamp;
    fTime timestamp;
    res boolean;
    flightAmountSeats int := 0;
begin
    fTime := (select FligtTime from Flights where FlightId=FlightIdArr);
    if fTime < currentTime then
        return false;
    end if; 

    flightAmountSeats := (select SeatNo from Seats where PlaneId in (select (PlaneId) from Flights where FlightId=FlightIdArr));
    if SeatNoArr > flightAmountSeats then  
        return false;
    end if;


    if FlightIdArr in (select FlightId from BoughtSeats where FlightId=FlightIdArr and SeatNo=SeatNoArr) then 
        return false;
    end if;

    if FlightIdArr in (select FlightId from ReservedSeats where FlightId=FlightIdArr and SeatNo=SeatNoArr) then 
        bron := (select ReservedTime from ReservedSeats where FlightId=FlightIdArr and SeatNo=SeatNoArr);
        if bron + diffTime < currentTime then
            insert into BoughtSeats(FlightId, SeatNo) values (FlightIdArr, SeatNoArr);
            delete from ReservedSeats where FlightId=FlightIdArr and SeatNo=SeatNoArr;
            return true;
        else
            return false;
        end if;
    else
        insert into BoughtSeats(FlightId, SeatNo) values (FlightIdArr, SeatNoArr);
        return true;
    end if;
end;
$$
language plpgsql;








create or replace function buyReserved(UserIdArr int, PassArr varchar(60), FlightIdArr int, SeatNoArr int) 
    returns boolean
as
$$
declare
    currentTime timestamp := now();
    diffTime interval := interval '3 Hours';
    bron timestamp;
    fTime timestamp;
    res boolean;
    reserUserId int;
    flightAmountSeats int := 0;
begin
    fTime := (select FligtTime from Flights where FlightId=FlightIdArr);
    if fTime < currentTime then
        return false;
    end if; 

    flightAmountSeats := (select SeatNo from Seats where PlaneId in (select (PlaneId) from Flights where FlightId=FlightIdArr));
    if SeatNoArr > flightAmountSeats then  
        return false;
    end if;


    if FlightIdArr in (select FlightId from BoughtSeats where FlightId=FlightIdArr and SeatNo=SeatNoArr) then 
        return false;
    end if;

    if FlightIdArr in (select FlightId from ReservedSeats where FlightId=FlightIdArr and UserId=UserIdArr and SeatNo=SeatNoArr) then 
        bron := (select ReservedTime from ReservedSeats where FlightId=FlightIdArr and SeatNo=SeatNoArr);
        if bron + diffTime > currentTime then
            if PassArr in (select UserPassword from Users where UserId=UserIdArr) then
                reserUserId := (select UserId from ReservedSeats where FlightId=FlightIdArr and SeatNo=SeatNoArr);
                if reserUserId = UserIdArr then
                    delete from ReservedSeats where FlightId=FlightIdArr and SeatNo=SeatNoArr;
                    insert into BoughtSeats(FlightId, SeatNo) values (FlightIdArr, SeatNoArr);
                    return true;
                end if;
                return false;
            else
                return false;
            end if;
            return false;
        else
            return false;
        end if;
    else
        return false;
    end if;
end;
$$
language plpgsql;








create or replace function flightsStatistics(UserIdArg int, PassArg text)
    returns table (fId int, canReserve boolean, canBuy boolean, freeCount bigint, reservedCount bigint, boughtCount bigint) as
$$
declare
    currentTime timestamp := now();
    diffTime interval := interval '3 Hours';
begin
    return query select FlightId as fId,
                 (select count(unnest) > 0 from unnest(freeSeats(Flights.FlightId))) as canReserve,
                 (select count(unnest) > 0
                    from (select unnest
                        from unnest(freeSeats(Flights.FlightId))
                        union 
                        select seatNo as unnest 
                            from ReservedSeats natural join users
                            where FlightId = Flights.FlightId and ReservedTime + diffTime > currentTime and UserId=UserIdArg and UserPassword=PassArg) subreq
                 ) as canBuy ,
                 (select count(unnest) from unnest(freeSeats(Flights.FlightId))) as freeCount,
                 (select count(seatNo) from BoughtSeats where flightId=Flights.FlightId) as boughtCount,
                 (select count(seatNo) 
                  from ReservedSeats 
                  where FlightId=Flights.FlightId and ReservedTime + diffTime > currentTime) as reservedCount
                 from Flights;
end;
$$
language plpgsql;








create or replace function flightStat(UserIdArg int, PassArg text, FlightIdArg int)
    returns table (canReserve boolean, canBuy boolean, freeCount bigint, reservedCount bigint, boughtCount bigint) as
$$
declare
    currentTime timestamp := now();
    diffTime interval := interval '3 Hours';
begin
    return query select req1.canReserve, req2.canBuy, req3.freeCount, req4.reservedCount, req5.boughtCount 
                 from 
                     (select count(unnest) > 0 as canReserve from unnest(freeSeats(FlightIdArg))) req1,
                     (select count(unnest) > 0 as canBuy
                        from (select unnest
                            from unnest(freeSeats(FlightIdArg))
                            union 
                            select seatNo as unnest 
                                from ReservedSeats natural join users
                                where FlightId=FlightIdArg and ReservedTime + diffTime > currentTime and UserId=UserIdArg and UserPassword=PassArg) subreq
                     ) req2 ,
                     (select count(unnest) as freeCount from unnest(freeSeats(FlightIdArg))) req3,
                     (select count(seatNo) as boughtCount from BoughtSeats where flightId=FlightIdArg) req5,
                     (select count(seatNo) as reservedCount 
                      from ReservedSeats 
                      where FlightId=FlightIdArg and ReservedTime + diffTime > currentTime) req4;
end;
$$
language plpgsql;
