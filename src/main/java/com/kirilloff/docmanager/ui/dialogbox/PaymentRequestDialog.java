package com.kirilloff.docmanager.ui.dialogbox;

import static com.kirilloff.docmanager.util.Constants.CANCEL_MESSAGE;
import static com.kirilloff.docmanager.util.Constants.DATE_FORMATTER;
import static com.kirilloff.docmanager.util.Constants.ERROR_MESSAGE;

import com.kirilloff.docmanager.domain.PaymentRequest;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import lombok.Getter;

public class PaymentRequestDialog extends JDialog {

  private final JTextField numberField;
  private final JTextField userField;
  private final JTextField counterpartyField;
  private final JTextField amountField;
  private final JTextField currencyField;
  private final JTextField exchangeRateField;
  private final JTextField commissionField;

  private JFormattedTextField dateField;

  @Getter
  private boolean confirmed = false;

  public PaymentRequestDialog(Frame parent) {
    super(parent, "Создание заявки на оплату", true);
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    int row = 0;

    addLabel("Номер:", row);
    numberField = new JTextField(15);
    addField(numberField, row++);

    addLabel("Дата (ДД.ММ.ГГГГ):", row);
    try {
      MaskFormatter dateMask = new MaskFormatter("##.##.####");
      dateMask.setPlaceholderCharacter('_');
      dateField = new JFormattedTextField(dateMask);
    } catch (ParseException e) {
      dateField = new JFormattedTextField();
    }
    addField(dateField, row++);

    addLabel("Пользователь:", row);
    userField = new JTextField(15);
    addField(userField, row++);

    addLabel("Контрагент:", row);
    counterpartyField = new JTextField(15);
    addField(counterpartyField, row++);

    addLabel("Сумма:", row);
    amountField = new JTextField(15);
    addField(amountField, row++);

    addLabel("Валюта:", row);
    currencyField = new JTextField(15);
    addField(currencyField, row++);

    addLabel("Курс валюты:", row);
    exchangeRateField = new JTextField(15);
    addField(exchangeRateField, row++);

    addLabel("Комиссия:", row);
    commissionField = new JTextField(15);
    addField(commissionField, row++);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton okButton = new JButton("OK");
    JButton cancelButton = new JButton(CANCEL_MESSAGE);

    okButton.addActionListener(e -> {
      confirmed = true;
      dispose();
    });

    cancelButton.addActionListener(e -> dispose());

    buttonPanel.add(okButton);
    buttonPanel.add(cancelButton);

    gbc.gridx = 0;
    gbc.gridy = row;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.EAST;
    add(buttonPanel, gbc);

    pack();
    setLocationRelativeTo(parent);
  }

  private void addLabel(String text, int row) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = row;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.insets = new Insets(5, 5, 5, 5);
    add(new JLabel(text), gbc);
  }

  private void addField(JComponent field, int row) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = row;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);
    add(field, gbc);
  }

  public PaymentRequest getPaymentRequest() {
    try {
      return PaymentRequest.builder()
          .number(numberField.getText())
          .date(LocalDate.parse(dateField.getText(), DATE_FORMATTER))
          .user(userField.getText())
          .counterparty(counterpartyField.getText())
          .amount(Double.parseDouble(amountField.getText()))
          .currency(currencyField.getText())
          .exchangeRate(Double.parseDouble(exchangeRateField.getText()))
          .commission(Double.parseDouble(commissionField.getText()))
          .build();
    } catch (DateTimeParseException e) {
      JOptionPane.showMessageDialog(this, "Неверный формат даты!", ERROR_MESSAGE,
          JOptionPane.ERROR_MESSAGE);
      return null;
    }
  }
}
