package com.github.jdbarad.intellijplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

public class AddTextToFileAction extends DumbAwareAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        Editor editor = e.getData(CommonDataKeys.EDITOR);

        if (project != null && editor != null) {
            Document document = editor.getDocument();
            String inputText = Messages.showInputDialog(project, "Enter the text to add:", "Add Text", Messages.getQuestionIcon());

            if (inputText != null) {
                WriteCommandAction.runWriteCommandAction(project, () -> {
                    document.insertString(editor.getCaretModel().getOffset(), inputText);
                });
            }
        }
    }
}
