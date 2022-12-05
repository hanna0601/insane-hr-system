package ui;

import check_profile_validation.CheckProfileIGateway;
import check_profile_validation.CheckProfileInputBoundary;
import check_profile_validation.CheckProfileInteractor;
import controller.CheckProfileController;
import data_access.CheckProfileDataAccess;
import presenter.CheckProfilePresenter;
import presenter.IViewModel;
import presenter.Controllers;
import presenter.UseCaseButtons;
import view_model.IView;
import view_model.Table;
import view_model.ViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class ScreenBuilder implements IView {
    private final IViewModel dataModel;
    private final Integration view;
    private final CheckProfileController buttonController;

    public ScreenBuilder(IViewModel dataModel) {
        this.dataModel = dataModel;
        view = new Integration(dataModel.getFrameName());
        this.dataModel.addObserver(view);
        CheckProfileIGateway gateway = new CheckProfileDataAccess();
        CheckProfilePresenter presenter =new CheckProfilePresenter();
        CheckProfileInputBoundary interactor = new CheckProfileInteractor(gateway, presenter);
        buttonController = new CheckProfileController(interactor);
    }

    public ScreenBuilder(ViewModel dataModel, CheckProfileIGateway gateway) {

        this.dataModel = dataModel;
        // Add view to the dataModel observable.
        view = new Integration(dataModel.getFrameName());
        this.dataModel.addObserver(view);
        CheckProfilePresenter presenter =new CheckProfilePresenter();
        CheckProfileInputBoundary interactor = new CheckProfileInteractor(gateway, presenter);
        buttonController = new CheckProfileController(interactor);

    }


    /**
     * This method initialize the frame.
     */
    @Override
    public void initialization(){

    }

    /**
     * This method set the Introduction part of the frame.
     * @return is a long and well-formed string that contain all detail of the user or organization.
     */
    @Override
    public String setIntro() {
        return dataModel.getIntro();
    }

    /**
     * This method set the Introduction title.
     * @return is a string that at least have the name of the user or organization.
     */
    @Override
    public String setInfoTitle() {
        return dataModel.getInfoTitle();
    }

    /**
     * This method set the Frame name in the top. Default is HR system.
     * @return is a string of the frame name
     */
    @Override
    public String setFrameName() {
        return dataModel.getFrameName();
    }

    /**
     * This method set the Left table using Table model.
     * @return a Table that will be present in left JTable.
     */
    @Override
    public Table setLeftTable() {
//        return new Table(new String[]{"Left Table need to be override!"}, new Object[][]{new Object[]{"Leon"}, new Object[]{"Alice"}}, new Object[]{10,20});
        return dataModel.getLeftTable();
    }

    /**
     * This method set the Right table using Table model.
     * @return a Table that will be present in right JTable.
     */
    @Override
    public Table setRightTable(){
//        return new Table(new String[]{"Right Table need to be override!"}, new Object[][]{new Object[]{"Leon"}, new Object[]{"Alice"}}, new Object[]{10,20});
        return dataModel.getRightTable();
    }

    @Override
    public String setLeftButtonLabel() {
        return "go to selected " + dataModel.getLeftTable().getColumnName()[0];
    }
    /**
     * This method will be invoked after the left button is clicked.
     */
    @Override
    public void customizeLeftButton(){
        for (int i : view.getLeftTable().getSelectedRows()){
            Object reference = dataModel.getLeftTable().getReference()[i];
            plugInController(reference);

        }


    }

    @Override
    public String setRightButtonLabel() {
        return "go to selected " + dataModel.getRightTable().getColumnName()[0];
    }
    /**
     * This method will be invoked after the right button is clicked.
     */
    @Override
    public void customizeRightButton(){
//        int[] nums = view.getRightTable().getSelectedRows();
//        for (int num : nums){
//            String name = (String) dataModel.getRightTable().getData()[num][0];
//            String reference = (String) dataModel.getRightTable().getReference()[num];
//            dataModel.updateIntro(dataModel.getIntro() + name + reference + " have been selected\n");
//        }
        for (int i : view.getRightTable().getSelectedRows()){
            Object reference = dataModel.getRightTable().getReference()[i];
            plugInController(reference);

        }

    }

    private void plugInController(Object reference) {
        if (reference instanceof Integer) {
            buttonController.create(dataModel.getRequesterID(), (Integer) reference);
        } else if (reference instanceof UUID) {
            buttonController.create(dataModel.getRequesterID(), (UUID) reference);
        } else {
            JOptionPane.showMessageDialog(view,
                    "The reference is not in correct Type",
                    "Inane error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public JPanel customizeLeftPanel(){
        JPanel jPanel = new JPanel(new GridBagLayout());
        try{
            for (Controllers controllers : dataModel.getUseCases()) {
                jPanel.add(UseCaseButtons.getPanel(controllers, this));
            }
        } catch (NullPointerException e) {
            jPanel.add(UseCaseButtons.getUseCase1(this));
        }


        return jPanel;
    }

    private JLabel useCase1() {
        return new JLabel("No use case is allowed");
    }

    @Override
    public JPanel customizeRightPanel(){
        JPanel jPanel = new JPanel(new GridBagLayout());
        jPanel.add(new JLabel("You need to add customized Right Panel here!"));
        return jPanel;
    }

    public void addConnection(ViewModel dataModel) {
        dataModel.addObserver(this.view);
    }
    private void addLeftTable(Table table) {
        view.getLeftTable().setModel(new DefaultTableModel(table.getData(), table.getColumnName()));
    }
    private void addRightTable(Table table) {
        view.getRightTable().setModel(new DefaultTableModel(table.getData(), table.getColumnName()));
    }

    public JFrame view(){
        switch (this.dataModel.getVisualLevel()){
            case INVISIBLE: return getNotVisible();
            case ONLY_FACE: return getIntroOnly();
            case PROFILE: return getIntroTableAndButton();
            case EDITABLE: return getView();
        }
        return getNotVisible();
    }

    /**
     * get the frame after build.
     * @return Integration frame
     */

    public Integration getView(){

        // Initialize the front data.
        initialization();
        view.setTitle(setFrameName());
        view.setNameLabel(setInfoTitle());
        view.getDetailLabel().setText(setIntro());
        view.getLeftButton().setText(setLeftButtonLabel());
        view.getRightButton().setText(setRightButtonLabel());
        addLeftTable(setLeftTable());
        addRightTable(setRightTable());

        // Plug in Buttons
        view.getLeftButton().addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                customizeLeftButton();
            }
        });
        view.getRightButton().addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                customizeRightButton();
            }
        });

        // Plug in customized Panels
        view.setLeftControllerPanel(customizeLeftPanel());
        view.setRightControllerPanel(customizeRightPanel());
        return view;
    }


    private Integration getIntroOnly(){
        initialization();
        view.setTitle(setFrameName());
        view.setNameLabel(setInfoTitle());
        view.getDetailLabel().setText(setIntro());

        removeDataPanels();
        removeButtons();
        removeControllerPanels();

        view.validate();

        return view;
    }

    private Integration getIntroAndTable(){
        initialization();
        view.setTitle(setFrameName());
        view.setNameLabel(setInfoTitle());
        view.getDetailLabel().setText(setIntro());

        removeButtons();
        removeControllerPanels();

        view.validate();


        return view;
    }

    private Integration getIntroTableAndButton(){
        initialization();
        view.setTitle(setFrameName());
        view.setNameLabel(setInfoTitle());
        view.getDetailLabel().setText(setIntro());

        removeControllerPanels();

        view.validate();

        return view;
    }
    void removeButtons() {
        view.getLeftPanel().remove(view.getLeftButton());
        view.getRightPanel().remove(view.getRightPanel());
        view.getLeftPanel().invalidate();
        view.getRightPanel().invalidate();
    }
    void removeDataPanels() {
        view.getRootPanel().remove(view.getLeftPanel());
        view.getRootPanel().remove(view.getRightPanel());
        view.getRootPanel().invalidate();
    }
    void removeControllerPanels() {
        view.getRootPanel().remove(view.getLeftCustomizedPanel());
        view.getRootPanel().remove(view.getRightCustomizedPanel());
        view.getRootPanel().invalidate();
    }

    @Override
    public IViewModel getDataModel() {
        return dataModel;
    }



    //APIs for get the data in the GUI.

    public Object[][] getLeftSelectedRows() {
        JTable jTable= view.getLeftTable();
        if (jTable == null) {
            return null;
        }
        Object[][] result = new Object[jTable.getSelectedRowCount()][jTable.getColumnCount()];
        for (int i= 0; i < result.length; i ++) {
            result[i] = this.dataModel.getLeftTable().getData()[i];
        }
        return result;
    }
    public Object[][] getRightSelectedRows() {
        JTable jTable= view.getRightTable();
        if (jTable == null) {
            return null;
        }
        Object[][] result = new Object[jTable.getSelectedRowCount()][jTable.getColumnCount()];
        for (int i= 0; i < result.length; i ++) {
            result[i] = this.dataModel.getLeftTable().getData()[i];
        }
        return result;
    }


    public JFrame getNotVisible() {
        // TODO: add a notification dialog to show this screen is not visible.
        return null;
    }

    public static void main(String[] args) {
        Table left = new Table(new String[]{"Employee Name"}, new Object[][]{new Object[]{"Bob"}, new Object[]{"john"}}, new Object[]{11,22});
        Table right = new Table(new String[]{"Head12 Name"}, new Object[][]{new Object[]{"Leon"}, new Object[]{"Alice"}}, new Object[]{11,22});
        String intro = String.format("Introduction:\nUid:\t%s\n", 1);
        IViewModel viewModel = new ViewModel(1, "FrameName", "User Name", intro, left, right);
        ScreenBuilder screenBuilder = new ScreenBuilder(viewModel);
        JFrame app = screenBuilder.getView();
        app.pack();
        app.setVisible(true);
    }
}