create table clans (
    id int PRIMARY KEY,
    name VARCHAR(50) not null unique,
    gold int not null
);