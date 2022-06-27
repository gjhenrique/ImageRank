CREATE TABLE class_image
(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255)
);

CREATE TABLE dataset_classes
(
    id SERIAL PRIMARY KEY NOT NULL,
    class_id INT,
    dataset_id INT
);

CREATE TABLE datasets
(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255)
);

CREATE TABLE extractions
(
    id SERIAL PRIMARY KEY NOT NULL,
    image_id INT NOT NULL,
    extractor_id INT,
    extraction_data _FLOAT8,
    extraction_time BIGINT
);

CREATE TABLE extractors
(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50),
    type_identifier VARCHAR(50),
    class_name VARCHAR(50),
    levels_wavelet INT
);

CREATE TABLE images
(
    id SERIAL PRIMARY KEY NOT NULL,
    file_name VARCHAR(255),
    image BYTEA,
    dataset_class_id INT,
    mime_type VARCHAR(60)
);

CREATE TABLE distance_functions
(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    class_name VARCHAR(255) NOT NULL
);

CREATE TABLE queries
(
    id SERIAL PRIMARY KEY NOT NULL,
    image_id INT,
    extraction_id INT NOT NULL,
    distance_function_id INT NOT NULL,
    query_duration BIGINT,
    k INT
);

CREATE TABLE query_results
(
    id SERIAL PRIMARY KEY NOT NULL,
    query_id INT,
    return_order INT,
    distance REAL NOT NULL,
    extraction_id INT
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
CREATE UNIQUE INDEX unique_name ON distance_functions ( name );
ALTER TABLE queries ADD FOREIGN KEY ( extraction_id ) REFERENCES extractions ( id );
ALTER TABLE queries ADD FOREIGN KEY ( image_id ) REFERENCES images ( id ) ON UPDATE CASCADE;
ALTER TABLE queries ADD FOREIGN KEY ( distance_function_id ) REFERENCES distance_functions ( id );
CREATE UNIQUE INDEX unique_queries_extraction_id_distance_function_id_k ON queries (extraction_id, distance_function_id, k);
ALTER TABLE query_results ADD FOREIGN KEY ( query_id ) REFERENCES queries ( id ) ON UPDATE CASCADE;
ALTER TABLE query_results ADD FOREIGN KEY ( extraction_id ) REFERENCES extractions ( id );
