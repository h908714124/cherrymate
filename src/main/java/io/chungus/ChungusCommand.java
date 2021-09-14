package io.chungus;

import net.jbock.Command;
import net.jbock.Option;
import net.jbock.Parameter;

import java.nio.file.Path;

@Command(superCommand = true)
abstract class ChungusCommand {

    @Option(names = {"--root-dir"})
    abstract Path rootDir();

    @Option(names = {"--source-branch"})
    abstract String sourceBranch();

    @Option(names = {"--target-branch"})
    abstract String targetBranch();

    @Parameter(index = 0)
    abstract Subcommand subcommand();
}
