START TRANSACTION ISOLATION LEVEL READ COMMITTED
-- т.к. мы сначала добавляем новую запись в PurchasedSeats, а потом удаляем 
-- запись из ReservedSeats, то нам достаточно read committed, т.к. мы читаем 
-- из каждой таблицы один раз, а первичный ключ в PurchasedSeats не дает нам 
-- купить одно и то же место.
