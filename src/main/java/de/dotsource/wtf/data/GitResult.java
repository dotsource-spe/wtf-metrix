package de.dotsource.wtf.data;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.project.Project;
import git4idea.repo.GitRepository;

public class GitResult extends Result<AnActionEvent> {
    public AnActionEvent getResult(){
        return myResult;
    }
}
