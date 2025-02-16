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
public class PaymentRequest extends BaseDocument {

  private String counterparty;

  private String currency;

  private double exchangeRate;

  private double commission;

  @Override
  public String toString() {
    return "Заявка на оплату от " + getDate() + " номер " + getNumber();
  }

  @Override
  public String toFileFormat() {
    return String.format(Locale.US, "PaymentRequest;%s;%s;%s;%.2f;%s;%.2f;%.2f",
        getNumber(),
        getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
        getCounterparty(),
        getAmount(),
        getCurrency(),
        getExchangeRate(),
        getCommission()
    );
  }

  @Override
  public String toReadableFormat() {
    return "Номер: " + getNumber() + System.lineSeparator() +
        "Дата: " + getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        + System.lineSeparator() +
        "Пользователь: " + getUser() + System.lineSeparator() +
        "Контрагент: " + getCounterparty() + System.lineSeparator() +
        "Сумма: " + getAmount() + System.lineSeparator() +
        "Валюта: " + getCurrency() + System.lineSeparator() +
        "Курс: " + getExchangeRate() + System.lineSeparator() +
        "Комиссия: " + getCommission();
  }
}
