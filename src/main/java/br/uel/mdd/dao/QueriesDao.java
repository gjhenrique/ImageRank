package br.uel.mdd.dao;

import br.uel.mdd.db.tables.pojos.PrecisionRecall;
import br.uel.mdd.db.tables.pojos.Queries;
import br.uel.mdd.db.tables.records.QueriesRecord;
import org.jooq.*;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.DSL;
import org.jooq.util.postgres.PostgresDataType;

import java.util.ArrayList;
import java.util.List;

import static br.uel.mdd.db.Sequences.QUERIES_ID_SEQ;
import static br.uel.mdd.db.tables.DatasetClasses.DATASET_CLASSES;
import static br.uel.mdd.db.tables.Extractions.EXTRACTIONS;
import static br.uel.mdd.db.tables.Images.IMAGES;
import static br.uel.mdd.db.tables.Queries.QUERIES;
import static br.uel.mdd.db.tables.QueryResults.QUERY_RESULTS;
import static org.jooq.impl.DSL.avg;
import static org.jooq.impl.DSL.max;

public class QueriesDao extends DAOImpl<QueriesRecord, Queries, Integer> {

    private DSLContext create = DSL.using(this.configuration());


    public QueriesDao(Configuration configuration) {
        super(QUERIES, Queries.class, configuration);
    }

    @Override
    protected Integer getId(Queries object) {
        return object.getId();
    }

    public void insertNullPk(Queries queries) {
        long id = DSL.using(this.configuration()).nextval(QUERIES_ID_SEQ);
        queries.setId((int) id);
        this.insert(queries);
    }

    public Queries fetchByExtractionIdAndDistanceFunctionIdAndK(int extractionId, int distanceFunctionId, int k) {
        return create.select(QUERIES.fields())
                .from(QUERIES)
                .where(
                        QUERIES.EXTRACTION_ID.equal(extractionId)
                                .and(QUERIES.DISTANCE_FUNCTION_ID.equal(distanceFunctionId))
                                .and(QUERIES.K.equal(k))
                ).fetchOneInto(Queries.class);
    }

    public List<PrecisionRecall> precisionRecallByDistanceFunctionId(Integer distanceFunctionId, Integer... extractorIds) {

        Condition condition = whereDistanceFunction(distanceFunctionId);

        if (extractorIds.length > 0)
            condition = condition.and(whereExtractorId(extractorIds));

        return performQuery(condition, EXTRACTIONS.EXTRACTOR_ID);
    }

    public List<PrecisionRecall> precisionRecallByExtractorId(Integer extractorId, Integer ... distanceFunctionIds) {

        Condition condition = whereExtractorId(extractorId);

        if (distanceFunctionIds.length > 0)
            condition = condition.and(whereDistanceFunction(distanceFunctionIds));

        return performQuery(condition, QUERIES.DISTANCE_FUNCTION_ID);
    }

    private Condition whereDistanceFunction(Integer... distanceFunctionId) {
        return QUERIES.DISTANCE_FUNCTION_ID.in(distanceFunctionId);
    }

    private Condition whereExtractorId(Integer... extractorId) {
        return EXTRACTIONS.EXTRACTOR_ID.in(extractorId);
    }

    public List<PrecisionRecall> performQuery(Condition condition, TableField<? extends Record, Integer> field) {

        Select select = getQueries(condition, field);
        Result<Record> queryResults = getQueryResults(select);
        return getPrecisionRecallList(queryResults);
    }

    private List<PrecisionRecall> getPrecisionRecallList(Result<Record> queryResults) {
        List<PrecisionRecall> precisionRecallList = new ArrayList<>();

        for (Record queryResult : queryResults) {
            buildPrecisionRecall(precisionRecallList, queryResult);
        }
        return precisionRecallList;
    }

    private void buildPrecisionRecall(List<PrecisionRecall> precisionRecallList, Record queryResult) {
        PrecisionRecall precisionRecall = new PrecisionRecall(
                (Integer) queryResult.getValue("id"),
                (Double) queryResult.getValue("precision"),
                (Double) queryResult.getValue("recall")
        );
        precisionRecallList.add(precisionRecall);
    }

    public Select getQueries(Condition condition, TableField<? extends Record, Integer> field) {

        Table<Record5<Integer, Integer, Integer, Integer, Integer>> queryTable = getQueryTable(condition, field);

        Table<Record5<Double, Double, Integer, Integer, Integer>> windowedPr = getWindowedTable(queryTable);

        Table<Record4<Double, Double, Integer, Integer>> fullTable = getFullTable(windowedPr);

        return create.select(
                    fullTable.field("recall"),
                    avg((Field<Double>) fullTable.field("precision")).as("precision"),
                    fullTable.field("id"))
                .from(fullTable)
                .groupBy(fullTable.field("recall"), fullTable.field("id"))
                .orderBy(fullTable.field("id"), fullTable.field("recall"));
    }

    private Table<Record4<Double, Double, Integer, Integer>> getFullTable(Table<Record5<Double, Double, Integer, Integer, Integer>> windowedPr) {
        Field<Double> recallField = (Field<Double>) windowedPr.field("recall");
        Field<Double> precisionField = (Field<Double>) windowedPr.field("precision");
        Field<Integer> k = (Field<Integer>) windowedPr.field("k");
        Field<Integer> extractorId = (Field<Integer>) windowedPr.field("id");

        return create.select(
                recallField,
                precisionField,
                k,
                extractorId
        ).from(windowedPr)
                .groupBy(recallField, precisionField, k, extractorId).asTable().as("full_table");
    }

    private Table<Record5<Double, Double, Integer, Integer, Integer>> getWindowedTable(Table<Record5<Integer, Integer, Integer, Integer, Integer>> queryTable) {
        Field<Double> recall = queryTable.field("k").cast(PostgresDataType.FLOAT8).div(
                (Field<Double>) max(queryTable.field("k"))
                        .over().partitionBy(queryTable.field("extraction_id")));

        Field<Double> precision = DSL.count(QUERY_RESULTS.EXTRACTION_ID)
                .over().partitionBy(queryTable.field("query_id"))
                .div(queryTable.field("k").cast(PostgresDataType.FLOAT8)).cast(PostgresDataType.FLOAT8);

        return create.select(
                    precision.as("precision"),
                    recall.as("recall"),
                    queryTable.field("id").cast(PostgresDataType.INT4).as("id"),
                    queryTable.field("k").cast(PostgresDataType.INT4).as("k"),
                    QUERY_RESULTS.QUERY_ID
            ).from(queryTable)
                    .join(QUERY_RESULTS)
                    .on(QUERY_RESULTS.QUERY_ID.eq((Field<Integer>) queryTable.field("query_id")))
                    .join(EXTRACTIONS).onKey()
                    .join(IMAGES).onKey()
                    .join(DATASET_CLASSES)
                    .on(IMAGES.DATASET_CLASS_ID.eq(DATASET_CLASSES.ID)
                            .and(DATASET_CLASSES.CLASS_ID.eq((Field<Integer>) queryTable.field("class_id"))))
                    .orderBy(QUERY_RESULTS.QUERY_ID).asTable().as("windowed_pr");
    }

    private Table<Record5<Integer, Integer, Integer, Integer, Integer>> getQueryTable(Condition condition, TableField<? extends Record, Integer> field) {
        return create.select(
                QUERIES.ID.as("query_id"),
                EXTRACTIONS.ID.as("extraction_id"),
                DATASET_CLASSES.CLASS_ID,
                QUERIES.K.as("k"),
                field.as("id"))
                .from(EXTRACTIONS)
                .join(QUERIES).on(EXTRACTIONS.ID.eq(QUERIES.EXTRACTION_ID))
                .join(IMAGES).on(EXTRACTIONS.IMAGE_ID.eq(IMAGES.ID))
                .join(DATASET_CLASSES).onKey()
                .where(condition).asTable().as("query_table");
    }


    public Result<Record> getQueryResults(Select queries) {
//        System.out.println(queries.getSQL());
//        System.exit(-1);
        return create.fetch(queries.fetchResultSet());
    }

}
