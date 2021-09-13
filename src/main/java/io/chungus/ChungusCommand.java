package io.chungus;

import net.jbock.Command;
import net.jbock.Option;

import java.nio.file.Path;

@Command
abstract class ChungusCommand {

    @Option(names = {"-d", "--git-dir"})
    abstract Path gitDir();
}
