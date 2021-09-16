package io.chungus;

import net.jbock.Command;
import net.jbock.Option;
import net.jbock.Parameter;

import java.nio.file.Path;

@Command(superCommand = true)
public interface ChungusCommand {

    @Option(names = {"--root-dir"})
    Path rootDir();

    @Option(names = {"--source-branch"})
    String sourceBranch();

    @Option(names = {"--target-branch"})
    String targetBranch();

    @Parameter(index = 0)
    Subcommand subcommand();
}
