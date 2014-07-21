-- Ponciano
INSERT INTO extractors (id, name, type_identifier, class_name) VALUES
  (5, 'Low Histogram', 'Histogram', 'PerceptualParameter'),
  (6, 'Traditional Histogram', 'Histogram', 'PerceptualParameter'),
  (7, 'High Histogram', 'Histogram', 'PerceptualParameter'),
  (8, 'Ponciano - Global', 'Global Descriptor', 'PerceptualParameter');

--Wavelet
INSERT INTO extractors (id, name, type_identifier, class_name, levels_wavelet) VALUES
  (100, 'Wavelet SubEspaco', 'Haar1', 'ReducedScaleWaveletExtractor', 4),
  (101, 'Wavelet SubEspaco', 'Haar1', 'ReducedScaleWaveletExtractor', 5),
  (102, 'Wavelet SubEspaco', 'Haar1', 'ReducedScaleWaveletExtractor', 6),
  (103, 'Wavelet SubEspaco', 'Daubechies2', 'ReducedScaleWaveletExtractor', 4),
  (104, 'Wavelet SubEspaco', 'Daubechies2', 'ReducedScaleWaveletExtractor', 5),
  (105, 'Wavelet SubEspaco', 'Daubechies2', 'ReducedScaleWaveletExtractor', 6),
  (106, 'Wavelet SubEspaco', 'Daubechies3', 'ReducedScaleWaveletExtractor', 4),
  (107, 'Wavelet SubEspaco', 'Daubechies3', 'ReducedScaleWaveletExtractor', 5),
  (108, 'Wavelet SubEspaco', 'Daubechies3', 'ReducedScaleWaveletExtractor', 6),
  (109, 'Wavelet SubEspaco', 'Daubechies8', 'ReducedScaleWaveletExtractor', 4),
  (110, 'Wavelet SubEspaco', 'Daubechies8', 'ReducedScaleWaveletExtractor', 5),
  (111, 'Wavelet SubEspaco', 'Daubechies8', 'ReducedScaleWaveletExtractor', 6),
  (112, 'Wavelet SubEspaco', 'Coiflet1', 'ReducedScaleWaveletExtractor', 4),
  (113, 'Wavelet SubEspaco', 'Coiflet1', 'ReducedScaleWaveletExtractor', 5),
  (114, 'Wavelet SubEspaco', 'Coiflet1', 'ReducedScaleWaveletExtractor', 6),
  (115, 'Wavelet SubEspaco', 'Coiflet2', 'ReducedScaleWaveletExtractor', 4),
  (116, 'Wavelet SubEspaco', 'Coiflet2', 'ReducedScaleWaveletExtractor', 5),
  (117, 'Wavelet SubEspaco', 'Coiflet2', 'ReducedScaleWaveletExtractor', 6),
  (118, 'Wavelet SubEspaco', 'Symlets2', 'ReducedScaleWaveletExtractor', 4),
  (119, 'Wavelet SubEspaco', 'Symlets2', 'ReducedScaleWaveletExtractor', 5),
  (120, 'Wavelet SubEspaco', 'Symlets2', 'ReducedScaleWaveletExtractor', 6),
  (121, 'Wavelet SubEspaco', 'Symlets20', 'ReducedScaleWaveletExtractor', 4),
  (122, 'Wavelet SubEspaco', 'Symlets20', 'ReducedScaleWaveletExtractor', 5),
  (123, 'Wavelet SubEspaco', 'Symlets20', 'ReducedScaleWaveletExtractor', 6);

-- JFeatureLib
INSERT INTO extractors (id, name, type_identifier, class_name) VALUES
  (200, 'Textural Features', 'Haralick', 'JFeatureLib'),
  (201, 'Textural Features', 'Tamura', 'JFeatureLib'),
  (202, 'Textural Features', 'CEDD', 'JFeatureLib'),
  (203, 'Textural Features', 'Tamura', 'JFeatureLib'),
  (204, 'Textural Features', 'Thumbnail', 'JFeatureLib'),
  (205, 'Textural Features', 'FCTH', 'JFeatureLib'),
  (206, 'Textural Features', 'JCD', 'JFeatureLib'),
  (207, 'Textural Features', 'LuminanceLayout', 'JFeatureLib');

-- Metric Evaluators
INSERT INTO distance_functions (id, name, class_name) VALUES
  (1, 'Euclidean', 'EuclideanDistance'),
  (2, 'Manhattan', 'ManhattanDistance'),
  (3, 'Linf', 'ChebyshevDistance'),
  (4, 'Canberra', 'CanberraDistance'),
  (5, 'ChiSquare', 'ChiSquareDistance'),
  (6, 'Jeffrey', 'JeffreyDivergence');

