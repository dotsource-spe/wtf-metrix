package de.dotsource.wtf.intellij.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.sun.javadoc.Doc;
import de.dotsource.wtf.data.FeedbackEntry;
import de.dotsource.wtf.data.GitResult;
import de.dotsource.wtf.service.FeedbackService;
import git4idea.commands.Git;
import git4idea.commands.GitCommand;
import git4idea.commands.GitCommandResult;
import git4idea.commands.GitLineHandler;
import git4idea.repo.GitConfig;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.io.File;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReadGitResultAction extends ReadAction<AnActionEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(ReadGitResultAction.class);

    @Override
    public void run(Result<AnActionEvent> result) throws Throwable {
        GitResult result1 = (GitResult) result;
        AnActionEvent event = result1.getResult();

        List<FeedbackEntry> wtfList = new ArrayList<FeedbackEntry>();
        VirtualFile file = event.getData(PlatformDataKeys.VIRTUAL_FILE);
        Project project = event.getData(PlatformDataKeys.PROJECT);
        Git git = Git.getInstance();
        GitRepositoryManager gitRepositoryManager = GitRepositoryManager.getInstance(project);
        GitRepository gitRepository = gitRepositoryManager.getRepositories().get(0);
        String revision = gitRepository.getInfo().getCurrentRevision();
        GitCommandResult commandResult = git.config(gitRepository,"user.email");
        String user = commandResult.getOutputAsJoinedString();

        String path = file.getCanonicalPath();
        String repository = project.getBasePath();

        Caret caret = event.getData(PlatformDataKeys.CARET);
        int lineStart = caret.getSelectionStartPosition().line;
        int lineEnd = caret.getSelectionEndPosition().line;
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
        FeedbackService feedbackService = ServiceManager.getService(FeedbackService.class);
        for (FeedbackEntry entry : wtfList)
        feedbackService.storeFeedback(entry);
    }
}
