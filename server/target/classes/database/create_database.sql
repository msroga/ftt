DROP DATABASE IF EXISTS fttdb;
-- DROP DATABASE IF EXISTS myomedstoragedb;
DROP ROLE IF EXISTS fttuser;

CREATE USER fttUser;
ALTER USER fttUser with ENCRYPTED password 'test';
CREATE DATABASE fttdb WITH ENCODING 'UNICODE' OWNER fttuser;
-- CREATE DATABASE fttstoragedb WITH ENCODING 'UNICODE' OWNER fttUser;