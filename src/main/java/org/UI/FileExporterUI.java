package org.UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import org.example.CsvExporter;
import org.example.FileExporter;
import org.example.JsonExporter;


public class FileExporterUI extends JFrame {
    private File file;

    public FileExporterUI(){
        setTitle("Konwertor");
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(25, 0, 25, 40));

        JLabel heading = new JLabel("Konwertor");
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton chooseFile = new JButton("Wybierz plik:");
        chooseFile.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel fileName = new JLabel("Wybrano plik:");
        fileName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel formatLabel = new JLabel("Wybierz format");
        formatLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ButtonGroup formatGroup = new ButtonGroup();
        JRadioButton csvButton = new JRadioButton("Csv");
        JRadioButton jsonButton = new JRadioButton("Json");
        JPanel formatPanel = new JPanel();
        formatPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        formatGroup.add(jsonButton);
        formatGroup.add(csvButton);
        formatPanel.add(jsonButton);
        formatPanel.add(csvButton);
        formatPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        jsonButton.setSelected(true);
        formatPanel.setOpaque(false);

        ButtonGroup separatorGroup = new ButtonGroup();
        JRadioButton tab = new JRadioButton("Tab");
        JRadioButton semicolon = new JRadioButton("Semicolon");
        JPanel separatorPanel = new JPanel();
        separatorPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        separatorGroup.add(tab);
        separatorGroup.add(semicolon);
        separatorPanel.add(tab);
        separatorPanel.add(semicolon);
        separatorPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        separatorPanel.setOpaque(false);
        tab.setSelected(true);
        separatorPanel.setVisible(false);

        JButton generateBtn = new JButton("Generuj plik");
        generateBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        csvButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                separatorPanel.setVisible(true);
            }
        });

        jsonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                separatorPanel.setVisible(false);
            }
        });

        chooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if(result == JFileChooser.APPROVE_OPTION){
                    file = fileChooser.getSelectedFile();
                    fileName.setText("Wybrano plik: "+file.getName());
                }
            }
        });

        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(file == null){
                    JOptionPane.showMessageDialog(FileExporterUI.this,"Musisz wybrać najpierw plik");
                    return;
                }
                String fileName = file.getName().trim().split("\\.")[0];
                if(jsonButton.isSelected()){
                    FileExporter fileExporter = new JsonExporter(file.toString(),"New"+fileName);
                    fileExporter.processData();
                } else if(csvButton.isSelected()){
                    boolean isTabSelected = tab.isSelected();
                    FileExporter fileExporter = new CsvExporter(file.toString(),"New"+fileName,isTabSelected);
                    fileExporter.processData();
                }
            }
        });

        mainPanel.add(heading);
        mainPanel.add(Box.createVerticalStrut(25));

        mainPanel.add(chooseFile);
        mainPanel.add(Box.createVerticalStrut(10));

        mainPanel.add(fileName);
        mainPanel.add(Box.createVerticalStrut(25));

        mainPanel.add(formatLabel);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(formatPanel);

         mainPanel.add(separatorPanel);

        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(generateBtn);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FileExporterUI());
    }
}