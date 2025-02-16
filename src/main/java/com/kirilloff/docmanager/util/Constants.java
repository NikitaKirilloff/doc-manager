package com.kirilloff.docmanager.util;

import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
  public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
  public static final String ERROR_MESSAGE = "Ошибка";
  public static final String CANCEL_MESSAGE = "Отмена";
}
