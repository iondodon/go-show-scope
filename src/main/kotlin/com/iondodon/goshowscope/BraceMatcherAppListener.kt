package com.iondodon.goshowscope

import com.intellij.openapi.editor.event.EditorFactoryEvent
import com.intellij.openapi.editor.event.EditorFactoryListener

class BraceMatcherAppListener : EditorFactoryListener {
    private val braceHighlightingCaretListener: BraceHighlightingCaretListener? = null
    private val braceHighlightingDocumentListener: BraceHighlightingDocumentListener? = null

    override fun editorCreated(event: EditorFactoryEvent) {
        val editor = event.editor

        val braceHighlightingCaretListener = BraceHighlightingCaretListener(editor)
        val braceHighlightingDocumentListener = BraceHighlightingDocumentListener(editor)

        editor.caretModel.addCaretListener(braceHighlightingCaretListener)
        editor.document.addDocumentListener(braceHighlightingDocumentListener)
    }

    override fun editorReleased(event: EditorFactoryEvent) {
        val editor = event.editor

        if (braceHighlightingCaretListener != null) {
            editor.caretModel.removeCaretListener(braceHighlightingCaretListener)
        }
        if (braceHighlightingDocumentListener != null) {
            editor.document.removeDocumentListener(braceHighlightingDocumentListener)
        }
    }
}
