package de.dotsource.wtf.intellij.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.RunResult;
import git4idea.repo.GitRepositoryManager;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This action is invoked on a Block of Code to Mark it with WTF's
 *
 * @author jba
 */
public class WtfAction extends AnAction {
    private static final Logger LOG = LoggerFactory.getLogger(WtfAction.class);

    @Override
    public void actionPerformed(@NotNull AnActionEvent actionEvent) {
        RunResult<AnActionEvent> runResult = new RunResult<>(new ReadGitResultAction(actionEvent));
        ApplicationManager.getApplication().executeOnPooledThread(runResult::run);
    }

    @Override
    public void update(@NotNull AnActionEvent actionEvent) {
        actionEvent.getPresentation().setEnabled(isPossible(actionEvent));
    }

    private boolean isPossible(@NotNull AnActionEvent actionEvent) {
        return actionEvent.getProject() != null &&
                CollectionUtils.isNotEmpty(GitRepositoryManager.getInstance(actionEvent.getProject()).getRepositories());
    }

    @Override
    public boolean isDumbAware() {
        return true;
    }
}
