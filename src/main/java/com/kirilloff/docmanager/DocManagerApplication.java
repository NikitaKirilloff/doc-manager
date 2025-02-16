package com.kirilloff.docmanager;

import com.kirilloff.docmanager.ui.MainFrame;
import javax.swing.SwingUtilities;

public class DocManagerApplication {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      MainFrame mainFrame = new MainFrame();
      mainFrame.setVisible(true);
    });
  }
}
