SELECT
    StudentName,
    CourseName
FROM
    (
        SELECT
            DISTINCT Students.StudentId,
            StudentName,
            Courses.CourseId,
            CourseName
        FROM
            Students,
            Plan,
            Courses
        WHERE
            Students.GroupId = Plan.GroupId
            AND Plan.CourseId = Courses.CourseId
            AND (
                NOT EXISTS (
                    SELECT
                        Marks.StudentId,
                        Marks.CourseId
                    FROM
                        Marks
                    WHERE
                        Marks.StudentId = Students.StudentId
                        AND Marks.CourseId = Courses.CourseId
                        AND Mark > 2
                )
                OR NOT EXISTS (
                    SELECT
                        Marks.StudentId,
                        Marks.CourseId
                    FROM
                        Marks
                    WHERE
                        Marks.StudentId = Students.StudentId
                        AND Marks.CourseId = Courses.CourseId
                )
            )
    ) AS Core


    # StudentName     , CourseName             
    # 'Иванов И.И.'   , 'Управление проектами' 
    # 'Иванов И.И.'   , 'ППО'                  
    # 'Иванов И.И.'   , 'Теория информации'    
    # 'Иванов И.И.'   , 'Технологии Java'      
    # 'Петров П.П.'   , 'ППО'                  
    # 'Петров П.П.'   , 'Теория информации'    
    # 'Петров П.П.'   , 'Технологии Java'      
    # 'Петров П.П.'   , 'Базы данных'          
    # 'Сидров С.С.'   , 'Базы данных'          
    # 'Сидров С.С.'   , 'ППО'                  
    # 'Сидров С.С.'   , 'Теория информации'    
    # 'Сидров С.С.'   , 'Технологии Java'      
    # 'Безымянный Б.Б', 'Математический анализ'
    # 'Безымянный Б.Б', 'Технологии Java'      
    # 'Иксов И.И'     , 'ППО'                  
    # 'Иксов И.И'     , 'Теория информации'    
    # 'Иксов И.И'     , 'Технологии Java'      
    # 'Игреков И.И'   , 'ППО'                  
    # 'Игреков И.И'   , 'Теория информации'    
    # 'Игреков И.И'   , 'Технологии Java'  
