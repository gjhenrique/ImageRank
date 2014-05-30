CREATE TABLE class_image (
 id SERIAL PRIMARY KEY,
 name VARCHAR(255)
);

CREATE TABLE datasets (
 id SERIAL PRIMARY KEY ,
 name VARCHAR (255)
);

CREATE TABLE extractors (
 id SERIAL PRIMARY KEY ,
 name VARCHAR(50),
 family varchar(50),
 support INT
);

CREATE TABLE images (
 id SERIAL PRIMARY KEY ,
 file_name varchar(255),
 image BYTEA,
 dataset_id INT
);

CREATE TABLE queries (
 id SERIAL PRIMARY KEY ,
 image_id INT,
 extractor_id INT
);

CREATE TABLE query_results (
 id SERIAL PRIMARY KEY,
 image_id INT,
 query_id INT,
 return_order INT
);

CREATE TABLE dataset_classes (
 id SERIAL PRIMARY KEY ,
 class_id INT,
 dataset_id INT
);

CREATE TABLE extractions (
 id SERIAL PRIMARY KEY ,
 image_id INT,
 extraction_data BYTEA,
 extractor_id INT
);

-- Foreign Keys

ALTER TABLE images
  ADD CONSTRAINT FK_images_datasets FOREIGN KEY (dataset_id)
  REFERENCES datasets (id)
  ON UPDATE CASCADE
  ON DELETE RESTRICT;


ALTER TABLE queries
  ADD CONSTRAINT FK_queries_images FOREIGN KEY (image_id)
  REFERENCES images (id)
  ON UPDATE CASCADE
  ON DELETE RESTRICT;

ALTER TABLE queries
  ADD CONSTRAINT FK_queries_extractors FOREIGN KEY (extractor_id)
  REFERENCES extractors (id)
  ON UPDATE CASCADE
  ON DELETE RESTRICT;


ALTER TABLE query_results
  ADD CONSTRAINT FK_query_results_image FOREIGN KEY (image_id)
  REFERENCES images (id)
  ON UPDATE CASCADE
  ON DELETE RESTRICT;


ALTER TABLE query_results
  ADD CONSTRAINT FK_query_results_queries
  FOREIGN KEY (query_id)
  REFERENCES queries (id)
  ON UPDATE CASCADE
  ON DELETE RESTRICT;

ALTER TABLE dataset_classes
  ADD CONSTRAINT FK_dataset_classes_classes FOREIGN KEY (class_id)
  REFERENCES class_image (id)
  ON UPDATE CASCADE
  ON DELETE RESTRICT;

ALTER TABLE dataset_classes
  ADD CONSTRAINT FK_dataset_classes_datasets FOREIGN KEY (dataset_id)
  REFERENCES datasets (id)
  ON UPDATE CASCADE
  ON DELETE RESTRICT;

ALTER TABLE extractions
  ADD CONSTRAINT FK_extractions_images FOREIGN KEY (image_id)
  REFERENCES images (id)
  ON UPDATE CASCADE
  ON DELETE RESTRICT;

ALTER TABLE extractions
  ADD CONSTRAINT FK_extractions_1 FOREIGN KEY (extractor_id)
  REFERENCES extractors (id)
  ON UPDATE CASCADE
  ON DELETE RESTRICT;

