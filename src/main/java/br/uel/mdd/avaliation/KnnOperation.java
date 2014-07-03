package br.uel.mdd.avaliation;

import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.db.tables.pojos.QueryResults;

import java.util.Collection;

public interface KnnOperation {

    public Collection<QueryResults> performKnn(Extractions extractionQuery, int k);

}
