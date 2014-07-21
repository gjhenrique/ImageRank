package br.uel.mdd.io.loading;

import br.uel.mdd.dao.ExtractionsDao;
import br.uel.mdd.dao.ExtractorsDao;
import br.uel.mdd.dao.ImagesDao;
import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.db.tables.pojos.Images;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static br.uel.mdd.db.tables.Extractors.EXTRACTORS;
import static br.uel.mdd.db.tables.Images.IMAGES;

public class PoncianoLoader {

    private ExtractionsDao extractionsDao;

    private ExtractorsDao extractorsDao;

    private ImagesDao imagesDao;

    private final Logger logger = LoggerFactory.getLogger(PoncianoLoader.class);

    @Inject
    public PoncianoLoader(ExtractionsDao extractionsDao, ExtractorsDao extractorsDao, ImagesDao imagesDao) {
        this.extractionsDao = extractionsDao;
        this.extractorsDao = extractorsDao;
        this.imagesDao = imagesDao;
    }

    public void insertExtractionsFromFile(File file) {
        String line;
        int abc = 1;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String[] lineValues = line.split("\t");

                Extractors extractors = extractorsDao.fetchOne(EXTRACTORS.NAME, lineValues[0]);
                Images images = imagesDao.fetchOne(IMAGES.FILE_NAME, lineValues[1]);


                String[] dataString = lineValues[2].split(" ");
                Double[] dataExtraction = new Double[dataString.length];

                for (int i = 0; i < dataString.length; i++) {
                    dataExtraction[i] = Double.parseDouble(dataString[i]);
                }

                Extractions extractions = new Extractions();
                extractions.setImageId(images.getId());
                extractions.setExtractorId(extractors.getId());
                extractions.setExtractionData(dataExtraction);
                extractions.setExtractionTime(0L);

                extractionsDao.insertNullPk(extractions);

                logger.info("Inserting new extraction with extractor_id {} and image_id {}", extractors.getId(), images.getId());
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
