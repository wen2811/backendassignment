INSERT INTO users (username, firstname, lastname, dateofbirth, email, password, enabled)
VALUES ('User1', 'Maria', 'Cruz', '2000-02-02', 'user1@test.nl', '$2a$12$s6pTevSVkE1MhbG1jp.LqeOr87tz47Jnw4L8XudrcVlqo3jpjJlCG', true),
       ('User2', 'Donald', 'Duck', '1970-08-08', 'user2@test.nl', '$2a$12$JVoz.a3j1AzPMfqnoFNQNupVOLxU.6a8BB3vX.MOfrSySPoX/IXLW', true);

INSERT INTO authorities (authority, username)
VALUES ('ROLE_EMPLOYEE', 'User1'),
        ('ROLE_ADMIN', 'User2');



/*INSERT INTO calendars(date, starttime, endtime, available_time) VALUES('2023-09-19', '12.00', '13.00', true)*/
