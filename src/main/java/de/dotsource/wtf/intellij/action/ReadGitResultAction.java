package de.dotsource.wtf.intellij.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vfs.VirtualFile;
import de.dotsource.wtf.data.FeedbackEntry;
import de.dotsource.wtf.data.FileMetricEntry;
import de.dotsource.wtf.service.FeedbackService;
import git4idea.config.GitConfigUtil;
import git4idea.repo.GitRemote;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReadGitResultAction extends ReadAction<AnActionEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(ReadGitResultAction.class);
    private final AnActionEvent event;
    private final int lineStart;
    private final int lineEnd;
    private final VirtualFile file;
    private final Project project;

    public ReadGitResultAction(AnActionEvent event) {
        this.event = event;
        file = event.getData(PlatformDataKeys.VIRTUAL_FILE);
        project = event.getData(PlatformDataKeys.PROJECT);

        Caret caret = event.getData(PlatformDataKeys.CARET);
        this.lineStart = caret.getSelectionStartPosition().line;
        this.lineEnd = caret.getSelectionEndPosition().line;
    }

    @Override
    public void run(@Nonnull Result<AnActionEvent> result) throws VcsException {

        List<FeedbackEntry> wtfList = new ArrayList<>();

        GitRepositoryManager gitRepositoryManager = GitRepositoryManager.getInstance(project);
        GitRepository gitRepository = gitRepositoryManager.getRepositories().get(0);
        String revision = gitRepository.getInfo().getCurrentRevision();
        String repository = gitRepository.getRemotes().stream().findFirst().map(GitRemote::getFirstUrl).orElse("WTFRepo");
        String user = GitConfigUtil.getValue(project, gitRepository.getRoot(), "user.email");

        String path = file.getPath();

        for (int i = lineStart; i <= lineEnd; i++) {
            FeedbackEntry entry = new FeedbackEntry();
            entry.setLine(i + 1);
            entry.setPath(path);

            entry.setComment(StringUtils.EMPTY);
            entry.setUserName(user);
            entry.setTimeStamp(OffsetDateTime.now());
            entry.setRepository(repository);
            entry.setRevision(revision);

            wtfList.add(entry);
        }

        FeedbackService feedbackService = FeedbackService.getInstance(project);
        for (FeedbackEntry entry : wtfList) {
            feedbackService.storeFeedback(entry);
        }
        result.setResult(event);
    }
}
