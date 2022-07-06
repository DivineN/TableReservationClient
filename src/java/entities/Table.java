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
@Entity(name = "tables")
public class Table {
    @Id
    @Column(name = "table_no")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tableNo;
    private boolean isReserved;

    public Table() {
    }

    public Table(boolean isReserved) {
        this.isReserved = isReserved;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }
    
    public boolean isIsReserved() {
        return isReserved;
    }

    public void setIsReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }
}
