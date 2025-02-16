package com.kirilloff.docmanager.domain;

import static com.kirilloff.docmanager.util.Constants.DATE_FORMATTER;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class BaseDocument {

  private String number;

  private LocalDate date;

  private String user;

  private double amount;

  public abstract String toFileFormat();

  public abstract String toReadableFormat();

  public static BaseDocument fromFileFormat(String data) {
    String[] parts = data.split(";");
    if (parts.length < 2) {
      throw new IllegalArgumentException("Некорректный формат файла");
    }

    String type = parts[0];
    return switch (type) {
      case "Invoice" -> Invoice.builder()
          .number(parts[1])
          .date(LocalDate.parse(parts[2], DATE_FORMATTER))
          .user(parts[3])
          .amount(Double.parseDouble(parts[4]))
          .currency(parts[5]).build();
      case "PaymentOrder" -> PaymentOrder.builder()
          .number(parts[1])
          .date(LocalDate.parse(parts[2], DATE_FORMATTER))
          .user(parts[3])
          .amount(Double.parseDouble(parts[4]))
          .employee(parts[5]).build();
      case "PaymentRequest" -> PaymentRequest.builder()
          .number(parts[1])
          .date(LocalDate.parse(parts[2], DATE_FORMATTER))
          .counterparty(parts[3])
          .amount(Double.parseDouble(parts[4]))
          .currency(parts[5])
          .exchangeRate(Double.parseDouble(parts[6]))
          .commission(Double.parseDouble(parts[7])).build();
      default -> throw new IllegalArgumentException("Неизвестный тип документа: " + type);
    };
  }
}

