package com.kirilloff.docmanager.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.kirilloff.docmanager.domain.BaseDocument;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import org.junit.jupiter.api.Test;

class DocServiceTest {

  @Test
  void testSaveDocumentToFile_Failure() throws Exception {
    JFileChooser fileChooserMock = mock(JFileChooser.class);
    File selectedFileMock = mock(File.class);

    when(fileChooserMock.showSaveDialog(any())).thenReturn(JFileChooser.APPROVE_OPTION);
    when(fileChooserMock.getSelectedFile()).thenReturn(selectedFileMock);
    when(selectedFileMock.getName()).thenReturn("document");

    BufferedWriter writerMock = mock(BufferedWriter.class);
    doThrow(new IOException("Ошибка записи")).when(writerMock).write(anyString());

    BaseDocument documentMock = mock(BaseDocument.class);
    when(documentMock.toFileFormat()).thenReturn("document content");
  }

  @Test
  void testLoadDocumentFromFile_Failure() throws Exception {
    JFileChooser fileChooserMock = mock(JFileChooser.class);
    File selectedFileMock = mock(File.class);

    when(fileChooserMock.showOpenDialog(any())).thenReturn(JFileChooser.APPROVE_OPTION);
    when(fileChooserMock.getSelectedFile()).thenReturn(selectedFileMock);
    when(selectedFileMock.getName()).thenReturn("document.txt");

    BufferedReader readerMock = mock(BufferedReader.class);
    when(readerMock.readLine()).thenThrow(new IOException("Ошибка чтения"));
  }
}