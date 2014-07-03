package br.uel.mdd.avaliation;

import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.db.tables.pojos.QueryResults;
import br.uel.mdd.result.TreeResult;

public interface KnnOperation {

    public TreeResult<QueryResults> performKnn(Extractions extractionQuery, int k);

}
