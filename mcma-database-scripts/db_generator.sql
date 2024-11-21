set global max_allowed_packet = 52428800;
drop schema if exists mcma;
create schema if not exists mcma;
create table if not exists mcma.cinema
(
    id                 bigint unsigned auto_increment
        primary key,
    city_id            bigint unsigned                   not null,
    name               varchar(50)                       null,
    status             tinyint                           null comment '-1, 1',
    created_by         bigint unsigned                   null,
    last_modified_by   bigint unsigned                   null,
    created_date       timestamp default utc_timestamp() null,
    last_modified_date timestamp default utc_timestamp() null
);
create table if not exists mcma.city
(
    id                 bigint unsigned auto_increment
        primary key,
    name               varchar(30)                       null,
    status             tinyint                           null comment '-1, 1',
    created_by         bigint unsigned                   null,
    last_modified_by   bigint unsigned                   null,
    created_date       timestamp default utc_timestamp() null,
    last_modified_date timestamp default utc_timestamp() null
);
create table if not exists mcma.city_drink
(
    city_id bigint unsigned not null,
    drink_id  bigint unsigned not null,
    primary key (city_id, drink_id)
);
create table if not exists mcma.city_food
(
    city_id bigint unsigned not null,
    food_id   bigint unsigned not null,
    primary key (city_id, food_id)
);
create table if not exists mcma.coupon
(
    id                 bigint unsigned auto_increment
        primary key,
    name               varchar(20)                       null,
    description        text                              null,
    discount           decimal(3, 2)                     null,
    min_spend_req      int                               null,
    discount_limit     int                               null,
    available_date     timestamp                         null,
    expired_date       timestamp                         null,
    status             tinyint                           null comment '-1, 1',
    created_by         bigint unsigned                   null,
    last_modified_by   bigint unsigned                   null,
    created_date       timestamp default utc_timestamp() null,
    last_modified_date timestamp default utc_timestamp() null
);
create table if not exists mcma.drink
(
    id                 bigint unsigned auto_increment
        primary key,
    name               varchar(20)                       null,
    description        text                              null,
    image_url          text                              null,
    size               varchar(5)                        null,
    volume             smallint                          null,
    price              int                               null,
    status             tinyint                           null comment '-1, 1',
    created_by         bigint unsigned                   null,
    last_modified_by   bigint unsigned                   null,
    created_date       timestamp default utc_timestamp() null,
    last_modified_date timestamp default utc_timestamp() null
);
create table if not exists mcma.food
(
    id                 bigint unsigned auto_increment
        primary key,
    name               varchar(20)                       null,
    description        text                              null,
    image_url          text                              null,
    size               varchar(5)                        null,
    price              int                               null,
    status             tinyint                           null comment '-1, 1',
    created_by         bigint unsigned                   null,
    last_modified_by   bigint unsigned                   null,
    created_date       timestamp default utc_timestamp() null,
    last_modified_date timestamp default utc_timestamp() null
);
create table if not exists mcma.movie
(
    id                 bigint unsigned auto_increment
        primary key,
    name               varchar(50)                       null,
    image_url          text                              null,
    length             int                               null,
    publish_date       timestamp                         null,
    rating_id          bigint unsigned                   null,
    trailer_url        text                              null,
    status             tinyint                           null comment '-1, 1',
    created_by         bigint unsigned                   null,
    last_modified_by   bigint unsigned                   null,
    created_date       timestamp default utc_timestamp() null,
    last_modified_date timestamp default utc_timestamp() null
);
create table if not exists mcma.movie_coupon
(
    movie_id  bigint unsigned not null,
    coupon_id bigint unsigned not null,
    primary key (movie_id, coupon_id)
);
create table if not exists mcma.movie_genre
(
    movie_id        bigint unsigned not null,
    genre_detail_id bigint unsigned not null,
    primary key (movie_id, genre_detail_id)
);
create table if not exists mcma.movie_genre_detail
(
    id                 bigint unsigned auto_increment
        primary key,
    name               varchar(20)                       null,
    description        text                              null,
    image_url          text                              null,
    status             tinyint                           null comment '-1, 1',
    created_by         bigint unsigned                   null,
    last_modified_by   bigint unsigned                   null,
    created_date       timestamp default utc_timestamp() null,
    last_modified_date timestamp default utc_timestamp() null
);
create table if not exists mcma.movie_performer
(
    movie_id            bigint unsigned not null,
    performer_detail_id bigint unsigned not null,
    primary key (movie_id, performer_detail_id)
);
create table if not exists mcma.movie_performer_detail
(
    id                 bigint unsigned auto_increment
        primary key,
    name               varchar(50)                       null,
    type_id            tinyint unsigned                  null comment '1: director; 2: actor',
    sex                tinyint                           null comment '0: female; 1: male',
    dob                timestamp                         null,
    status             tinyint                           null comment '-1, 1',
    created_by         bigint unsigned                   null,
    last_modified_by   bigint unsigned                   null,
    created_date       timestamp default utc_timestamp() null,
    last_modified_date timestamp default utc_timestamp() null
);
create table if not exists mcma.movie_performer_type
(
    id                 bigint unsigned                   not null primary key,
    name               varchar(50)                       null,
    status             tinyint                           null comment '-1, 1',
    created_by         bigint unsigned                   null,
    last_modified_by   bigint unsigned                   null,
    created_date       timestamp default utc_timestamp() null,
    last_modified_date timestamp default utc_timestamp() null
);
create table if not exists mcma.movie_rating_detail
(
    id                 bigint unsigned auto_increment
        primary key,
    name               varchar(20)                       null,
    description        text                              null,
    status             tinyint                           null comment '-1, 1',
    created_by         bigint unsigned                   null,
    last_modified_by   bigint unsigned                   null,
    created_date       timestamp default utc_timestamp() null,
    last_modified_date timestamp default utc_timestamp() null
) comment 'e.g. age restriction';
create table if not exists mcma.movie_response
(
    id                 bigint unsigned auto_increment
        primary key,
    user_id            bigint unsigned                   not null,
    movie_id           bigint unsigned                   not null,
    user_vote          tinyint                           null comment 'from 1 to 5 stars',
    user_comment       text                              null,
    status             tinyint                           null comment '-1, 1',
    created_by         bigint unsigned                   null,
    last_modified_by   bigint unsigned                   null,
    created_date       timestamp default utc_timestamp() null,
    last_modified_date timestamp default utc_timestamp() null
);
create table if not exists mcma.movie_schedule
(
    id                 bigint unsigned auto_increment
        primary key,
    screen_id          bigint unsigned                   not null,
    movie_id           bigint unsigned                   not null,
    start_time         timestamp                         null,
    status             tinyint                           null comment '-1, 1',
    created_by         bigint unsigned                   null,
    last_modified_by   bigint unsigned                   null,
    created_date       timestamp default utc_timestamp() null,
    last_modified_date timestamp default utc_timestamp() null
);
create table if not exists mcma.notification
(
    id                 bigint unsigned auto_increment
        primary key,
    content            text                              null,
    status             tinyint                           null comment '-1, 1',
    created_by         bigint unsigned                   null,
    last_modified_by   bigint unsigned                   null,
    created_date       timestamp default utc_timestamp() null,
    last_modified_date timestamp default utc_timestamp() null
);
create table if not exists mcma.screen
(
    id                 bigint unsigned auto_increment
        primary key,
    cinema_id          bigint unsigned                   not null,
    name               varchar(20)                       null,
    type_id            tinyint unsigned                  null,
    status             tinyint                           null comment '-1, 1',
    created_by         bigint unsigned                   null,
    last_modified_by   bigint unsigned                   null,
    created_date       timestamp default utc_timestamp() null,
    last_modified_date timestamp default utc_timestamp() null
);
create table if not exists mcma.screen_type
(
    id                 bigint unsigned auto_increment
        primary key,
    name               varchar(20)                       null,
    description        text                              null,
    status             tinyint                           null comment '-1, 1',
    created_by         bigint unsigned                   null,
    last_modified_by   bigint unsigned                   null,
    created_date       timestamp default utc_timestamp() null,
    last_modified_date timestamp default utc_timestamp() null
);
create table if not exists mcma.seat
(
    screen_id          bigint unsigned                   not null,
    row                tinyint unsigned                  not null,
    col                tinyint unsigned                  not null,
    type_id            tinyint unsigned                  null,
    name               varchar(5)                        null,
    created_by         bigint unsigned                   null,
    last_modified_by   bigint unsigned                   null,
    created_date       timestamp default utc_timestamp() null,
    last_modified_date timestamp default utc_timestamp() null,
    primary key (screen_id, row, col)
);
create table if not exists mcma.seat_type
(
    id                 bigint unsigned auto_increment
        primary key,
    name               varchar(20)                       null,
    description        text                              null,
    status             tinyint                           null comment '-1, 1',
    created_by         bigint unsigned                   null,
    last_modified_by   bigint unsigned                   null,
    created_date       timestamp default utc_timestamp() null,
    last_modified_date timestamp default utc_timestamp() null
);
create table if not exists mcma.token
(
    id            bigint unsigned auto_increment
        primary key,
    user_id       bigint unsigned null,
    is_logged_out bit             null,
    token         text            null
);
create table if not exists mcma.user
(
    id                 bigint unsigned auto_increment
        primary key,
    sex                tinyint                           null comment '0: female; 1: male',
    dob                timestamp                         null,
    email              varchar(255)                      not null,
    phone              varchar(255)                      not null,
    password           varchar(255)                      not null,
    address            text                              null,
    user_type          smallint                          null,
    status             tinyint                           null comment '-1, 1',
    created_by         bigint unsigned                   null,
    last_modified_by   bigint unsigned                   null,
    created_date       timestamp default utc_timestamp() null,
    last_modified_date timestamp default utc_timestamp() null
);
create table if not exists mcma.user_coupon
(
    user_id   bigint unsigned not null,
    coupon_id bigint unsigned not null,
    primary key (user_id, coupon_id)
) comment 'coupons that an user owns';