package com.kirilloff.docmanager.domain;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class PaymentOrder extends BaseDocument {

  private String employee;

  @Override
  public String toString() {
    return "Платёжка от " + getDate() + " номер " + getNumber();
  }

  @Override
  public String toFileFormat() {
    return String.format(Locale.US, "PaymentOrder;%s;%s;%s;%.2f;%s",
        getNumber(),
        getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
        getUser(),
        getAmount(),
        getEmployee()
    );
  }
  @Override
  public String toReadableFormat() {
    return "Номер: " + getNumber() + System.lineSeparator() +
        "Дата: " + getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        + System.lineSeparator() +
        "Пользователь: " + getUser() + System.lineSeparator() +
        "Сумма: " + getAmount() + System.lineSeparator() +
        "Сотрудник: " + getEmployee();
  }
}
