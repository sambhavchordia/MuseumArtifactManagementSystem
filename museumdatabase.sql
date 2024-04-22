CREATE database museum;
USE museum;

CREATE TABLE Categories (
    CategoryID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    Description TEXT,
    ParentCategoryID INT,
    FOREIGN KEY (ParentCategoryID) REFERENCES Categories(CategoryID)
);

CREATE TABLE Employees (
    EmployeeID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(255) NOT NULL,
    LastName VARCHAR(255) NOT NULL,
    Role VARCHAR(255),
    DepartmentID INT,
    HireDate DATE,
    Email VARCHAR(255),
    Phone VARCHAR(255)
);

CREATE TABLE Departments (
    DepartmentID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    ManagerID INT,
    Budget DECIMAL(10, 2),
    Phone VARCHAR(255),
    Email VARCHAR(255),
    OfficeLocation VARCHAR(255),
    FoundedYear YEAR,
    FOREIGN KEY (ManagerID) REFERENCES Employees(EmployeeID)
);

CREATE TABLE Locations (
    LocationID INT PRIMARY KEY AUTO_INCREMENT,
    RoomNumber VARCHAR(255) NOT NULL,
    BuildingName VARCHAR(255),
    Address TEXT,
    Capacity INT,
    SecurityLevel INT,
    IsExhibitionSpace BOOLEAN,
    IsAuctionSpace BOOLEAN
);

CREATE TABLE Artifacts (
    ArtifactID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    Description TEXT,
    DateAcquired DATE,
    Origin VARCHAR(255),
    Dimensions VARCHAR(255),
    CategoryID INT,
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
);

CREATE TABLE Exhibitions (
    ExhibitionID INT PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(255) NOT NULL,
    Theme VARCHAR(255),
    DateOpened DATE,
    DateClosed DATE,
    LocationID INT,
    CuratorID INT,
    Budget DECIMAL(10, 2),
    FOREIGN KEY (LocationID) REFERENCES Locations(LocationID),
    FOREIGN KEY (CuratorID) REFERENCES Employees(EmployeeID)
);

CREATE TABLE Auctions (
    AuctionID INT PRIMARY KEY AUTO_INCREMENT,
    AuctionName VARCHAR(255) NOT NULL,
    Date DATE,
    LocationID INT,
    OrganizerID INT,
    StartPrice DECIMAL(10, 2),
    EndPrice DECIMAL(10, 2),
    WinningBid DECIMAL(10, 2),
    FOREIGN KEY (LocationID) REFERENCES Locations(LocationID),
    FOREIGN KEY (OrganizerID) REFERENCES Employees(EmployeeID)
);

CREATE TABLE Buyers (
    BuyerID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    ContactInfo VARCHAR(255),
    Address TEXT,
    RegistrationDate DATE,
    BidNumber INT,
    Verified BOOLEAN,
    TotalSpend DECIMAL(10, 2)
);

CREATE TABLE AuctionItems (
    AuctionItemID INT PRIMARY KEY AUTO_INCREMENT,
    ArtifactID INT,
    AuctionID INT,
    ReservePrice DECIMAL(10, 2),
    EstimatedPrice DECIMAL(10, 2),
    FinalSalePrice DECIMAL(10, 2),
    BuyerID INT,
    FOREIGN KEY (ArtifactID) REFERENCES Artifacts(ArtifactID),
    FOREIGN KEY (AuctionID) REFERENCES Auctions(AuctionID),
    FOREIGN KEY (BuyerID) REFERENCES Buyers(BuyerID)
);

INSERT INTO Categories (Name, Description, ParentCategoryID) VALUES
('Sculptures', 'Statues, reliefs, and other similar artworks.', NULL),
('Paintings', 'Works of art created using paints.', NULL),
('Ancient Artifacts', 'Artifacts from ancient civilizations.', NULL),
('Modern Art', 'Artworks from the 20th century onwards.', NULL),
('Ceramics', 'Artworks made from ceramic materials.', NULL),
('Textiles', 'Artifacts made from weaving or knitting.', NULL),
('Photography', 'Artistic and documentary photography.', NULL),
('Digital Art', 'Artworks created using digital technology.', NULL);


INSERT INTO Locations (RoomNumber, BuildingName, Address, Capacity, SecurityLevel, IsExhibitionSpace, IsAuctionSpace) VALUES
('101', 'Main Gallery', '123 Museum Way', 200, 3, TRUE, FALSE),
('102', 'Main Gallery', '123 Museum Way', 150, 3, TRUE, FALSE),
('201', 'Auction Hall', '456 Auction Rd', 300, 4, FALSE, TRUE),
('202', 'Auction Hall', '456 Auction Rd', 250, 4, FALSE, TRUE),
('301', 'West Wing', '789 West St', 100, 2, TRUE, FALSE),
('302', 'West Wing', '789 West St', 100, 2, TRUE, FALSE),
('401', 'East Pavilion', '321 East Ave', 80, 1, TRUE, FALSE),
('402', 'East Pavilion', '321 East Ave', 80, 1, TRUE, FALSE);

INSERT INTO Employees (FirstName, LastName, Role, DepartmentID, HireDate, Email, Phone) VALUES
('John', 'Doe', 'Curator', NULL, '2020-01-10', 'john.doe@example.com', '555-1234'),
('Alice', 'Smith', 'Auctioneer', NULL, '2019-05-15', 'alice.smith@example.com', '555-5678'),
('Robert', 'Brown', 'Security', NULL, '2021-06-20', 'robert.brown@example.com', '555-4321'),
('Emily', 'Jones', 'Guide', NULL, '2018-03-05', 'emily.jones@example.com', '555-8765'),
('Michael', 'Wilson', 'Administrator', NULL, '2017-11-01', 'michael.wilson@example.com', '555-9876'),
('Jessica', 'Taylor', 'Conservator', NULL, '2022-02-15', 'jessica.taylor@example.com', '555-6543'),
('William', 'Davis', 'Marketing', NULL, '2019-09-10', 'william.davis@example.com', '555-3478'),
('Sarah', 'Moore', 'HR Manager', NULL, '2018-07-20', 'sarah.moore@example.com', '555-7890');

INSERT INTO Departments (Name, ManagerID, Budget, Phone, Email, OfficeLocation, FoundedYear) VALUES
('Curatorial', NULL, 50000.00, '555-9988', 'curatorial@example.com', 'Building A', 1980),
('Auctions', NULL, 30000.00, '555-8765', 'auctions@example.com', 'Building B', 1985),
('Security', NULL, 20000.00, '555-1122', 'security@example.com', 'Building C', 1990),
('Guidance', NULL, 15000.00, '555-3322', 'guidance@example.com', 'Building D', 2000),
('Administration', NULL, 25000.00, '555-4422', 'admin@example.com', 'Main Office', 1995),
('Conservation', NULL, 35000.00, '555-5522', 'conservation@example.com', 'Lab', 1988),
('Marketing', NULL, 30000.00, '555-6622', 'marketing@example.com', 'Building E', 2010),
('Human Resources', NULL, 40000.00, '555-7722', 'hr@example.com', 'HR Office', 2015);

-- Assign DepartmentIDs to Employees now that Departments exist
UPDATE Employees SET DepartmentID = (SELECT DepartmentID FROM Departments WHERE Name = 'Curatorial') WHERE LastName = 'Doe';
UPDATE Employees SET DepartmentID = (SELECT DepartmentID FROM Departments WHERE Name = 'Auctions') WHERE LastName = 'Smith';

-- Assign Managers to Departments
UPDATE Departments SET ManagerID = (SELECT EmployeeID FROM Employees WHERE LastName = 'Doe') WHERE Name = 'Curatorial';
UPDATE Departments SET ManagerID = (SELECT EmployeeID FROM Employees WHERE LastName = 'Smith') WHERE Name = 'Auctions';

INSERT INTO Artifacts (Name, Description, DateAcquired, Origin, Dimensions, CategoryID) VALUES
('Roman Statue', 'A marble statue from ancient Rome.', '2018-04-12', 'Italy', '200cm x 60cm x 60cm', 1),
('Impressionist Painting', 'A painting from the Impressionist period.', '2019-07-19', 'France', '100cm x 80cm', 2),
('Egyptian Sarcophagus', 'Sarcophagus from Ancient Egypt.', '2020-03-15', 'Egypt', '220cm x 100cm x 90cm', 3),
('Modern Sculpture', 'A contemporary art piece.', '2021-01-22', 'USA', '150cm x 150cm x 150cm', 4),
('Chinese Porcelain', 'A piece of Ming Dynasty porcelain.', '2022-05-30', 'China', '50cm x 30cm', 5),
('Medieval Tapestry', 'A tapestry from the medieval period.', '2023-02-14', 'England', '400cm x 300cm', 6),
('War Photo', 'A photo from World War II.', '2019-11-11', 'Germany', '60cm x 40cm', 7),
('Virtual Reality Art', 'A digital art piece using VR.', '2020-08-25', 'Japan', 'Digital', 8);

INSERT INTO Exhibitions (Title, Theme, DateOpened, DateClosed, LocationID, CuratorID, Budget) VALUES
('Ancient Civilizations', 'The history of ancient cultures', '2023-01-10', '2023-12-10', 1, 1, 10000.00),
('Impressionism and Beyond', 'Exploring the depths of Impressionism', '2023-03-05', '2023-10-05', 2, 1, 12000.00),
('World War Chronicles', 'Artifacts and stories from World Wars', '2023-05-15', '2023-11-15', 3, 2, 15000.00),
('Sculpture Through Ages', 'Evolution of sculpture from ancient to modern times', '2023-06-20', '2024-01-20', 4, 1, 13000.00),
('Digital Futures', 'The impact of digital technology on art', '2023-09-01', '2024-03-01', 5, 2, 14000.00),
('The Silk Road', 'Artifacts from the historical trade routes', '2023-11-10', '2024-05-10', 6, 1, 16000.00),
('Renaissance Revelations', 'Exploring the Renaissance era', '2024-01-15', '2024-07-15', 7, 1, 17000.00),
('Modern Art Movements', 'Key movements in 20th century art', '2024-02-20', '2024-08-20', 8, 2, 18000.00);

INSERT INTO Auctions (AuctionName, Date, LocationID, OrganizerID, StartPrice, EndPrice, WinningBid) VALUES
('Spring Fine Art Auction', '2023-04-15', 201, 2, 10000.00, 20000.00, 15000.00),
('Summer Antique Sale', '2023-07-20', 202, 2, 5000.00, 15000.00, 12000.00),
('Autumn Modern Art Auction', '2023-10-05', 201, 2, 15000.00, 30000.00, 25000.00),
('Winter Rare Books Auction', '2023-12-15', 202, 2, 2000.00, 8000.00, 6000.00),
('Annual Jewelry Auction', '2024-02-10', 201, 2, 25000.00, 50000.00, 45000.00),
('Classic Cars Auction', '2024-03-25', 202, 2, 30000.00, 100000.00, 75000.00),
('Vintage Clocks Auction', '2024-05-30', 201, 2, 1000.00, 5000.00, 3200.00),
('Contemporary Art Auction', '2024-08-20', 202, 2, 20000.00, 40000.00, 35000.00);

INSERT INTO Buyers (Name, ContactInfo, Address, RegistrationDate, BidNumber, Verified, TotalSpend) VALUES
('Elena Rodriguez', 'elena.rodriguez@example.com', '1542 Market Street, San Francisco, CA', '2023-03-15', 101, TRUE, 450000.00),
('Mark Johnson', 'mark.johnson@example.com', '711 Ocean Drive, Miami, FL', '2023-01-01', 102, TRUE, 380000.00),
('Li Wei', 'li.wei@example.com', '328 Garden Avenue, Vancouver, BC', '2023-02-20', 103, TRUE, 295000.00),
('Sofia Martinez', 'sofia.martinez@example.com', '990 Pine Street, Mexico City, MX', '2023-04-05', 104, FALSE, 170000.00),
('Aarav Singh', 'aarav.singh@example.com', '1201 Lotus Path, New Delhi, IN', '2023-01-25', 105, TRUE, 220000.00),
('Chloe Dubois', 'chloe.dubois@example.com', '233 New Lane, Paris, FR', '2023-03-10', 106, TRUE, 350000.00),
('Luca Bianchi', 'luca.bianchi@example.com', '45 Via Roma, Rome, IT', '2023-01-10', 107, TRUE, 410000.00),
('Emma Müller', 'emma.muller@example.com', '78 Stern Street, Berlin, DE', '2023-02-15', 108, FALSE, 185000.00),
('Yuki Tanaka', 'yuki.tanaka@example.com', '506 Cherry Blossom Ave, Tokyo, JP', '2023-03-01', 109, TRUE, 320000.00),
('Olivia Smith', 'olivia.smith@example.com', '2076 Maple Road, Toronto, ON', '2023-04-08', 110, TRUE, 240000.00);

INSERT INTO AuctionItems (ArtifactID, AuctionID, ReservePrice, EstimatedPrice, FinalSalePrice, BuyerID) VALUES
(1, 9, 8000.00, 12000.00, 15000.00, 1), -- Roman Statue at Spring Fine Art Auction
(2, 9, 5000.00, 10000.00, 11000.00, 2), -- Impressionist Painting at Spring Fine Art Auction
(3, 10, 3000.00, 6000.00, 6500.00, 3), -- Egyptian Sarcophagus at Summer Antique Sale
(4, 10, 20000.00, 25000.00, 22000.00, 4), -- Modern Sculpture at Summer Antique Sale
(5, 11, 12000.00, 18000.00, 20000.00, 5), -- Chinese Porcelain at Autumn Modern Art Auction
(6, 11, 8000.00, 12000.00, 14000.00, 6), -- Medieval Tapestry at Autumn Modern Art Auction
(7, 12, 1500.00, 3000.00, 4000.00, 7), -- War Photo at Winter Rare Books Auction
(8, 12, 18000.00, 22000.00, NULL, NULL), -- Virtual Reality Art at Winter Rare Books Auction (Not Sold)
(1, 13, 20000.00, 30000.00, 32000.00, 8), -- Roman Statue at Annual Jewelry Auction
(2, 13, 28000.00, 35000.00, 38000.00, 9), -- Impressionist Painting at Annual Jewelry Auction
(3, 14, 3500.00, 8000.00, 10000.00, 10), -- Egyptian Sarcophagus at Classic Cars Auction
(4, 14, 60000.00, 80000.00, 75000.00, 11), -- Modern Sculpture at Classic Cars Auction
(5, 15, 800.00, 2500.00, 3000.00, 12), -- Chinese Porcelain at Vintage Clocks Auction
(6, 15, 15000.00, 20000.00, 18000.00, 13), -- Medieval Tapestry at Vintage Clocks Auction
(7, 16, 18000.00, 30000.00, NULL, NULL), -- War Photo at Contemporary Art Auction (Not Sold)
(8, 16, 25000.00, 35000.00, NULL, NULL); -- Virtual Reality Art at Contemporary Art Auction (Not Sold)
