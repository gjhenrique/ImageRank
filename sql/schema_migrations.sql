/**
 Format:
 -- Author: <name>
 -- Date/Time: <date>
 */

-- Author: Pedro Tanaka
-- Date/Time: 29/05/2014
ALTER TABLE classes
    ADD COLUMN tipo_imagem VARCHAR(255) NOT NULL DEFAULT 'none';


-- Author: Pedro Tanaka
-- Date/Time: 29/05/2014
ALTER TABLE experimentos
ALTER COLUMN nivel DROP NOT NULL,
ALTER COLUMN coeficiente DROP NOT NULL;