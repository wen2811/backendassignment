INSERT INTO users (username, firstname, lastname, dateofbirth, email, password, enabled)
VALUES ('User1', 'Maria', 'Cruz', '2000-02-02', 'user1@test.nl', '$2a$12$3pyLBM0VogqgM0XfcbUXTeKnK2FtqndUvVEO4KKV5wkMnbl9Ceq8O', true),
       ('User2', 'Donald', 'Duck', '1970-08-08', 'user2@test.nl', '$2a$12$JVoz.a3j1AzPMfqnoFNQNupVOLxU.6a8BB3vX.MOfrSySPoX/IXLW', true);

INSERT INTO authorities (authority, username)
VALUES ('ROLE_EMPLOYEE', 'User1'),
        ('ROLE_ADMIN', 'User2');

INSERT INTO calendars (date, start_time, end_time, available_time)
VALUES ('2023-10-10', '10:00:00', '11:00:00', true),
       ('2021-10-11', '11:30:00', '12:30:00', false);




