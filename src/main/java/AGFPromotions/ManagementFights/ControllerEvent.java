package AGFPromotions.ManagementFights;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import java.util.List;

import AGFPromotions.ManagementFights.model.DAO.EventoDAO;
import AGFPromotions.ManagementFights.model.DAO.PeleadorDAO;
import AGFPromotions.ManagementFights.model.domain.Evento;
import AGFPromotions.ManagementFights.model.domain.Matchmaker;
import AGFPromotions.ManagementFights.model.domain.Peleador;
import AGFPromotions.ManagementFights.model.enums.Modalidad;
import AGFPromotions.ManagementFights.model.singleton.MatchmakerSession;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
public class ControllerEvent {
	
	
	@FXML
	private TextField tfId,tfNombre,tfRecinto,tfCiudad,tfPais;
	@FXML
	private DatePicker dtFecha;
	@FXML
	private ComboBox<Modalidad> cbModalidad = new ComboBox<>();
	@FXML
	private ComboBox<String> cbPeleador1 = new ComboBox<>();
	@FXML
	private ComboBox<String> cbPeleador2 = new ComboBox<>();
	@FXML
	private TableView <Evento> tvEvents;
    @FXML
    private TableColumn<Evento, Integer> colId;
    @FXML
    private TableColumn<Evento, String> colNombre;
    @FXML
    private TableColumn<Evento, String> colRecinto;
    @FXML
    private TableColumn<Evento, String> colCiudad;
    @FXML
    private TableColumn<Evento, String> colPais;
    @FXML
    private TableColumn<Evento, Date> colFecha;
    @FXML
    private TableColumn<Evento, Modalidad> colModalidad;
    @FXML
    private TableColumn<Evento, String> colPeleador1;
    @FXML
    private TableColumn<Evento, String> colPeleador2;
    @FXML
    private TableColumn<Evento, Matchmaker> colMatchmaker;
	
    EventoDAO eventoDAO = new EventoDAO();
    PeleadorDAO peleadorDAO = new PeleadorDAO();
    private ObservableList<Evento> eventos;
    

    
    
    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }
    @FXML
    private void btaddEvent() throws SQLException {
    	addEvent();
    }
    @FXML
    private void btGetAll() throws SQLException {
    	getAll();
    }
    @FXML
    private void btDelete() throws SQLException {
    	deleteSelected();
    }
    
    @FXML
    private void initialize() throws SQLException {
    	
    	cbModalidad.setItems(FXCollections.observableArrayList(Modalidad.values()));
    	List<Peleador> peleadores = peleadorDAO.findAll();
    	
    	ObservableList<String> dniList = FXCollections.observableArrayList(); 

        for (Peleador peleador : peleadores) {
            dniList.add(peleador.getDni()); 
        }

        cbPeleador1.setItems(dniList); 
        cbPeleador2.setItems(dniList); 
    	
		tvEvents.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                Evento selectedE = (Evento) newSelection;

                tfId.setText(Integer.toString(selectedE.getId_evento()));
                tfNombre.setText(selectedE.getNombre());
                tfRecinto.setText(selectedE.getRecinto());
                tfCiudad.setText(selectedE.getCiudad());
                tfPais.setText(selectedE.getPais());
                cbModalidad.setValue(selectedE.getModalidad());
                Date fechaSql = (Date) selectedE.getFecha();
                LocalDate localDate = fechaSql.toLocalDate();
                dtFecha.setValue(localDate);

                
				try {
					Peleador peleador1 = peleadorDAO.findByDni(selectedE.getF1().getDni());
					cbPeleador1.setValue(peleador1.getDni());
				} catch (SQLException e) {

					e.printStackTrace();
				}
                
				try {
					Peleador peleador2 = peleadorDAO.findByDni(selectedE.getF2().getDni());
					cbPeleador2.setValue(peleador2.getDni());
				} catch (SQLException e) {

					e.printStackTrace();
				}

                

                tvEvents.getSelectionModel().clearSelection();
                
                int rowIndex = tvEvents.getItems().indexOf(selectedE);
                if (rowIndex >= 0) {
                	tvEvents.getSelectionModel().clearAndSelect(rowIndex);
                }
            }
        });
    	
    	
    }
    
    
    
    @FXML
	private void getAll() throws SQLException {
		List<Evento> eventos = eventoDAO.findAll();	
		
		if(tvEvents.getItems().isEmpty()) {
		
		colId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getId_evento()));
    	
    	colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
    	
    	colRecinto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRecinto()));
    
    	colCiudad.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCiudad()));
    	
    	colPais.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPais()));
    	
    	colFecha.setCellValueFactory(cellData -> {
    	    Date fecha = (Date) cellData.getValue().getFecha();
    	    return new SimpleObjectProperty<Date>(fecha);
    	});
    	
    	colModalidad.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getModalidad()));
    	
    	colPeleador1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getF1().getDni()));
    	
    	colPeleador2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getF2().getDni()));
    	
    	colMatchmaker.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getM1()));
    	
    	tvEvents.getItems().addAll(eventos);
		}else {
			tvEvents.getItems().clear();
		}
	}
    
    
    private Peleador getSelectedPeleador(ComboBox<String> comboBox) throws SQLException {
        String selectedValue = comboBox.getValue();
        Peleador peleador = peleadorDAO.findByDni(selectedValue);
        return peleador;
    }
    
    @FXML
    private void addEvent() throws SQLException {
    	int id = Integer.parseInt(tfId.getText());
    	String nombre = tfNombre.getText();    
    	String recinto = tfRecinto.getText();    
    	String ciudad = tfCiudad.getText();
    	String pais = tfPais.getText();
    	LocalDate localDate = dtFecha.getValue();
    	java.sql.Date fecha = java.sql.Date.valueOf(localDate);
    	Modalidad modalidad = cbModalidad.getValue();
    	Peleador peleador1 = getSelectedPeleador(cbPeleador1);
    	Peleador peleador2 = getSelectedPeleador(cbPeleador2);
    	Matchmaker matchmaker = MatchmakerSession.getUser();
    	
    	
    	if (nombre.isEmpty() || recinto.isEmpty() || pais.isEmpty() || ciudad.isEmpty()) {
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Campos vacíos");
	        alert.setContentText("Por favor, complete todos los campos.");
	        alert.showAndWait();
	        return;
		}
    	
    	Evento nEvento = new Evento (id,nombre,recinto,ciudad,pais,fecha,modalidad,peleador1,peleador2,matchmaker);
    	try {
			eventoDAO.save(nEvento);
			Alert alerta = new Alert(AlertType.INFORMATION);
		    alerta.setTitle("Registro de Evento");
		    alerta.setHeaderText("Registro exitoso");
		    alerta.setContentText("Se ha registrado el evento correctamente.");
		    alerta.showAndWait();
		} catch (Exception e) {
			Alert alerta = new Alert(AlertType.ERROR);
		    alerta.setTitle("Registro de Evento");
		    alerta.setHeaderText("Error");
		    alerta.setContentText("No se ha registrado el evento correctamente.");
		    alerta.showAndWait();
		    e.printStackTrace();
		}
    }
    
    
    @FXML
    private void deleteSelected() {
        Evento selectedE = (Evento) tvEvents.getSelectionModel().getSelectedItem();
        
        if (selectedE != null) {
        	tvEvents.getItems().remove(selectedE);

            try {
				eventoDAO.delete(selectedE);
				Alert alerta = new Alert(AlertType.INFORMATION);
			    alerta.setTitle("Eliminar");
			    alerta.setHeaderText("Evento borrado");
			    alerta.setContentText("Se ha eliminado el evento correctamente.");
			    alerta.showAndWait();
			} catch (SQLException e) {
				e.printStackTrace();
			}
            
        }
    }    
    
    
}