package io.chungus.sub;

import io.chungus.ChungusCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BaseCommand {

    private final List<RevCommit> sourceCommits;
    private final List<RevCommit> targetCommits;

    public BaseCommand(
            List<RevCommit> sourceCommits,
            List<RevCommit> targetCommits) {
        this.sourceCommits = sourceCommits;
        this.targetCommits = targetCommits;
    }

    public String findBase() {
        List<String> source = sourceCommits.stream().map(c -> c.getId().name()).toList();
        Set<String> target = new HashSet<>(targetCommits.stream().map(c -> c.getId().name()).toList());
        for (int i = 0; i < source.size(); i++) {
            String sha = source.get(i);
            if (!target.contains(sha)) {
                return source.get(i - 1);
            }
        }
        throw new IllegalStateException("base not found");
    }
}
