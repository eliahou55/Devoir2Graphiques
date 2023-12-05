package sio.devoir2graphiques;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sio.devoir2graphiques.Tools.ConnexionBDD;
import sio.devoir2graphiques.Tools.GraphiqueController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Devoir2GraphiquesController implements Initializable
{

    ConnexionBDD maCnx;
    GraphiqueController graphiqueController;
    XYChart.Series<String, Integer> serieGraph1;
    XYChart.Series<String, Integer> serieGraph2;
    @FXML
    private Button btnGraph1;
    @FXML
    private Button btnGraph2;
    @FXML
    private Button btnGraph3;
    @FXML
    private AnchorPane apGraph1;
    @FXML
    private LineChart graph1;
    @FXML
    private Label lblTitre;
    @FXML
    private AnchorPane apGraph2;
    @FXML
    private AnchorPane apGraph3;
    @FXML
    private PieChart graph3;
    @FXML
    private BarChart graph2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblTitre.setText("Devoir : Graphique n°1");
        apGraph1.toFront();

        try {
            maCnx = new ConnexionBDD();
            graphiqueController = new GraphiqueController();
            remplirGraph1();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void menuClicked(Event event) throws SQLException {
        if(event.getSource() == btnGraph1)
        {
            lblTitre.setText("Devoir : Graphique n°1");
            apGraph1.toFront();

            remplirGraph1();

        }
        else if(event.getSource() == btnGraph2)
        {
            lblTitre.setText("Devoir : Graphique n°2");
            apGraph2.toFront();
            graph2.getData().clear();
            HashMap<String, ArrayList<String>> dataGraph3 = graphiqueController.getDataGraph2();
            serieGraph2 = new XYChart.Series<>();

            for (String unNom : dataGraph3.keySet()) {
                serieGraph2.setName(unNom);
                for (int i = 0; i < dataGraph3.get(unNom).size(); i += 2) {

                    serieGraph2.getData().add(new XYChart.Data<>(dataGraph3.get(unNom).get(i).toString(),
                            Integer.parseInt(dataGraph3.get(unNom).get(i + 1))));

                }
                graph2.getData().add(serieGraph2);
                serieGraph2 = new XYChart.Series<>();
            }







                  // ///////////////////////////////////////////////////////////////////


           /* HashMap<Integer, ArrayList<String>> dataGraph2 = graphiqueController.getDataGraph2();
            serieGraph2 = new XYChart.Series<>();

            for (Integer unAge : dataGraph2.keySet()) {
                serieGraph2.setName(unAge);
                for (int i = 0; i < dataGraph3.get(unAge).size(); i += 2) {

                    serieGraph2.getData().add(new XYChart.Data<>(dataGraph2.get(unAge).get(i).
                            Integer.parseInt(dataGraph2.get(unAge).get(i + 1))));

                }
                graph2.getData().add(serieGraph2);
                serieGraph2 = new XYChart.Series<>();
            }*/
        }
        else
        {
            lblTitre.setText("Devoir : Graphique n°3");
            apGraph3.toFront();
            HashMap<String, Integer> dataGraph3 = graphiqueController.getDataGraph3();
            ObservableList lst = FXCollections.observableArrayList();

            for (String unPourc : dataGraph3.keySet()) {
                lst.add(new PieChart.Data(unPourc, dataGraph3.get(unPourc)));
            }
            graph3.setData(lst);


        }
    }

    private void remplirGraph1() {
        graph1.getData().clear();
        HashMap<String, Integer> dataGraph = graphiqueController.getDataGraph1();
        serieGraph1 = new XYChart.Series<>();

        for (String Age : dataGraph.keySet()) {
            serieGraph1.getData().add(new XYChart.Data<>(Age, dataGraph.get(Age)));
        }
        graph1.getData().add(serieGraph1);
        serieGraph1.setName("Moyenne");
    }
}