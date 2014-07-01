package br.uel.mdd.io.loading;

import br.uel.mdd.db.tables.pojos.Extractions;

public interface QueryLoader {

    public void knn(Extractions extractionQuery, int k);

}
