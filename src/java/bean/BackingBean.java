/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entities.Reservation;
import entities.Table;
import entities.User;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author divine
 */
@ManagedBean(name = "bb")
@SessionScoped
public class BackingBean implements Serializable {
    private entities.Table table;
    private Reservation reservation;
    private User user;
    private int tableNo;
    public boolean isLogged = false;
    
    public BackingBean(){
        table = new entities.Table();
        reservation = new Reservation();
        user = new User();
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    } 
    
    // for tables
    public List<entities.Table> getAllTables(){
        return ClientBuilder.newClient()
                .target("http://localhost:8080/Table_Reservation/api/table")
                .request().get(new GenericType<List<entities.Table>>() {
                });
    }
    
    public List<entities.Table> getAvailableTables(){
        return ClientBuilder.newClient()
                .target("http://localhost:8080/Table_Reservation/api/table/reserved")
                .request().get(new GenericType<List<entities.Table>>() {
                });
    }
    
    public String createTable(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        table.setIsReserved(false);
        ClientBuilder.newClient().target("http://localhost:8080/Table_Reservation/api/table")
                .request().post(Entity.json(table));
        return "tables.xhtml";
    }
    
    // for reservations
    public List<Reservation> getAllReservations(){
        return ClientBuilder.newClient()
                .target("http://localhost:8080/Table_Reservation/api/reservation")
                .request().get(new GenericType<List<Reservation>>() {
                });
    }
    
    public String createReservation(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        table.setTableNo(tableNo);
        reservation.setTable(table);
        ClientBuilder.newClient().target("http://localhost:8080/Table_Reservation/api/reservation")
                .request().post(Entity.json(reservation));
            return "reservations.xhtml";
    }
    
    public String deleteReservation(int id){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        reservation = ClientBuilder.newClient()
                .target("http://localhost:8080/Table_Reservation/api/reservation/" + id)
                .request().delete(Reservation.class);
        System.out.println(reservation.getFullName());
        return null;
    }
    
    public boolean validate(Reservation r){
        boolean status = false;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if(r.getFullName().trim().length() < 3){
            facesContext.addMessage("reservation-frm", new FacesMessage("Full name must be at least 3 characters."));
            status = true;
        }else if(!isValid(r.getEmail())){
            facesContext.addMessage("reservation-frm", new FacesMessage("Invalid email."));
            status = true;
        }
        return status;
    }
    
    // for users
    public String authenticate(){
        User result = ClientBuilder.newClient()
                .target("http://localhost:8080/Table_Reservation/api/user")
                .request().post(Entity.json(user), User.class);
        if(result != null){
            isLogged = true;
            return "reserve.xhtml";
        }else{
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("login-frm", new FacesMessage("Authentication Failed"));
            return null;
        }
    }
    public boolean isValid(String email){
        String regex = "^[\\w-\\.+]*[\\w-\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
