create table if not exists photoz (
    id int primary key not null auto_increment,
    file_name varchar(255),
    content_type varchar(255),
    data binary
    );