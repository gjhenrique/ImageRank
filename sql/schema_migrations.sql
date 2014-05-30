/**
 Format:
 -- Author: <name>
 -- Date/Time: <date>
 */


-- Pedro
-- 30/05/14
ALTER TABLE dataset_classes
    ADD UNIQUE (class_id, dataset_id);
ALTER TABLE images
    DROP CONSTRAINT fk_images_datasets,
    DROP COLUMN dataset_id,
    ADD COLUMN dataset_class_id INT,
    ADD CONSTRAINT fk_images_dataset_classes FOREIGN KEY (dataset_class_id)
      REFERENCES dataset_classes(id) ON UPDATE CASCADE ON DELETE RESTRICT;
