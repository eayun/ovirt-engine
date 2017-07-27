DELETE FROM audit_log;
ALTER TABLE audit_log ALTER COLUMN origin Set DEFAULT 'Fusionstack' ;
DROP INDEX audit_log_origin_custom_event_id_idx;
CREATE UNIQUE INDEX audit_log_origin_custom_event_id_idx ON audit_log USING BTREE (origin, custom_event_id) WHERE ((origin)::text !~~* 'fusionstack'::text);

