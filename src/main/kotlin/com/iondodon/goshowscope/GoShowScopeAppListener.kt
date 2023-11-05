package com.iondodon.goshowscope

import com.intellij.openapi.editor.event.EditorFactoryEvent
import com.intellij.openapi.editor.event.EditorFactoryListener

class GoShowScopeAppListener : EditorFactoryListener {
    private val bracketHighlightingCaretListener: BracketHighlightingCaretListener? = null
    private val bracketHighlightingDocumentListener: BracketHighlightingDocumentListener? = null

    override fun editorCreated(event: EditorFactoryEvent) {
        val editor = event.editor

        val bracketHighlightingCaretListener = BracketHighlightingCaretListener(editor)
        val bracketHighlightingDocumentListener = BracketHighlightingDocumentListener(editor)

        editor.caretModel.addCaretListener(bracketHighlightingCaretListener)
        editor.document.addDocumentListener(bracketHighlightingDocumentListener)
    }

    override fun editorReleased(event: EditorFactoryEvent) {
        val editor = event.editor

        if (bracketHighlightingCaretListener != null) {
            editor.caretModel.removeCaretListener(bracketHighlightingCaretListener)
        }
        if (bracketHighlightingDocumentListener != null) {
            editor.document.removeDocumentListener(bracketHighlightingDocumentListener)
        }
    }
}
