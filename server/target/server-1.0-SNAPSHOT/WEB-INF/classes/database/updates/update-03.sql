BEGIN;

SET SESSION AUTHORIZATION 'fttuser';

ALTER TABLE "tag" ADD COLUMN active BOOLEAN NOT NULL DEFAULT TRUE;

COMMIT;