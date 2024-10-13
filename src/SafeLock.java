package SafeLock;

import javax.swing.*;
import java.awt.*;

class Contact {
    String name;
    long number;
    Contact next;

    public Contact(String name, long number) {
        this.name = name;
        this.number = number;
        this.next = null;
    }
}

public class SafeLock extends JFrame {
    private static Contact head;
    private static JTextArea displayArea;

    public SafeLock() {
        setTitle("SafeLock Phone Book");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        displayArea = new JTextArea(15, 30);
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea));

        JButton saveButton = new JButton("Save Contact");

        JButton deleteButton = new JButton("Delete Contact");
        JButton updateButton = new JButton("Update Contact");
        JButton displayButton = new JButton("Display All Contacts");
        JButton callButton = new JButton("Call Contact");
        JButton sortButton = new JButton("Sort Contacts");
        JButton searchButton = new JButton("Search Contact");

        add(saveButton);

        add(deleteButton);
        add(updateButton);
        add(displayButton);
        add(callButton);
        add(sortButton);
        add(searchButton);

        saveButton.addActionListener(e -> saveContact());

        deleteButton.addActionListener(e -> deleteContact());
        updateButton.addActionListener(e -> updateContact());
        displayButton.addActionListener(e -> displayAllContacts());
        callButton.addActionListener(e -> callContact());
        sortButton.addActionListener(e -> sortContacts());
        searchButton.addActionListener(e -> searchContact());

        setVisible(true);
    }

    private void callContact() {
        String name = JOptionPane.showInputDialog(this, "Who would you like to call? (Firstname Lastname)");
        if (name != null) {
            displayArea.append("Calling " + name + "\n");
        }
    }

    private void saveContact() {
        String name = JOptionPane.showInputDialog(this, "What is the name of the person you would like to save? (Firstname Lastname)");
        String numberStr = JOptionPane.showInputDialog(this, "What is the phone number of the person you are saving? (+264 815945484)");
        if (name != null && numberStr != null) {
            long number = Long.parseLong(numberStr);
            Contact newContact = new Contact(name, number);
            if (head == null) {
                head = newContact;
            } else {
                Contact current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newContact;
            }
            displayArea.append("Saving contact " + name + ": " + number + "\n");
        }
    }


    private void deleteContact() {
        String name = JOptionPane.showInputDialog(this, "What is the name of the contact you want to delete? (Firstname Lastname)");
        if (name != null) {
            if (head == null) {
                displayArea.append("Contact list is empty.\n");
                return;
            }
            if (head.name.equalsIgnoreCase(name)) {
                head = head.next;
                displayArea.append("Deleted contact: " + name + "\n");
                return;}
            Contact current = head;
            while (current.next != null) {
                if (current.next.name.equalsIgnoreCase(name)) {
                    current.next = current.next.next;
                    displayArea.append("Deleted contact: " + name + "\n");
                    return;}
                current = current.next;
            }
            displayArea.append("Could not find " + name + " to delete.\n");
        }
    }

    private void updateContact() {
        String name = JOptionPane.showInputDialog(this, "What is the name of the contact you want to update? (Firstname Lastname)");
        if (name != null) {
            String newNumberStr = JOptionPane.showInputDialog(this, "What is the new phone number? (+264 815945484)");
            if (newNumberStr != null) {
                long newNumber = Long.parseLong(newNumberStr);
                Contact current = head;
                while (current != null) {
                    if (current.name.equalsIgnoreCase(name)) {
                        current.number = newNumber;
                        displayArea.append("Updated contact " + name + " to new number: " + newNumber + "\n");
                        return; }
                    current = current.next;
                }
                displayArea.append("Could not find " + name + " to update.\n");
            }
        }
    }

    private void displayAllContacts() {
        if (head == null) {
            displayArea.append("No contacts available.\n");
            return;
        }
        displayArea.append("Contact List:\n");
        Contact current = head;
        while (current != null) {
            displayArea.append(current.name + ": " + current.number + "\n");
            current = current.next;
        }
    }

    private void sortContacts() {
        if (head == null || head.next == null) {
            displayArea.append("No contacts available to sort.\n");
            return;}

        // Bubble Sort
        boolean swapped;
        do {
            swapped = false;
            Contact current = head;
            while (current != null && current.next != null) {
                if (current.name.compareToIgnoreCase(current.next.name) > 0) {
                    // Swap the names and numbers
                    String tempName = current.name;
                    long tempNumber = current.number;
                    current.name = current.next.name;
                    current.number = current.next.number;
                    current.next.name = tempName;
                    current.next.number = tempNumber;
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);

        displayArea.append("Contacts sorted alphabetically.\n");
    }

    private void searchContact() {
        String name = JOptionPane.showInputDialog(this, "What is the name of the contact you want to search for? (Firstname Lastname)");
        if (name != null) {
            Contact current = head;
            while (current != null) {
                if (current.name.equalsIgnoreCase(name)) {
                    displayArea.append("Found: " + current.name + " - " + current.number + "\n");
                    return;}
                current = current.next;
            }
            displayArea.append("Could not find " + name + "\n");
        }
    }

    public static void main(String[] args) {
        new SafeLock();
    }
}
