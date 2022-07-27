package gui;

import model.Parquet;
import model.ServerConnection;
import model.WoodKind;
import org.hibernate.query.Query;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    private JPanel panel1;
    private JButton potwierdzButton;
    private JButton showParquetsButton;
    private JList basketJlist;
    private JButton usunButton;
    private JPanel content;
    private JPanel basketContent;
    private JTabbedPane tabbedPane1;
    private JTextPane tutajBędzieListaZamówieńTextPane;
    private JTextPane tutajBędąUstawieniaTextPane;
    private JTextField searchText;
    private JButton searchButton;
    private JTextPane dataPane;
    private JTextField metersTextField;
    private JButton zmodyfikujButton;
    private JButton dodajButton;
    private JList parquetList;
    private JComboBox comboOfWoodKinds;
    private JLabel fullPrice;
    private JTextField addValue;
    private JPanel placeForPar;
    private JFrame frame;
    DefaultListModel<String> listModel;
    DefaultListModel<String> basketJlistModel;
    private App thisClass;

    private JPanel listPanel;
    List<Parquet> parquets;
    List<WoodKind> woodKinds;
    List<BasketElement> basket = new ArrayList<>();

    public App(JFrame frame) {
        frame.setTitle("Sklep");
        tabbedPane1.setSelectedIndex(1);
        thisClass = this;
        this.frame = frame;


        basketJlistModel = new DefaultListModel<>();
        basketJlist.setModel(basketJlistModel);

        listModel = new DefaultListModel<>();
        parquetList.setModel(listModel);


        potwierdzButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Potwierdzenie.show(basket, frame);
            }
        });

        dodajButton.addActionListener(new AddActionListener());


        var listSelectionModel = parquetList.getSelectionModel();
        listSelectionModel.addListSelectionListener(
                new ListSelectionHandler());

        zmodyfikujButton.addActionListener(new EditActionListener());
        usunButton.addActionListener(new DeleteActionListener());
        searchButton.addActionListener(new SearchActionListener());

        listParquets(null, null);
        loadWood();
    }

    private void loadWood() {
        var query = ServerConnection.getInstance().session.createQuery("from WoodKind ");
        woodKinds = query.list();
        ComboBoxModel model = new DefaultComboBoxModel<>();

        comboOfWoodKinds.setModel(model);
        comboOfWoodKinds.addItem("dowolny");
        for (WoodKind woodKind : woodKinds) {
            comboOfWoodKinds.addItem(woodKind.getName());
        }

    }

    public void listParquets(String search, WoodKind woodKind){

        listModel.removeAllElements();

        if(woodKind == null && (search == null || search.equals(""))) {
            var query = ServerConnection.getInstance().session.createQuery("from Parquet ");
            executeQueryToList(query);
        }else if(woodKind == null){
            var regex = "%"+search+"%";
            var query = ServerConnection.
                    getInstance().
                    session.
                    createQuery("from Parquet where name like :regex ");
            query.setParameter("regex",regex);
            executeQueryToList(query);
        }else{
            var regex = "%"+search+"%";
            var query = ServerConnection.getInstance().session.createQuery("from Parquet where name like :regex " +
                    "and woodKind.id = :woodId");
            query.setParameter("regex",regex);
            query.setParameter("woodId", woodKind.getId() );
            executeQueryToList(query);
        }
        parquetList.updateUI();

    }

    void executeQueryToList(Query query){
        query.setMaxResults(100);
        parquets = query.list();
        for (Parquet parquet : parquets) {
            listModel.addElement(parquet.getName());
        }
    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App(frame).panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.validate();
        frame.repaint();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ServerConnection.close();
                frame.dispose();
            }
        });
        frame.setVisible(true);
        frame.setSize(900,400);
    }




    private class DeleteActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            var index = basketJlist.getSelectedIndex();
            if (index != -1) {
                basket.remove(index);
                basketJlistModel.remove(index);
                updateBasketList();
            }

        }
    }

    private class SearchActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            updateList();

        }
    }

    void updateList(){
        WoodKind searchedWood = comboOfWoodKinds.getSelectedIndex() < 1?
                null :
                woodKinds.get(comboOfWoodKinds.getSelectedIndex()-1);
        listParquets(searchText.getText(), searchedWood);
    }


    private class EditActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

                if (basket.size() != 0) {
                    JOptionPane.showMessageDialog(null, "By modyfikować parkiety koszyk najpierw musi być pusty");
                    return;
                }

                int selectedIndex = parquetList.getSelectedIndex();
                if(selectedIndex == -1) return;
                long selectedParquetId = parquets.get(selectedIndex).getId();
                Edit.show(selectedParquetId, frame, thisClass);
                updateBasketList();

        }
    }

    public void setFullPrice(int price){

        fullPrice.setText("       Koszt całkowuty: "+ price+ " zł");
    }

    private class AddActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = parquetList.getSelectedIndex();
            if(selectedIndex == -1) return;
            if(metersTextField.getText().equals("")){
                JOptionPane.showMessageDialog(null, "podano nieprawidłową ilość parkietów" );
                return;
            }
            Parquet addedParquet = parquets.get(selectedIndex);

            Double meters = Double.parseDouble(metersTextField.getText());

            basket.add(new BasketElement(addedParquet,meters));

            updateBasketList();

        }
    }

    public void updateBasketList(){

        basketJlistModel.clear();
        int fullPrice = 0;
        for(int i = 0 ; i < basket.size(); i++){
            Parquet parquet = basket.get(i).parquet;
            double size = basket.get(i).quantity;
            int price = (int)Math.ceil( parquet.getPriceM2()*size);
            fullPrice += price;

            String s = ""+ parquet.getName() + "  "+ "  ilość: "+ size + " m2"+"    cena: "+Math.ceil( parquet.getPriceM2()*size)+" zł";
            basketJlistModel.addElement(s);


        }
        setFullPrice(fullPrice);

        basketJlist.updateUI();


    }

    private class ListSelectionHandler implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();
            int selected = -1;
            for (int i = 0; i <= lsm.getMaxSelectionIndex(); i++) {
                if (lsm.isSelectedIndex(i)) {
                    selected = i;
                    break;
                }
            }

            String data = generateData(selected);
            dataPane.setText(data);

        }
    }

    public String generateData(int index) {
        if (index == -1) {
            return "";
        }

        if (parquets == null) {
            return "error";
        }

        Parquet parquet = parquets.get(index);

        String s = "Nazwa: ";
        s += parquet.getName();
        s += System.lineSeparator();

        s += "Rodzaj: ";
        s += parquet.getType();
        s += System.lineSeparator();

        s += "Cena: ";
        s += parquet.getPriceM2();
        s += System.lineSeparator();

        s += "Gatunek drewna: ";
        s += parquet.getWoodKind().getName();
        s += System.lineSeparator();

        return s;
    }

}
