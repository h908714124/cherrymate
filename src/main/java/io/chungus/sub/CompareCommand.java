package io.chungus.sub;

import io.chungus.ChungusCommand;
import org.eclipse.jgit.api.Git;

public class CompareCommand {

    private final Git git;
    private final ChungusCommand command;
    private final String base;

    public CompareCommand(
            Git git,
            ChungusCommand command,
            String base) {
        this.git = git;
        this.command = command;
        this.base = base;
    }

    public void compare() {
    }
}
