package com.kirilloff.docmanager.service;

import static com.kirilloff.docmanager.util.Constants.ERROR_MESSAGE;

import com.kirilloff.docmanager.domain.BaseDocument;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class DocService {
  public static void saveDocumentToFile(BaseDocument document) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Сохранить документ");
    fileChooser.setFileFilter(new FileNameExtensionFilter("Текстовые файлы (*.txt)", "txt"));

    int userSelection = fileChooser.showSaveDialog(null);
    if (userSelection == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      if (!file.getName().endsWith(".txt")) {
        file = new File(file.getAbsolutePath() + ".txt");
      }

      try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        writer.write(document.toFileFormat());
        writer.newLine();
        JOptionPane.showMessageDialog(null, "Документ сохранён", "Успех",
            JOptionPane.INFORMATION_MESSAGE);
      } catch (IOException e) {
        JOptionPane.showMessageDialog(null, ERROR_MESSAGE + " при сохранении", ERROR_MESSAGE,
            JOptionPane.ERROR_MESSAGE);
        log.error(e.getMessage());
      }
    }
  }

  public static void loadDocumentFromFile(DefaultListModel<BaseDocument> documentListModel) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Загрузить документ");
    fileChooser.setFileFilter(new FileNameExtensionFilter("Текстовые файлы (*.txt)", "txt"));

    int userSelection = fileChooser.showOpenDialog(null);
    if (userSelection == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();

      try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line = reader.readLine();
        if (line != null) {
          BaseDocument document = BaseDocument.fromFileFormat(line);
          documentListModel.addElement(document);
          JOptionPane.showMessageDialog(null, "Документ загружен", "Успех",
              JOptionPane.INFORMATION_MESSAGE);
        } else {
          JOptionPane.showMessageDialog(null, "Файл пуст", ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
        }
      } catch (IOException | IllegalArgumentException e) {
        JOptionPane.showMessageDialog(null, ERROR_MESSAGE + " при загрузке", ERROR_MESSAGE,
            JOptionPane.ERROR_MESSAGE);
        log.error(e.getMessage());
      }
    }
  }
}
