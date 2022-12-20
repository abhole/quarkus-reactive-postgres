CREATE TABLE building
(
    building_id VARCHAR(36),
    name VARCHAR(50),
    location varchar(50),
    opened date
);
INSERT INTO building(building_id, name, location, opened)
VALUES (newid(), 'burj khalifa','dubai', '2001-01-01'),
       (newid(), 'empire state building','new york', '2002-02-02'),
       (newid(), 'twin towers','kuala lumpur', '2003-03-03');


-- INSERT INTO building(building_id, name, location, opened)
-- VALUES (gen_random_uuid(), 'burj khalifa','dubai', '2001-01-01'),
--        (gen_random_uuid(), 'empire state building','new york', '2002-02-02'),
--        (gen_random_uuid(), 'twin towers','kuala lumpur', '2003-03-03');
