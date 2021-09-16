package io.chungus;

import io.chungus.sub.BaseCommand;
import io.chungus.sub.CompareCommand;
import io.chungus.util.GetCommits;
import net.jbock.util.SuperResult;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.IOException;
import java.util.List;

public class Main {

    private final ChungusCommand command;
    private final List<String> rest;

    private Main(ChungusCommand command, List<String> rest) {
        this.command = command;
        this.rest = rest;
    }

    public static void main(String[] args) throws IOException, GitAPIException {
        SuperResult<ChungusCommand> r = new ChungusCommandParser().parseOrExit(args);
        ChungusCommand command = r.result();
        new Main(command, List.of(r.rest())).dispatch();
    }

    void dispatch() throws IOException, GitAPIException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = builder.setGitDir(command.rootDir().resolve(".git").toFile())
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();
        Git git = new Git(repository);
        GetCommits getCommits = new GetCommits(git);
        List<RevCommit> sourceCommits = getCommits.getCommits(command.sourceBranch());
        List<RevCommit> targetCommits = getCommits.getCommits(command.targetBranch());
        BaseCommand baseCommand = new BaseCommand(sourceCommits, targetCommits);
        String base = baseCommand.findBase();
        switch (command.subcommand()) {
            case BASE -> {
                System.out.println("Branch 1: " + command.sourceBranch());
                System.out.println("Branch 2: " + command.targetBranch());
                System.out.println("BASE: " + base);
            }
            case COMPARE -> new CompareCommand(sourceCommits, targetCommits, base).compare();
            default -> throw new IllegalArgumentException("unknown command: " + command.subcommand());
        }
    }
}
