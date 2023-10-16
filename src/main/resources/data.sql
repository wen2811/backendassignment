INSERT INTO users (username, firstname, lastname, dateofbirth, email, password, enabled)
--password User1 = hallo
VALUES ('User1', 'Maria', 'Cruz', '2000-02-02', 'user1@test.nl', '$2a$12$3pyLBM0VogqgM0XfcbUXTeKnK2FtqndUvVEO4KKV5wkMnbl9Ceq8O', true),
       ('User2', 'Donald', 'Duck', '1970-08-08', 'user2@test.nl', '$2a$12$JVoz.a3j1AzPMfqnoFNQNupVOLxU.6a8BB3vX.MOfrSySPoX/IXLW', true);

INSERT INTO authorities (authority, username)
VALUES ('ROLE_EMPLOYEE', 'User1'),
        ('ROLE_ADMIN', 'User2');

/*INSERT INTO bookings (id, date, total_amount, booking_status, treatment_id, customer_id, user_id)
VALUES (1, '2023-09-30', 90, 'NEW', 1, 1, 1);
INSERT INTO bookings (id, date, total_amount, booking_status, treatment_id, customer_id, user_id)
VALUES (2, '2023-10-15', 120, 'CONFIRMED', 2, 2, 2);*/

INSERT INTO bookings (id, date, total_amount)
VALUES (1, '2023-11-01', 90),
       (2, '2023-10-30', 120);

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

INSERT INTO customers (first_name, last_name, email, phone_number)
VALUES ('Joelle', 'Pedro', 'user1@test.nl', '1234567890'),
       ('Kim', 'Boss', 'user2@test.nl', '3456789012'),
       ('Mischa', 'Peters', 'user3@test.nl', '5678901234'),
       ('Jeanine', 'Jean', 'user4@test.nl', '6789012345');

/*INSERT INTO invoices (amount, invoicedate, booking_id)
VALUES (90, '2023-11-01', 1),
       (120, '2023-10-30', 2);
*/



