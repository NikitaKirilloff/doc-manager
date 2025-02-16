package com.kirilloff.docmanager.domain;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Invoice extends BaseDocument {

  private String currency;

  private double exchangeRate;

  private String product;

  private double quantity;

  @Override
  public String toString() {
    return "Накладная от " + getDate() + " номер " + getNumber();
  }

  @Override
  public String toFileFormat() {
    return String.format(Locale.US, "Invoice;%s;%s;%s;%.2f;%s",
        getNumber(),
        getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
        getUser(),
        getAmount(),
        getCurrency()
    );
  }

  @Override
  public String toReadableFormat() {
    return "Номер: " + getNumber() + System.lineSeparator() +
        "Дата: " + getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        + System.lineSeparator() +
        "Пользователь: " + getUser() + System.lineSeparator() +
        "Сумма: " + getAmount() + System.lineSeparator() +
        "Валюта: " + getCurrency() + System.lineSeparator() +
        "Курс: " + getExchangeRate() + System.lineSeparator() +
        "Товар: " + getProduct() + System.lineSeparator() +
        "Количество: " + getQuantity();
  }
}
