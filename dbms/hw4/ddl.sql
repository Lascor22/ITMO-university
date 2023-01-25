CREATE TABLE Groups (
    GroupId int PRIMARY KEY,
    GroupName VARCHAR(8) NOT NULL UNIQUE
);

CREATE TABLE Students (
    StudentId int PRIMARY KEY,
    StudentName VARCHAR(64) NOT NULL,
    GroupId int NOT NULL -- constraint fk1
        foreign key(GroupId)
        references Groups(GroupId) 
        on delete cascade
);

CREATE TABLE Lecturers (
    LecturerId int PRIMARY KEY,
    LecturerName VARCHAR(64) NOT NULL
);

CREATE TABLE Courses (
    CourseId int PRIMARY KEY,
    CourseName VARCHAR(128) NOT NULL
);

CREATE TABLE Plan (
    GroupId int NOT NULL,
    CourseId int NOT NULL,
    LecturerId int NOT NULL -- constraint fk2 
    --     foreign key(GroupId) 
    --     references Groups(GroupId),
    -- constraint fk3 
    --     foreign key(CourseId) 
    --     references Courses(CourseId),
    -- constraint fk4 
    --     foreign key(LecturerId) 
    --     references Lecturers(LecturerId)
);

CREATE TABLE Marks (
    StudentId int NOT NULL,
    CourseId int NOT NULL,
    Mark int NOT NULL -- constraint fk5 
    --     foreign key(StudentId) 
    --     references Students(StudentId),
    -- constraint fk6 
    --     foreign key(CourseId) 
    --     references Courses(CourseId)
);
