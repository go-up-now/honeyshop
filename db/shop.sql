create database shop_manager;
use shop_manager;

create table Users(
	Id varchar(50) not null,
    fullname nvarchar(50),
    email varchar(50),
    password varchar(100),
    dob date,
    address  varchar(100),
    thumbnail  varchar(100),
    phone varchar(10),
    gender bit,
    roleId tinyint,
    createDate datetime,
    updateDate datetime,
    primary key (Id)
);

create table Inventory(
	id varchar(50) not null,
    productID varchar(50),
    quantity int,
    changeType varchar(20),
    reason text,
    changedBy varchar(50),
    updateDate datetime,
    primary key(id)
);

create table Roles(
	id tinyint not null,
    name nvarchar(50),
    description text,
    primary key (id)
);
create table Permission(
	id tinyint not null,
    name nvarchar(50),
    description text,
    primary key(id)
);

create table Products(
	id varchar(50) not null,
    name nvarchar(50),
    description text,
    price decimal,
    categoryID varchar(50),
    createDate datetime,
    updateDate datetime,
    primary key(id)
);

create table Categories(
	id varchar(50) not null,
    name nvarchar(50),
    description text,
    createDate datetime,
    updateDate datetime,
    primary key(id)
);

create table Orders(
	id varchar(50) not null,
    userId varchar(50),
    totalAmount decimal,
    status varchar(50),
    createDate datetime,
    primary key(id)
);

create table OrderDetails(
	id varchar(50) not null,
    orderID varchar(50),
    productID varchar(50),
    quantity int,
    price decimal,
    createDate datetime,
    updateDate datetime,
    primary key(id)
);

ALTER TABLE Orders
ADD CONSTRAINT fk_orders_users FOREIGN KEY (userId) REFERENCES Users(Id);

ALTER TABLE Inventory
ADD CONSTRAINT fk_inventory_users FOREIGN KEY (changedBy) REFERENCES Users(Id),
ADD CONSTRAINT fk_inventory_products FOREIGN KEY (productID) REFERENCES Products(id);

ALTER TABLE Products
ADD CONSTRAINT fk_products_categories FOREIGN KEY (categoryID) REFERENCES Categories(id);

ALTER TABLE OrderDetails
ADD CONSTRAINT fk_orderdetails_orders FOREIGN KEY (orderID) REFERENCES Orders(id),
ADD CONSTRAINT fk_orderdetails_products FOREIGN KEY (productID) REFERENCES Products(id);






