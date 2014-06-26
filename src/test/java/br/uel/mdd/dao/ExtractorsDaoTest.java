package br.uel.mdd.dao;

import br.uel.mdd.module.AppModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExtractorsDaoTest {

    Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new AppModule());
    }

    @After
    public void tearDown() throws Exception {
        injector = null;
    }

    @Test
    public void testHasExtractions() throws Exception {
        ExtractorsDao dao = injector.getInstance(ExtractorsDao.class);
        assertEquals(true, dao.hasExtractions(1));
        assertEquals(true, dao.hasExtractions(4));
    }
}