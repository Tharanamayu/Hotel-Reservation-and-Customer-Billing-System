/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dao.CountryDao;
import dao.CustomerDao;
import dao.CustomerTypeDao;
import dao.GenderDao;
import dao.IDTypeDao;
import entity.Country;
import entity.Customer;
import entity.Customertype;
import entity.Gender;
import entity.Idtype;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tharana
 */
public class CustomerUIController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="Form-Data">
    Customer customer;
    Customer oldCustomer;

    Stage idTypeStage;

    String initial;
    String valid;
    String invalid;
    String updated;

    int page;
    int row;

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="FXML-Data">
    @FXML
    private TextArea txtAddress;
    @FXML
    private JFXComboBox<Country> cmbCountry;
    @FXML
    private JFXComboBox<Customertype> cmbSearchCustomerType;
    @FXML
    private JFXComboBox<Idtype> cmbIDType;
    @FXML
    private JFXComboBox<Gender> cmbGender;
    @FXML
    private JFXDatePicker dtpDOBDate;
    @FXML
    private JFXTextField txtID;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private JFXButton btnClear;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<Customer> tblCustomer;
    @FXML
    private TableColumn<Customer, String> colName;
    @FXML
    private TableColumn<Customer, String> colMobile;
    @FXML
    private TableColumn<Customer, String> colEmail;
    @FXML
    private JFXButton btnClearSearch;
    @FXML
    private JFXTextField txtSearchByID;
    @FXML
    private JFXTextField txtSearchByGuest;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtMobile;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXComboBox<Customertype> cmbCustomerType;
    @FXML
    private JFXDatePicker dtpAssignedDate;
    @FXML
    private Label lblCustomerID;
    @FXML
    private JFXButton btnNewIDType;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Initializing-Methods">
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initial = Style.initial;
        valid = Style.valid;
        invalid = Style.invalid;
        updated = Style.updated;

        loadForm();

        loadTable();
    }

    private void loadForm() {

        customer = new Customer();
        oldCustomer = null;

        cmbGender.setItems(GenderDao.getAll());//get all cmb details
        cmbGender.getSelectionModel().clearSelection();//clear the selected item

        cmbCustomerType.setItems(CustomerTypeDao.getAll());
        cmbCustomerType.getSelectionModel().clearSelection();

        cmbIDType.setItems(IDTypeDao.getAll());
        cmbIDType.getSelectionModel().clearSelection();

        cmbCountry.setItems(CountryDao.getAll());
        cmbCountry.getSelectionModel().clearSelection();

        txtName.setText("");
        txtAddress.setText("");
        txtID.setText("");
        txtMobile.setText("");
        txtEmail.setText("");

        dtpDOBDate.setValue(null);

        dtpAssignedDate.setDisable(true);
        dtpAssignedDate.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        Date assign = java.sql.Date.valueOf(dtpAssignedDate.getValue());
        customer.setAssigned(assign);

        if (CustomerDao.getLastJobId() != null) {

            lblCustomerID.setText(String.format("%06d", CustomerDao.getLastJobId() + 1));

        } else {

            lblCustomerID.setText(String.format("%06d", 1));

        }

        dissableButtons(false, false, true, true);

        setStyle(initial);
    }

    private void setStyle(String style) {

        cmbCountry.setStyle(style);
        cmbCustomerType.setStyle(style);
        cmbIDType.setStyle(style);
        cmbGender.setStyle(style);

        txtName.setStyle(style);
        txtID.setStyle(style);
        txtMobile.setStyle(style);
        txtEmail.setStyle(style);

        if (!txtAddress.getChildrenUnmodifiable().isEmpty()) {

            ((ScrollPane) txtAddress.getChildrenUnmodifiable().get(0)).getContent().setStyle(style);

        }

        dtpDOBDate.getEditor().setStyle(style);
        dtpAssignedDate.getEditor().setStyle(style);

        cmbSearchCustomerType.setStyle(style);
        txtSearchByGuest.setStyle(style);
        txtSearchByID.setStyle(style);

    }

    private void dissableButtons(boolean select, boolean insert, boolean update, boolean delete) {

        btnAdd.setDisable(insert);
        btnUpdate.setDisable(update);
        btnDelete.setDisable(delete);

    }
	 private void loadTable() {

        cmbSearchCustomerType.setItems(CustomerTypeDao.getAll());
        cmbSearchCustomerType.getSelectionModel().clearSelection();

        txtSearchByGuest.setText("");
        txtSearchByID.setText("");

        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colMobile.setCellValueFactory(new PropertyValueFactory("mobile"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));

////        tblEmployee.setRowFactory(new Callback<TableView<Employee>, TableRow<Employee>>() {
////
////            @Override
////            public TableRow<Employee> call(TableView<Employee> dateTableView) {
////
////                return new TableRow<Employee>() {
////
////                    @Override
////                    protected void updateItem(Employee date, boolean b) {
////                        super.updateItem(date, b);
////
////                        setStyle("-fx-background-color: linear-gradient(#04ef57 1%, #FFFFFF 100%);");
////
////                    }
////
////                };
////
////            }
////
////        });
        row = 0;
        page = 0;

        fillTable(CustomerDao.getAll());

        pagination.setCurrentPageIndex(0);

    }
	private void fillTable(ObservableList<Customer> employees) {

        if (employees != null && !employees.isEmpty()) {

            int rowsCount = 5;
            int pageCount = ((employees.size() - 1) / rowsCount) + 1;
            pagination.setPageCount(pageCount);

            pagination.setPageFactory((Integer pageIndex) -> {
                int start = pageIndex * rowsCount;
                int end = pageIndex == pageCount - 1 ? employees.size() : pageIndex * rowsCount + rowsCount;
                tblCustomer.getItems().clear();
                tblCustomer.setItems(FXCollections.observableArrayList(employees.subList(start, end)));
                return tblCustomer;
            });

        } else {

            pagination.setPageCount(1);
            tblCustomer.getItems().clear();

        }

        pagination.setCurrentPageIndex(page);
        tblCustomer.getSelectionModel().select(row);

    }



//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Binding-Methods">
   
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Operation-Methods">
    

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Search-Methods">
   
//</editor-fold>

}