package br.uel.mdd.main;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public abstract class CommandLineValues {

    protected CmdLineParser parser;

    public CommandLineValues(String... values) {
        parser = new CmdLineParser(this);
        try {
            parser.parseArgument(values);
        } catch (CmdLineException e) {
            e.printStackTrace();
            parser.printUsage(System.err);
            System.exit(1);
        }
    }

    protected abstract void  processArguments();

}
