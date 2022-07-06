/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.*;

/**
 *
 * @author divine
 */
@Entity(name = "resevations")
public class Reservation {
    @Id
    @Column(name = "reservation_no")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int reservationNo;
    @Column(name = "full_name")
    private String fullName;
    private String email;
    @OneToOne
    @JoinColumn(name = "table_no")
    private Table table;

    public Reservation() {
    }

    public Reservation(String fullName, String email, entities.Table table) {
        this.fullName = fullName;
        this.email = email;
        this.table = table;
    }

    public int getReservationNo() {
        return reservationNo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public entities.Table getTable() {
        return table;
    }

    public void setTable(entities.Table table) {
        this.table = table;
    }
}
