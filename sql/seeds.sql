--Wavelet
INSERT INTO extractors (id, name, type_identifier, class_name, levels_wavelet) VALUES
  (1, 'Wavelet SubEspaco', 'Haar1', 'ReducedScaleWaveletExtractor', 4),
  (2, 'Wavelet SubEspaco', 'Haar1', 'ReducedScaleWaveletExtractor', 5),
  (3, 'Wavelet SubEspaco', 'Haar1', 'ReducedScaleWaveletExtractor', 6),
  (4, 'Wavelet SubEspaco', 'Daubechies2', 'ReducedScaleWaveletExtractor', 4),
  (5, 'Wavelet SubEspaco', 'Daubechies2', 'ReducedScaleWaveletExtractor', 5),
  (6, 'Wavelet SubEspaco', 'Daubechies2', 'ReducedScaleWaveletExtractor', 6),
  (7, 'Wavelet SubEspaco', 'Daubechies3', 'ReducedScaleWaveletExtractor', 4),
  (8, 'Wavelet SubEspaco', 'Daubechies3', 'ReducedScaleWaveletExtractor', 5),
  (9, 'Wavelet SubEspaco', 'Daubechies3', 'ReducedScaleWaveletExtractor', 6),
  (10, 'Wavelet SubEspaco', 'Daubechies8', 'ReducedScaleWaveletExtractor', 4),
  (11, 'Wavelet SubEspaco', 'Daubechies8', 'ReducedScaleWaveletExtractor', 5),
  (12, 'Wavelet SubEspaco', 'Daubechies8', 'ReducedScaleWaveletExtractor', 6),
  (13, 'Wavelet SubEspaco', 'Coiflet1', 'ReducedScaleWaveletExtractor', 4),
  (14, 'Wavelet SubEspaco', 'Coiflet1', 'ReducedScaleWaveletExtractor', 5),
  (15, 'Wavelet SubEspaco', 'Coiflet1', 'ReducedScaleWaveletExtractor', 6),
  (16, 'Wavelet SubEspaco', 'Coiflet2', 'ReducedScaleWaveletExtractor', 4),
  (17, 'Wavelet SubEspaco', 'Coiflet2', 'ReducedScaleWaveletExtractor', 5),
  (18, 'Wavelet SubEspaco', 'Coiflet2', 'ReducedScaleWaveletExtractor', 6),
  (19, 'Wavelet SubEspaco', 'Symlets2', 'ReducedScaleWaveletExtractor', 4),
  (20, 'Wavelet SubEspaco', 'Symlets2', 'ReducedScaleWaveletExtractor', 5),
  (21, 'Wavelet SubEspaco', 'Symlets2', 'ReducedScaleWaveletExtractor', 6),
  (22, 'Wavelet SubEspaco', 'Symlets20', 'ReducedScaleWaveletExtractor', 4),
  (23, 'Wavelet SubEspaco', 'Symlets20', 'ReducedScaleWaveletExtractor', 5),
  (24, 'Wavelet SubEspaco', 'Symlets20', 'ReducedScaleWaveletExtractor', 6);

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
