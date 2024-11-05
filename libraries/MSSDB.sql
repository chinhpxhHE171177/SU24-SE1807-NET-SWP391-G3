CREATE DATABASE MSSDB

USE MSSDB

-- 1. Departments Table
CREATE TABLE Departments (
    DepartmentID INT PRIMARY KEY IDENTITY(1,1),
    DepartmentName VARCHAR(100) NOT NULL,
);

-- 2. Rooms Table
CREATE TABLE Rooms (
    RoomID INT PRIMARY KEY IDENTITY(1,1),
    RoomName VARCHAR(100) NOT NULL,
    DepartmentID INT,
    FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentID)
);

-- 3. Production Lines Table
CREATE TABLE ProductionLines (
    LineID INT PRIMARY KEY IDENTITY(1,1),
    LineName VARCHAR(100) NOT NULL,
    RoomID INT,
    FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID)
);

-- 4. Stages Table
CREATE TABLE Stages (
    StageID INT PRIMARY KEY IDENTITY(1,1),
    StageName VARCHAR(100) NOT NULL,
    LineID INT,
    FOREIGN KEY (LineID) REFERENCES ProductionLines(LineID)
);

-- 5. Equipment Table (thiet bi)
CREATE TABLE Equipment (
    EquipmentID INT PRIMARY KEY IDENTITY(1,1), --ID
	EquipmentCode VARCHAR(50), --CODE
    EquipmentName VARCHAR(255), --NAME
    DateUse DATE,
    Origin NVARCHAR(150),
    YOM INT,
    QRCode VARCHAR(MAX), 
    StageID INT, --STAGE
	Issue NVARCHAR(MAX), -- Issue
	IdCode VARCHAR(250) -- --ID_CODE
    FOREIGN KEY (StageID) REFERENCES Stages(StageID)
);

--ALTER TABLE Equipment 
--ADD Issue NVARCHAR(MAX)
--ALTER TABLE Equipment 
--ADD IdCode VARCHAR(250)

CREATE TABLE Shifts (
    ShiftID INT PRIMARY KEY IDENTITY(1,1),
    ShiftName NVARCHAR(50) NOT NULL,  
    StartTime TIME NOT NULL,          
    EndTime TIME NOT NULL      
);

INSERT INTO Shifts (ShiftName, StartTime, EndTime) VALUES
('Ca 1', '06:00', '14:00'),
('Ca 2', '14:00', '22:00'),
('Ca 3', '22:00', '06:00');


CREATE TABLE ShiftSlots (
    SlotID INT PRIMARY KEY IDENTITY(1,1),
    ShiftID INT,                        -- Mỗi slot thuộc về một ca
    SlotStartTime TIME NOT NULL,         -- Giờ bắt đầu slot
    SlotEndTime TIME NOT NULL,           -- Giờ kết thúc slot
    Duration INT NOT NULL,               -- Thời gian hoạt động thực tế (phút)
    FOREIGN KEY (ShiftID) REFERENCES Shifts(ShiftID)
);


INSERT INTO ShiftSlots (ShiftID, SlotStartTime, SlotEndTime, Duration) VALUES
--ca 1
(1, '06:00', '07:00', 60),
(1, '07:00', '08:00', 60),
(1, '08:00', '09:00', 50), 
(1, '09:00', '10:00', 60), 
(1, '10:00', '11:00', 50), 
(1, '11:00', '12:00', 50), 
(1, '12:00', '13:00', 50), 
(1, '13:00', '14:00', 50), 

-- ca 2
(2, '14:00', '15:00', 60),
(2, '15:00', '16:00', 60),
(2, '16:00', '17:00', 50),
(2, '17:00', '18:00', 60),
(2, '18:00', '19:00', 50),
(2, '19:00', '20:00', 50),
(2, '20:00', '21:00', 50),
(2, '21:00', '22:00', 50),

-- ca 3
(3, '22:00', '23:00', 55),
(3, '23:00', '00:00', 50),
(3, '00:00', '01:00', 50),
(3, '01:00', '02:00', 50),
(3, '02:00', '03:00', 50),
(3, '03:00', '04:00', 50),
(3, '04:00', '05:00', 55),
(3, '05:00', '06:00', 55);

CREATE TABLE StopType (
    TypeID INT PRIMARY KEY IDENTITY(1,1),
	TypeName NVARCHAR(250) NULL
)

ALTER TABLE StopType
ALTER COLUMN TypeName NVARCHAR(250) NULL;

INSERT INTO StopType(TypeName) VALUES
(N'Dừng ngắn'),
(N'Dừng dài'),
(N'Phế phẩm'),
(N'Đổi mã, Thay dao'),
(N'Đầu cuối ca'),
(N'Ngoài ra')

--Update StopType SET TypeName = N'Ngoài ra' WHERE TypeID = 6
--Update StopType SET TypeName = N'Dừng ngắn' WHERE TypeID = 1
--Update StopType SET TypeName = N'Dừng dài' WHERE TypeID = 2
--Update StopType SET TypeName = N'Phế phẩm' WHERE TypeID = 3
--Update StopType SET TypeName = N'Đổi mã, Thay dao' WHERE TypeID = 4
--Update StopType SET TypeName = N'Đầu cuối ca' WHERE TypeID = 5

-- 10. ErrorHistory Table (L?ch s? l?i c?a thi?t b?)
CREATE TABLE ErrorHistory (
    ErrorID INT PRIMARY KEY IDENTITY(1,1),
    EquipmentID INT,                    -- Thi?t b? x?y ra l?i
    ErrorDescription NVARCHAR(MAX),     -- Mô t? l?i
    StartTime DATETIME,                  -- Th?i gian b?t d?u l?i
    EndTime DATETIME,                    -- Th?i gian k?t thúc l?i
    Duration AS DATEDIFF(MINUTE, StartTime, EndTime), -- T?ng th?i gian x?y ra l?i (tính b?ng phút)
    StageID INT,      
	LineID INT NULL,   
	SlotID INT,
	TypeID INT NULL
    FOREIGN KEY (EquipmentID) REFERENCES Equipment(EquipmentID),
    FOREIGN KEY (StageID) REFERENCES Stages(StageID),
	FOREIGN KEY (SlotID) REFERENCES ShiftSlots(SlotID),
	FOREIGN KEY (TypeID) REFERENCES StopType(TypeID) ON DELETE SET NULL,
	FOREIGN KEY (LineID) REFERENCES ProductionLines(LineID) ON DELETE SET NULL
);

--ALTER TABLE ErrorHistory
----ADD LineID INT NULL
--ADD CONSTRAINT FK_ErrorHistory_Lines
--FOREIGN KEY (LineID) REFERENCES ProductionLines(LineID) ON DELETE SET NULL

--ALTER TABLE ErrorHistory
--ADD CONSTRAINT FK_ErrorHistory_Slot FOREIGN KEY (SlotID) REFERENCES ShiftSlots(SlotID);


--ALTER TABLE ErrorHistory 
--ALTER COLUMN ErrorDescription NVARCHAR(MAX);

-- Alter table to drop the ErrorDescription column
--ALTER TABLE ErrorHistory 
--DROP COLUMN IdCode;


--SELECT e.EquipmentID, eh.ErrorDescription FROM ErrorHistory eh 
--JOIN Equipment e ON  e.EquipmentID = eh.EquipmentID
--WHERE e.EquipmentID = 2

--Trigger này sẽ tự động xác định SlotID dựa vào thời g
--ian bắt đầu lỗi (StartTime) 
--và gán vào bản ghi trong bảng ErrorHistory.

CREATE TRIGGER trg_AssignSlotID ON ErrorHistory
AFTER INSERT
AS
BEGIN
    UPDATE eh
    SET eh.SlotID = ss.SlotID
    FROM ErrorHistory eh
    JOIN ShiftSlots ss 
        ON CAST(eh.StartTime AS TIME) BETWEEN ss.SlotStartTime AND ss.SlotEndTime
    WHERE eh.SlotID IS NULL
      AND EXISTS (SELECT 1 FROM inserted i WHERE i.ErrorID = eh.ErrorID);
END;

--INSERT INTO ErrorHistory (EquipmentID, ErrorDescription, StartTime, EndTime, StageID) 
--VALUES 
--(1, 'Conveyor belt malfunction', '2024-01-15 08:00:00', '2024-01-15 09:00:00', 1),
--(1, 'Motor overheating', '2024-01-15 08:05:00', '2024-01-15 08:10:00', 1);

--Truy Vấn Tính Tỷ Lệ Downtime và Gán Ca Theo Thời Gian
SELECT 
    ss.SlotID, 
    s.ShiftName, 
    ss.SlotStartTime, 
    ss.SlotEndTime, 
	eh.StartTime,
	eh.EndTime,
    SUM(eh.Duration) AS DowntimeMinutes, 
    ss.Duration AS TotalSlotMinutes,
    (CAST(SUM(eh.Duration) AS FLOAT) / ss.Duration) * 100 AS DowntimePercentage
FROM 
    ErrorHistory eh
LEFT JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID
LEFT JOIN Shifts s ON ss.ShiftID = s.ShiftID
WHERE eh.Duration <= 5
GROUP BY 
    ss.SlotID, s.ShiftName, ss.SlotStartTime, ss.SlotEndTime, ss.Duration, eh.StartTime, eh.EndTime;


	UPDATE eh
SET eh.SlotID = ss.SlotID
FROM ErrorHistory eh
JOIN ShiftSlots ss 
  ON (
    -- Nếu slot không phải của ca 3 (trường hợp bình thường)
    (ss.SlotStartTime <= ss.SlotEndTime 
     AND CAST(eh.StartTime AS TIME) >= ss.SlotStartTime 
     AND CAST(eh.StartTime AS TIME) < ss.SlotEndTime)
    
    -- Trường hợp đặc biệt của ca 3 (22:00 - 06:00)
    OR 
    (ss.SlotStartTime > ss.SlotEndTime 
     AND (
        CAST(eh.StartTime AS TIME) >= ss.SlotStartTime 
        OR CAST(eh.StartTime AS TIME) < ss.SlotEndTime
     ))
  )
WHERE eh.SlotID IS NULL;


--UPDATE eh
--SET eh.SlotID = ss.SlotID
--FROM ErrorHistory eh
--JOIN ShiftSlots ss 
--  ON CAST(eh.StartTime AS TIME) BETWEEN ss.SlotStartTime AND ss.SlotEndTime
--WHERE eh.SlotID IS NULL;


--UPDATE eh
--SET eh.SlotID = ss.SlotID
--FROM ErrorHistory eh
--JOIN ShiftSlots ss 
--  ON (
--      (ss.SlotStartTime < ss.SlotEndTime AND CAST(eh.StartTime AS TIME) BETWEEN ss.SlotStartTime AND ss.SlotEndTime)
--      OR 
--      (ss.SlotStartTime > ss.SlotEndTime AND (
--          CAST(eh.StartTime AS TIME) >= ss.SlotStartTime OR CAST(eh.StartTime AS TIME) < ss.SlotEndTime
--      ))
--  )
--WHERE eh.SlotID IS NULL;




-- 7. Roles Table
CREATE TABLE Roles (
    RoleID INT PRIMARY KEY IDENTITY(1,1),
    RoleName NVARCHAR(250)
);

---- Bảng Permission
--CREATE TABLE Permissions (
--    PermissionID INT PRIMARY KEY IDENTITY(1,1),
--    PermissionName VARCHAR(50) NOT NULL UNIQUE
--);

---- Bảng RolePermissions
--CREATE TABLE RolePermissions (
--    RoleID INT,
--    PermissionID INT,
--    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID),
--    FOREIGN KEY (PermissionID) REFERENCES Permissions(PermissionID),
--    PRIMARY KEY (RoleID, PermissionID)
--);

----1: truy can, 2:edit, 3: add, 4: delete

--INSERT INTO RolePermissions VALUES 
--(1, 1),
--(1, 2),
--(1, 3),


-- Bảng quyền (Permissions) cho từng vai trò trên mỗi trang
CREATE TABLE Permissions (
    PermissionID INT PRIMARY KEY IDENTITY(1,1),
    RoleID INT,
    PageName VARCHAR(100),
    CanAccess BIT,
    CanAdd BIT,
    CanEdit BIT,
    CanDelete BIT,
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID)
);

-- Quyền cho Điều hành viên
INSERT INTO Permissions (RoleID, PageName, CanAccess, CanAdd, CanEdit, CanDelete) VALUES
-- Admin 
(1, 'Dashboard', 1, 1, 1, 1),
(1, 'DeviceManagement', 1, 1, 1, 1),
(1, 'ShortStopInfo', 1, 1, 1, 1),
(1, 'ReportDetail', 1, 0, 0, 0),
(1, 'ReportTopDetail', 1, 0, 0, 0),
(1, 'UserManagement', 1, 1, 1, 1),
(1, 'UserRole', 1, 1, 1, 1),

-- User
(2, 'Dashboard', 1, 0, 0, 0),
(2, 'DeviceManagement', 1, 0, 0, 0),
(2, 'ShortStopInfo', 1, 0, 0, 0),
(2, 'ReportDetail', 1, 0, 0, 0),
(2, 'ReportTopDetail', 1, 0, 0, 0),
(2, 'UserManagement', 1, 0, 0, 0),
(2, 'UserRole', 1, 0, 0, 0);



-- 8. Users Table
CREATE TABLE Users (
    UserID INT PRIMARY KEY IDENTITY(1,1),
	UserCode VARCHAR(255),
    FullName VARCHAR(100),
    Password VARCHAR(255),
    Email VARCHAR(100),
    Phone VARCHAR(20),
    Address VARCHAR(200),
    Gender VARCHAR(10),
    DateOfBirth DATE,
	Avatar NVARCHAR(255),
    RoleID INT,
	RoomID INT, 
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID),
    FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID)
);

---- 9. (Optional) Permissions Table
--CREATE TABLE Permissions (
--    PermissionID INT PRIMARY KEY IDENTITY(1,1),
--    PermissionName VARCHAR(50) NOT NULL
--);

---- 10. (Optional) User Permissions Table
--CREATE TABLE UserPermissions (
--    UserPermissionID INT PRIMARY KEY IDENTITY(1,1),
--    UserID INT,
--    PermissionID INT,
--    FOREIGN KEY (UserID) REFERENCES Users(UserID),
--    FOREIGN KEY (PermissionID) REFERENCES Permissions(PermissionID)
--);

---- 11. (Optional) Audit Logs Table
--CREATE TABLE AuditLogs (
--    LogID INT PRIMARY KEY IDENTITY(1,1),
--    Action VARCHAR(255) NOT NULL,
--    UserID INT,
--    Timestamp DATETIME NOT NULL DEFAULT GETDATE(),
--    FOREIGN KEY (UserID) REFERENCES Users(UserID)
--);







-- Inserting 8 Departments
INSERT INTO Departments (DepartmentName) VALUES 
('MFG 1'),
('MFG 2'),
('PC'),
('QA'),
('HR'),
('IS'),
('Maintenance'),
('Logistics');


-- Inserting Rooms for each Department
INSERT INTO Rooms (RoomName, DepartmentID) VALUES 
('Pro 1', 2), -- MFG 2
('Pro 2', 1),  --MFG1
('Pro 3', 1),
('Pro 4', 2), -- MFG 2
('PC 1', 3), -- PC
('PC 2', 3),
('PC 3', 3),
('QA 1', 4), -- QA
('QA 2', 4),
('QA 3', 4),
('HR 1', 5), -- HR
('HR 2', 5),
('HR 3', 5),
('IS 1', 6), -- IS
('IS 2', 6),
('IS 3', 6),
('MI 1', 7), -- Maintenance
('MI 2', 7),
('MI 3', 7),
('LG 1', 8), -- Logistics
('LG 2', 8);

-- Inserting Production Lines with Specific Names
INSERT INTO ProductionLines (LineName, RoomID) VALUES 
('Assembly Line A', 2),  -- MFG 1, Room 1.1
('Assembly Line B', 2),  -- MFG 1, Room 1.2
('Packaging Line A', 1),  -- MFG 2, Room 2.1
('Packaging Line B', 4),  -- MFG 2, Room 2.2
('Machining Line A', 5),  -- PC, Room 3.1
('Quality Control Line', 8),  -- QA, Room 4.1
('Human Resources Line', 11),  -- HR, Room 5.1
('IT Support Line', 14),  -- IS, Room 6.1
('Maintenance Line A', 17),  -- Maintenance, Room 7.1
('Logistics Line A', 20);  -- Logistics, Room 8.1


-- Inserting Stages with Specific Names for Each Production Line
INSERT INTO Stages (StageName, LineID) VALUES 
-- Assembly Line A (Line ID: 1)
('Preparation Stage', 1),  -- Stage 1 for Assembly Line A
('Assembly Stage', 1),     -- Stage 2 for Assembly Line A

-- Assembly Line B (Line ID: 2)
('Preparation Stage', 2),  -- Stage 1 for Assembly Line B
('Final Assembly Stage', 2), -- Stage 2 for Assembly Line B

-- Packaging Line A (Line ID: 3)
('Filling Stage', 3),      -- Stage 1 for Packaging Line A
('Sealing Stage', 3),      -- Stage 2 for Packaging Line A

-- Packaging Line B (Line ID: 4)
('Preparation Stage', 4),  -- Stage 1 for Packaging Line B
('Packaging Stage', 4),    -- Stage 2 for Packaging Line B

-- Machining Line A (Line ID: 5)
('Setup Stage', 5),        -- Stage 1 for Machining Line A
('Machining Stage', 5),    -- Stage 2 for Machining Line A

-- Quality Control Line (Line ID: 6)
('Initial Inspection Stage', 6),  -- Stage 1 for Quality Control Line
('Final Inspection Stage', 6),   -- Stage 2 for Quality Control Line

-- Human Resources Line (Line ID: 7)
('Recruitment Stage', 7),  -- Stage 1 for Human Resources Line
('Training Stage', 7),      -- Stage 2 for Human Resources Line

-- IT Support Line (Line ID: 8)
('User Setup Stage', 8),    -- Stage 1 for IT Support Line
('User Support Stage', 8),   -- Stage 2 for IT Support Line

-- Maintenance Line A (Line ID: 9)
('Maintenance Check Stage', 9),  -- Stage 1 for Maintenance Line A
('Repair Stage', 9),             -- Stage 2 for Maintenance Line A

-- Logistics Line A (Line ID: 10)
('Loading Stage', 10),     -- Stage 1 for Logistics Line A
('Transportation Stage', 10); -- Stage 2 for Logistics Line A



-- Inserting Equipment Data for Each Stage
INSERT INTO Equipment (EquipmentCode, EquipmentName, DateUse, Origin, YOM, QRCode, StageID) VALUES 
-- Equipment for Preparation Stage (Stage ID: 1)
('EQ001', 'Conveyor Belt A', '2022-01-15', 'USA', 2021, 'QR001', 1),
('EQ002', 'Robotic Arm A', '2022-03-20', 'Germany', 2020, 'QR002', 1),

-- Equipment for Assembly Stage (Stage ID: 2)
('EQ003', 'Soldering Iron', '2022-02-10', 'China', 2021, 'QR003', 2),
('EQ004', 'Welding Machine', '2022-03-15', 'Japan', 2021, 'QR004', 2),

-- Equipment for Filling Stage (Stage ID: 3)
('EQ005', 'Filling Machine A', '2022-04-25', 'Italy', 2021, 'QR005', 3),
('EQ006', 'Filling Machine B', '2022-04-25', 'Italy', 2021, 'QR006', 3),

-- Equipment for Sealing Stage (Stage ID: 4)
('EQ007', 'Sealer Machine A', '2022-02-12', 'China', 2022, 'QR007', 4),
('EQ008', 'Sealer Machine B', '2022-02-12', 'Germany', 2021, 'QR008', 4),

-- Equipment for Labeling Stage (Stage ID: 5)
('EQ009', 'Labeling Machine A', '2022-01-10', 'USA', 2021, 'QR009', 5),
('EQ010', 'Labeling Machine B', '2022-01-10', 'USA', 2021, 'QR010', 5);
---- Equipment for Setup Stage (Stage ID: 6)
--('EQ011', 'Setup Tool A', '2021-11-30', 'Canada', 2021, 'QR011', 6),
--('EQ012', 'Setup Tool B', '2021-11-30', 'Canada', 2021, 'QR012', 6),

---- Equipment for Machining Stage (Stage ID: 7)
--('EQ013', 'CNC Machine A', '2022-05-20', 'Italy', 2020, 'QR013', 7),
--('EQ014', 'CNC Machine B', '2022-05-20', 'Italy', 2020, 'QR014', 7),

---- Equipment for Initial Inspection Stage (Stage ID: 8)
--('EQ015', 'Inspection Camera A', '2022-06-10', 'Japan', 2021, 'QR015', 8),
--('EQ016', 'Inspection Tool A', '2022-06-10', 'USA', 2021, 'QR016', 8),

---- Equipment for Final Inspection Stage (Stage ID: 9)
--('EQ017', 'Final Inspection Machine', '2022-07-10', 'Germany', 2021, 'QR017', 9),
--('EQ018', 'Quality Control Scanner', '2022-07-15', 'Japan', 2021, 'QR018', 9),

---- Equipment for Recruitment Stage (Stage ID: 10)
--('EQ019', 'HR Software A', '2022-01-15', 'USA', 2021, 'QR019', 10),
--('EQ020', 'HR Software B', '2022-01-15', 'USA', 2021, 'QR020', 10),

---- Equipment for Training Stage (Stage ID: 11)
--('EQ021', 'Training Software A', '2023-01-01', 'Germany', 2023, 'QR021', 11),
--('EQ022', 'Training Software B', '2023-01-01', 'Germany', 2023, 'QR022', 11),

---- Equipment for User Setup Stage (Stage ID: 12)
--('EQ023', 'User Setup Tool A', '2023-02-01', 'Canada', 2023, 'QR023', 12),

---- Equipment for User Support Stage (Stage ID: 13)
--('EQ024', 'Support Tool A', '2023-03-01', 'France', 2023, 'QR024', 13),
--('EQ025', 'Support Tool B', '2023-03-01', 'France', 2023, 'QR025', 13),

---- Equipment for Maintenance Check Stage (Stage ID: 14)
--('EQ026', 'Maintenance Tool A', '2023-04-01', 'USA', 2023, 'QR026', 14),

---- Equipment for Repair Stage (Stage ID: 15)
--('EQ027', 'Repair Tool A', '2023-05-01', 'Japan', 2023, 'QR027', 15),
--('EQ028', 'Repair Tool B', '2023-05-01', 'Japan', 2023, 'QR028', 15),

---- Equipment for Loading Stage (Stage ID: 16)
--('EQ029', 'Forklift A', '2023-06-01', 'Canada', 2023, 'QR029', 16),
--('EQ030', 'Forklift B', '2023-06-01', 'Canada', 2023, 'QR030', 16),

---- Equipment for Transportation Stage (Stage ID: 17)
--('EQ031', 'Transport Van A', '2023-07-01', 'USA', 2023, 'QR031', 17),

---- Equipment for Documentation Stage (Stage ID: 18)
--('EQ032', 'Documentation Tool A', '2023-08-01', 'Germany', 2023, 'QR032', 18),
--('EQ033', 'Documentation Tool B', '2023-08-01', 'Germany', 2023, 'QR033', 18),

---- Equipment for Feedback Stage (Stage ID: 19)
--('EQ034', 'Feedback System A', '2023-09-01', 'Canada', 2023, 'QR034', 19),

---- Equipment for Packaging Stage (Stage ID: 20)
--('EQ035', 'Packaging Tool A', '2023-10-01', 'USA', 2023, 'QR035', 20),
--('EQ036', 'Packaging Tool B', '2023-10-01', 'USA', 2023, 'QR036', 20),

---- Equipment for Final Assembly Stage (Stage ID: 21)
--('EQ037', 'Final Assembly Tool A', '2023-11-01', 'Japan', 2023, 'QR037', 21),
--('EQ038', 'Final Assembly Tool B', '2023-11-01', 'Japan', 2023, 'QR038', 21);


-- Inserting Error Records into ErrorHistory Table
INSERT INTO ErrorHistory (EquipmentID, ErrorDescription, StartTime, EndTime, StageID) VALUES 
-- Errors for Equipment in Sealing Stage (Stage ID: 4)
-- Errors for Equipment in Preparation Stage (Stage ID: 1)
(1, 'Conveyor belt malfunction', '2023-01-15 08:00:00', '2023-01-15 09:00:00', 1),
(1, 'Eror malfunction', '2023-01-15 08:00:00', '2023-01-15 08:05:00', 1),
(1, 'Robotic not working', '2023-01-16 10:00:00', '2023-01-16 10:04:00', 1),
(2, 'Robotic not misalignment', '2023-01-16 11:00:00', '2023-01-16 11:03:00', 1),
(2, 'Robotic arm missing', '2023-01-16 10:00:00', '2023-01-16 11:00:00', 1),
(2, 'Robotic stopping', '2023-01-16 12:00:00', '2023-01-16 12:05:00', 1),

-- Errors for Equipment in Assembly Stage (Stage ID: 2)
(3, 'Soldering iron overheating', '2023-02-10 14:00:00', '2023-02-10 15:00:00', 2),
(4, 'Welding machine failure', '2023-02-11 09:30:00', '2023-02-11 10:30:00', 2),
(3, 'Error sonar', '2023-02-10 14:55:00', '2023-02-10 15:00:00', 2),
(4, 'Machine stop', '2023-02-11 09:30:00', '2023-02-11 10:30:00', 2),
(3, 'No network', '2023-02-10 14:00:00', '2023-02-10 14:10:00', 2),
(4, 'Go out', '2023-02-11 09:30:00', '2023-02-11 9:35:00', 2),

-- Errors for Equipment in Filling Stage (Stage ID: 3)
(5, 'Filling machine jammed', '2023-03-20 12:00:00', '2023-03-20 13:00:00', 3),
(6, 'Filling machine leak', '2023-03-21 10:00:00', '2023-03-21 11:00:00', 3),
(5, 'Not Working', '2023-03-20 12:00:00', '2023-03-20 12:10:00', 3),
(6, 'Loosing internet', '2023-03-21 10:00:00', '2023-03-21 10:04:00', 3),

-- Errors for Equipment in Sealing Stage (Stage ID: 4)
(7, 'Sealer machine not sealing', '2023-04-10 16:00:00', '2023-04-10 17:00:00', 4),
(8, 'Sealer machine calibration error', '2023-04-11 08:00:00', '2023-04-11 09:00:00', 4),

-- Errors for Equipment in Labeling Stage (Stage ID: 5)
(9, 'Labeling machine offline', '2023-05-15 11:00:00', '2023-05-15 12:00:00', 5),
(10, 'Labeling machine stuck', '2023-05-16 13:00:00', '2023-05-16 14:00:00', 5),
(9, 'Machine offline', '2023-05-15 11:00:00', '2023-05-15 11:05:00', 5),
(10, 'Machine stuck', '2023-05-16 13:00:00', '2023-05-16 13:06:00', 5),
(9, 'Machine stop', '2023-05-15 11:00:00', '2023-05-15 11:03:00', 5),
(10, 'Labeling machine stucks', '2023-05-16 13:00:00', '2023-05-16 13:05:00', 5)

---- Errors for Equipment in Setup Stage (Stage ID: 6)
--(11, 'Setup tool malfunction', '2023-06-20 15:00:00', '2023-06-20 16:00:00', 6),
--(12, 'Setup tool missing parts', '2023-06-21 10:30:00', '2023-06-21 11:30:00', 6),

---- Errors for Equipment in Machining Stage (Stage ID: 7)
--(13, 'CNC machine error', '2023-07-25 14:30:00', '2023-07-25 15:30:00', 7),
--(14, 'CNC machine tool wear', '2023-07-26 09:00:00', '2023-07-26 10:00:00', 7),

---- Errors for Equipment in Initial Inspection Stage (Stage ID: 8)
--(15, 'Inspection camera failure', '2023-08-10 10:00:00', '2023-08-10 11:00:00', 8),
--(16, 'Inspection tool malfunction', '2023-08-11 12:30:00', '2023-08-11 13:30:00', 8),

---- Errors for Equipment in Final Inspection Stage (Stage ID: 9)
--(17, 'Final inspection machine error', '2023-09-05 09:00:00', '2023-09-05 10:00:00', 9),
--(18, 'Quality control scanner issue', '2023-09-06 14:00:00', '2023-09-06 15:00:00', 9),

---- Errors for Equipment in Recruitment Stage (Stage ID: 10)
--(19, 'HR software crash', '2023-01-10 09:00:00', '2023-01-10 10:00:00', 10),
--(20, 'HR software data corruption', '2023-01-11 12:00:00', '2023-01-11 13:00:00', 10),

---- Errors for Equipment in Training Stage (Stage ID: 11)
--(21, 'Training software not responding', '2023-02-15 08:30:00', '2023-02-15 09:30:00', 11),
--(22, 'Training software data loss', '2023-02-16 11:00:00', '2023-02-16 12:00:00', 11),
---- Errors for Equipment in Training Stage (Stage ID: 11)
--(21, 'Training software not responding not connect', '2023-02-15 08:30:00', '2023-02-15 08:35:00', 11),
--(22, 'Machine data loss', '2023-02-16 11:00:00', '2023-02-16 11:04:00', 11),

---- Errors for Equipment in User Setup Stage (Stage ID: 12)
--(23, 'User setup tool glitch', '2023-03-20 10:00:00', '2023-03-20 11:00:00', 12),

---- Errors for Equipment in User Support Stage (Stage ID: 13)
--(24, 'Support tool not accessible', '2023-04-15 09:00:00', '2023-04-15 10:00:00', 13),
--(25, 'Support tool update failed', '2023-04-16 12:30:00', '2023-04-16 13:30:00', 13),

---- Errors for Equipment in Maintenance Check Stage (Stage ID: 14)
--(26, 'Maintenance tool failure', '2023-05-01 08:00:00', '2023-05-01 09:00:00', 14),
--(26, 'Maintenance tool failure', '2023-05-01 09:00:00', '2023-05-01 09:02:00', 14),
--(26, 'Maintenance tool failure', '2023-05-01 12:00:00', '2023-05-01 12:04:00', 14),

---- Errors for Equipment in Repair Stage (Stage ID: 15)
--(27, 'Repair tool malfunction', '2023-06-10 10:00:00', '2023-06-10 11:00:00', 15),
--(28, 'Repair tool part failure', '2023-06-11 13:30:00', '2023-06-11 14:30:00', 15),

---- Errors for Equipment in Loading Stage (Stage ID: 16)
--(29, 'Forklift maintenance required', '2023-07-05 08:30:00', '2023-07-05 09:30:00', 16),
--(30, 'Forklift sensor error', '2023-07-06 10:30:00', '2023-07-06 11:30:00', 16),

---- Errors for Equipment in Transportation Stage (Stage ID: 17)
--(31, 'Transport van breakdown', '2023-08-20 14:00:00', '2023-08-20 15:00:00', 17),

---- Errors for Equipment in Documentation Stage (Stage ID: 18)
--(32, 'Documentation tool error', '2023-09-15 11:00:00', '2023-09-15 12:00:00', 18),
--(33, 'Documentation tool data mismatch', '2023-09-16 09:30:00', '2023-09-16 10:30:00', 18),

---- Errors for Equipment in Feedback Stage (Stage ID: 19)
--(34, 'Feedback system', '2023-09-20 10:00:00', '2023-09-20 11:00:00', 19),
--(34, 'Feedback system', '2023-09-20 11:00:00', '2023-09-20 11:05:00', 19),
--(34, 'Feedback system malfunction', '2023-09-20 12:00:00', '2023-09-20 12:04:00', 19),

---- Errors for Equipment in Packaging Stage (Stage ID: 20)
--(35, 'Packaging tool error', '2023-10-01 10:30:00', '2023-10-01 11:30:00', 20),
--(36, 'Packaging tool part failure', '2023-10-02 12:30:00', '2023-10-02 13:30:00', 20),

---- Errors for Equipment in Final Assembly Stage (Stage ID: 21)
--(37, 'Final assembly tool issue', '2023-11-01 14:00:00', '2023-11-01 15:00:00', 21),
--(38, 'Final assembly tool calibration error', '2023-11-02 09:00:00', '2023-11-02 10:00:00', 21);

--DROP TABLE ErrorHistory
--DROP TABLE Equipment
--DROP TABLE ProductionLines
--DROP TABLE Stages
--DROP TABLE Rooms
--DROP TABLE Departments

-- Inserting sample Roles
INSERT INTO Roles (RoleName) VALUES 
('Admin'),
('User');

-- Inserting sample Users
INSERT INTO Users (UserCode, FullName, Password, Email, Phone, Address, Gender, DateOfBirth, Avatar, RoleID, RoomID) VALUES 
('USR001', 'John Doe', '123', 'johndoe@example.com', '1234567890', '123 Main St, City', 'Male', '1985-05-15', NULL, 1, 2),  -- Admin
('USR002', 'Jane Smith', '123', 'janesmith@example.com', '0987654321', '456 Elm St, City', 'Female', '1990-07-20', NULL, 2, 1); -- Operator




--SELECT COUNT(*) FROM ErrorHistory

--SELECT eh.ErrorDescription, e.EquipmentCode FROM ErrorHistory eh 
--JOIN Equipment e ON e.EquipmentID = eh.EquipmentID
--WHERE e.EquipmentID = 12

--SELECT TOP (1000) [RoomID]
--      ,[RoomName]
--      ,[DepartmentID]
--  FROM [MSSDB].[dbo].[Rooms]


--SELECT e.EquipmentID, e.EquipmentCode, e.EquipmentName,
--pl.LineName, s.StageName, eh.ErrorDescription, eh.StartTime, eh.EndTime, eh.Duration, r.RoomName, r.RoomID, s.StageID, pl.LineID
--FROM Equipment e
--JOIN ErrorHistory eh ON e.EquipmentID = eh.EquipmentID 
--JOIN Stages s ON e.StageID = s.StageID 
--JOIN ProductionLines pl ON s.LineID = pl.LineID
--JOIN Rooms r ON pl.RoomID = r.RoomID
--WHERE pl.LineID = 1 AND s.StageID = 2


--SELECT 
--    COUNT(*) AS TotalOccurrences, 
--    SUM(Duration) AS TotalTime
--FROM 
--    ErrorHistory
--WHERE 
--    (StageID = @StageID OR @StageID IS NULL)
--    AND (LineID = @LineID OR @LineID IS NULL)
--GROUP BY 
--    CASE WHEN @StageID IS NOT NULL THEN StageID ELSE LineID END


--SELECT TOP 5 pl.LineName, SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime
--FROM ProductionLines pl
--JOIN Stages s ON pl.LineID = s.LineID
--JOIN ErrorHistory eh ON s.StageID = eh.StageID
--GROUP BY pl.LineName
--ORDER BY TotalDowntime DESC;


--SELECT TOP 5 s.StageName, SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime
--FROM Stages s
--JOIN ErrorHistory eh ON s.StageID = eh.StageID
--GROUP BY s.StageName
--ORDER BY TotalDowntime DESC;

--SELECT e.EquipmentID, e.EquipmentCode, e.EquipmentName,
--pl.LineName, s.StageName, eh.ErrorDescription, eh.StartTime, eh.EndTime, eh.Duration,
--SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime, COUNT(eh.ErrorID) AS TotalError
--FROM Equipment e
--JOIN ErrorHistory eh ON e.EquipmentID = eh.EquipmentID
--JOIN Stages s ON e.StageID = s.StageID
--JOIN ProductionLines pl ON s.LineID = pl.LineID
--JOIN Rooms r ON pl.RoomID = r.RoomID
--WHERE 1 = 1
--GROUP BY e.EquipmentID, e.EquipmentCode, e.EquipmentName,
--pl.LineName, s.StageName, eh.ErrorDescription, eh.StartTime, eh.EndTime, eh.Duration


--SELECT TOP (1000) [ErrorID]
--      ,[EquipmentID]
--      ,[ErrorDescription]
--      ,[StartTime]
--      ,[EndTime]
--      ,[Duration]
--      ,[StageID]
--  FROM [MSSDB].[dbo].[ErrorHistory]

--SELECT e.EquipmentName, eh.ErrorDescription, eh.StartTime, eh.EndTime,
--DATEDIFF(MINUTE, eh.StartTime, eh.EndTime) AS Duration, pl.LineName
--FROM ErrorHistory eh
--JOIN Equipment e ON eh.EquipmentID = e.EquipmentID
--JOIN Stages s ON e.StageID = s.StageID 
--JOIN ProductionLines pl ON s.LineID = pl.LineID


--SELECT 
--    pl.LineName,
--    s.StageName,
--    SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime
--FROM 
--    ErrorHistory eh
--JOIN 
--    Equipment e ON eh.EquipmentID = e.EquipmentID
--JOIN 
--    Stages s ON e.StageID = s.StageID
--JOIN 
--    ProductionLines pl ON s.LineID = pl.LineID
--GROUP BY 
--    pl.LineName, s.StageName
--ORDER BY 
--    pl.LineName, s.StageName;

--	SELECT pl.LineName, SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS Downtime, 
--	COUNT(eh.ErrorID) AS TotalDowntimeCount
--    FROM ProductionLines pl
--    JOIN Stages s ON pl.LineID = s.LineID 
--    JOIN Equipment e ON s.StageID = e.StageID 
--    JOIN ErrorHistory eh ON e.EquipmentID = eh.EquipmentID 
--    GROUP BY pl.LineName

--SELECT * FROM ProductionLines
--SELECT * FROM Stages
--SELECT * FROM ErrorHistory

--SELECT 
--    s.StageName,
--    COUNT(eh.ErrorID) AS TotalDowntimeCount,
--    SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntimeMinutes
--FROM 
--    ErrorHistory eh
--JOIN 
--    Stages s ON eh.StageID = s.StageID
--GROUP BY 
--    s.StageID, s.StageName
--ORDER BY 
--    TotalDowntimeMinutes DESC;


--SELECT * FROM (
--    SELECT ROW_NUMBER() OVER (ORDER BY e.EquipmentID) AS RowNum, 
--           e.EquipmentID, e.EquipmentCode, e.EquipmentName, 
--           e.Origin, e.QRCode, e.YOM, er.ErrorID,
--           er.ErrorDescription, er.StartTime, er.EndTime, 
--           DATEDIFF(MINUTE, er.StartTime, er.EndTime) AS Duration, 
--           s.StageName, l.LineName
--    FROM Equipment e
--    JOIN ErrorHistory er ON er.EquipmentID = e.EquipmentID
--    JOIN Stages s ON s.StageID = e.StageID
--    LEFT JOIN ProductionLines l ON l.LineID = s.LineID
--) AS Result
--WHERE RowNum BETWEEN 1 AND 38

--SELECT * FROM (
--    SELECT ROW_NUMBER() OVER (ORDER BY eh.ErrorID) AS RowNum, 
--           eh.*,e.EquipmentCode, e.EquipmentName, s.StageName, l.LineName
--    FROM ErrorHistory eh
--    JOIN Equipment e ON e.EquipmentID = eh.EquipmentID
--    JOIN Stages s ON s.StageID = e.StageID
--    LEFT JOIN ProductionLines l ON l.LineID = s.LineID
--) AS Result
--WHERE RowNum BETWEEN 1 AND 10

--SELECT eh.*, e.EquipmentCode, e.EquipmentName, s.StageName, l.LineName
--FROM ErrorHistory eh
--JOIN Equipment e ON e.EquipmentID = eh.EquipmentID
--JOIN Stages s ON s.StageID = e.StageID
--LEFT JOIN ProductionLines l ON l.LineID = s.LineID
--ORDER BY eh.StartTime desc
--OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;


--SELECT * FROM Stages
--SELECT eh.*, e.EquipmentCode, e.EquipmentName, s.StageName, l.LineName
--FROM ErrorHistory eh
--JOIN Equipment e ON e.EquipmentID = eh.EquipmentID
--LEFT JOIN Stages s ON s.StageID = eh.StageID
--LEFT JOIN ProductionLines l ON l.LineID = s.LineID
--ORDER BY eh.StartTime desc
--SELECT * 




-- SELECT COUNT(*) FROM ErrorHistory eh JOIN Equipment e ON e.EquipmentID = eh.EquipmentID WHERE 1=1

--SELECT pl.LineName, SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,
--COUNT(eh.ErrorID) AS TotalDowntimeCount
--FROM ProductionLines pl
--JOIN Stages s ON s.LineID = pl.LineID
--JOIN Equipment e ON s.StageID = e.StageID
--JOIN ErrorHistory eh ON e.EquipmentID = eh.EquipmentID
--GROUP BY pl.LineName

--SELECT eh.*, e.EquipmentCode, e.EquipmentName, s.StageName, l.LineName
-- FROM ErrorHistory eh
-- JOIN Equipment e ON e.EquipmentID = eh.EquipmentID
-- JOIN Stages s ON s.StageID = eh.StageID
-- LEFT JOIN ProductionLines l ON l.LineID = s.LineID
-- WHERE l.LineID = 6

-- SELECT pl.LineName, SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,
-- COUNT(eh.ErrorID) AS TotalDowntimeCount
-- FROM ErrorHistory eh
-- JOIN Equipment e ON e.EquipmentID = eh.EquipmentID
-- JOIN Stages s ON s.StageID = eh.StageID
-- LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID
-- GROUP BY pl.LineName



-- SELECT EquipmentCode FROM Equipment WHERE EquipmentName = 'Final Assembly Tool B'

-- SELECT EquipmentCode FROM Equipment WHERE EquipmentName = ?

-- SELECT EquipmentID FROM Equipment WHERE TRIM(LOWER(EquipmentCode)) = 'EQ001'


--SELECT e.EquipmentID, e.EquipmentCode, e.EquipmentName,
--pl.LineName, s.StageName, eh.ErrorDescription,
--eh.StartTime, eh.EndTime, eh.Duration, r.RoomID
--FROM Equipment e
--JOIN ErrorHistory eh ON e.EquipmentID = eh.EquipmentID 
--JOIN Stages s ON e.StageID = s.StageID 
--JOIN ProductionLines pl ON s.LineID = pl.LineID 
--JOIN Rooms r ON pl.RoomID = r.RoomID 
--WHERE e.EquipmentCode = 'EQ001'
--AND (r.RoomID = ? OR ? IS NULL) 
--AND (s.LineID = ? OR ? IS NULL)
--AND (s.StageID = ? OR ? IS NULL)

--SELECT eh.ErrorDescription, eh.StartTime, eh.EndTime, eh.Duration,
--e.EquipmentCode, e.EquipmentName, s.StageName, l.LineName, d.DepartmentName
--FROM ErrorHistory eh
--JOIN Equipment e ON e.EquipmentID = eh.EquipmentID
--JOIN Stages s ON s.StageID = eh.StageID
--LEFT JOIN ProductionLines l ON l.LineID = s.LineID
--LEFT JOIN Rooms r ON l.RoomID = r.RoomID LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID
--WHERE 1 = 1

--SELECT COUNT(*) FROM ErrorHistory eh 
--JOIN Equipment e ON e.EquipmentID = eh.EquipmentID 
--JOIN Stages s ON s.StageID = eh.StageID 
--LEFT JOIN ProductionLines l ON l.LineID = s.LineID 
--LEFT JOIN Rooms r ON l.RoomID = r.RoomID 
--LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID WHERE 1 = 1


--SELECT 
--    e.EquipmentCode,
--    eh.ErrorDescription,
--    eh.StartTime,
--    eh.EndTime,
--    DateDiff(MINUTE, eh.StartTime, eh.EndTime) AS DowntimeMinutes,
--    YEAR(eh.StartTime) AS ErrorYear,
--    MONTH(eh.StartTime) AS ErrorMonth,
--    DAY(eh.StartTime) AS ErrorDay
--FROM ErrorHistory eh
--JOIN Equipment e ON e.EquipmentID = eh.EquipmentID
--JOIN Stages s ON eh.StageID = s.StageID
--LEFT JOIN ProductionLines l ON s.LineID = l.LineID
--LEFT JOIN Rooms r ON l.RoomID = r.RoomID
--LEFT JOIN Departments d ON r.DepartmentID = r.DepartmentID
--WHERE d.DepartmentID = 1
--ORDER BY eh.StartTime;









--SELECT 
--    DATEPART(HOUR, eh.StartTime) AS ErrorHour, 
--    COUNT(eh.ErrorID) AS ErrorCount,
--    e.EquipmentCode,
--	eh.StartTime
--FROM 
--    ErrorHistory eh
--    JOIN Equipment e ON e.EquipmentID = eh.EquipmentID
--GROUP BY 
--    DATEPART(HOUR, eh.StartTime),
--    e.EquipmentCode,
--	eh.StartTime
--ORDER BY 
--    ErrorHour ASC, 
--    e.EquipmentCode ASC;

--	SELECT 
--    CAST(eh.StartTime AS DATE) AS ErrorDate, 
--    COUNT(eh.ErrorID) AS ErrorCount,
--	e.EquipmentCode
--FROM 
--    ErrorHistory eh
--    JOIN Equipment e ON e.EquipmentID = eh.EquipmentID
--GROUP BY 
--    CAST(StartTime AS DATE),
--	e.EquipmentCode
--ORDER BY 
--    ErrorCount DESC






--	SELECT
--    CAST(StartTime AS DATE) AS ErrorDate, 
--    COUNT(ErrorID) AS ErrorCount
--FROM 
--    ErrorHistory
--GROUP BY 
--    CAST(StartTime AS DATE)
--ORDER BY 
--    ErrorCount DESC



	
--SELECT 
--    pl.LineID, 
--    pl.LineName, 
--    SUM(eh.Duration) AS TotalDuration
--FROM ErrorHistory eh
--JOIN Stages s ON eh.StageID = s.StageID
--JOIN ProductionLines pl ON s.LineID = pl.LineID
--GROUP BY pl.LineID, pl.LineName;

--SELECT 
--    pl.LineID, 
--    pl.LineName, 
--    SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop,
--    SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,
--	SUM(eh.Duration) AS TotalDuration,
--	COUNT(eh.ErrorID) AS ErrorCount
--FROM ErrorHistory eh
--JOIN Stages s ON eh.StageID = s.StageID
--JOIN ProductionLines pl ON s.LineID = pl.LineID
--GROUP BY pl.LineID, pl.LineName, eh.StartTime


--SELECT 
--    s.StageID, 
--    s.StageName, 
--    SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop,
--    SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,
--	SUM(eh.Duration) AS TotalDuration,
--	COUNT(eh.ErrorID) AS ErrorCount
--FROM ErrorHistory eh
--JOIN Stages s ON eh.StageID = s.StageID
--WHERE s.StageID = 1
--GROUP BY s.StageID, s.StageName;



--SELECT
--    pl.LineName, 
--	s.StageName,
--    SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop,
--    SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,
--	SUM(eh.Duration) AS TotalDuration,
--	COUNT(eh.ErrorID) AS ErrorCount
--FROM ErrorHistory eh
--JOIN Stages s ON eh.StageID = s.StageID
--JOIN ProductionLines pl ON s.LineID = pl.LineID
--WHERE pl.LineID = 1 AND s.StageID = 1
--GROUP BY pl.LineName, s.StageName


--SELECT
--    pl.LineID,
--    pl.LineName,
--    SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop,
--    SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,
--    SUM(eh.Duration) AS TotalDuration,
--    COUNT(eh.ErrorID) AS ErrorCount
--FROM
--    ErrorHistory eh
--JOIN
--    Stages s ON eh.StageID = s.StageID
--JOIN
--    ProductionLines pl ON s.LineID = pl.LineID
--WHERE
--    eh.StartTime >= '2023-01-16' AND eh.EndTime <= '2023-01-17'
--GROUP BY
--    pl.LineID,
--    pl.LineName;



--	SELECT
--    pl.LineID,
--    pl.LineName,
--    SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop,
--    SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,
--    SUM(eh.Duration) AS TotalDuration,
--    COUNT(eh.ErrorID) AS ErrorCount
--FROM
--    ErrorHistory eh
--JOIN
--    Stages s ON eh.StageID = s.StageID
--JOIN
--    ProductionLines pl ON s.LineID = pl.LineID
--WHERE CAST(eh.StartTime AS DATE) >= '2023-01-10' AND CAST(eh.EndTime AS DATE) <= '2023-01-17'
--GROUP BY
--    pl.LineID,
--    pl.LineName;










--	--27/10/2024

---- index dashboard 
--SELECT 
--    LineName,
--    Year,
--	SUM(TotalDowntime) AS TotalDowntime,
--    SUM(ShortStop) AS ShortStop,
--	SUM(LongStop) AS LongStop,
--    SUM(TotalDowntimeCount) TotalDowntimeCount,
--    ROUND(SUM(Efficiency) * 100, 2) AS ShortStopPercentage
--FROM (
--    SELECT 
--        e.EquipmentID,
--        pl.LineName,
--        FORMAT(eh.StartTime, 'yyyy') AS Year,
--        SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,
--        SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,
--        SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, 
--        COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS TotalDowntimeCount,
--        CAST(SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS FLOAT) / ss.Duration AS Efficiency
--    FROM ErrorHistory eh
--    JOIN Equipment e ON e.EquipmentID = eh.EquipmentID
--    JOIN Stages s ON s.StageID = eh.StageID
--    LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID
--    JOIN Rooms r ON pl.RoomID = r.RoomID
--    JOIN Users u ON r.RoomID = u.RoomID
--    JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID
--    --WHERE u.UserCode = ? AND YEAR(eh.StartTime) = YEAR(GETDATE())
--    GROUP BY 
--        e.EquipmentID, pl.LineName, FORMAT(eh.StartTime, 'yyyy'), ss.Duration
--) AS Summary
--GROUP BY 
--    LineName, Year;



--SELECT 
--    LineName,
--    Date,
--	SUM(TotalDowntime) AS TotalDowntime,
--    SUM(ShortStop) AS ShortStop,
--	SUM(LongStop) AS LongStop,
--    SUM(TotalDowntimeCount) TotalDowntimeCount,
--	SUM(slotCount) AS SLOTCOUNT,
--    ROUND(SUM(Efficiency) * 100, 2) AS ShortStopPercentage
--FROM (
--    SELECT 
--        e.EquipmentID,
--        pl.LineName,
--        FORMAT(eh.StartTime, 'yyyy-MM-dd') AS Date,
--        SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,
--        SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,
--        SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, 
--        COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS TotalDowntimeCount,
--		COUNT(ss.SlotID) AS slotCount,
--        CAST(SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS FLOAT) / ss.Duration AS Efficiency
--    FROM ErrorHistory eh
--    JOIN Equipment e ON e.EquipmentID = eh.EquipmentID
--    JOIN Stages s ON s.StageID = eh.StageID
--    LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID
--    JOIN Rooms r ON pl.RoomID = r.RoomID
--    JOIN Users u ON r.RoomID = u.RoomID
--    JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID
--    WHERE CAST(eh.StartTime AS DATE) = CAST(GETDATE() AS DATE)
--    GROUP BY 
--        e.EquipmentID, pl.LineName, FORMAT(eh.StartTime, 'yyyy-MM-dd'), ss.Duration
--) AS Summary
--GROUP BY 
--    LineName, Date;




--SELECT 
--    LineName,
--    Months,
--    SUM(TotalDowntime) AS TotalDowntime,
--    SUM(ShortStop) AS ShortStop,
--    SUM(LongStop) AS LongStop,
--    SUM(TotalDowntimeCount) AS TotalDowntimeCount,
--    ROUND(AVG(Efficiency) * 100, 2) AS ShortStopPercentage
--FROM (
--    SELECT 
--        e.EquipmentID,
--        pl.LineName,
--        FORMAT(eh.StartTime, 'MM/yyyy') AS Months,
--        SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,
--        SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,
--        SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, 
--        COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS TotalDowntimeCount,
--        CAST(SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS FLOAT) / 
--        NULLIF(SUM(ss.Duration), 0) AS Efficiency
--    FROM ErrorHistory eh
--    JOIN Equipment e ON e.EquipmentID = eh.EquipmentID
--    JOIN Stages s ON s.StageID = eh.StageID
--    LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID
--    JOIN Rooms r ON pl.RoomID = r.RoomID
--    JOIN Users u ON r.RoomID = u.RoomID
--    JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID
--	WHERE u.UserCode = 'USR001' AND MONTH(eh.StartTime) = MONTH(GETDATE())
--    GROUP BY 
--        e.EquipmentID, pl.LineName, FORMAT(eh.StartTime, 'MM/yyyy')
--) AS Summary
--GROUP BY 
--    LineName, Months;



--SELECT 
--CONVERT(VARCHAR(16), eh.StartTime, 120) AS StartTime,
--SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop,
--SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,
--SUM(eh.Duration) AS TotalDuration,
--COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS ShortCount,
--COUNT(CASE WHEN eh.Duration > 5 THEN 1 END) AS LongCount,
--COUNT(eh.ErrorID) AS ErrorCount,
--CAST(SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS FLOAT) / 
--NULLIF(SUM(ss.Duration), 0) AS Efficiency
--FROM ErrorHistory eh
--JOIN Equipment e ON e.EquipmentID = eh.EquipmentID
--JOIN Stages s ON eh.StageID = s.StageID
--JOIN ProductionLines l ON s.LineID = l.LineID
--JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID
--WHERE CAST(eh.StartTime AS DATE) = '2024-10-27'
--GROUP BY CONVERT(VARCHAR(16), eh.StartTime, 120)
--ORDER BY CONVERT(VARCHAR(16), eh.StartTime, 120) DESC




---- reportDetail 

--SELECT 
--    Day,
--	SUM(LongStop) AS LongStop,
--    SUM(ShortStop) AS ShortStop,
--	SUM(TotalDowntime) AS TotalDuration,
--	SUM(ShortCount) AS ShortCount,
--	SUM(LongCount) AS LongCount,
--    SUM(TotalDowntimeCount) AS ErrorCount,
--    ROUND(SUM(Efficiency) * 100, 2) AS ShortStopPercentage
--FROM (
--    SELECT 
--        CAST(eh.StartTime AS DATE) AS Day,
--        SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, 
--        SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,
--        SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,
--		COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS ShortCount,
--        COUNT(CASE WHEN eh.Duration > 5 THEN 1 END) AS LongCount,
--        COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS TotalDowntimeCount,
--        CAST(SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS FLOAT) / ss.Duration AS Efficiency
--    FROM ErrorHistory eh
--    JOIN Equipment e ON e.EquipmentID = eh.EquipmentID
--    JOIN Stages s ON s.StageID = eh.StageID
--    LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID
--    JOIN Rooms r ON pl.RoomID = r.RoomID
--    JOIN Users u ON r.RoomID = u.RoomID
--    JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID
--    WHERE CAST(eh.StartTime AS DATE) >= '2024-10-27' AND CAST(eh.EndTime AS DATE) <= '2024-10-27'
--    GROUP BY CAST(eh.StartTime AS DATE), ss.Duration
--) AS Summary
--GROUP BY Day;


--SELECT 
--    StartTime,
--	SUM(LongStop) AS LongStop,
--    SUM(ShortStop) AS ShortStop,
--	SUM(TotalDowntime) AS TotalDuration,
--	SUM(ShortCount) AS ShortCount,
--	SUM(LongCount) AS LongCount,
--    SUM(TotalDowntimeCount) AS ErrorCount,
--    ROUND(SUM(Efficiency) * 100, 2) AS ShortStopPercentage
--FROM (
--    SELECT 
--        CONVERT(VARCHAR(16), eh.StartTime, 120) AS StartTime,
--        SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, 
--        SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,
--        SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,
--		COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS ShortCount,
--        COUNT(CASE WHEN eh.Duration > 5 THEN 1 END) AS LongCount,
--        COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS TotalDowntimeCount,
--        CAST(SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS FLOAT) / ss.Duration AS Efficiency
--    FROM ErrorHistory eh
--    JOIN Equipment e ON e.EquipmentID = eh.EquipmentID
--    JOIN Stages s ON s.StageID = eh.StageID
--    LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID
--    JOIN Rooms r ON pl.RoomID = r.RoomID
--    JOIN Users u ON r.RoomID = u.RoomID
--    JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID
--    WHERE CAST(eh.StartTime AS DATE) = '2024-10-27'
--    GROUP BY CONVERT(VARCHAR(16), eh.StartTime, 120), ss.Duration
--) AS Summary
--GROUP BY StartTime;


--SELECT 
--    Day,
--	ShiftName,
--	SUM(LongStop) AS LongStop,
--    SUM(ShortStop) AS ShortStop,
--	SUM(TotalDowntime) AS TotalDuration,
--	SUM(ShortCount) AS ShortCount,
--	SUM(LongCount) AS LongCount,
--    SUM(TotalDowntimeCount) AS ErrorCount,
--    ROUND(SUM(Efficiency) * 100, 2) AS ShortStopPercentage
--FROM (
--    SELECT 
--        CAST(eh.StartTime AS DATE) AS Day,
--		sh.ShiftName AS ShiftName,
--        SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, 
--        SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,
--        SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,
--		COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS ShortCount,
--        COUNT(CASE WHEN eh.Duration > 5 THEN 1 END) AS LongCount,
--        COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS TotalDowntimeCount,
--        CAST(SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS FLOAT) / ss.Duration AS Efficiency
--    FROM ErrorHistory eh
--    JOIN Equipment e ON e.EquipmentID = eh.EquipmentID
--    JOIN Stages s ON s.StageID = eh.StageID
--    LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID
--    JOIN Rooms r ON pl.RoomID = r.RoomID
--    JOIN Users u ON r.RoomID = u.RoomID
--    JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID
--	LEFT JOIN Shifts sh ON ss.ShiftID = sh.ShiftID
--    WHERE CAST(eh.StartTime AS DATE) >= ? AND CAST(eh.EndTime AS DATE) <= ?
--    GROUP BY CAST(eh.StartTime AS DATE), sh.ShiftName, ss.Duration
--) AS Summary
--GROUP BY Day, ShiftName;
















--05/11/2024
SELECT 
    LineName,
    Day, 
    SUM(TotalDowntime) AS TotalDowntime,
    SUM(ShortStop) AS ShortStop,
    SUM(LongStop) AS LongStop,
    SUM(TotalDowntimeCount) AS TotalDowntimeCount,
    ROUND(SUM(Efficiency) * 100, 2) AS ShortStopPercentage
FROM (
    SELECT 
        e.EquipmentID,
        pl.LineName,
        FORMAT(eh.StartTime, 'dd/MM/yyyy') AS Day, 
        SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,
        SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,
        SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, 
        COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS TotalDowntimeCount,
        CAST(SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS FLOAT) / ss.Duration AS Efficiency
    FROM ErrorHistory eh
    JOIN Equipment e ON e.EquipmentID = eh.EquipmentID
    --JOIN Stages s ON s.StageID = eh.StageID
    LEFT JOIN ProductionLines pl ON eh.LineID = pl.LineID
    JOIN Rooms r ON pl.RoomID = r.RoomID
    JOIN Users u ON r.RoomID = u.RoomID
    JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID
    WHERE MONTH(eh.StartTime) = MONTH(GETDATE()) AND YEAR(eh.StartTime) = YEAR(GETDATE()) -- Ensuring it's the current month of the current year
    GROUP BY 
        e.EquipmentID, pl.LineName, FORMAT(eh.StartTime, 'dd/MM/yyyy'), ss.Duration
) AS Summary
GROUP BY LineName, Day;



SELECT 
    LineName,
    Day, 
    SUM(TotalDowntime) AS TotalDowntime,
    SUM(ShortStop) AS ShortStop,
    SUM(LongStop) AS LongStop,
    SUM(TotalDowntimeCount) AS TotalDowntimeCount,
	COUNT,
    ROUND(SUM(Efficiency) * 100, 2) AS ShortStopPercentage
FROM (
    SELECT 
        pl.LineName,
        FORMAT(eh.StartTime, 'dd/MM/yyyy') AS Day, 
        SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,
        SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,
        SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, 
        COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS TotalDowntimeCount,
		COUNT(eh.ErrorID) As COUNT,
        CAST(SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS FLOAT) / ss.Duration AS Efficiency
    FROM ErrorHistory eh
    LEFT JOIN ProductionLines pl ON eh.LineID = pl.LineID
    JOIN Rooms r ON pl.RoomID = r.RoomID
    JOIN Users u ON r.RoomID = u.RoomID
    JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID
    WHERE MONTH(eh.StartTime) = MONTH(GETDATE()) AND YEAR(eh.StartTime) = YEAR(GETDATE())
    GROUP BY pl.LineName, FORMAT(eh.StartTime, 'dd/MM/yyyy'), ss.Duration
) AS Summary
GROUP BY LineName, Day, COUNT;



SELECT 
    LineName,
    Day, 
    SUM(TotalDowntime) AS TotalDowntime,
    SUM(ShortStop) AS ShortStop,
    SUM(LongStop) AS LongStop,
    SUM(Defective) AS Defective,
    SUM(CountTotal) AS TotalDowntimeCount,
    ROUND(SUM(ShortStop) * 100.0 / NULLIF(SUM(TotalDowntime), 0), 2) AS ShortStopPercentage,
    ROUND(SUM(LongStop) * 100.0 / NULLIF(SUM(TotalDowntime), 0), 2) AS LongStopPercentage,
    ROUND(SUM(Defective) * 100.0 / NULLIF(SUM(TotalDowntime), 0), 2) AS DefectivePercentage
FROM (
    SELECT 
        pl.LineName AS LineName,
        FORMAT(eh.StartTime, 'dd/MM/yyyy') AS Day, 
        SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,
        SUM(CASE WHEN eh.TypeID = 1 THEN eh.Duration ELSE 0 END) AS ShortStop,  
        SUM(CASE WHEN eh.TypeID = 2 THEN eh.Duration ELSE 0 END) AS LongStop,   
        SUM(CASE WHEN eh.TypeID = 3 THEN eh.Duration ELSE 0 END) AS Defective, 
        COUNT(eh.ErrorID) AS CountTotal,
        CAST(SUM(CASE WHEN eh.TypeID = 1 THEN eh.Duration ELSE 0 END) AS FLOAT) / ss.Duration AS Efficiency
    FROM ErrorHistory eh
    LEFT JOIN ProductionLines pl ON eh.LineID = pl.LineID
    JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID
    WHERE MONTH(eh.StartTime) = MONTH(GETDATE()) 
        AND YEAR(eh.StartTime) = YEAR(GETDATE())
    GROUP BY pl.LineName, FORMAT(eh.StartTime, 'dd/MM/yyyy'), ss.Duration
) AS Summary
GROUP BY LineName, Day
ORDER BY LineName, Day;  
