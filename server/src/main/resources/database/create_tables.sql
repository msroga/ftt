BEGIN;

SET SESSION AUTHORIZATION 'fttuser';

CREATE TABLE "user"(
  id BIGSERIAL PRIMARY KEY,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  login VARCHAR(32) NOT NULL,
  email VARCHAR(128) NOT NULL,
  first_name VARCHAR(32) NOT NULL,
  last_name VARCHAR(32) NOT NULL,
  "type" int4 NOT NULL,
  UNIQUE (login),
  UNIQUE (email)
);
CREATE INDEX user_active_idx ON "user" USING btree (active);
CREATE INDEX user_first_name_idx ON "user" USING btree (first_name);
CREATE INDEX user_last_name_idx ON "user" USING btree (last_name);
CREATE INDEX user_email_idx ON "user" USING btree (email);
CREATE INDEX user_login_idx ON "user" USING btree (login);

CREATE TABLE "password"
(
  id BIGSERIAL PRIMARY KEY,
  user_id int8 NOT NULL,
  password VARCHAR(256) NOT NULL,
  UNIQUE (user_id),
  CONSTRAINT user_fk FOREIGN KEY (user_id)
    REFERENCES "user" (id) ON DELETE CASCADE ON UPDATE CASCADE DEFERRABLE INITIALLY IMMEDIATE
);
CREATE INDEX password_user_id_idx ON "password" USING btree (user_id);

CREATE TABLE "station"(
  id BIGSERIAL PRIMARY KEY,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  name VARCHAR(128) NOT NULL,
  gps_lat float8,
  gps_lon float8,
  UNIQUE (name)
);
CREATE INDEX station_active_idx ON "station" USING btree (active);
CREATE INDEX station_name_idx ON "station" USING btree (name);

CREATE TABLE "connection"(
  id BIGSERIAL PRIMARY KEY,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  number VARCHAR(16) NOT NULL,
  comment TEXT,
  "type" VARCHAR (32) NOT NULL,
  UNIQUE (number)
);
CREATE INDEX connection_active_idx ON "connection" USING btree (active);
CREATE INDEX connection_number_idx ON "connection" USING btree (number);

CREATE TABLE "connection_station_relation"(
  id BIGSERIAL PRIMARY KEY,
  connection_id int8 NOT NULL,
  station_id int8 NOT NULL,
  index int4 NOT NULL,
  arival_time time,
  departure_time time NOT NULL,
  CONSTRAINT connection_id_fk FOREIGN KEY (connection_id)
    REFERENCES "connection" (id) ON DELETE CASCADE,
  CONSTRAINT station_id_fk FOREIGN KEY (station_id)
    REFERENCES "station" (id) ON DELETE CASCADE,
  UNIQUE (connection_id, station_id)
);
CREATE INDEX connection_station_relation_connection_id_idx ON "connection_station_relation" USING btree (connection_id);
CREATE INDEX connection_station_relation_station_id_idx ON "connection_station_relation" USING btree (station_id);

CREATE TABLE "tag"(
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR (128) NOT NULL,
  description text,
  UNIQUE (name)
);
CREATE INDEX tag_name_idx ON "tag" USING btree (name);

CREATE TABLE "connection_tag_relation"(
  id BIGSERIAL PRIMARY KEY,
  connection_id int8 NOT NULL,
  tag_id int8 NOT NULL,
  CONSTRAINT connection_id_fk FOREIGN KEY (connection_id)
    REFERENCES "connection" (id) ON DELETE CASCADE,
  CONSTRAINT tag_id_fk FOREIGN KEY (tag_id)
    REFERENCES "tag" (id) ON DELETE CASCADE,
  UNIQUE (connection_id, tag_id)
);
CREATE INDEX connection_tag_relation_connection_id_idx ON "connection_tag_relation" USING btree (connection_id);
CREATE INDEX connection_tag_relation_tag_id_idx ON "connection_tag_relation" USING btree (tag_id);

CREATE TABLE "schedulde_exception"(
  id BIGSERIAL PRIMARY KEY,
  connection_id int8 NOT NULL,
  exception_date date NOT NULL,
  CONSTRAINT connection_id_fk FOREIGN KEY (connection_id)
    REFERENCES "connection" (id) ON DELETE CASCADE,
  UNIQUE (connection_id, exception_date)
);

COMMIT;