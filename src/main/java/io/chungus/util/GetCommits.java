package io.chungus.util;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class GetCommits {

    private final Git git;

    public GetCommits(Git git) {
        this.git = git;
    }

    public List<String> getCommits(String branch) throws GitAPIException {
        git.checkout().setName(branch).call();
        Stream<RevCommit> stream = StreamSupport.stream(git.log().call().spliterator(), false);
        List<String> result = new ArrayList<>(stream.map(c -> c.getId().name()).toList());
        Collections.reverse(result);
        return result;
    }
}
