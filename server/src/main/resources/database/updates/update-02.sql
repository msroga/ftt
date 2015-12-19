BEGIN;

SET SESSION AUTHORIZATION 'fttuser';

ALTER TABLE "connection" DROP CONSTRAINT IF EXISTS connection_identifier_key;

COMMIT;