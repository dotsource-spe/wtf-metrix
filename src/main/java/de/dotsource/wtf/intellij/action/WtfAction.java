package de.dotsource.wtf.intellij.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
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
    }

    @Override
    public boolean isDumbAware() {
        return false;
    }
}
