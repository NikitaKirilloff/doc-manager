package com.kirilloff.docmanager.ui.dialogbox;

import static com.kirilloff.docmanager.util.Constants.CANCEL_MESSAGE;
import static com.kirilloff.docmanager.util.Constants.DATE_FORMATTER;
import static com.kirilloff.docmanager.util.Constants.ERROR_MESSAGE;

import com.kirilloff.docmanager.domain.Invoice;
import java.awt.Frame;
import java.awt.GridLayout;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import lombok.Getter;

public class InvoiceDialog extends JDialog {

  private final JTextField numberField;
  private final JTextField userField;
  private final JTextField amountField;
  private final JTextField currencyField;
  private final JTextField exchangeRateField;
  private final JTextField productField;
  private final JTextField quantityField;

  private JFormattedTextField dateField;

  @Getter
  private boolean confirmed = false;

  public InvoiceDialog(Frame parent) {
    super(parent, "Создание накладной", true);
    setLayout(new GridLayout(9, 2, 5, 5));

    add(new JLabel("Номер:"));
    numberField = new JTextField();
    add(numberField);

    add(new JLabel("Дата (ДД.ММ.ГГГГ):"));
    try {
      MaskFormatter dateMask = new MaskFormatter("##.##.####");
      dateMask.setPlaceholderCharacter('_');
      dateField = new JFormattedTextField(dateMask);
    } catch (ParseException e) {
      dateField = new JFormattedTextField();
    }
    add(dateField);

    add(new JLabel("Пользователь:"));
    userField = new JTextField();
    add(userField);

    add(new JLabel("Сумма:"));
    amountField = new JTextField();
    add(amountField);

    add(new JLabel("Валюта:"));
    currencyField = new JTextField();
    add(currencyField);

    add(new JLabel("Курс валюты:"));
    exchangeRateField = new JTextField();
    add(exchangeRateField);

    add(new JLabel("Товар:"));
    productField = new JTextField();
    add(productField);

    add(new JLabel("Количество:"));
    quantityField = new JTextField();
    add(quantityField);

    JButton okButton = new JButton("OK");
    JButton cancelButton = new JButton(CANCEL_MESSAGE);

    okButton.addActionListener(e -> {
      confirmed = true;
      dispose();
    });

    cancelButton.addActionListener(e -> dispose());

    add(okButton);
    add(cancelButton);

    pack();
    setLocationRelativeTo(parent);
  }

  public Invoice getInvoice() {
    try {
      return Invoice.builder()
          .number(numberField.getText())
          .date(LocalDate.parse(dateField.getText(), DATE_FORMATTER))
          .user(userField.getText())
          .amount(Double.parseDouble(amountField.getText()))
          .currency(currencyField.getText())
          .exchangeRate(Double.parseDouble(exchangeRateField.getText()))
          .product(productField.getText())
          .quantity(Double.parseDouble(quantityField.getText()))
          .build();
    } catch (DateTimeParseException e) {
      JOptionPane.showMessageDialog(this, "Неверный формат даты!", ERROR_MESSAGE,
          JOptionPane.ERROR_MESSAGE);
      return null;
    }
  }
}