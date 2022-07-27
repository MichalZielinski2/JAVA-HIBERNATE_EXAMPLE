package gui;

import model.Customer;
import model.OrderN;
import model.ServerConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.List;

public class Potwierdzenie extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel sumZl;
    private JComboBox deliverySelection;
    private JList SumUpList;
    private int sum = 0;

    public Potwierdzenie(List<BasketElement> basketElement, Frame frame) {
        super(frame);
        this.setTitle("Potwierdzenie");
        this.setSize(200,200);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        var listModel = new DefaultListModel<String>();
        SumUpList.setModel(listModel);
        for (BasketElement element : basketElement) {
            var parquet = element.parquet;
            var quantity = element.quantity;
            var price = (int)Math.ceil( quantity* parquet.getPriceM2());
            listModel.addElement(parquet.getName()+"    ilość: "+quantity+ "m²   cena: " + price + " zł");
            sum+=price;
        }



        sumZl.setText("Suma: " + sum + "zł");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //String deliveryMethod = (String) deliverySelection.getSelectedItem();

                OrderN order;
                if (deliverySelection.getSelectedIndex()== 0) {
                    order = new OrderN(LocalDate.now().plusWeeks(3), null, sum, OrderN.OrderState.UNDER_EXECUTION, OrderN.ReceivementMethod.IN_PERSON);
                } else {
                    order = new OrderN(LocalDate.now().plusWeeks(3), null, sum, OrderN.OrderState.UNDER_EXECUTION, OrderN.ReceivementMethod.DELIVERY);
                }

                Customer customer = (Customer) ServerConnection.getInstance().session.createQuery("from Customer").list().get(0);

                order.setCustomer(customer);

                for (BasketElement element : basketElement) {
                    try {
                        order.addParquet(element.parquet,element.quantity);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }

                if (order.checkCorrectness()) {
                    order.save();
                } else {
                    JOptionPane.showMessageDialog(null, "Niepoprawny koszyk", "Alert",
                            JOptionPane.WARNING_MESSAGE);
                }


                dispose();


            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }


    public static void show(List<BasketElement> basketElement, Frame frame) {
        Potwierdzenie dialog = new Potwierdzenie(basketElement, frame);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

}
