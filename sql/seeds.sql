-- Extractors
INSERT INTO extractors (id, name, type_identifier, class_name, levels_wavelet)
VALUES (1, 'Wavelet SubEspaco', 'Haar1', 'ReducedScaleWaveletExtractor', 6);

INSERT INTO extractors (id, name, type_identifier, class_name, levels_wavelet)
VALUES (2, 'Wavelet SubEspaco', 'Daubechies2', 'ReducedScaleWaveletExtractor', 6);

INSERT INTO extractors (id, name, type_identifier, class_name, levels_wavelet)
VALUES (3, 'Wavelet SubEspaco', 'Symlets2', 'ReducedScaleWaveletExtractor', 6);

INSERT INTO extractors (id, name, type_identifier, class_name)
VALUES (4, 'Textural Features', 'Haralick', 'JFeatureLib');

-- Metric Evaluators
INSERT INTO distance_functions (id, name, class_name) VALUES (1, 'Euclidean', 'EuclideanDistance');
INSERT INTO distance_functions (id, name, class_name) VALUES (2, 'Manhattan', 'ManhattanDistance');
