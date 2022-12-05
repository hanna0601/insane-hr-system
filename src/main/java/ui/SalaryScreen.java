package ui;

import presenter.DataFormatter;
import salary_calculator.SalaryResponseModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;

public class SalaryScreen extends JFrame {

    JTable table;
    /**
     * The controller
     */

    /**
     * A window with a title and a JButton.
     */
    public SalaryScreen() {

    }

    public JTable getTable(SalaryResponseModel results) {
        Object[][] objects = DataFormatter.tableFormatter(results);
        Object[] columnNameObj = objects[0];
        String[] columnName = new String[columnNameObj.length];
        for (int i=0;i<columnNameObj.length; i++) {
            columnName[i] = columnNameObj[i].toString();
        }
        Object[][] data = Arrays.copyOfRange(objects, 1, objects.length + 1);
        table.setModel(new DefaultTableModel(data, columnName));
        return this.table;
    }


}