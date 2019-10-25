package de.dotsource.wtf.intellij.action;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.FilePath;
import com.intellij.openapi.vcs.actions.VcsContext;
import com.intellij.openapi.vcs.actions.VcsContextFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
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
        Caret caret = actionEvent.getData(PlatformDataKeys.CARET);
        int lineStart = caret.getSelectionStartPosition().line;
        int lineEnd = caret.getSelectionEndPosition().line;
        VirtualFile file = actionEvent.getData(PlatformDataKeys.VIRTUAL_FILE);
        //VcsContextFactory vcsContextFactory = PeerFactory.getInstance().getVcsContextFactory()
        //  VcsContext context = vcsContextFactory.createContextOn(actionEvent);
        String path = file.getCanonicalPath();

        LOG.error("Selection starts in Line {} and ends in Line {} in file {}", lineStart, lineEnd, path);
    }

    @Override
    public boolean isDumbAware() {
        return false;
    }
}
