CREATE TABLE class_image
(
    id INT PRIMARY KEY NOT NULL,
    name VARCHAR(255)
);
CREATE TABLE class_image_id_seq
(
    sequence_name VARCHAR NOT NULL,
    last_value BIGINT NOT NULL,
    start_value BIGINT NOT NULL,
    increment_by BIGINT NOT NULL,
    max_value BIGINT NOT NULL,
    min_value BIGINT NOT NULL,
    cache_value BIGINT NOT NULL,
    log_cnt BIGINT NOT NULL,
    is_cycled BOOL NOT NULL,
    is_called BOOL NOT NULL
);
CREATE TABLE dataset_classes
(
    id INT PRIMARY KEY NOT NULL,
    class_id INT,
    dataset_id INT
);
CREATE TABLE dataset_classes_id_seq
(
    sequence_name VARCHAR NOT NULL,
    last_value BIGINT NOT NULL,
    start_value BIGINT NOT NULL,
    increment_by BIGINT NOT NULL,
    max_value BIGINT NOT NULL,
    min_value BIGINT NOT NULL,
    cache_value BIGINT NOT NULL,
    log_cnt BIGINT NOT NULL,
    is_cycled BOOL NOT NULL,
    is_called BOOL NOT NULL
);
CREATE TABLE datasets
(
    id INT PRIMARY KEY NOT NULL,
    name VARCHAR(255)
);
CREATE TABLE datasets_id_seq
(
    sequence_name VARCHAR NOT NULL,
    last_value BIGINT NOT NULL,
    start_value BIGINT NOT NULL,
    increment_by BIGINT NOT NULL,
    max_value BIGINT NOT NULL,
    min_value BIGINT NOT NULL,
    cache_value BIGINT NOT NULL,
    log_cnt BIGINT NOT NULL,
    is_cycled BOOL NOT NULL,
    is_called BOOL NOT NULL
);
CREATE TABLE extractions
(
    id INT PRIMARY KEY NOT NULL,
    image_id INT NOT NULL,
    extractor_id INT,
    extraction_data _FLOAT8,
    extraction_time BIGINT
);
CREATE TABLE extractions_id_seq
(
    sequence_name VARCHAR NOT NULL,
    last_value BIGINT NOT NULL,
    start_value BIGINT NOT NULL,
    increment_by BIGINT NOT NULL,
    max_value BIGINT NOT NULL,
    min_value BIGINT NOT NULL,
    cache_value BIGINT NOT NULL,
    log_cnt BIGINT NOT NULL,
    is_cycled BOOL NOT NULL,
    is_called BOOL NOT NULL
);
CREATE TABLE extractors
(
    id INT PRIMARY KEY NOT NULL,
    name VARCHAR(50),
    filter_identifier VARCHAR(50),
    class_name VARCHAR(50),
    levels_wavelet INT
);
CREATE TABLE extractors_id_seq
(
    sequence_name VARCHAR NOT NULL,
    last_value BIGINT NOT NULL,
    start_value BIGINT NOT NULL,
    increment_by BIGINT NOT NULL,
    max_value BIGINT NOT NULL,
    min_value BIGINT NOT NULL,
    cache_value BIGINT NOT NULL,
    log_cnt BIGINT NOT NULL,
    is_cycled BOOL NOT NULL,
    is_called BOOL NOT NULL
);
CREATE TABLE images
(
    id INT PRIMARY KEY NOT NULL,
    file_name VARCHAR(255),
    image BYTEA,
    dataset_class_id INT,
    pixel_matrix _INT4
);
CREATE TABLE images_id_seq
(
    sequence_name VARCHAR NOT NULL,
    last_value BIGINT NOT NULL,
    start_value BIGINT NOT NULL,
    increment_by BIGINT NOT NULL,
    max_value BIGINT NOT NULL,
    min_value BIGINT NOT NULL,
    cache_value BIGINT NOT NULL,
    log_cnt BIGINT NOT NULL,
    is_cycled BOOL NOT NULL,
    is_called BOOL NOT NULL
);
CREATE TABLE metric_evaluator
(
    id INT PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    class_name VARCHAR(255) NOT NULL
);
CREATE TABLE metric_evaluator_id_seq
(
    sequence_name VARCHAR NOT NULL,
    last_value BIGINT NOT NULL,
    start_value BIGINT NOT NULL,
    increment_by BIGINT NOT NULL,
    max_value BIGINT NOT NULL,
    min_value BIGINT NOT NULL,
    cache_value BIGINT NOT NULL,
    log_cnt BIGINT NOT NULL,
    is_cycled BOOL NOT NULL,
    is_called BOOL NOT NULL
);
CREATE TABLE queries
(
    id INT PRIMARY KEY NOT NULL,
    image_id INT,
    extractor_id INT,
    extraction_id INT NOT NULL,
    metric_evaluator_id INT NOT NULL,
    query_duration BIGINT NOT NULL
);
CREATE TABLE queries_id_seq
(
    sequence_name VARCHAR NOT NULL,
    last_value BIGINT NOT NULL,
    start_value BIGINT NOT NULL,
    increment_by BIGINT NOT NULL,
    max_value BIGINT NOT NULL,
    min_value BIGINT NOT NULL,
    cache_value BIGINT NOT NULL,
    log_cnt BIGINT NOT NULL,
    is_cycled BOOL NOT NULL,
    is_called BOOL NOT NULL
);
CREATE TABLE query_results
(
    id INT PRIMARY KEY NOT NULL,
    image_id INT,
    query_id INT,
    return_order INT,
    distance REAL NOT NULL
);
CREATE TABLE query_results_id_seq
(
    sequence_name VARCHAR NOT NULL,
    last_value BIGINT NOT NULL,
    start_value BIGINT NOT NULL,
    increment_by BIGINT NOT NULL,
    max_value BIGINT NOT NULL,
    min_value BIGINT NOT NULL,
    cache_value BIGINT NOT NULL,
    log_cnt BIGINT NOT NULL,
    is_cycled BOOL NOT NULL,
    is_called BOOL NOT NULL
);
CREATE UNIQUE INDEX class_image_name_key ON class_image ( name );
ALTER TABLE dataset_classes ADD FOREIGN KEY ( class_id ) REFERENCES class_image ( id ) ON UPDATE CASCADE;
ALTER TABLE dataset_classes ADD FOREIGN KEY ( dataset_id ) REFERENCES datasets ( id ) ON UPDATE CASCADE;
CREATE UNIQUE INDEX dataset_classes_class_id_dataset_id_key ON dataset_classes ( class_id, dataset_id );
CREATE UNIQUE INDEX datasets_name_key ON datasets ( name );
ALTER TABLE extractions ADD FOREIGN KEY ( extractor_id ) REFERENCES extractors ( id ) ON UPDATE CASCADE;
ALTER TABLE extractions ADD FOREIGN KEY ( image_id ) REFERENCES images ( id ) ON UPDATE CASCADE;
CREATE UNIQUE INDEX extractions_image_id_extractor_id_key ON extractions ( image_id, extractor_id );
ALTER TABLE images ADD FOREIGN KEY ( dataset_class_id ) REFERENCES dataset_classes ( id ) ON UPDATE CASCADE;
CREATE UNIQUE INDEX images_file_name_dataset_class_id_key ON images ( file_name, dataset_class_id );
CREATE UNIQUE INDEX unique_name ON metric_evaluator ( name );
ALTER TABLE queries ADD FOREIGN KEY ( extraction_id ) REFERENCES extractions ( id );
ALTER TABLE queries ADD FOREIGN KEY ( image_id ) REFERENCES images ( id ) ON UPDATE CASCADE;
ALTER TABLE queries ADD FOREIGN KEY ( metric_evaluator_id ) REFERENCES metric_evaluator ( id );
ALTER TABLE query_results ADD FOREIGN KEY ( image_id ) REFERENCES images ( id ) ON UPDATE CASCADE;
ALTER TABLE query_results ADD FOREIGN KEY ( query_id ) REFERENCES queries ( id ) ON UPDATE CASCADE;
