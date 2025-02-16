package com.kirilloff.docmanager.ui;

import static com.kirilloff.docmanager.service.DocService.loadDocumentFromFile;
import static com.kirilloff.docmanager.service.DocService.saveDocumentToFile;
import static com.kirilloff.docmanager.util.Constants.ERROR_MESSAGE;

import com.kirilloff.docmanager.domain.BaseDocument;
import com.kirilloff.docmanager.ui.dialogbox.DocumentViewDialog;
import com.kirilloff.docmanager.ui.dialogbox.InvoiceDialog;
import com.kirilloff.docmanager.ui.dialogbox.PaymentOrderDialog;
import com.kirilloff.docmanager.ui.dialogbox.PaymentRequestDialog;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame {

  private DefaultListModel<BaseDocument> documentListModel;

  private JList<BaseDocument> documentList;

  private JButton saveButton;
  private JButton loadButton;
  private JButton viewButton;
  private JButton deleteButton;
  private JButton exitButton;

  public MainFrame() {
    setTitle("Документооборот");
    setSize(600, 400);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    initializeUI();
  }

  private void initializeUI() {
    setLayout(new BorderLayout());

    initializeDocumentList();
    initializeButtons();

    JPanel buttonPanel = createButtonPanel();
    JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    centerPanel.add(buttonPanel);

    add(centerPanel, BorderLayout.EAST);
  }

  private void initializeDocumentList() {
    documentListModel = new DefaultListModel<>();
    documentList = new JList<>(documentListModel);
    documentList.setCellRenderer(new CheckboxListCellRenderer());

    documentList.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleListMouseClick(e);
      }
    });

    documentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    add(new JScrollPane(documentList), BorderLayout.CENTER);
  }

  private void handleListMouseClick(MouseEvent e) {
    int index = documentList.locationToIndex(e.getPoint());
    if (index >= 0) {
      Rectangle bounds = documentList.getCellBounds(index, index);
      if (bounds.contains(e.getPoint())) {
        ListCellRenderer<? super BaseDocument> renderer = documentList.getCellRenderer();
        Component component = renderer.getListCellRendererComponent(documentList,
            documentList.getModel().getElementAt(index), index, false, false);

        if (component instanceof JPanel panel) {
          for (Component c : panel.getComponents()) {
            if (c instanceof JCheckBox) {
              Rectangle cbBounds = c.getBounds();
              cbBounds.setLocation(bounds.x + cbBounds.x, bounds.y + cbBounds.y);
              if (cbBounds.contains(e.getPoint())) {
                CheckboxListCellRenderer.toggleCheckbox(index);
                documentList.repaint();
                break;
              }
            }
          }
        }
      }
    }
  }

  private void initializeButtons() {
    saveButton = createButton("Сохранить");
    loadButton = createButton("Загрузить");
    viewButton = createButton("Просмотр");
    deleteButton = createButton("Удалить");
    exitButton = createButton("Выход");

    saveButton.addActionListener(e -> saveDocument());
    loadButton.addActionListener(e -> loadDocumentFromFile(documentListModel));
    viewButton.addActionListener(e -> viewDocument());
    deleteButton.addActionListener(e -> deleteSelectedDocuments());
    exitButton.addActionListener(e -> System.exit(0));
  }

  private void saveDocument() {
    BaseDocument selectedDocument = documentList.getSelectedValue();
    if (selectedDocument != null) {
      saveDocumentToFile(selectedDocument);
    } else {
      JOptionPane.showMessageDialog(null, "Выберите документ для сохранения", ERROR_MESSAGE, JOptionPane.WARNING_MESSAGE);
    }
  }

  private JPanel createButtonPanel() {
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    addButtonsToPanel(buttonPanel);
    return buttonPanel;
  }

  private void addButtonsToPanel(JPanel buttonPanel) {
    buttonPanel.add(Box.createVerticalGlue());
    buttonPanel.add(createInvoiceButton());
    buttonPanel.add(Box.createVerticalStrut(5));
    buttonPanel.add(createPaymentOrderButton());
    buttonPanel.add(Box.createVerticalStrut(5));
    buttonPanel.add(createPaymentRequestButton());
    buttonPanel.add(Box.createVerticalStrut(5));
    buttonPanel.add(viewButton);
    buttonPanel.add(Box.createVerticalStrut(5));
    buttonPanel.add(deleteButton);
    buttonPanel.add(Box.createVerticalStrut(5));
    buttonPanel.add(saveButton);
    buttonPanel.add(Box.createVerticalStrut(5));
    buttonPanel.add(loadButton);
    buttonPanel.add(Box.createVerticalStrut(35));
    buttonPanel.add(exitButton);
  }

  private JButton createInvoiceButton() {
    JButton createInvoiceButton = createButton("Накладная");
    createInvoiceButton.addActionListener(e -> createInvoice());
    return createInvoiceButton;
  }

  private JButton createPaymentOrderButton() {
    JButton createPaymentOrderButton = createButton("Платёжка");
    createPaymentOrderButton.addActionListener(e -> createPaymentOrder());
    return createPaymentOrderButton;
  }

  private JButton createPaymentRequestButton() {
    JButton createPaymentRequestButton = createButton("Заявка на оплату");
    createPaymentRequestButton.addActionListener(e -> createPaymentRequest());
    return createPaymentRequestButton;
  }


  private JButton createButton(String text) {
    JButton button = new JButton(text);
    button.setAlignmentX(Component.CENTER_ALIGNMENT);
    button.setHorizontalAlignment(SwingConstants.CENTER);
    button.setMaximumSize(new Dimension(150, 30));
    button.setPreferredSize(new Dimension(150, 30));
    return button;
  }

  private void createInvoice() {
    InvoiceDialog dialog = new InvoiceDialog(this);
    dialog.setVisible(true);
    if (dialog.isConfirmed()) {
      documentListModel.addElement(dialog.getInvoice());
    }
  }

  private void createPaymentOrder() {
    PaymentOrderDialog dialog = new PaymentOrderDialog(this);
    dialog.setVisible(true);
    if (dialog.isConfirmed()) {
      documentListModel.addElement(dialog.getPaymentOrder());
    }
  }

  private void createPaymentRequest() {
    PaymentRequestDialog dialog = new PaymentRequestDialog(this);
    dialog.setVisible(true);
    if (dialog.isConfirmed()) {
      documentListModel.addElement(dialog.getPaymentRequest());
    }
  }

  private void viewDocument() {
    BaseDocument selectedDocument = documentList.getSelectedValue();
    if (selectedDocument != null) {
      new DocumentViewDialog(this, selectedDocument).setVisible(true);
    } else {
      JOptionPane.showMessageDialog(this, "Выберите документ!", ERROR_MESSAGE,
          JOptionPane.WARNING_MESSAGE);
    }
  }

  private void deleteSelectedDocuments() {
    Set<Integer> checkedIndices = CheckboxListCellRenderer.getCheckedIndices();
    if (checkedIndices.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Выберите документы для удаления!", ERROR_MESSAGE,
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    List<Integer> sortedIndices = new ArrayList<>(checkedIndices);
    sortedIndices.sort(Collections.reverseOrder());

    for (int index : sortedIndices) {
      documentListModel.remove(index);
    }

    checkedIndices.clear();
    documentList.repaint();
  }
}
