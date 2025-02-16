package com.kirilloff.docmanager.ui;

import com.kirilloff.docmanager.domain.BaseDocument;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import lombok.Getter;

public class CheckboxListCellRenderer extends JPanel implements ListCellRenderer<BaseDocument> {

  private final JCheckBox checkBox;

  private final JLabel label;

  @Getter
  private static final Set<Integer> checkedIndices = new HashSet<>();

  public CheckboxListCellRenderer() {
    setLayout(new BorderLayout());
    checkBox = new JCheckBox();
    label = new JLabel();

    add(label, BorderLayout.WEST);
    add(checkBox, BorderLayout.EAST);

    setOpaque(true);
  }

  @Override
  public Component getListCellRendererComponent(JList<? extends BaseDocument> list, BaseDocument value, int index, boolean isSelected, boolean cellHasFocus) {
    if (value == null) {
      return new Component() {
      };
    }

    label.setText(value.toString());
    checkBox.setSelected(checkedIndices.contains(index));

    if (isSelected) {
      setBackground(list.getSelectionBackground());
      label.setForeground(list.getSelectionForeground());
    } else {
      setBackground(list.getBackground());
      label.setForeground(list.getForeground());
    }

    return this;
  }

  public static void toggleCheckbox(int index) {
    if (checkedIndices.contains(index)) {
      checkedIndices.remove(index);
    } else {
      checkedIndices.add(index);
    }
  }
}