package com.github.jdbarad.intellijplugin.toolWindow

import com.github.jdbarad.intellijplugin.MyBundle
import com.github.jdbarad.intellijplugin.services.MyProjectService
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBTextField
import com.intellij.ui.content.ContentFactory
import com.intellij.util.ui.JBUI.CurrentTheme.Editor
import javax.swing.JButton


class MyToolWindowFactory : ToolWindowFactory {

    init {
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = MyToolWindow(toolWindow)
        val content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true

    class MyToolWindow(private val toolWindow: ToolWindow) {

        private val service = toolWindow.project.service<MyProjectService>()

        fun getContent() = JBPanel<JBPanel<*>>().apply {
            val titleLabel = JBLabel(MyBundle.message("titleLabel"))
            val titleField = JBTextField()
            val label = JBLabel(MyBundle.message("randomLabel", "?"))
            add(titleLabel)
            add(titleField)
            add(label)
            add(JButton(MyBundle.message("submit")).apply {
                addActionListener {
                    label.text = MyBundle.message("titleMessage", titleField.text)
                    val project: Project = toolWindow.project
                    val editorManager = FileEditorManager.getInstance(project)
                    val dfbjk = editorManager.selectedFiles
                    val targetFile = LocalFileSystem.getInstance().findFileByPath("/Users/icoderz_44/IdeaProjects/untitled1/src/Main.java")!!
                    val editor = (editorManager.getSelectedEditor(targetFile) as TextEditor).editor
                    WriteCommandAction.runWriteCommandAction(project) {
                        editor.document.insertString(editor.caretModel.offset, titleField.text)
                    }
                }
            })
        }
    }
}
