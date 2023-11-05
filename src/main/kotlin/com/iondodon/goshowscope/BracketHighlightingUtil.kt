package com.iondodon.goshowscope

import com.goide.psi.GoBlock
import com.goide.psi.GoFile
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.markup.HighlighterLayer
import com.intellij.openapi.editor.markup.HighlighterTargetArea
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.refactoring.suggested.startOffset

fun findMatchingBracesRange(editor: Editor, caretOffset: Int): TextRange? {
    val project = editor.project ?: return null
    val document = editor.document
    val psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document) as? GoFile ?: return null

    val elementAtCaret = psiFile.findElementAt(caretOffset)


    val goBlock = PsiTreeUtil.getParentOfType(elementAtCaret, GoBlock::class.java) ?: return null

    val lBrace = goBlock.lbrace
    val rBrace = goBlock.rbrace

    if (lBrace != null && rBrace != null) {
        return TextRange(lBrace.startOffset, rBrace.startOffset)
    }

    return null
}

fun highlightBraces(editor: Editor, range: TextRange) {
    val markupModel = editor.markupModel
    val textAttributesKey = TextAttributesKey.find("MATCHED_BRACE_ATTRIBUTES")
    val textAttributes = EditorColorsManager.getInstance().globalScheme.getAttributes(textAttributesKey)

    // Create highlighters for the opening and closing braces
    markupModel.addRangeHighlighter(
        range.startOffset,
        range.startOffset + 1,
        HighlighterLayer.SELECTION,
        textAttributes,
        HighlighterTargetArea.EXACT_RANGE
    )

    // Highlight the closing brace
    if (!Character.isWhitespace(editor.document.charsSequence[range.endOffset])) {
        markupModel.addRangeHighlighter(
            range.endOffset,
            range.endOffset + 1,
            HighlighterLayer.SELECTION,
            textAttributes,
            HighlighterTargetArea.EXACT_RANGE
        )
    }
}

fun clearBraceHighlights(editor: Editor) {
    editor.markupModel.removeAllHighlighters()
}

fun drawIndicator(editor: Editor, range: TextRange) {
    val markupModel = editor.markupModel
    val startOffset = range.startOffset
    val endOffset = range.endOffset
    val lineMarkerRenderer = BracketLineMarkerRenderer(startOffset, endOffset)

    markupModel.addRangeHighlighter(
        startOffset,
        endOffset,
        HighlighterLayer.LAST + 1,
        null,
        HighlighterTargetArea.LINES_IN_RANGE
    ).apply { this.lineMarkerRenderer = lineMarkerRenderer }
}