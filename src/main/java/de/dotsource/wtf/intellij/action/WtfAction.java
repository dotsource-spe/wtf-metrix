package de.dotsource.wtf.intellij.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.application.RunResult;
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
    private static final ReadAction<AnActionEvent> READ_GIT_RESULT_ACTION = new ReadGitResultAction();

    @Override
    public void actionPerformed(@NotNull AnActionEvent actionEvent) {
        RunResult<AnActionEvent> runResult = new RunResult<>(READ_GIT_RESULT_ACTION);
        runResult.run();
    }

    @Override
    public boolean isDumbAware() {
        return true;
    }
}
