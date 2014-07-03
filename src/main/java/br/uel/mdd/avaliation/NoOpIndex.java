package br.uel.mdd.avaliation;

import br.uel.mdd.db.tables.pojos.Extractions;

public class NoOpIndex implements Index {

    @Override
    public void addEntry(Extractions object) {
//        No OP Index
    }
}
