CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    firstName VARCHAR(50),
    lastName VARCHAR(50)
);
INSERT INTO users (id, firstName, lastName) VALUES ('1d0bbcb2-9ef6-11ef-ba7c-6f55e9414db3', 'John', 'Smith');