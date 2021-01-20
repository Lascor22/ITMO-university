module Block1Task1
        (
          DayOfWeek(Friday,
                    Monday,
                    Saturday,
                    Sunday,
                    Thursday,
                    Tuesday,
                    Wednesday
            ),
          nextDay,
          afterDays,
          isWeekend,
          daysToParty
        ) where

data DayOfWeek
    = Monday
    | Tuesday
    | Wednesday
    | Thursday
    | Friday
    | Saturday
    | Sunday
    deriving Show


instance Eq DayOfWeek where
    (==) a b = show a == show b

instance  Enum DayOfWeek  where
    toEnum 0 = Monday
    toEnum 1 = Tuesday
    toEnum 2 = Wednesday
    toEnum 3 = Thursday
    toEnum 4 = Friday
    toEnum 5 = Saturday
    toEnum 6 = Sunday
    toEnum n = toEnum (mod n 7)

    fromEnum  Monday    =  0
    fromEnum  Tuesday   =  1
    fromEnum  Wednesday =  2
    fromEnum  Thursday  =  3
    fromEnum  Friday    =  4
    fromEnum  Saturday  =  5
    fromEnum  Sunday    =  6

nextDay :: DayOfWeek -> DayOfWeek
nextDay = succ

afterDays :: DayOfWeek -> Int -> DayOfWeek
afterDays d 0 = d
afterDays d n = afterDays (nextDay d) (n - 1)

isWeekend :: DayOfWeek -> Bool
isWeekend Saturday = True
isWeekend Sunday   = True
isWeekend _        = False

daysToParty :: DayOfWeek -> Int
daysToParty Friday = 0
daysToParty day    = (+) 1 $ daysToParty $ nextDay day
