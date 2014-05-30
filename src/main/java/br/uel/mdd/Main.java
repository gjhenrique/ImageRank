package br.uel.mdd;

import br.uel.mdd.dao.ImagesDao;
import br.uel.mdd.db.jdbc.PostgresConnectionFactory;
import br.uel.mdd.db.tables.pojos.Images;
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
        ImagesDao dao = new ImagesDao(configuration);
        List<Images> all = dao.findAll();
        Arrays.deepToString(all.toArray());
    }
}
