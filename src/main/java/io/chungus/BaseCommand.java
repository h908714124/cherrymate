package io.chungus;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class BaseCommand {

    private final Git git;
    private final ChungusCommand command;

    BaseCommand(Git git, ChungusCommand command) {
        this.git = git;
        this.command = command;
    }

    void findBase() throws GitAPIException {
        List<String> source = new GetCommits(git).getCommits(command.sourceBranch());
        Set<String> target = new HashSet<>(new GetCommits(git).getCommits(command.targetBranch()));
        for (int i = 0; i < source.size(); i++) {
            String sha = source.get(i);
            if (!target.contains(sha)) {
                System.out.println("Branch 1: " + command.sourceBranch());
                System.out.println("Branch 2: " + command.targetBranch());
                System.out.println("BASE: " + source.get(i - 1));
                return;
            }
        }
    }
}
