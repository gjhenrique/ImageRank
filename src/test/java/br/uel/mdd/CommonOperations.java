package br.uel.mdd;


import br.uel.mdd.db.jdbc.ConnectionFactory;
import com.google.inject.Inject;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

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
