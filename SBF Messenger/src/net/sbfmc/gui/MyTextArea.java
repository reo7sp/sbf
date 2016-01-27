package net.sbfmc.gui;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class MyTextArea extends JEditorPane {
	private static final long serialVersionUID = 4443417341120211933L;

	public MyTextArea() {
		super();

		setFont(new Font(null, Font.PLAIN, 11));
		setForeground(Color.DARK_GRAY);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentType("text/html");
		setText("<html>");
	}

	public void append(String string) {
		try {
			Document doc = getDocument();
			if (doc != null) {
				EditorKit editorKit = getEditorKit();
				if (editorKit instanceof HTMLEditorKit && doc instanceof HTMLDocument) {
					((HTMLEditorKit) editorKit).insertHTML(
							(HTMLDocument) doc,
							doc.getEndPosition().getOffset() - 1,
							string,
							1,
							0,
							null);
				} else {
					doc.insertString(doc.getEndPosition().getOffset() - 1, string, null);
				}
			}
		} catch (BadLocationException err) {
			err.printStackTrace();
		} catch (IOException err) {
			err.printStackTrace();
		}
	}
}
