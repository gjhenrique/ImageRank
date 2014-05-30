package br.uel.mdd.loading;

import br.uel.mdd.NameClassExtractor;
import br.uel.mdd.dao.ClassesDao;
import br.uel.mdd.dao.ExperimentosDao;
import br.uel.mdd.db.jdbc.PostgresConnectionFactory;
import br.uel.mdd.model.Classes;
import br.uel.mdd.model.Experimentos;
import com.google.common.io.ByteStreams;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static br.uel.mdd.db.Sequences.CLASSES_ID_SEQ;
import static br.uel.mdd.db.Sequences.EXPERIMENTOS_ID_SEQ;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 29/05/14.
 */
public class LungImageLoader {


    public static void main(String args[]) {
        LungImageLoader lil = new LungImageLoader();
        try {
            lil.loadFilesFromFolder("/home/pedro/Documentos/Mestrado/PDS/Projeto/Imagens-Pulmao");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadFilesFromFolder(String path) throws IOException {
        Connection connection = new PostgresConnectionFactory().getConnection();
        Configuration configuration = new DefaultConfiguration().set(connection).set(SQLDialect.POSTGRES);

        ExperimentosDao experimentosDao = new ExperimentosDao(configuration);
        ClassesDao classesDao = new ClassesDao(configuration);
        NameClassExtractor classExtractor = new NameClassExtractor();


        for (File file : this.getFilesFromFolder(path)) {
            Experimentos exp = new Experimentos();
            exp.setId(DSL.using(configuration).nextval(EXPERIMENTOS_ID_SEQ));
            FileInputStream fis = new FileInputStream(file);
            byte[] fileBytes = ByteStreams.toByteArray(fis);

            exp.setImagem(fileBytes);
            exp.setNomeImagem(file.getName());
            if (classesDao.fetchByNome(classExtractor.extractClass(file)).isEmpty()) {
                Classes classe = new Classes();
                classe.setId(DSL.using(configuration).nextval(CLASSES_ID_SEQ));
                classe.setNome(classExtractor.extractClass(file));
                classe.setTipoImagem("lung");
                classesDao.insert(classe);
            }
            exp.setClasseId(classesDao.fetchByNome(classExtractor.extractClass(file)).get(0).getId());
            experimentosDao.insert(exp);
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
