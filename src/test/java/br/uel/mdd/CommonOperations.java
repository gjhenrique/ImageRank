package br.uel.mdd;


import br.uel.mdd.db.jdbc.ConnectionFactory;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.destination.DriverManagerDestination.with;
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;

public class CommonOperations {

    @Inject
    private ConnectionFactory factory;

    public static final Operation DELETE_ALL = deleteAllFrom("query_results", "queries", "extractions",
            "extractors", "images", "distance_functions", "dataset_classes", "class_image", "datasets");

    public static final Operation SEED_VALUES = sequenceOf(
            insertInto("extractors").
                    columns("id", "name", "type_identifier", "class_name", "levels_wavelet").
                    values(1, "Wavelet SubEspaco", "Haar1", "ReducedScaleWaveletExtractor", 4).
                    build(),
            insertInto("extractors").
                    columns("id", "name", "type_identifier", "class_name").
                    values(2, "Textural Features", "Haralick", "JFeatureLib").
                    build(),
            insertInto("distance_functions").
                    columns("id", "name", "class_name").
                    values(1, "Euclidean", "EuclideanDistance").
                    build());

    public static final Operation DATASETS = Operations.sequenceOf(
            insertInto("datasets").
                    columns("id", "name").
                    values(1, "Pulmao").
                    build()
    );

    public static final Operation CLASS_IMAGE = Operations.sequenceOf(
            insertInto("class_image").
                    columns("id", "name").
                    values(1, "Consolidacao").
                    values(2, "Enfisema").
                    build()
    );

    public static final Operation DATASET_CLASSES = Operations.sequenceOf(
            insertInto("dataset_classes").
                    columns("id", "class_id", "dataset_id").
                    values(1, 1, 1).
                    values(2, 2, 1).
                    build()
    );

    public static final Operation IMAGES = sequenceOf(
            insertInto("images").
                    columns("id", "file_name", "image", "dataset_class_id", "mime_type").
                    values(1, "Consolidacao0002685C_08.dcm", extractBytes("dicom/Pulmao/Consolidacao0002685C_08.dcm"), 1, "application/dicom").
                    values(2, "Consolidacao0769646D_12.dcm", extractBytes("dicom/Pulmao/Consolidacao0769646D_12.dcm"), 1, "application/dicom").
                    values(3, "Enfisema0106321J_09.dcm", extractBytes("dicom/Pulmao/Enfisema0106321J_09.dcm"), 2, "application/dicom").
                    build());

    private static byte[] extractBytes(String path) {
        String rootPath = "src/test/resources/imgs/";
        try {
            FileInputStream fis = new FileInputStream(rootPath + path);
            BufferedInputStream bis = new BufferedInputStream(fis);
            return ByteStreams.toByteArray(bis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DbSetup createDbSetup(Operation operation) {
        Operation finalOperation = Operations.sequenceOf(getDefaultOperations(), operation);
        return createNewDbSetup(finalOperation);
    }

    public DbSetup createDbSetup() {
        return createNewDbSetup(getDefaultOperations());
    }

    private Operation getDefaultOperations() {
        return sequenceOf(
                CommonOperations.DELETE_ALL,
                CommonOperations.SEED_VALUES
        );
    }

    private DbSetup createNewDbSetup(Operation operation) {
        return new DbSetup(
                with(factory.getUrl(), factory.getUser(), factory.getPassword()),
                operation);
    }

}
