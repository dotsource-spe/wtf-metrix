package de.dotsource.wtf.intellij.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
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
        LOG.error("Action Performed with {}", actionEvent);
        final Project project = actionEvent.getData(CommonDataKeys.PROJECT);
        final Editor editor = actionEvent.getData(CommonDataKeys.EDITOR);
        final Caret caret = actionEvent.getData(CommonDataKeys.CARET);
        int lineNumber = caret.getLogicalPosition().line;
        caret.getSelectionStart();
        LOG.error("Caret is at line {}, Selection starts at {}.", lineNumber, null);
    }

    @Override
    public boolean isDumbAware() {
        return false;
    }
}
