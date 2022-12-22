CREATE TABLE person
(
    person_id VARCHAR(36),
    name VARCHAR(50),
    location varchar(50),
    birth date
);
INSERT INTO person(person_id, name, location, birth)
VALUES (gen_random_uuid(), 'john','boston', '2001-01-01'),
       (gen_random_uuid(), 'mark','new york', '2002-02-02'),
       (gen_random_uuid(), 'joe','chicago', '2003-03-03');
