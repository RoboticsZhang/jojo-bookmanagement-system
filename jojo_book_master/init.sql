CREATE SCHEMA IF NOT EXISTS jojo_bookmanagement
    DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE jojo_bookmanagement;

CREATE TABLE IF NOT EXISTS admin
(
    admin_id  CHAR(7),
    password  CHAR(32),
    name      VARCHAR(10),
    contact CHAR(11),
    PRIMARY KEY (admin_id)
);

CREATE TABLE IF NOT EXISTS book
(
    bno      char(8),
    category char(10),
    title    varchar(40),
    press    varchar(30),
    year     int(11),
    author   varchar(20),
    price    decimal(7, 2),
    total    int(11),
    stock    int(11),
    PRIMARY KEY (bno)
);

CREATE TABLE IF NOT EXISTS card
(
    cno        CHAR(7),
    name       VARCHAR(10),
    password  CHAR(32),
    department VARCHAR(40),
    type       CHAR(1),
    PRIMARY KEY (cno)
);
CREATE TABLE IF NOT EXISTS borrow
(
    cno         CHAR(7),
    bno         CHAR(8),
    borrow_date DATETIME,
    return_date DATETIME,
    return_deadline DATETIME,
    admin_id    CHAR(7),
    PRIMARY KEY (cno, bno, borrow_date),
    foreign key (cno) references card(cno) 
		on delete cascade
        on update cascade,
    foreign key (bno) references book(bno) 
		on delete cascade 
        on update cascade,
    foreign key (admin_id) references admin(admin_id)
		on delete set null  
        on update set null
);

CREATE TABLE IF NOT EXISTS return_record
(
    cno         CHAR(7),
    bno         CHAR(8),
    borrow_date DATETIME,
    return_date DATETIME,
    admin_id    CHAR(7),
    overtime 	int(1),
    PRIMARY KEY (cno, bno, return_date),
    foreign key (cno) references card(cno) 
		on delete cascade
        on update cascade,
    foreign key (bno) references book(bno) 
		on delete cascade 
        on update cascade,
    foreign key (admin_id) references admin(admin_id)
		on delete set null 
        on update set null
);

insert into admin values(0, 0, '自助服务' , 'jojo_Library');
insert into admin values(1000, 'admin', 'admin', 18888988191);
insert into card values(1000, 'test', '111111', 'Computer Science', 1);

