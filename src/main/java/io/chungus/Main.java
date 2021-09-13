package io.chungus;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.IOException;

public class Main {

    private final ChungusCommand command;

    private Main(ChungusCommand command) {
        this.command = command;
    }

    public static void main(String[] args) throws IOException, GitAPIException {
        ChungusCommand command = new ChungusCommandParser().parseOrExit(args);
        new Main(command).printLog1();
    }

    void printLog1() throws IOException, GitAPIException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = builder.setGitDir(command.gitDir().toFile())
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();
        Git git = new Git(repository);
        Iterable<RevCommit> commits = git.log().setMaxCount(1).call();
        for (RevCommit commit : commits) {
            System.out.println(commit.getFullMessage());
        }
    }
}
