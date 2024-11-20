truncate table mcma.cinema;
truncate table mcma.city;
truncate table mcma.coupon;
truncate table mcma.drink;
truncate table mcma.food;
truncate table mcma.movie;
truncate table mcma.movie_coupon;
truncate table mcma.movie_genre;
truncate table mcma.movie_genre_detail;
truncate table mcma.movie_performer;
truncate table mcma.movie_performer_detail;
truncate table mcma.movie_performer_detail;
truncate table mcma.movie_rating_detail;
truncate table mcma.movie_response;
truncate table mcma.movie_schedule;
truncate table mcma.notification;
truncate table mcma.screen;
truncate table mcma.screen_type;
truncate table mcma.seat;
truncate table mcma.seat_type;
truncate table mcma.token;
truncate table mcma.user;
truncate table mcma.user_coupon;
insert into mcma.cinema (city_id, name, status, created_by, last_modified_by)
values (1, 'Hanoi Cinema 1', 1, 1, 1),
       (1, 'Hanoi Cinema 2', 1, 1, 1),
       (1, 'Hanoi Cinema 3', 1, 1, 1),
       (1, 'Hanoi Cinema 4', 1, 1, 1),
       (2, 'HCMC Cinema 1', 1, 1, 1),
       (2, 'HCMC Cinema 2', 1, 1, 1),
       (2, 'HCMC Cinema 3', 1, 1, 1),
       (2, 'HCMC Cinema 4', 1, 1, 1),
       (3, 'Da Nang Cinema 1', 1, 1, 1),
       (3, 'Da Nang Cinema 2', 1, 1, 1),
       (4, 'Hai Phong Cinema 1', 1, 1, 1),
       (4, 'Hai Phong Cinema 2', 1, 1, 1),
       (4, 'Hai Phong Cinema 3', 1, 1, 1);
insert into mcma.city_drink (city_id, drink_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (2, 1),
       (2, 2),
       (2, 3),
       (2, 4),
       (2, 5),
       (2, 6),
       (2, 7),
       (3, 1),
       (3, 2),
       (3, 3),
       (3, 4),
       (3, 5),
       (3, 6),
       (3, 7),
       (4, 1),
       (4, 2),
       (4, 3),
       (4, 4),
       (4, 5),
       (4, 6),
       (4, 7);
insert into mcma.city_food (city_id, food_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (2, 1),
       (2, 2),
       (2, 3),
       (2, 4),
       (2, 5),
       (2, 6),
       (3, 1),
       (3, 2),
       (3, 3),
       (3, 4),
       (3, 5),
       (3, 6),
       (4, 1),
       (4, 2),
       (4, 3),
       (4, 4),
       (4, 5),
       (4, 6);
insert into mcma.city (name, status, created_by, last_modified_by)
values ('Hanoi', 1, 1, 1),
       ('Ho Chi Minh City', 1, 1, 1),
       ('Da Nang', 1, 1, 1),
       ('Hai Phong', 1, 1, 1);
insert into mcma.coupon (name, description, discount, min_spend_req, discount_limit, available_date, expired_date,
                         status, created_by, last_modified_by)
values
    -- Small discount, no minimum spend
    ('SAVE5', '5% off on any order', 0.05, NULL, NULL, '2024-01-01 00:00:00', '2024-06-30 23:59:59', 1, 1, 1),
    ('SAVE10', '10% off on orders above 100,000 VND', 0.10, 100000, 50000, '2024-01-01 00:00:00', '2024-12-31 23:59:59',
     1, 1, 1),
    ('SAVE20', '20% off up to 100,000 VND for orders above 200,000 VND', 0.20, 200000, 100000, '2024-02-01 00:00:00',
     '2024-07-31 23:59:59', 1, 1, 1),
    ('FLASH50', '50% off up to 200,000 VND for a limited time', 0.50, 500000, 200000, '2024-03-01 00:00:00',
     '2024-03-15 23:59:59', 1, 1, 1),
    ('SEASON20', '20% off on all products during the season sale', 0.20, NULL, NULL, '2024-06-01 00:00:00',
     '2024-06-30 23:59:59', 1, 1, 1),
    ('BIGSPEND50', '50,000 VND discount for orders above 1,000,000 VND', NULL, 1000000, 50000, '2024-01-01 00:00:00',
     '2024-12-31 23:59:59', 1, 1, 1),
    ('WELCOME', '30% off for new users up to 150,000 VND', 0.30, 50000, 150000, '2024-01-01 00:00:00',
     '2024-12-31 23:59:59', 1, 1, 1);
insert into mcma.drink (name, description, image_url, size, volume, price, status, created_by, last_modified_by)
values ('Coke', 'Classic Coca-Cola', 'http://example.com/images/coke.jpg', 'XL', 16, 50000, 1, 1, 1),
       ('Coke', 'Classic Coca-Cola', 'http://example.com/images/coke.jpg', 'L', 15, 48000, 1, 1, 1),
       ('Coke', 'Classic Coca-Cola', 'http://example.com/images/coke.jpg', 'M', 14, 46000, 1, 1, 1),
       ('Pepsi', 'Pepsi Cola', 'http://example.com/images/pepsi.jpg', 'XL', 16, 50000, 1, 1, 1),
       ('Pepsi', 'Pepsi Cola', 'http://example.com/images/pepsi.jpg', 'L', 15, 48000, 1, 1, 1),
       ('Pepsi', 'Pepsi Cola', 'http://example.com/images/pepsi.jpg', 'M', 51400, 46000, 1, 1, 1),
       ('Orange Juice', 'Freshly squeezed orange juice', 'http://example.com/images/orange-juice.jpg', 'L', 15, 35000,
        1, 1, 1);
insert into mcma.food (name, description, image_url, size, price, status, created_by, last_modified_by)
values ('Buttery Popcorn', 'Buttery popcorn', 'http://example.com/images/popcorn.jpg', 'L', 50000, 1, 1, 1),
       ('Buttery Popcorn', 'Buttery popcorn', 'http://example.com/images/popcorn.jpg', 'M', 40000, 1, 1, 1),
       ('Caramel Popcorn', 'Caramel popcorn', 'http://example.com/images/popcorn.jpg', 'L', 70000, 1, 1, 1),
       ('Caramel Popcorn', 'Caramel popcorn', 'http://example.com/images/popcorn.jpg', 'M', 65000, 1, 1, 1),
       ('Nachos', 'Crispy nachos with cheese dip', 'http://example.com/images/nachos.jpg', null, 40000, 1, 1, 1),
       ('Hotdog', 'Juicy hotdog in a bun', 'http://example.com/images/hotdog.jpg', null, 35000, 1, 1, 1);
insert into mcma.user (sex, dob, email, phone, password, address, user_type, status, created_by, last_modified_by)
values (1, '2004-04-11 00:00:00', 'at@gmail.com', '0976289114', 'abcd1234', 'Cau Giay, Hanoi, Vietnam', 1, 1, null,
        null);
