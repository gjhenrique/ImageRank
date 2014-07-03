package br.uel.mdd.module;

import br.uel.mdd.db.tables.pojos.DistanceFunctions;
import br.uel.mdd.io.loading.QueryLoaderImpl;

public interface QueryLoaderFactory {
    public QueryLoaderImpl create(DistanceFunctions distanceFunctionss);
}
