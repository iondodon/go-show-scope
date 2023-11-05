package com.iondodon.goshowscope

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.markup.LineMarkerRenderer
import com.intellij.ui.JBColor
import java.awt.BasicStroke
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Rectangle

class BraceLineMarkerRenderer(private val startOffset: Int, private var endOffset: Int) : LineMarkerRenderer {
    override fun paint(editor: Editor, g: Graphics, r: Rectangle) {
        if (g is Graphics2D) {
            // Save the original stroke
            val originalStroke = g.stroke

            val startVisualPosition = editor.offsetToVisualPosition(startOffset)
            val endVisualPosition = editor.offsetToVisualPosition(endOffset)

            val openingBracePoint = editor.visualPositionToXY(startVisualPosition)
            val lineHeight = editor.lineHeight
            val closingBracePoint = editor.visualPositionToXY(endVisualPosition).apply {
                y += lineHeight
            }


            g.stroke = BasicStroke(5f)
            g.color = JBColor.GRAY // Set the color of the line
            g.drawLine(0, openingBracePoint.y, 0, closingBracePoint.y)

            g.stroke = BasicStroke(1f)
            g.color = JBColor.GRAY // Set the color of the line
            g.drawLine(0, openingBracePoint.y - 2, openingBracePoint.x, openingBracePoint.y - 2)

            g.stroke = BasicStroke(1f)
            g.color = JBColor.GRAY // Set the color of the line
            g.drawLine(0, closingBracePoint.y + 3, openingBracePoint.x, closingBracePoint.y + 3)

            // Restore the original stroke immediately after drawing your line
            g.stroke = originalStroke
        }
    }
}