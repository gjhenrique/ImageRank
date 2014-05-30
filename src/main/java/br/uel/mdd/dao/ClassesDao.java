package br.uel.mdd.dao;

import br.uel.mdd.db.tables.records.ClassesRecord;
import br.uel.mdd.model.Classes;
import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;

import java.util.List;

import static br.uel.mdd.db.Tables.CLASSES;

/**
 * @author Pedro Tanaka <pedro.stanaka@gmail.com>
 * @TODO Auto-generated comment
 */
public class ClassesDao extends DAOImpl<ClassesRecord, Classes, Long> {

    public ClassesDao(Configuration configuration) {
        super(CLASSES, Classes.class, configuration);
    }

    @Override
    protected Long getId(Classes object) {
        return object.getId();
    }

    public List<Classes> fetchByNome(java.lang.String... values) {
        return fetch(CLASSES.NOME, values);
    }
}
