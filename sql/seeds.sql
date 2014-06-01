-- Extractors
INSERT INTO extractors (id, name, filter_identifier, class_name, levels_wavelet)
VALUES (1, 'Wavelet SubEspaco', 'Haar1', 'ReducedScaleWaveletExtractor', 4);

INSERT INTO extractors (id, name, filter_identifier, class_name, levels_wavelet)
VALUES (2, 'Wavelet SubEspaco', 'Daubechies2', 'ReducedScaleWaveletExtractor', 4);

INSERT INTO extractors (id, name, filter_identifier, class_name, levels_wavelet)
VALUES (3, 'Wavelet SubEspaco', 'Symlets2', 'ReducedScaleWaveletExtractor', 4);


-- Metric Evaluators
INSERT INTO metric_evaluator (name, class_name) VALUES ('Euclidean', 'EuclideanDistance');
INSERT INTO metric_evaluator (name, class_name) VALUES ('Manhattan', 'ManhattanDistance');