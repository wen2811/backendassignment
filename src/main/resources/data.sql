
INSERT INTO customers (first_name, last_name, email, phone_number)
VALUES ('Joelle', 'Pedro', 'customer1@test.nl', '1234567890'),
       ('Kim', 'Boss', 'customer2@test.nl', '3456789012'),
       ('Mischa', 'Peters', 'customer3@test.nl', '5678901234'),
       ('Jeanine', 'Jean', 'customer4@test.nl', '6789012345'),
        ('Iris', 'Maan', 'customer5@test.nl', '7890123456');


INSERT INTO users (username, firstname, lastname, dateofbirth, email, password, enabled, user_role)
--password User1 = hallo
--password User2 = zondag
--password User3 = maandag
--password User4 = dinsdag
--password User5 = woensdag
VALUES ('User1', 'Maria', 'Cruz', '2000-02-02', 'employee@test.nl', '$2a$12$abGuYkVvI10IJXuR0JKP8emyPpEgJ9hVjzBWe2UusNCpbyfr/1SkG', true, 'EMPLOYEE'),
       ('User2', 'Donald', 'Duck', '1970-08-08', 'admin@test.nl', '$2a$12$zDHpcCbf3aeHEPL.4ZGiJef21xXPLunBcxcwZhRn1qUD3q3hAZn/2', true, 'ADMIN'),
       ('User3', 'Joelle', 'Pedro', '1978-05-11', 'customer1@test.nl', '$2a$12$Y4rPMU2HOmbcxf0es5hlXO6KjHmd/6vrWm3XMyB042Hz2nxGvc7vO', true, 'CUSTOMER'),
       ('User4', 'Kim', 'Boss', '1985-03-20', 'customer2@test.nl', '$2a$12$JQyRUuujOmiciyrMsObYkewDxDrPjGruX5aEFMQ474oR2SO0S/Wey', true, 'CUSTOMER'),
       ('User5', 'Mischa', 'Peters', '1988-08-18', 'customer3@test.nl', '$2a$12$ehYiMWQl2lCw.s733z7sROPGHEF4rFj5kDIfi.2kFteP902KaAMTa', true, 'CUSTOMER');

INSERT INTO treatments (id, name, type, description, duration, price)
VALUES (1, 'Kobido Facial Massage', 'FACIAL_TREATMENT', 'Kobido is a traditional Japanese facial massage that utilizes gentle, rhythmic hand movements and acupressure points to improve blood circulation and rejuvenate the skin. It is known for its relaxing and invigorating effects, helping to reduce tension and promote a radiant complexion.', 60, 100),
       (2, 'Carboxy Treatment', 'FACIAL_TREATMENT', 'A carboxy facial treatment utilizes carbon dioxide gas to boost skin circulation, enhancing collagen production for improved skin texture and tone, offering rejuvenating and renewing benefits.', 45, 80),
       (3, 'Hydrafacial Treatment', 'FACIAL_TREATMENT', 'The hydrafacial is a five-step facial treatment that cleanses, hydrates, and rejuvenates the skin, providing a dewy glow by extracting debris, moisturizing, and exfoliating with a suction device.', 60, 110),
       (4, 'LED Light Therapy', 'FACIAL_TREATMENT', 'LED light therapy rejuvenates skin with targeted light wavelengths, effectively addressing various concerns without downtime or discomfort.', 30, 45),
       (5, 'Carboxy Treatment', 'BODY_TREATMENT', 'A carboxy body treatment utilizes carbon dioxide gas to boost skin circulation, enhancing collagen production for improved skin texture and tone, offering rejuvenating and renewing benefits.', 45, 100),
       (6, 'Massage Treatment', 'BODY_TREATMENT', 'A carboxy facial treatment utilizes carbon dioxide gas to boost skin circulation, enhancing collagen production for improved skin texture and tone, offering rejuvenating and renewing benefits.', 60, 85),
       (7, 'Detox Treatment', 'BODY_TREATMENT', 'A carboxy facial treatment utilizes carbon dioxide gas to boost skin circulation, enhancing collagen production for improved skin texture and tone, offering rejuvenating and renewing benefits.', 60, 125),
       (8, 'Body mask Treatment', 'BODY_TREATMENT', 'A carboxy facial treatment utilizes carbon dioxide gas to boost skin circulation, enhancing collagen production for improved skin texture and tone, offering rejuvenating and renewing benefits.', 45, 115);


INSERT INTO calendars (date, start_time, end_time, available_time)
VALUES ('2023-10-10', '10:00:00', '11:00:00', true),
       ('2021-10-11', '11:30:00', '12:30:00', false);


INSERT INTO bookings (id, date, total_amount, booking_status, customer_id, user_username)
VALUES (21, '2023-11-01', 100, 'NEW', 1, 'User3'),
       (22, '2023-11-15', 80, 'BOOKED', 2, 'User4'),
       (23, '2023-11-08', 110, 'BOOKED', 3, 'User5');



INSERT INTO bookingtreatments (id,quantity, booking_id, treatment_id)
VALUES (11, 1, 21 ,1),
       (12, 1, 22, 2),
       (13, 1, 23, 3);



INSERT INTO invoices (amount, invoicedate, booking_id, customer_id)
VALUES (90, '2023-11-01', 21, 3),
       (125, '2023-11-15', 22, 4),
       (80, '2023-11-08', 23, 5);



INSERT INTO authorities (authority, username)
VALUES ('EMPLOYEE', 'User1'),
        ('ADMIN', 'User2'),
        ('CUSTOMER', 'User3'),
        ('CUSTOMER','User4'),
        ('CUSTOMER', 'User5');












