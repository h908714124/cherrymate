package io.chungus.sub;

import io.chungus.ChungusCommand;
import io.chungus.util.GetCommits;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BaseCommand {

    private final Git git;
    private final ChungusCommand command;

    public BaseCommand(Git git, ChungusCommand command) {
        this.git = git;
        this.command = command;
    }

    public String findBase() throws GitAPIException {
        List<String> source = new GetCommits(git).getCommits(command.sourceBranch());
        Set<String> target = new HashSet<>(new GetCommits(git).getCommits(command.targetBranch()));
        for (int i = 0; i < source.size(); i++) {
            String sha = source.get(i);
            if (!target.contains(sha)) {
                return source.get(i - 1);
            }
        }
        throw new IllegalStateException("base not found");
    }
}
