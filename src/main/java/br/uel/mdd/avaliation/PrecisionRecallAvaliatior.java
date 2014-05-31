package br.uel.mdd.avaliation;

import br.uel.mdd.db.jdbc.PostgresConnectionFactory;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 30/05/14.
 */
public class PrecisionRecallAvaliatior {


    private void saveMetrics(){

        Connection connection = new PostgresConnectionFactory().getConnection();
        Configuration configuration = new DefaultConfiguration()
                                                .set(connection)
                                                .set(SQLDialect.POSTGRES);


        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
