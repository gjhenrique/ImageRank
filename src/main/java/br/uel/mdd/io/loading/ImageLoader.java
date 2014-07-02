package br.uel.mdd.io.loading;

import br.uel.mdd.dao.ClassImageDao;
import br.uel.mdd.dao.DatasetClassesDao;
import br.uel.mdd.dao.DatasetsDao;
import br.uel.mdd.dao.ImagesDao;
import br.uel.mdd.db.tables.pojos.ClassImage;
import br.uel.mdd.db.tables.pojos.DatasetClasses;
import br.uel.mdd.db.tables.pojos.Datasets;
import br.uel.mdd.db.tables.pojos.Images;
import br.uel.mdd.utils.Mime;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Pedro Tanaka
 *         Load images from specific folder
 *         <p/>
 *         Created by pedro on 29/05/14.
 */
public class ImageLoader {

    private DatasetsDao datasetsDao;

    private ImagesDao imagesDao;

    private ClassImageDao classImageDao;

    private DatasetClassesDao datasetClassesDao;

    private static final Logger logger = LoggerFactory.getLogger(ImageLoader.class);

    @Inject
    public ImageLoader(DatasetsDao datasetsDao, ImagesDao imagesDao, ClassImageDao classImageDao, DatasetClassesDao datasetClassesDao) {
        this.datasetsDao = datasetsDao;
        this.datasetClassesDao = datasetClassesDao;
        this.imagesDao = imagesDao;
        this.classImageDao = classImageDao;
        this.datasetClassesDao = datasetClassesDao;
    }

    public void loadFilesFromFolder(String path) {
        this.loadFilesFromFolder(new File(path));
    }

    public void loadFilesFromFolder(File file) {
        logger.info("Extracting image from the file {}", file.getName());

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File newFile : files) {
                loadFilesFromFolder(newFile);
            }
        } else {
            if (isImage(file)) {
                String datasetName = file.getParentFile().getName();
                persistImage(file, datasetName);
            }
        }
    }

    private boolean isImage(File file) {
        String mimeType = Mime.getMimeType(file);

        return mimeType.startsWith("image") || mimeType.endsWith("dicom");
    }

    public void persistImage(File file, String datasetName) {
        Datasets datasets = createOrFetchDataset(datasetName);

        ClassImage classImage = createOrFetchClassImage(file);

        DatasetClasses datasetClasses = createOrFetchDatasetClasses(classImage, datasets);

        createImage(file, datasetClasses);
    }

    private Datasets createOrFetchDataset(String datasetName) {

        Datasets datasets = datasetsDao.fetchOne(br.uel.mdd.db.tables.Datasets.DATASETS.NAME, datasetName);

        if (datasets == null) {
            datasets = new Datasets();
            datasets.setName(datasetName);
            datasetsDao.insertNullPk(datasets);
            logger.debug("Persisting dataset {} in the database", datasets.getName());
        }

        return datasets;
    }

    private ClassImage createOrFetchClassImage(File file) {

        String imageClass = extractClassFromFileName(file);

        ClassImage classImage = classImageDao.fetchOne(br.uel.mdd.db.tables.ClassImage.CLASS_IMAGE.NAME, imageClass);

        if (classImage == null) {
            classImage = new ClassImage();
            classImage.setName(imageClass);
            classImageDao.insertNullPk(classImage);
            logger.debug("Persisting ClassImage {} in the database", classImage.getName());
        }

        return classImage;
    }

    private DatasetClasses createOrFetchDatasetClasses(ClassImage classImage, Datasets datasets) {

        DatasetClasses datasetClasses = datasetClassesDao.fetchByClassAndDataset(classImage, datasets);

        if (datasetClasses == null) {
            datasetClasses = new DatasetClasses();
            datasetClasses.setClassId(classImage.getId());
            datasetClasses.setDatasetId(datasets.getId());
            datasetClassesDao.insertNullPk(datasetClasses);
            logger.debug("Persisting DatasetClasses {}", datasetClasses.getId());
        }

        return datasetClasses;
    }

    private void createImage(File file, DatasetClasses datasetClasses) {
        Images image = imagesDao.fetchByFileNameAndDatasetClassId(file.getName(), datasetClasses);
        if (image == null) {
            image = buildImage(file);
            image.setDatasetClassId(datasetClasses.getId());
            imagesDao.insertNullPk(image);
            logger.debug("Persisting image {}", image.getFileName());
        }
        else {
           logger.info("Image {} already populated in the database", image.getFileName());
        }
    }

    private Images buildImage(File file) {
        Images image = new Images();

//        Extracting the bytes from the image
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            byte[] fileBytes = ByteStreams.toByteArray(bis);
            image.setImage(fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String mimeType = Mime.getMimeType(file);
        image.setMimeType(mimeType);

        image.setFileName(file.getName());

        return image;
    }

    private String extractClassFromFileName(File file) {

        String fileName = file.getName();

//            Get the first occurrence of a digit in the file name
        String patternStr = "[0-9]";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(fileName);

        String className = null;

        if (matcher.find()) {
            int firstOccurrence = matcher.start();
            className = fileName.substring(0, firstOccurrence);
        } else {
            throw new IllegalArgumentException("File of name " + fileName + " does not match the format expected");
        }

        return className;
    }

}
