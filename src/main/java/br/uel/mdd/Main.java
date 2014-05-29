package br.uel.mdd;

import br.uel.mdd.db.jdbc.PostgresConnectionFactory;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;

import static br.uel.mdd.db.Tables.CLASSES;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 28/05/14.
 */
public class Main {
    public static void main(String args[]){
        Connection connection = new PostgresConnectionFactory().getConnection();
        DSLContext create = DSL.using(connection, SQLDialect.POSTGRES);
        Result<Record> result = create.select().from(CLASSES).fetch();
        for (Record r : result) {
            Integer id = r.getValue(CLASSES.ID);
            String nome = r.getValue(CLASSES.NOME);
            System.out.println("ID: " + id + " nome: " + nome);
        }
    }
}
