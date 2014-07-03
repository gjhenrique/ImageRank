package br.uel.mdd.io.loading;

import br.uel.mdd.db.tables.pojos.Extractions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QueryLoaderDispatcher {

    private static final int THREADS_NUMBER = 8;

    private final QueryLoaderListener listener;

    private ExecutorService threadPool;

    public QueryLoaderDispatcher(QueryLoaderListener listener) {
        this.listener = listener;
        threadPool = Executors.newFixedThreadPool(THREADS_NUMBER);
    }

    public void runQuery(final QueryLoader queryLoader, final Extractions extractions, final int k) {
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                queryLoader.knn(extractions, k);
                listener.queryComplete();
            }
        });
    }

    public interface QueryLoaderListener {
        public void queryComplete();
    }

}
