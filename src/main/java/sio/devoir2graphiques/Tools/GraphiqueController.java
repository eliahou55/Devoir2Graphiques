package sio.devoir2graphiques.Tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphiqueController
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public GraphiqueController() {
        cnx = ConnexionBDD.getCnx();
    }
    public HashMap<String, Integer> getDataGraph1() {
        HashMap<String, Integer> lesSalaire = new HashMap<>();

        try {
            ps = cnx.prepareStatement("SELECT ageEmp, AVG(salaireEmp) AS salaire_moyen\n" +
                    "FROM employe\n" +
                    "GROUP BY ageEmp\n" +
                    "ORDER BY ageEmp;");
            rs = ps.executeQuery();
            //on stock le resultSet dans la HashMap
            while (rs.next()) {
                lesSalaire.put(rs.getString(1), rs.getInt(2));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lesSalaire;
    }

    public HashMap<String, ArrayList<String>> getDataGraph2(){
        HashMap<String,ArrayList<String>> datag2 = new HashMap<>();

        try {
            ps = cnx.prepareStatement("SELECT FLOOR(ageEmp/10) * 10 AS trancheAge, sexe, COUNT(*) AS nombre\n" +
                    "FROM employe\n" +
                    "GROUP BY tranche_age, sexe\n" +
                    "ORDER BY tranche_age, sexe;");


            // ou la requette avec le UNION mais qui marchai pas ; SELECT ageEmp, sexe, COUNT(*) AS nombre
            //FROM employe
            //GROUP BY ageEmp, sexe
            //UNION
            //SELECT ageEmp, sexe, 0 AS nombre
            //FROM employe
            //WHERE (ageEmp, sexe) NOT IN (
            //    SELECT ageEmp, sexe
            //    FROM employe
            //    GROUP BY ageEmp, sexe
            //)
            //ORDER BY ageEmp, sexe;


            rs = ps.executeQuery();

            while (rs.next()) {
                if (!datag2.containsKey(  rs.getString(1))){
                    ArrayList<String>lesAgesM = new ArrayList<>();
                  lesAgesM.add(rs.getString(2));
                  lesAgesM.add(rs.getString(3));
                    datag2.put(rs.getString(1),lesAgesM);
                }
                else {
                    datag2.get(rs.getString(1)).add(rs.getString(2));
                    datag2.get(rs.getString(1)).add(rs.getString(3));
                }
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return datag2;
    }
   // int rsultAge = 1;


    public HashMap<String, Integer> getDataGraph3() {
        HashMap<String, Integer> lePoucentage= new HashMap<>();
        //récupération du pourcentage
        try {
            ps = cnx.prepareStatement("SELECT sexe, COUNT(*) AS nombre\n" +
                    "FROM employe\n" +
                    "GROUP BY sexe;");
            rs = ps.executeQuery();
            //on stock le resultSet dans la HashMap
            while (rs.next()) {
                lePoucentage.put(rs.getString(1), rs.getInt(2));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lePoucentage;
    }
}
