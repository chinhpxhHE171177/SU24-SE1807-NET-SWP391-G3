-- Tạo cơ sở dữ liệu
CREATE DATABASE SampleDB;
GO

-- Sử dụng cơ sở dữ liệu
USE SampleDB;
GO

-- Tạo bảng Role
CREATE TABLE Role (
    id INT IDENTITY(1,1) PRIMARY KEY,
    rolename NVARCHAR(50) NOT NULL,
	description NVARCHAR(250) NULL,
	createdAt DATETIME NOT NULL DEFAULT GETDATE()
);

-- Bảng quyền (Permissions) cho từng vai trò trên mỗi trang
CREATE TABLE Permissions (
    PermissionID INT PRIMARY KEY IDENTITY(1,1),
    RoleID INT,
    PageName VARCHAR(100),
    CanAccess BIT,
    CanAdd BIT,
    CanEdit BIT,
    CanDelete BIT,
    FOREIGN KEY (RoleID) REFERENCES Role(id) ON DELETE CASCADE
);

-- Tạo bảng Users
CREATE TABLE Users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    code NVARCHAR(50) NOT NULL UNIQUE,
    name NVARCHAR(100) NOT NULL,
    gender NVARCHAR(10) CHECK (gender IN ('Male', 'Female')) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    roleId INT NOT NULL FOREIGN KEY REFERENCES Role(id),
    createdAt DATETIME NOT NULL DEFAULT GETDATE()
);

-- Tạo bảng Lines
CREATE TABLE Lines (
    lineId INT IDENTITY(1,1) PRIMARY KEY,
    lineName NVARCHAR(100) NOT NULL
);

-- Tạo bảng Codes
CREATE TABLE Codes (
    id INT IDENTITY(1,1) PRIMARY KEY,
    code NVARCHAR(50) NOT NULL UNIQUE,
    name NVARCHAR(100) NOT NULL,
    lineId INT NOT NULL FOREIGN KEY REFERENCES Lines(lineId) ON DELETE CASCADE,
    image NVARCHAR(255),
    createdDate DATETIME NOT NULL DEFAULT GETDATE()
);


-- Tạo bảng CodeAPI
CREATE TABLE CodeAPI (
    idApi INT IDENTITY(1,1) PRIMARY KEY,
	code NVARCHAR(50) NOT NULL UNIQUE,
	image NVARCHAR(255),
	createdDate DATETIME NOT NULL DEFAULT GETDATE(),
    codeId INT NOT NULL FOREIGN KEY REFERENCES Codes(id) ON DELETE CASCADE,
);

-- Tạo bảng User_Lines
CREATE TABLE User_Lines (
    userId INT NOT NULL FOREIGN KEY REFERENCES Users(id) ON DELETE CASCADE,
    lineId INT NOT NULL FOREIGN KEY REFERENCES Lines(lineId) ON DELETE CASCADE,
    PRIMARY KEY (userId, lineId)
);

-- Cau lenh insert --
INSERT INTO Role (rolename, createdAt)
VALUES ('admin', GETDATE()), ('user', GETDATE());

INSERT INTO Users (code, name, gender, password, roleId, createdAt) VALUES 
('S00012', N'Phạm Xuân Chinh', 'Male', '12345678', 1, '2023-01-01T00:00:00'),
('S00011', N'Lê Hoàng Anh', 'Male', '12345678', 2, '2023-01-01T00:00:00');

INSERT INTO Lines (lineName) VALUES 
('APM 3'),
('MCV-e');

INSERT INTO Codes (code, name, lineId, image, createdDate) VALUES 
('CODE123', 'Sample Code', 1, 'https://cdn.tuoitre.vn/471584752817336320/2024/12/14/d07c54192fa295fcccb3-1734156644236437084792.jpg', '2023-01-01T00:00:00'),
('CODE124', 'Sample Code 2', 2, 'https://media-cdn-v2.laodong.vn/storage/newsportal/2024/12/20/1438144/12.jpg', '2023-01-02T00:00:00'),
('CODE125', 'Sample Code 3', 1, 'https://i.pinimg.com/236x/d0/3d/a6/d03da6b038d665d9518a046a1f9f1a14.jpg', '2023-01-03T00:00:00');

INSERT INTO User_Lines (userId, lineId) VALUES 
(1, 1),
(1, 2),
(2, 2);


-- Cau lenh truy van -- 

SELECT u.*, r.rolename, STRING_AGG(l.lineName, ', ') AS Lines
FROM Users u
JOIN Role r ON u.roleId = r.id
JOIN User_Lines ul ON u.id = ul.userId
JOIN Lines l ON ul.lineId = l.lineId
GROUP BY u.id, u.code, u.name, u.gender, 
u.password, u.roleId, u.createdAt, r.rolename;



SELECT image from Codes WHERE code = 'CODE123'


INSERT INTO CodeAPI(code, image, createdDate, codeId) Values 
('CODE123', 'https://cdn.tuoitre.vn/471584752817336320/2024/12/14/d07c54192fa295fcccb3-1734156644236437084792.jpg', GETDATE(), 1)

DROP Table CodeAPI

SELECT ca.*, c.name, c.lineId, l.lineName
FROM CodeApi ca
JOIN Codes c ON ca.codeId = c.id
LEFT JOIN Lines l ON c.lineId = l.lineId;

SELECT * FROM CodeApi

SELECT COUNT(*) AS count FROM CodeAPI

Update CodeApi SET code = 'CODE123'






