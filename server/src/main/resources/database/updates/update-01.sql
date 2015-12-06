BEGIN;

SET SESSION AUTHORIZATION 'fttuser';

ALTER TABLE "connection_station_relation" ALTER COLUMN arrival_time TYPE TIME;
ALTER TABLE "connection_station_relation" ALTER COLUMN departure_time TYPE TIME;

COMMIT;