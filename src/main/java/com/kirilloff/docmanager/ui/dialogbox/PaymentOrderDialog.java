package com.kirilloff.docmanager.ui.dialogbox;

import static com.kirilloff.docmanager.util.Constants.CANCEL_MESSAGE;
import static com.kirilloff.docmanager.util.Constants.DATE_FORMATTER;
import static com.kirilloff.docmanager.util.Constants.ERROR_MESSAGE;

import com.kirilloff.docmanager.domain.PaymentOrder;
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

public class PaymentOrderDialog extends JDialog {

  private final JTextField numberField;
  private final JTextField userField;
  private final JTextField amountField;
  private final JTextField employeeField;

  private JFormattedTextField dateField;

  @Getter
  private boolean confirmed = false;

  public PaymentOrderDialog(Frame parent) {
    super(parent, "Создание платёжки", true);
    setLayout(new GridLayout(6, 2, 5, 5));

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

    add(new JLabel("Сотрудник:"));
    employeeField = new JTextField();
    add(employeeField);

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

  public PaymentOrder getPaymentOrder() {
    try {
      return PaymentOrder.builder()
          .number(numberField.getText())
          .date(LocalDate.parse(dateField.getText(), DATE_FORMATTER))
          .user(userField.getText())
          .amount(Double.parseDouble(amountField.getText()))
          .employee(employeeField.getText())
          .build();
    } catch (DateTimeParseException e) {
      JOptionPane.showMessageDialog(this, "Неверный формат даты!", ERROR_MESSAGE,
          JOptionPane.ERROR_MESSAGE);
      return null;
    }
  }
}