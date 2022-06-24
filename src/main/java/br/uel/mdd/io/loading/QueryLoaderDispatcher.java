package br.uel.mdd.io.loading;

import br.uel.mdd.db.tables.pojos.Extractions;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.inject.Inject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QueryLoaderDispatcher {

    private final QueryLoaderListener listener;

    private ExecutorService threadPool;

    public QueryLoaderDispatcher(QueryLoaderListener listener, ExecutorService threadPool) {
        this.listener = listener;
        this.threadPool = threadPool;
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

    public void shutdown() {
        threadPool.shutdown();
    }

    public interface QueryLoaderListener {
        public void queryComplete();
    }

}
