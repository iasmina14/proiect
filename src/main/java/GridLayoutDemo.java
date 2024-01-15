import javax.swing.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;


public class GridLayoutDemo extends JFrame {
    GridLayoutDemo() {
        setResizable(true);
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(7, 2));

        JLabel nameLabel, codeLabel, designationLabel, salaryLabel;
        JTextField nameTextField, salaryTextField, codeTextField, designationTextField;
        JButton buttonSave, buttonExit;
        JCheckBox manCheckBox, womanCheckBox;
        
        nameLabel = new JLabel("NAME");
        nameTextField = new JTextField(20);
        
        codeLabel = new JLabel("CODE");
        codeTextField = new JTextField(20);
        
        designationLabel = new JLabel("DESIGNATION");
        designationTextField = new JTextField(20);
        
        salaryLabel = new JLabel("SALARY");
        salaryTextField = new JTextField(20);
        
        buttonSave = new JButton("SAVE");
        buttonExit = new JButton("EXIT");
        
        womanCheckBox = new JCheckBox("Femeie");
        manCheckBox = new JCheckBox("Barbat");

        p1.add(nameLabel);
        p1.add(nameTextField);
        p1.add(codeLabel);
        p1.add(codeTextField);
        p1.add(designationLabel);
        p1.add(designationTextField);
        p1.add(salaryLabel);
        p1.add(salaryTextField);
        p1.add(womanCheckBox);
        p1.add(manCheckBox);

        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        p2.add(buttonSave);
        p2.add(buttonExit);

        add(p1, BorderLayout.NORTH);
        add(p2, BorderLayout.SOUTH);

        setVisible(true);
        this.setSize(500, 400);

        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 saveDataToJson(
                         nameTextField,
                         codeTextField,
                         designationTextField,
                         salaryTextField,
                         womanCheckBox,
                         manCheckBox
                     );
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void saveDataToJson(JTextField nameTextField,
                                JTextField codeTextField,
                                JTextField designationTextField,
                                JTextField salaryTextField,
                                JCheckBox womanCheckBox,
                                JCheckBox manCheckBox) {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray;

            jsonObject.put("nume", nameTextField.getText());
            jsonObject.put("cod", codeTextField.getText());
            jsonObject.put("designatie", designationTextField.getText());
            jsonObject.put("salariu", salaryTextField.getText());
            jsonObject.put("genFemeie", womanCheckBox.isSelected());
            jsonObject.put("genBarbat", manCheckBox.isSelected());

            File file = new File("date.json");

            if (file.exists()) {
       
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(new FileReader(file));

                if (obj instanceof JSONArray) {
                    jsonArray = (JSONArray) obj;
                } else if (obj instanceof JSONObject) {
                    JSONObject existingJsonObject = (JSONObject) obj;
                    jsonArray = (JSONArray) existingJsonObject.get("data");
                } else {
                    throw new ClassCastException("Unexpected JSON structure. Expected JSONObject or JSONArray.");
                }

           
                jsonArray.add(jsonObject);
            } else {
                jsonArray = new JSONArray();
                jsonArray.add(jsonObject);
            }

      
            JSONObject updatedJsonObject = new JSONObject();
            updatedJsonObject.put("data", jsonArray);

  
            FileWriter fw = new FileWriter("date.json", false);
            fw.write(updatedJsonObject.toJSONString());
            fw.close();

            JOptionPane.showMessageDialog(null, "Datele au fost salvate cu succes în date.json.");
            
        } catch (Exception ex) {
        	
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "A apărut o eroare la salvarea datelor.");
        }
    }

}


