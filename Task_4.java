// imports for handling GUI components
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
// for array handling
import java.util.ArrayList;
//Analyse JSON from API.
import org.json.JSONObject;

// public class extends JFrame to enable GUI window
public class Task_4 extends JFrame {
    private JComboBox<String> baseCurr, targetCurr; // Available Currency dropdowns.
    private JTextField amount;//Text field to enter amount.
    private JLabel result;//Label to show conversion.
    private JButton convertBtn, clearBtn; // Action buttons.
    private JToggleButton darkMode;//Toggles dark mode.
    private JTextArea historyArea;//Shows list of past conversions.

    // Array list of available conversion currencies
    private final String[] currency = {"USD", "INR", "EUR", "GBP", "JPY", "CAD", "AUD"};
    private boolean isDarkMode = false;// setting darkmode intially off
    private java.util.List<String> historyList = new ArrayList<>();//Stores past conversion strings.

    //Constructor
    public Task_4() {
        //Tittle
        setTitle("------Currency Converter------");
        //Setting grid nd Layout
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 1, 10, 10));

        //Initializes all the components with default settings.
        baseCurr = new JComboBox<>(currency);
        targetCurr = new JComboBox<>(currency);
        amount = new JTextField();
        result = new JLabel("Converted Amount: ", JLabel.CENTER);
        convertBtn = new JButton("Convert");
        clearBtn = new JButton("Clear");
        darkMode = new JToggleButton("Enable Dark Mode");

        //JTextArea inside a JScrollPane shows scrollable history.
        //Border gives it a titled frame.
        historyArea = new JTextArea();
        historyArea.setEditable(false);
        JScrollPane historyScroll = new JScrollPane(historyArea);
        historyScroll.setBorder(BorderFactory.createTitledBorder("Conversion History"));

        // Add components
        add(new JLabel("Select Base Currency:", JLabel.CENTER));
        add(baseCurr);
        add(new JLabel("Select Target Currency:", JLabel.CENTER));
        add(targetCurr);
        add(new JLabel("Enter Amount:", JLabel.CENTER));
        add(amount);
        add(convertBtn);
        add(clearBtn);
        add(darkMode);
        add(result);
        add(historyScroll);

        // Action Listeners
        convertBtn.addActionListener(e -> convert());
        clearBtn.addActionListener(e -> clear());
        darkMode.addActionListener(e -> DarkMode());

        setVisible(true);
    }

    //Logic of Conversion
    private void convert() {
        try {
            String base = baseCurr.getSelectedItem().toString();
            String target = targetCurr.getSelectedItem().toString();
            double amt = Double.parseDouble(amount.getText());

            //Builds API URL and fetches response as a JSON string.
            String apiUrl = "https://api.exchangerate-api.com/v4/latest/" + base;
            String response = fetch(apiUrl);

            JSONObject json = new JSONObject(response);
            double rate = json.getJSONObject("rates").getDouble(target);
            double converted = amt * rate;

            String resultText = String.format("Converted Amount: %.2f %s", converted, target);
            result.setText(resultText);

            // Update history
            String historyEntry = String.format("%.2f %s = %.2f %s", amt, base, converted, target);
            historyList.add(historyEntry);
            update();
        } catch (Exception ex) {
            result.setText("Error: " + ex.getMessage());
        }
    }

    // Logic to clear I/P Field
    private void clear() {
        amount.setText("");
        result.setText("Converted Amount: ");
        baseCurr.setSelectedIndex(0);
        targetCurr.setSelectedIndex(0);
    }

    //Logic of updation history
    private void update() {
        StringBuilder sb = new StringBuilder();
        for (String entry : historyList) {
            sb.append(entry).append("\n");
        }
        historyArea.setText(sb.toString());
    }

    //Dar mode Logic
    private void DarkMode() {
        isDarkMode = !isDarkMode;

        Color bgColor = isDarkMode ? Color.DARK_GRAY : Color.WHITE;
        Color fgColor = isDarkMode ? Color.WHITE : Color.BLACK;

        getContentPane().setBackground(bgColor);

        for (Component comp : getContentPane().getComponents()) {
            comp.setBackground(bgColor);
            comp.setForeground(fgColor);
            if (comp instanceof JButton || comp instanceof JToggleButton) {
                comp.setBackground(isDarkMode ? new Color(70, 70, 70) : UIManager.getColor("Button.background"));
            }
        }

        historyArea.setBackground(isDarkMode ? Color.BLACK : Color.WHITE);
        historyArea.setForeground(isDarkMode ? Color.GREEN : Color.BLACK);

        darkMode.setText(isDarkMode ? "Disable Dark Mode" : "Enable Dark Mode");
    }

    // FETCH API DATA
    private String fetch(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder res = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            res.append(line);
        }
        reader.close();
        return res.toString();
    }


    //Main Method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Task_4());
    }
}
