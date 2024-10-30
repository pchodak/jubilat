package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProcessViewerApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProcessViewerApp::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Process Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        JTextField pidField = new JTextField(10);
        JButton submitButton = new JButton("Get Process Info");

        panel.add(new JLabel("Enter PID:"));
        panel.add(pidField);
        panel.add(submitButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pid = pidField.getText();
                displayProcessInfo(pid);
            }
        });
    }

    private static void displayProcessInfo(String pid) {
        try {
            long processId = Long.parseLong(pid);
            ProcessHandle processHandle = ProcessHandle.of(processId).orElse(null);

            if (processHandle != null) {
                StringBuilder info = new StringBuilder("Process Info:\n");
                info.append("PID: ").append(processId).append("\n");
                info.append("Command: ").append(processHandle.info().command().orElse("N/A")).append("\n");
                info.append("Arguments: ").append(processHandle.info().arguments().orElse(new String[] {}).toString())
                        .append("\n");
                info.append("User: ").append(processHandle.info().user().orElse("N/A")).append("\n");

                JOptionPane.showMessageDialog(null, info.toString());
            } else {
                JOptionPane.showMessageDialog(null, "No process found with PID: " + pid);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid PID format. Please enter a number.");
        }
    }
}
