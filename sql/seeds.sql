--Wavelet
INSERT INTO extractors (id, name, type_identifier, class_name, levels_wavelet) VALUES
  (0, 'Wavelet SubEspaco', 'Haar1', 'ReducedScaleWaveletExtractor', 4),
  (1, 'Wavelet SubEspaco', 'Haar1', 'ReducedScaleWaveletExtractor', 5),
  (2, 'Wavelet SubEspaco', 'Haar1', 'ReducedScaleWaveletExtractor', 6),
  (3, 'Wavelet SubEspaco', 'Daubechies2', 'ReducedScaleWaveletExtractor', 4),
  (4, 'Wavelet SubEspaco', 'Daubechies2', 'ReducedScaleWaveletExtractor', 5),
  (5, 'Wavelet SubEspaco', 'Daubechies2', 'ReducedScaleWaveletExtractor', 6),
  (6, 'Wavelet SubEspaco', 'Daubechies3', 'ReducedScaleWaveletExtractor', 4),
  (7, 'Wavelet SubEspaco', 'Daubechies3', 'ReducedScaleWaveletExtractor', 5),
  (8, 'Wavelet SubEspaco', 'Daubechies3', 'ReducedScaleWaveletExtractor', 6),
  (9, 'Wavelet SubEspaco', 'Daubechies8', 'ReducedScaleWaveletExtractor', 4),
  (10, 'Wavelet SubEspaco', 'Daubechies8', 'ReducedScaleWaveletExtractor', 5),
  (11, 'Wavelet SubEspaco', 'Daubechies8', 'ReducedScaleWaveletExtractor', 6),
  (12, 'Wavelet SubEspaco', 'Coiflet1', 'ReducedScaleWaveletExtractor', 4),
  (13, 'Wavelet SubEspaco', 'Coiflet1', 'ReducedScaleWaveletExtractor', 5),
  (14, 'Wavelet SubEspaco', 'Coiflet1', 'ReducedScaleWaveletExtractor', 6),
  (15, 'Wavelet SubEspaco', 'Coiflet2', 'ReducedScaleWaveletExtractor', 4),
  (16, 'Wavelet SubEspaco', 'Coiflet2', 'ReducedScaleWaveletExtractor', 5),
  (17, 'Wavelet SubEspaco', 'Coiflet2', 'ReducedScaleWaveletExtractor', 6),
  (18, 'Wavelet SubEspaco', 'Symlets2', 'ReducedScaleWaveletExtractor', 4),
  (19, 'Wavelet SubEspaco', 'Symlets2', 'ReducedScaleWaveletExtractor', 5),
  (20, 'Wavelet SubEspaco', 'Symlets2', 'ReducedScaleWaveletExtractor', 6),
  (21, 'Wavelet SubEspaco', 'Symlets20', 'ReducedScaleWaveletExtractor', 4),
  (22, 'Wavelet SubEspaco', 'Symlets20', 'ReducedScaleWaveletExtractor', 5),
  (23, 'Wavelet SubEspaco', 'Symlets20', 'ReducedScaleWaveletExtractor', 6);

-- JFeatureLib
INSERT INTO extractors (id, name, type_identifier, class_name) VALUES
  (100, 'Textural Features', 'Haralick', 'JFeatureLib'),
  (101, 'Textural Features', 'Tamura', 'JFeatureLib'),
  (102, 'Textural Features', 'CEDD', 'JFeatureLib'),
  (103, 'Textural Features', 'Tamura', 'JFeatureLib'),
  (104, 'Textural Features', 'Thumbnail', 'JFeatureLib'),
  (105, 'Textural Features', 'FCTH', 'JFeatureLib'),
  (106, 'Textural Features', 'JCD', 'JFeatureLib'),
  (107, 'Textural Features', 'LuminanceLayout', 'JFeatureLib');

-- Metric Evaluators
INSERT INTO distance_functions (id, name, class_name) VALUES
  (1, 'Euclidean', 'EuclideanDistance'),
  (2, 'Manhattan', 'ManhattanDistance'),
  (3, 'Linf', 'ChebyshevDistance'),
  (4, 'Canberra', 'CanberraDistance'),
  (5, 'ChiSquare', 'ChiSquareDistance'),
  (6, 'Jeffrey', 'JeffreyDivergence');
