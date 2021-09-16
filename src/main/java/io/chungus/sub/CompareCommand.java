package io.chungus.sub;

import org.eclipse.jgit.revwalk.RevCommit;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class CompareCommand {

    private final List<RevCommit> sourceCommits;
    private final List<RevCommit> targetCommits;
    private final String base;

    public CompareCommand(
            List<RevCommit> sourceCommits,
            List<RevCommit> targetCommits,
            String base) {
        this.sourceCommits = sourceCommits;
        this.targetCommits = targetCommits;
        this.base = base;
    }

    public void compare() {
        List<RevCommit> sourceCommits = fromBase(this.sourceCommits);
        Map<String, List<RevCommit>> source = groupByMessage(sourceCommits);
        Map<String, List<RevCommit>> target = groupByMessage(fromBase(targetCommits));
        List<String> messages = sourceCommits.stream().map(RevCommit::getShortMessage).distinct().toList();
        for (String message : messages) {
            List<RevCommit> s = source.getOrDefault(message, List.of());
            List<RevCommit> t = target.getOrDefault(message, List.of());
            if (s.size() != t.size()) {
                System.out.println("Expecting " + s.size() + " commits but found " + t.size() + ". Message: " + message.trim());
            }
        }
    }

    private Map<String, List<RevCommit>> groupByMessage(List<RevCommit> commits) {
        return commits.stream()
                .collect(groupingBy(RevCommit::getShortMessage));
    }

    private List<RevCommit> fromBase(List<RevCommit> commits) {
        return commits.stream()
                .dropWhile(c -> !c.getId().name().equals(base))
                .filter(c -> c.getParentCount() <= 1) // no merges
                .toList();
    }
}
