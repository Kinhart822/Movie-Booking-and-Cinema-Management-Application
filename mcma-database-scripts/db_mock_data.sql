set foreign_key_checks = 0;
truncate table mcma.booking;
truncate table mcma.cinema;
truncate table mcma.city;
truncate table mcma.coupon;
truncate table mcma.drink;
truncate table mcma.food;
truncate table mcma.genre;
truncate table mcma.map_booking_coupon;
truncate table mcma.map_booking_drink;
truncate table mcma.map_booking_food;
truncate table mcma.map_booking_ticket;
truncate table mcma.map_movie_coupon;
truncate table mcma.map_movie_genre;
truncate table mcma.map_movie_performer;
truncate table mcma.map_user_coupon;
truncate table mcma.movie;
truncate table mcma.notification;
truncate table mcma.performer;
truncate table mcma.rating;
truncate table mcma.review;
truncate table mcma.schedule;
truncate table mcma.screen;
truncate table mcma.screen_type;
truncate table mcma.seat;
truncate table mcma.ticket;
truncate table mcma.token;
truncate table mcma.user;
set foreign_key_checks = 1;
insert into mcma.city (name, status, created_by, created_date, last_modified_by, last_modified_date)
values ('Hanoi', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Ho Chi Minh City', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Da Nang', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Hai Phong', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('New York', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Los Angeles', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Chicago', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Houston', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Phoenix', 1, 1, utc_timestamp(), 1, utc_timestamp());
insert into mcma.cinema (city_id, name, status, created_by, created_date, last_modified_by, last_modified_date)
values (1, 'Hanoi Cinema 1', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (1, 'Hanoi Cinema 2', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (1, 'Hanoi Cinema 3', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (1, 'Hanoi Cinema 4', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (2, 'HCMC Cinema 1', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (2, 'HCMC Cinema 2', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (2, 'HCMC Cinema 3', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (2, 'HCMC Cinema 4', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (3, 'Da Nang Cinema 1', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (3, 'Da Nang Cinema 2', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (4, 'Hai Phong Cinema 1', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (4, 'Hai Phong Cinema 2', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (4, 'Hai Phong Cinema 3', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (5, 'Cineworld NYC', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (5, 'AMC Empire', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (5, 'Regal Union Square', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (6, 'LA Cinemas', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (6, 'Hollywood Theatre', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (6, 'Downtown LA Cinema', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (7, 'Chicago Movie Palace', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (7, 'AMC River East 21', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (7, 'Music Box Theatre', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (8, 'Houston Cineplex', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (8, 'AMC Houston 8', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (8, 'Regal Houston Center', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (9, 'Phoenix Showcase', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (9, 'AMC Arizona Center', 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (9, 'Harkins Arizona Mills', 1, 1, utc_timestamp(), 1, utc_timestamp());
-- not updated
# insert into mcma.coupon (name, description, discount, min_spend_req, discount_limit, available_date, expired_date,
#                          status, created_by, last_modified_by)
# values
#     -- Small discount, no minimum spend
#     ('SAVE5', '5% off on any order', 0.05, NULL, NULL, '2024-01-01 00:00:00', '2024-06-30 23:59:59', 1, 1, 1),
#     ('SAVE10', '10% off on orders above 100,000 VND', 0.10, 100000, 50000, '2024-01-01 00:00:00', '2024-12-31 23:59:59',
#      1, 1, 1),
#     ('SAVE20', '20% off up to 100,000 VND for orders above 200,000 VND', 0.20, 200000, 100000, '2024-02-01 00:00:00',
#      '2024-07-31 23:59:59', 1, 1, 1),
#     ('FLASH50', '50% off up to 200,000 VND for a limited time', 0.50, 500000, 200000, '2024-03-01 00:00:00',
#      '2024-03-15 23:59:59', 1, 1, 1),
#     ('SEASON20', '20% off on all products during the season sale', 0.20, NULL, NULL, '2024-06-01 00:00:00',
#      '2024-06-30 23:59:59', 1, 1, 1),
#     ('BIGSPEND50', '50,000 VND discount for orders above 1,000,000 VND', NULL, 1000000, 50000, '2024-01-01 00:00:00',
#      '2024-12-31 23:59:59', 1, 1, 1),
#     ('WELCOME', '30% off for new users up to 150,000 VND', 0.30, 50000, 150000, '2024-01-01 00:00:00',
#      '2024-12-31 23:59:59', 1, 1, 1);
# insert into mcma.drink (name, description, image_url, size, volume, price, status, created_by, last_modified_by)
# values ('Coke', 'Classic Coca-Cola', 'http://example.com/images/coke.jpg', 'XL', 16, 50000, 1, 1, 1),
#        ('Coke', 'Classic Coca-Cola', 'http://example.com/images/coke.jpg', 'L', 15, 48000, 1, 1, 1),
#        ('Coke', 'Classic Coca-Cola', 'http://example.com/images/coke.jpg', 'M', 14, 46000, 1, 1, 1),
#        ('Pepsi', 'Pepsi Cola', 'http://example.com/images/pepsi.jpg', 'XL', 16, 50000, 1, 1, 1),
#        ('Pepsi', 'Pepsi Cola', 'http://example.com/images/pepsi.jpg', 'L', 15, 48000, 1, 1, 1),
#        ('Pepsi', 'Pepsi Cola', 'http://example.com/images/pepsi.jpg', 'M', 51400, 46000, 1, 1, 1),
#        ('Orange Juice', 'Freshly squeezed orange juice', 'http://example.com/images/orange-juice.jpg', 'L', 15, 35000,
#         1, 1, 1);
# insert into mcma.food (name, description, image_url, size, price, status, created_by, last_modified_by)
# values ('Buttery Popcorn', 'Buttery popcorn', 'http://example.com/images/popcorn.jpg', 'L', 50000, 1, 1, 1),
#        ('Buttery Popcorn', 'Buttery popcorn', 'http://example.com/images/popcorn.jpg', 'M', 40000, 1, 1, 1),
#        ('Caramel Popcorn', 'Caramel popcorn', 'http://example.com/images/popcorn.jpg', 'L', 70000, 1, 1, 1),
#        ('Caramel Popcorn', 'Caramel popcorn', 'http://example.com/images/popcorn.jpg', 'M', 65000, 1, 1, 1),
#        ('Nachos', 'Crispy nachos with cheese dip', 'http://example.com/images/nachos.jpg', null, 40000, 1, 1, 1),
#        ('Hotdog', 'Juicy hotdog in a bun', 'http://example.com/images/hotdog.jpg', null, 35000, 1, 1, 1);
--
insert into mcma.screen_type (name, description, price, status, created_by, created_date, last_modified_by,
                              last_modified_date)
values ('IMAX', 'High-definition immersive experience', 10, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('3D', 'Three-dimensional viewing experience', 20, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Standard', 'Regular viewing', 30, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Dolby Atmos', 'Advanced sound system with 360-degree audio', 40, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Standard', 'Traditional cinema experience', 50, 1, 1, utc_timestamp(), 1, utc_timestamp());
insert into mcma.screen (cinema_id, name, type_id, status, mutable, created_by, created_date, last_modified_by,
                         last_modified_date)
values (4, 'screen 1 cinema 4', 1, 1, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (4, 'screen 2 cinema 4', 1, 1, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (5, 'screen 1 cinema 5', 1, 1, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (5, 'screen 2 cinema 5', 1, 1, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (6, 'screen 1 cinema 6', 1, 1, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       (6, 'screen 2 cinema 6', 1, 1, 1, 1, utc_timestamp(), 1, utc_timestamp());
insert into mcma.user (first_name, last_name, sex, dob, email, phone, password, address, user_type, status, created_by,
                       created_date, last_modified_by, last_modified_date)
values ('At', 'Nguyen', 1, '2004-04-11 00:00:00', 'at0@gmail.com', '0976289114',
        '$2a$10$do2lQSohzuLfiSljWORi0Oc3OVG37mtPWxR/cAmJSsOLebcF4A5Oi', 'Hanoi, Viet Nam', 0, 1, 0, utc_timestamp(), 0,
        utc_timestamp());
insert into mcma.rating (name, description, status, created_by, last_modified_by, created_date, last_modified_date)
values ('PG-13', 'Parental Guidance-13', 1, 1, 1, utc_timestamp(), utc_timestamp()),
       ('R', 'Restricted', 1, 1, 1, utc_timestamp(), utc_timestamp()),
       ('G', 'General Audience', 1, 1, 1, utc_timestamp(), utc_timestamp());

insert into mcma.movie (name, length, publish_date, rating_id, status, created_by, created_date, last_modified_by,
                        last_modified_date)
values ('Inception', 148, '2010-07-16 00:00:00', 1, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Interstellar', 169, '2014-11-07 00:00:00', 2, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('The Dark Knight', 152, '2008-07-18 00:00:00', 3, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Tenet', 150, '2020-08-26 00:00:00', 1, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Dunkirk', 106, '2017-07-21 00:00:00', 2, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('not released', 10, '2025-01-31 00:00:00', 3, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('The Fast Saga', 120, '2023-01-01 00:00:00', 1, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Laughter Night', 90, '2023-02-15 00:00:00', 1, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Emotional Rollercoaster', 140, '2023-03-10 00:00:00', 1, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Space Adventure', 150, '2023-04-22 00:00:00', 1, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Horror Night', 100, '2023-05-05 00:00:00', 2, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Laughing Matters', 105, '2023-06-11 00:00:00', 2, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Action Hero', 130, '2023-07-20 00:00:00', 2, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Family Ties', 95, '2023-08-08 00:00:00', 2, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('Mystery Mansion', 110, '2023-09-30 00:00:00', 3, 1, 1, utc_timestamp(), 1, utc_timestamp()),
       ('The Last Stand', 115, '2023-10-12 00:00:00', 3, 1, 1, utc_timestamp(), 1, utc_timestamp());
insert into mcma.genre (name, description, status, created_by, last_modified_by, created_date, last_modified_date)
values ('Action', 'Action-packed movie', 1, 1, 1, utc_timestamp(), utc_timestamp()),
       ('Comedy', 'Humorous movie', 1, 1, 1, utc_timestamp(), utc_timestamp()),
       ('Drama', 'Emotional storytelling', 1, 1, 1, utc_timestamp(), utc_timestamp()),
       ('Sci-fi', 'Science Fiction', 1, 1, 1, utc_timestamp(), utc_timestamp());
insert into mcma.map_movie_genre (movie_id, genre_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 2),
       (2, 3),
       (2, 4),
       (3, 3),
       (3, 4),
       (3, 1),
       (4, 4),
       (4, 1),
       (4, 2),
       (5, 1),
       (5, 2),
       (5, 3),
       (6, 2),
       (6, 3),
       (6, 4),
       (7, 3),
       (7, 4),
       (7, 1),
       (8, 4),
       (8, 1),
       (8, 2),
       (9, 1),
       (9, 2),
       (9, 3),
       (10, 2),
       (10, 3),
       (10, 4),
       (11, 3),
       (11, 4),
       (11, 1),
       (12, 4),
       (12, 1),
       (12, 2),
       (13, 1),
       (13, 2),
       (13, 3),
       (14, 2),
       (14, 3),
       (14, 4),
       (15, 3),
       (15, 4),
       (15, 1),
       (16, 4),
       (16, 1),
       (16, 2);

insert into mcma.performer (name, type_id, sex, dob, status, created_by, last_modified_by, created_date,
                            last_modified_date)
values ('John Doe', 1, 0, '1980-05-15 00:00:00', 1, 1, 1, utc_timestamp(), utc_timestamp()),
       ('Jane Smith', 0, 1, '1990-10-20 00:00:00', 1, 1, 1, utc_timestamp(), utc_timestamp()),
       ('Alice Johnson', 0, 1, '1985-03-25 00:00:00', 1, 1, 1, utc_timestamp(), utc_timestamp()),
       ('Bob Brown', 1, 0, '1975-12-05 00:00:00', 1, 1, 1, utc_timestamp(), utc_timestamp()),
       ('Charlie Davis', 1, 1, '1982-07-30 00:00:00', 1, 1, 1, utc_timestamp(), utc_timestamp());
insert into mcma.map_movie_performer (movie_id, performer_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (2, 1),
       (3, 2),
       (3, 3),
       (3, 4),
       (4, 5),
       (4, 1),
       (4, 2),
       (5, 3),
       (5, 4),
       (5, 5),
       (6, 1),
       (6, 2),
       (6, 3),
       (7, 4),
       (7, 5),
       (7, 1),
       (8, 2),
       (8, 3),
       (8, 4),
       (9, 5),
       (9, 1),
       (9, 2),
       (10, 3),
       (10, 4),
       (10, 5),
       (11, 1),
       (11, 2),
       (11, 3),
       (12, 4),
       (12, 5),
       (12, 1),
       (13, 2),
       (13, 3),
       (13, 4),
       (14, 5),
       (14, 1),
       (14, 2),
       (15, 3),
       (15, 4),
       (15, 5),
       (16, 1),
       (16, 2),
       (16, 3);
# insert into schedule (screen_id, movie_id, start_time, end_time, status, created_by,last_modified_by,created_date,last_modified_date)
# values (1,3,'2025-06-01 00:00:00'),
