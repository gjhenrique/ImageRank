/**
 Format:
 -- Author: <name>
 -- Date/Time: <date>
 */

-- Author: Guilherme
-- Date/Time: 30/05/2014 15:59
ALTER TABLE extractors DROP COLUMN family;
ALTER TABLE extractors DROP COLUMN support;
ALTER TABLE extractors ADD COLUMN filter_identifier VARCHAR(50);
ALTER TABLE extractors ADD COLUMN class_name VARCHAR(50);
ALTER TABLE extractors ADD COLUMN levels_wavelet INT;

-- Author: Guilherme
-- Date/Time: 30/05/2014 15:59
INSERT INTO extractors (id, name, filter_identifier, class_name)
  VALUES (1, 'Wavelet SubEspaco', 'Haar1', 'ReducedScaleWaveletExtractor');

INSERT INTO extractors (id, name, filter_identifier, class_name)
  VALUES (2, 'Wavelet SubEspaco', 'Daubechies2', 'ReducedScaleWaveletExtractor');

INSERT INTO extractors (id, name, filter_identifier, class_name)
  VALUES (3, 'Wavelet SubEspaco', 'Symlets2', 'ReducedScaleWaveletExtractor')
