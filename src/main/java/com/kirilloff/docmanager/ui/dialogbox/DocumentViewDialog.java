package com.kirilloff.docmanager.ui.dialogbox;

import com.kirilloff.docmanager.domain.BaseDocument;
import java.awt.BorderLayout;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DocumentViewDialog extends JDialog {

  public DocumentViewDialog(Frame parent, BaseDocument document) {
    super(parent, "Просмотр документа", true);
    setLayout(new BorderLayout());

    JTextArea textArea = new JTextArea(10, 30);
    textArea.setText(document.toReadableFormat());
    textArea.setEditable(false);

    JScrollPane scrollPane = new JScrollPane(textArea);
    add(scrollPane, BorderLayout.CENTER);

    JButton closeButton = new JButton("Закрыть");
    closeButton.addActionListener(e -> dispose());
    add(closeButton, BorderLayout.SOUTH);

    pack();
    setLocationRelativeTo(parent);
  }
}