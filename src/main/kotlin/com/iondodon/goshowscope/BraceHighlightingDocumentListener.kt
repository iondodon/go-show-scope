package com.iondodon.goshowscope

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.psi.PsiDocumentManager

class BraceHighlightingDocumentListener(private val editor: Editor) : DocumentListener {
    override fun documentChanged(event: DocumentEvent) {
        // Clear existing brace highlighters
        clearBraceHighlights(editor)

        // Find and highlight matching braces again
        PsiDocumentManager.getInstance(editor.project!!).performLaterWhenAllCommitted {
            val caret = editor.caretModel.primaryCaret
            val matchingBracesRange = findMatchingBracesRange(editor, caret.offset)

            if (matchingBracesRange != null) {
                highlightBraces(editor, matchingBracesRange)
            }
        }
    }
}