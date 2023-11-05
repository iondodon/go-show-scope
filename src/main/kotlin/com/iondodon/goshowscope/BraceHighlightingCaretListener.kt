package com.iondodon.goshowscope

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.event.CaretEvent
import com.intellij.openapi.editor.event.CaretListener


class BraceHighlightingCaretListener(private val editor: Editor) : CaretListener {

    override fun caretPositionChanged(event: CaretEvent) {
        val caret = event.caret ?: return
        clearBraceHighlights(editor)

        // Your logic to determine the offset range of the matching braces
        val matchingBracesRange = findMatchingBracesRange(editor, caret.offset)

        if (matchingBracesRange != null) {
            highlightBraces(editor, matchingBracesRange)
            drawLine(editor, matchingBracesRange)
        }
    }

}