import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpellCheckerUI extends JFrame {

    private JTextField dictionaryTextField;
    private JTextField textFileTextField;
    private JCheckBox caseSensitiveCheckBox;
    private JTextField customWordsTextField;
    private JTextArea resultTextArea;
    private JLabel timeLabel;

    private SpellChecker spellChecker;

    public SpellCheckerUI() {
        setTitle("Spell Checker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        createUIComponents();
    }

    private void createUIComponents() {
        JPanel inputPanel = new JPanel(new GridLayout(7, 2));

        JLabel dictionaryLabel = new JLabel("Dictionary File Path:");
        dictionaryTextField = new JTextField();
        JButton loadDictionaryButton = new JButton("Load Dictionary");
        loadDictionaryButton.addActionListener(new LoadDictionaryButtonListener());

        JLabel textFileLabel = new JLabel("Text File Path:");
        textFileTextField = new JTextField();
        JButton loadTextButton = new JButton("Load Text");
        loadTextButton.addActionListener(new LoadTextButtonListener());

        caseSensitiveCheckBox = new JCheckBox("Case Sensitive");

        JLabel customWordsLabel = new JLabel("Custom Words:");
        customWordsTextField = new JTextField();

        JButton addCustomWordsButton = new JButton("Add Words");
        addCustomWordsButton.addActionListener(new AddCustomWordsButtonListener());

        JButton checkTextButton = new JButton("Check Text");
        checkTextButton.addActionListener(new CheckTextButtonListener());

        timeLabel = new JLabel("Time taken: ");

        inputPanel.add(dictionaryLabel);
        inputPanel.add(dictionaryTextField);
        inputPanel.add(loadDictionaryButton);

        inputPanel.add(textFileLabel);
        inputPanel.add(textFileTextField);
        inputPanel.add(loadTextButton);

        inputPanel.add(caseSensitiveCheckBox);
        inputPanel.add(new JLabel());
        inputPanel.add(new JLabel());
        inputPanel.add(customWordsLabel);
        inputPanel.add(customWordsTextField);
        inputPanel.add(addCustomWordsButton);
        inputPanel.add(new JLabel());
        inputPanel.add(new JLabel());
        inputPanel.add(checkTextButton);

        // Add timeLabel to the UI
        inputPanel.add(timeLabel);

        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);

        JScrollPane resultScrollPane = new JScrollPane(resultTextArea);

        add(inputPanel, BorderLayout.NORTH);
        add(resultScrollPane, BorderLayout.CENTER);
    }

    private class LoadDictionaryButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String dictionaryFilePath = showFileChooser("Select Dictionary File");
            if (dictionaryFilePath != null) {
                dictionaryTextField.setText(dictionaryFilePath);
            }
        }
    }

    private class LoadTextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String textFilePath = showFileChooser("Select Text File");
            if (textFilePath != null) {
                textFileTextField.setText(textFilePath);
            }
        }
    }

    private class AddCustomWordsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String customWords = customWordsTextField.getText().trim();
            if (!customWords.isEmpty()) {
                addWordsToCustomDictionary(customWords.split("\\s+"));
                customWordsTextField.setText("");
            }
        }
    }

    private class CheckTextButtonListener implements ActionListener {
        String lastDictionaryFilePath = "", lastTextFilePath = "";

        @Override
        public void actionPerformed(ActionEvent e) {
            long startTime = System.currentTimeMillis(); // Record start time

            String dictionaryFilePath = dictionaryTextField.getText();
            String textFilePath = textFileTextField.getText();

            List<String> dictionary = FileIO.loadWords(dictionaryFilePath);
            if (dictionary == null) {
                resultTextArea.setText("Failed to load the dictionary.");
                return;
            }

            List<String> textContent = FileIO.loadWords(textFilePath);
            if (textContent == null) {
                resultTextArea.setText("Failed to load the text file.");
                return;
            }

            boolean isCaseSensitive = caseSensitiveCheckBox.isSelected();

            if (!dictionaryFilePath.equals(lastDictionaryFilePath) || !textFilePath.equals(lastTextFilePath)) {
                spellChecker = new SpellChecker(dictionary, isCaseSensitive);
                lastDictionaryFilePath = dictionaryFilePath;
                lastTextFilePath = textFilePath;
            }

            // Load or update the custom dictionary
            addWordsToCustomDictionary(customWordsTextField.getText().trim().split("\\s+"));

            List<String> correctedText = new ArrayList<>();

            for (String word : textContent) {
                if (!spellChecker.isSpellingCorrect(word)) {
                    List<String> suggestions = spellChecker.getSuggestions(word);
                    if (!suggestions.isEmpty()) {
                        String correctedWord = suggestions.get(0);
                        correctedText.add(correctedWord);
                        continue;
                    }
                }
                correctedText.add(word);
            }

            resultTextArea.setText(String.join(" ", correctedText));

            long totalTime = System.currentTimeMillis() - startTime;
            timeLabel.setText("Time taken: " + totalTime + " ms");

            String outputFilePath = textFilePath.replace(".txt", "_corrected.txt");
            saveCorrectedTextToFile(correctedText, outputFilePath);
        }

        private void saveCorrectedTextToFile(List<String> correctedText, String filePath) {
            try (FileWriter writer = new FileWriter(filePath)) {
                for (String word : correctedText) {
                    writer.write(word + " ");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String showFileChooser(String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getPath();
        }

        return null;
    }

    private void addWordsToCustomDictionary(String... words) {
        if (spellChecker != null) {
            for (String word : words) {
                System.out.println(word);
                spellChecker.addWordToCustomDictionary(word);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SpellCheckerUI spellCheckerUI = new SpellCheckerUI();
            spellCheckerUI.setVisible(true);
        });
    }
}
