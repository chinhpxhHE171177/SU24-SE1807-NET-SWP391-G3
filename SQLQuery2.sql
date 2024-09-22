-- Step 1: Create the database
CREATE DATABASE DatabaseContext;
USE DatabaseContext;

-- Step 2: Create USERS table with appropriate data types
CREATE TABLE USERS (
    UserID VARCHAR(500) PRIMARY KEY, 
    DisplayName NVARCHAR(200) NOT NULL,
    Gender BIT NOT NULL,
    DateWork DATE,
    DOB DATE,
    Password VARCHAR(255) NOT NULL,
    Title NVARCHAR(255),
    Levels NVARCHAR(50),
    Department NVARCHAR(255),
    Room NVARCHAR(255),
    [Group] NVARCHAR(500),
    ProductionLine NVARCHAR(500),
    Status NVARCHAR(50),
    Location NVARCHAR(150),
    PhoneNumber VARCHAR(50),
    Email VARCHAR(50),
    Role INT NOT NULL,
    Avatar NVARCHAR(255)
);

-- Step 4: Create ERRORTYPE table
CREATE TABLE ERRORTYPE (
    ETypeID INT PRIMARY KEY IDENTITY(1,1),
    EName NVARCHAR(500) NOT NULL
);

INSERT INTO ERRORTYPE (EName) VALUES
('Network Error'),
('Database Connection Error'),
('Authentication Failed'),
('Authorization Error'),
('Invalid Input'),
('Resource Not Found'),
('Timeout Error'),
('File Not Found'),
('Server Error'),
('Duplicate Entry'),
('Data Format Error'),
('Service Unavailable'),
('Insufficient Permissions'),
('Operation Not Allowed'),
('Configuration Error'),
('Missing Parameter'),
('Rate Limit Exceeded'),
('Internal Server Error'),
('Access Denied'),
('Session Expired'),
('Invalid Credentials'),
('Payment Failed'),
('Data Mismatch'),
('Email Already Exists'),
('Username Taken'),
('Invalid Token'),
('User Not Found'),
('Password Too Weak'),
('Service Timeout'),
('File Upload Error');


-- Step 7: Create LINES table with LineCode as the primary key
CREATE TABLE LINES (
    LineCode VARCHAR(150) PRIMARY KEY,
    LineName NVARCHAR(255)
);

-- Step 9: Create Process table
CREATE TABLE Process (
    ProccessID INT PRIMARY KEY IDENTITY(1,1),
    ProcessName NVARCHAR(255) NOT NULL
);


-- Step 3: Create DEVICES table with proper foreign keys
CREATE TABLE DEVICES (
    DeviceID VARCHAR(250) PRIMARY KEY,
    LineCode VARCHAR(150),
    DeviceName NVARCHAR(255),
    DateUse DATE,
    Origin NVARCHAR(150),
    YOM INT,
    QRCode VARCHAR(255),
    Location NVARCHAR(255),
    Status NVARCHAR(50),
    ProcessID INT,
    CONSTRAINT FK_Device_Process FOREIGN KEY (ProcessID) REFERENCES Process(ProccessID),
    CONSTRAINT FK_Device_LineCode FOREIGN KEY (LineCode) REFERENCES LINES(LineCode)
);

CREATE TABLE DeviceErrors (
    DeviceID VARCHAR(250),
    ETypeID INT,
    PRIMARY KEY (DeviceID, ETypeID),
    CONSTRAINT FK_DeviceErrors_Devices FOREIGN KEY (DeviceID) REFERENCES DEVICES(DeviceID),
    CONSTRAINT FK_DeviceErrors_ErrorType FOREIGN KEY (ETypeID) REFERENCES ERRORTYPE(ETypeID)
);


-- Step 5: Create ERRORLOG table with foreign key constraints
CREATE TABLE ERRORLOG (
    ELogID INT PRIMARY KEY IDENTITY(1,1),
    DeviceID VARCHAR(250),
    LineCode VARCHAR(150),
    DeviceName NVARCHAR(255),
    ETypeID INT, 
    EDiscription NVARCHAR(500),
    EDateTime DATETIME,
    TotalTime INT,
    ReportBy VARCHAR(500),
    FixedBy VARCHAR(500),
    Status NVARCHAR(50),
    FixedDateTime DATETIME,
    CONSTRAINT FK_Error_Device FOREIGN KEY (DeviceID) REFERENCES DEVICES(DeviceID),
    CONSTRAINT FK_Error_Type FOREIGN KEY (ETypeID) REFERENCES ERRORTYPE(ETypeID),
    CONSTRAINT FK_Error_Report FOREIGN KEY (ReportBy) REFERENCES USERS(UserID)
);

-- Step 6: Create MSCHEDULE table
CREATE TABLE MSCHEDULE (
    ScheduleID INT PRIMARY KEY IDENTITY(1,1),
    DeviceID VARCHAR(250),
    ScheduleDate DATETIME,
    Status NVARCHAR(50),
    Technician VARCHAR(500),
    CONSTRAINT FK_MSchedule_Device FOREIGN KEY (DeviceID) REFERENCES DEVICES(DeviceID),
    CONSTRAINT FK_MSchedule_Technician FOREIGN KEY (Technician) REFERENCES USERS(UserID)
);


-- Step 11: Insert sample data into USERS
INSERT INTO USERS (UserID, DisplayName, Gender, DateWork, DOB, Password, Title, Levels, Department, Room, [Group], ProductionLine, Status, Location, PhoneNumber, Email, Role, Avatar)
VALUES 
('US001', 'Pham Xuan Chinh', 1, '2020-03-15', '1990-06-21', '123', 'Manager', 'Senior', 'Sales', 'A101', 'Group1', 'Line1', 'Active', 'Hanoi', '0987654321', 'alice@example.com', 1, 'avatar1.jpg'),
('US002', 'Bob Tran', 1, '2018-08-25', '1985-11-02', '123', 'Engineer', 'Mid', 'Engineering', 'B202', 'Group2', 'Line2', 'Active', 'HCMC', '0976123456', 'bob@example.com', 2, 'avatar2.jpg'),
('US003', 'Charlie Pham', 1, '2019-07-12', '1992-02-13', '123', 'Technician', 'Junior', 'Maintenance', 'C303', 'Group3', 'Line3', 'Inactive', 'Danang', '0965123456', 'charlie@example.com', 3, 'avatar3.jpg'),
('US004', 'Diana Hoang', 0, '2021-01-05', '1995-09-30', '123', 'Supervisor', 'Senior', 'Operations', 'D404', 'Group4', 'Line4', 'Active', 'Hanoi', '0945123456', 'diana@example.com', 4, 'avatar4.jpg'),
('US005', 'Ethan Le', 1, '2022-05-20', '1988-12-14', '123', 'Developer', 'Mid', 'IT', 'E505', 'Group5', 'Line5', 'Active', 'HCMC', '0932123456', 'ethan@example.com', 5, 'avatar5.jpg');

-- Step 10: Insert sample data into Process
INSERT INTO Process (ProcessName) VALUES 
(N'Ép Oilseal'), (N'Ép Lever'), (N'Ép Housing B S/A'), (N'Ép Actuator'), 
(N'Ép Bearing'), (N'Ép Valve S/A'), (N'Lắp Fix valve'), (N'Lắp Housing B S/A'), 
(N'Đo chiều cao Screw'), (N'Lắp Bushing collar'), (N'Kiểm tra LIN'), 
(N'Lắp Oring'), (N'KT rò trong và điện áp phản hồi'), (N'KT rò ngoài'), 
(N'KT rò ngược'), (N'Lắp Shaft S/A'), (N'Lắp Gasket'), (N'Siết Screw'), 
(N'Khắc Laze');


-- Step 8: Insert sample data into LINES
INSERT INTO LINES (LineCode, LineName) VALUES 
('MCVe', 'MCVe'), ('APM', 'APM'), ('VIC2', 'VIC2'), 
('CVT', 'CVT'), ('Roto', 'Roto'), ('Wash', 'Wash'), 
('Fz', 'Fz'), ('Ocv1', 'Ocv1'), ('Midl', 'Midl'), ('O2', 'O2');

INSERT INTO DEVICES 
(DeviceID, LineCode, DeviceName, DateUse, Origin, YOM, QRCode, Location, Status, ProcessID)
VALUES
('D001', 'MCVe', 'Device A1', '2022-01-15', 'Japan', 2019, 'QR001', 'Location1', 'Active', 1),
('D002', 'APM', 'Device A2', '2021-05-10', 'USA', 2018, 'QR002', 'Location2', 'Active', 2),
('D003', 'VIC2', 'Device B1', '2020-12-20', 'Germany', 2020, 'QR003', 'Location3', 'Inactive', 3),
('D004', 'CVT', 'Device B2', '2022-03-30', 'South Korea', 2021, 'QR004', 'Location4', 'Active', 4),
('D005', 'Roto', 'Device C1', '2021-08-19', 'France', 2017, 'QR005', 'Location5', 'Inactive', 5),
('D006', 'Wash', 'Device C2', '2020-02-14', 'China', 2016, 'QR006', 'Location6', 'Active', 6),
('D007', 'Fz', 'Device D1', '2021-11-11', 'Vietnam', 2019, 'QR007', 'Location7', 'Inactive', 7),
('D008', 'Ocv1', 'Device D2', '2019-07-07', 'Italy', 2015, 'QR008', 'Location8', 'Active', 8),
('D009', 'Midl', 'Device E1', '2021-04-01', 'Russia', 2020, 'QR009', 'Location9', 'Active', 9),
('D010', 'O2', 'Device E2', '2020-09-15', 'Thailand', 2018, 'QR010', 'Location10', 'Inactive', 10),
('D011', 'MCVe', 'Device F1', '2019-05-25', 'Japan', 2017, 'QR011', 'Location11', 'Active', 11),
('D012', 'APM', 'Device F2', '2022-02-02', 'USA', 2019, 'QR012', 'Location12', 'Active', 12),
('D013', 'VIC2', 'Device G1', '2021-06-14', 'Germany', 2016, 'QR013', 'Location13', 'Inactive', 13),
('D014', 'CVT', 'Device G2', '2020-12-12', 'South Korea', 2018, 'QR014', 'Location14', 'Active', 14),
('D015', 'Roto', 'Device H1', '2021-07-20', 'France', 2021, 'QR015', 'Location15', 'Inactive', 15),
('D016', 'Wash', 'Device H2', '2019-03-23', 'China', 2015, 'QR016', 'Location16', 'Active', 16),
('D017', 'Fz', 'Device I1', '2020-10-05', 'Vietnam', 2016, 'QR017', 'Location17', 'Inactive', 17),
('D018', 'Ocv1', 'Device I2', '2022-06-16', 'Italy', 2020, 'QR018', 'Location18', 'Active', 18),
('D019', 'Midl', 'Device J1', '2021-01-10', 'Russia', 2017, 'QR019', 'Location19', 'Active', 19)

INSERT INTO DeviceErrors (DeviceID, ETypeID) VALUES
('D001', 1),  -- Device A1 has Network Error
('D001', 2),  -- Device A1 has Database Connection Error
('D001', 3),  -- Device A1 has Authentication Failed

('D002', 4),  -- Device A2 has Authorization Error
('D002', 5),  -- Device A2 has Invalid Input
('D002', 6),  -- Device A2 has Resource Not Found

('D003', 7),  -- Device B1 has Timeout Error
('D003', 8),  -- Device B1 has File Not Found
('D003', 9),  -- Device B1 has Server Error

('D004', 10), -- Device B2 has Duplicate Entry
('D004', 11), -- Device B2 has Data Format Error
('D004', 12), -- Device B2 has Service Unavailable

('D005', 13), -- Device C1 has Insufficient Permissions
('D005', 14), -- Device C1 has Operation Not Allowed
('D005', 15), -- Device C1 has Configuration Error

('D006', 16), -- Device C2 has Missing Parameter
('D006', 17), -- Device C2 has Rate Limit Exceeded
('D006', 18), -- Device C2 has Internal Server Error

('D007', 19), -- Device D1 has Access Denied
('D007', 20), -- Device D1 has Session Expired
('D007', 21); -- Device D1 has Invalid Credentials

-- Continue for remaining devices...

INSERT INTO DeviceErrors (DeviceID, ETypeID) VALUES
('D008', 22), -- Device D2 has Payment Failed
('D008', 23), -- Device D2 has Data Mismatch
('D008', 24), -- Device D2 has Email Already Exists

('D009', 25), -- Device E1 has Username Taken
('D009', 26), -- Device E1 has Invalid Token
('D009', 27), -- Device E1 has User Not Found

('D010', 28), -- Device E2 has Password Too Weak
('D010', 29), -- Device E2 has Service Timeout
('D010', 30), -- Device E2 has File Upload Error

('D011', 1),  -- Device F1 has Network Error
('D011', 2),  -- Device F1 has Database Connection Error
('D011', 3),  -- Device F1 has Authentication Failed

('D012', 4),  -- Device F2 has Authorization Error
('D012', 5),  -- Device F2 has Invalid Input
('D012', 6),  -- Device F2 has Resource Not Found

('D013', 7),  -- Device G1 has Timeout Error
('D013', 8),  -- Device G1 has File Not Found
('D013', 9),  -- Device G1 has Server Error

('D014', 10), -- Device G2 has Duplicate Entry
('D014', 11), -- Device G2 has Data Format Error
('D014', 12), -- Device G2 has Service Unavailable

('D015', 13), -- Device H1 has Insufficient Permissions
('D015', 14), -- Device H1 has Operation Not Allowed
('D015', 15), -- Device H1 has Configuration Error

('D016', 16), -- Device H2 has Missing Parameter
('D016', 17), -- Device H2 has Rate Limit Exceeded
('D016', 18), -- Device H2 has Internal Server Error

('D017', 19), -- Device I1 has Access Denied
('D017', 20), -- Device I1 has Session Expired
('D017', 21), -- Device I1 has Invalid Credentials

('D018', 22), -- Device I2 has Payment Failed
('D018', 23), -- Device I2 has Data Mismatch
('D018', 24), -- Device I2 has Email Already Exists

('D019', 25), -- Device J1 has Username Taken
('D019', 26), -- Device J1 has Invalid Token
('D019', 27); -- Device J1 has User Not Found



Update DEVICES SET Status = 'Working'


Update DEVICES SET QRCode = 'https://ampet.vn/wp-content/uploads/2022/09/Cho-phoc-soc-1.jpg'

SELECT de.*, et.EName FROM DeviceErrors de 
JOIN ERRORTYPE et ON et.ETypeID = de.ETypeID where de.DeviceID = 'D001'


SELECT d.*, p.ProcessName, et.EName
FROM DEVICES d
JOIN Process p ON p.ProccessID = d.ProcessID
LEFT JOIN DeviceErrors de ON d.DeviceID = de.DeviceID
LEFT JOIN ERRORTYPE et ON de.ETypeID = et.ETypeID
ORDER BY DateUse DESC
OFFSET 1 ROWS FETCH NEXT 10 ROWS ONLY;

SELECT 
    d.DeviceID,
    d.LineCode,
    d.DeviceName,
    p.ProcessName,
    d.DateUse,
    d.Origin,
    d.YOM,
	d.QRCode,
    STRING_AGG(et.EName, ', ') AS ErrorNames,
	d.ProcessID,
    d.Location,
	d.Status
FROM 
    DEVICES d
LEFT JOIN 
    DeviceErrors de ON d.DeviceID = de.DeviceID
LEFT JOIN 
    ERRORTYPE et ON de.ETypeID = et.ETypeID
LEFT JOIN 
    Process p ON p.ProccessID = d.ProcessID
GROUP BY 
    d.DeviceID, d.LineCode, d.DeviceName, p.ProcessName, d.DateUse, d.Origin, d.YOM, d.QRCode, d.ProcessID, d.Location, d.Status
ORDER BY 
    d.DateUse DESC
OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;


SELECT d.*, p.ProcessName\n" +
                "FROM DEVICES d\n" +
                "JOIN Process p ON p.ProccessID = d.ProcessID\n" +
                "ORDER BY DateUse DESC\n" +
                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;