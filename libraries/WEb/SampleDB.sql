-- Tạo cơ sở dữ liệu
CREATE DATABASE SampleDB;
GO

-- Sử dụng cơ sở dữ liệu
USE SampleDB;
GO

-- Tạo bảng Role
CREATE TABLE Role (
    id INT PRIMARY KEY,
    rolename NVARCHAR(50) NOT NULL
);

-- Tạo bảng Users
CREATE TABLE Users (
    id INT PRIMARY KEY,
    code NVARCHAR(50) NOT NULL UNIQUE,
    name NVARCHAR(100) NOT NULL,
    gender NVARCHAR(10) CHECK (gender IN ('Male', 'Female')) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    roleId INT NOT NULL FOREIGN KEY REFERENCES Role(id),
    createdAt DATETIME NOT NULL DEFAULT GETDATE()
);

-- Tạo bảng Lines
CREATE TABLE Lines (
    lineId INT PRIMARY KEY,
    lineName NVARCHAR(100) NOT NULL
);

-- Tạo bảng Codes
-- Tạo bảng Codes
CREATE TABLE Codes (
    id INT PRIMARY KEY,
    code NVARCHAR(50) NOT NULL UNIQUE,
    name NVARCHAR(100) NOT NULL,
    lineId INT NOT NULL FOREIGN KEY REFERENCES Lines(lineId) ON DELETE CASCADE,
    image NVARCHAR(255),
    createdDate DATETIME NOT NULL DEFAULT GETDATE()
);

-- Tạo bảng User_Lines
CREATE TABLE User_Lines (
    userId INT NOT NULL FOREIGN KEY REFERENCES Users(id),
    lineId INT NOT NULL FOREIGN KEY REFERENCES Lines(lineId) ON DELETE CASCADE,
    PRIMARY KEY (userId, lineId)
);


INSERT INTO Role (id, rolename)
VALUES (1, 'admin'), (2, 'user');

INSERT INTO Users (id, code, name, gender, password, roleId, createdAt) VALUES 
(1, 'S00012', N'Phạm Xuân Chinh', 'Male', '123456', 1, '2023-01-01T00:00:00'),
(2, 'S00011', N'Lê Hoàng Anh', 'Male', '123456', 2, '2023-01-01T00:00:00');

INSERT INTO Lines (lineId, lineName) VALUES 
(1, 'APM 3'),
(2, 'MCV-e');


INSERT INTO Codes (id, code, name, lineId, image, createdDate) VALUES 
(1, 'CODE123', 'Sample Code', 1, 'https://via.placeholder.com/150', '2023-01-01T00:00:00'),
(2, 'CODE124', 'Sample Code 2', 2, 'https://via.placeholder.com/150', '2023-01-02T00:00:00'),
(3, 'CODE125', 'Sample Code 3', 1, 'https://via.placeholder.com/150', '2023-01-03T00:00:00');

INSERT INTO User_Lines (userId, lineId) VALUES 
(1, 1),
(1, 2),
(2, 2);


SELECT * FROM Codes







