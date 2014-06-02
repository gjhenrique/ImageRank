
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