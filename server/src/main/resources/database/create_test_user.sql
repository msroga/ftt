BEGIN;
-- login: test, pass: test
INSERT INTO "user"(login, email, first_name, last_name, "type")
VALUES ('test', 'jan.kowalski@mail.pl', 'Jan', 'Kowalski', 1);

INSERT INTO "password"(user_id, password)
VALUES ((select id from "user" where login='test'), '$2a$10$ggXeoAdz59fTtsnKDg/YsuNar2MBcidO/.RvP/VFi42s3pMY0woEa');

COMMIT;
