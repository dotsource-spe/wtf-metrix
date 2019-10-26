package de.dotsource.wtf.intellij.action;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.FilePath;
import com.intellij.openapi.vcs.actions.VcsContext;
import com.intellij.openapi.vcs.actions.VcsContextFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import de.dotsource.wtf.data.FeedbackEntry;
import de.dotsource.wtf.data.GitResult;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

/**
 * This action is invoked on a Block of Code to Mark it with WTF's
 *
 * @author jba
 */
public class WtfAction extends AnAction {
    private static final Logger LOG = LoggerFactory.getLogger(WtfAction.class);

    @Override
    public void actionPerformed(@NotNull AnActionEvent actionEvent) {

        ReadGitResultAction action = new ReadGitResultAction();
        GitResult gitResult = new GitResult();
        gitResult.setResult(actionEvent);

        try {
            action.run(gitResult);
        } catch (Throwable e){
            LOG.error(e.getMessage(),e);
        }

    }

    @Override
    public boolean isDumbAware() {
        return true;
    }
}
