package br.uel.mdd;

import br.uel.mdd.dao.ExperimentosDao;
import br.uel.mdd.db.jdbc.PostgresConnectionFactory;
import br.uel.mdd.model.Experimentos;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 28/05/14.
 */
public class Main {
    public static void main(String args[]){

        Connection connection = new PostgresConnectionFactory().getConnection();

        Configuration configuration = new DefaultConfiguration().set(connection).set(SQLDialect.POSTGRES);

        ExperimentosDao dao = new ExperimentosDao(configuration);
        List<Experimentos> exp = dao.findAll();

        System.out.println(Arrays.deepToString(exp.toArray()));

    }
}
