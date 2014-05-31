package br.uel.mdd.io.loading;

import br.uel.mdd.ClassExtractor;
import br.uel.mdd.NameClassExtractor;
import br.uel.mdd.dao.ClassImageDao;
import br.uel.mdd.dao.DatasetClassesDao;
import br.uel.mdd.dao.DatasetsDao;
import br.uel.mdd.dao.ImagesDao;
import br.uel.mdd.db.tables.pojos.ClassImage;
import br.uel.mdd.db.tables.pojos.DatasetClasses;
import br.uel.mdd.db.tables.pojos.Datasets;
import br.uel.mdd.db.tables.pojos.Images;
import br.uel.mdd.module.AppModule;
import com.google.common.io.ByteStreams;
import com.google.inject.Guice;
import com.google.inject.Injector;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * @author Pedro Tanaka
 * Load images from specific folder
 * <p/>
 * Created by pedro on 29/05/14.
 */
public class LungImageLoader {

    @Inject
    private DatasetsDao datasetsDao;

    @Inject
    private ImagesDao imagesDao;

    @Inject
    private ClassImageDao classDao;

    private ClassExtractor classExtractor = new NameClassExtractor();

    @Inject
    private DatasetClassesDao datasetClassesDao;

    public static void main(String args[]) {

        Injector injector = Guice.createInjector(new AppModule());
        LungImageLoader lil = injector.getInstance(LungImageLoader.class);

        try {
            lil.loadFilesFromFolder("/home/guilherme/Documents/imgsPulmao/Pulmao");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadFilesFromFolder(String path) throws IOException {

        Datasets datasets =  new Datasets();
        datasets.setName("LungCT");
        datasetsDao.insertNullPk(datasets);

        for (File file : this.getFilesFromFolder(path)) {
            Images image = new Images();

            FileInputStream fis = new FileInputStream(file);
            byte[] fileBytes = ByteStreams.toByteArray(fis);

            image.setImage(fileBytes);
            image.setFileName(file.getName());

            String imageClass = classExtractor.extractClass(file);
            ClassImage iclass = classDao.fetchByDatasetAndClassName(datasets, imageClass);
            DatasetClasses dc = new DatasetClasses();
            if(iclass == null){ // There's no such class, create it then
                iclass = new ClassImage(null, imageClass);
                classDao.insertNullPk(iclass);
                System.out.println(imageClass);
                dc = datasetClassesDao.insert(datasets, iclass);
            }else{
                dc = datasetClassesDao.fetchByClassAndDataset(iclass, datasets);
            }
            image.setDatasetClassId(dc.getId());
            imagesDao.insertNullPk(image);
        }
    }

    public List<File> getFilesFromFolder(String path) {
        File folder = new File(path);
        List<File> fileList = null;
        if (folder.isDirectory()) {
            fileList = Arrays.asList(folder.listFiles());
        }
        return fileList;
    }

}
