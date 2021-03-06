
-- PEdro
-- 2/06/2014 20:05
ALTER TABLE metric_evaluator
    RENAME TO distance_functions;

ALTER TABLE queries
    RENAME COLUMN metric_evaluator_id TO distance_function_id;

ALTER TABLE queries
ALTER COLUMN query_duration DROP NOT NULL ;

ALTER TABLE query_results
    ADD UNIQUE (query_id, image_id);

ALTER TABLE extractors
    RENAME COLUMN filter_identifier TO type_identifier;

ALTER TABLE queries
    ADD COLUMN k INTEGER;

ALTER TABLE queries
    ADD UNIQUE (extraction_id, distance_function_id, k);

ALTER TABLE query_results
    DROP COLUMN image_id;

ALTER TABLE query_results
  ADD COLUMN extraction_id INTEGER;

alter table query_results
    add foreign key(extraction_id) references extractions(id);