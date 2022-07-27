package gui;

import model.Parquet;
import model.ServerConnection;
import model.WoodKind;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class Edit extends JDialog {
    private JButton anulujButton;
    private JButton zapiszButton;
    private JTextField nametextField;
    private JTextField TypetextField;
    private JComboBox woodTypeComboBox;
    private JTextField priceTextField;
    private JPanel content;
    private long editedParquetId;
    private WoodKind oldWoodKind;
    Parquet editedParquet;
    List<WoodKind> allWoodsTypes;
    int oldWoodTypeIndex;
    App app;

    public Edit(long editedParquetId, Frame oldDialog,App thisClass) {
        super(oldDialog);
        this.setTitle("Modyfikacja");
        app = thisClass;
        this.editedParquetId = editedParquetId;
        setContentPane(content);

        this.editedParquet = (Parquet) ServerConnection.getInstance().session.
                createQuery("from Parquet where id = " + editedParquetId).list().get(0);

        nametextField.setText(editedParquet.getName());
        TypetextField.setText(editedParquet.getType());
        priceTextField.setText("" + editedParquet.getPriceM2());
        this.oldWoodKind = editedParquet.getWoodKind();
        allWoodsTypes = (List<WoodKind>) ServerConnection.getInstance().session.createQuery("from WoodKind").list();

        for (int i = 0; i < allWoodsTypes.size(); i++) {
            if (allWoodsTypes.get(i).getId() == oldWoodKind.getId()) {
                oldWoodTypeIndex = i;
            }
            woodTypeComboBox.addItem(allWoodsTypes.get(i).getName());
        }

        woodTypeComboBox.setSelectedIndex(oldWoodTypeIndex);

        zapiszButton.addActionListener(new SaveActionListener());
        anulujButton.addActionListener((e) -> {
            dispose();
            app.updateList();
        });
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                app.updateList();
            }
        });

        content.updateUI();

    }

    private class SaveActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                var price = Integer.parseInt(priceTextField.getText());
                editedParquet.setPriceM2(price);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
                return;
            }

            editedParquet.setType(TypetextField.getText());

            try {
                editedParquet.setName(nametextField.getText());
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "imię musi być unikalne");
                return;
            }

            var index = woodTypeComboBox.getSelectedIndex();
            allWoodsTypes.get(index);
            editedParquet.setWoodKind(allWoodsTypes.get(index));
            ServerConnection.getInstance().session.beginTransaction();
            ServerConnection.getInstance().session.update(editedParquet);
            ServerConnection.getInstance().session.getTransaction().commit();
            dispose();
            app.updateList();
        }
    }

    public static void show(long editedParquetId, Frame oldDialog, App thisClass) {
        var dialog = new Edit(editedParquetId, oldDialog, thisClass);
        dialog.pack();
        dialog.setLocationRelativeTo(oldDialog);
        dialog.setSize(800, 300);
        dialog.setVisible(true);
    }

}
