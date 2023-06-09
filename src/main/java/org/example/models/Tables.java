package org.example.models;

public class Tables {
    private final int TableID;
    private final int TableSize;
    private String status;
    private String customerAssigned;

    public Tables(int tableID, int TableSize, String status, String customerAssigned) {
        this.TableID = tableID;
        this.TableSize = TableSize;
        this.status = "Open";
        this.customerAssigned = null;
    }
    public void addCustomer(String customer, int tableID) {
        if (status.equals("Open")) {
            status = "Occupied";
            customerAssigned = customer;
            System.out.println("Table " + tableID + " has been reserved for " + customer);
        } else {
            System.out.println("This table is not open for reservations");
        }
        }
        public void clearTable() {
            status = "Open";
            customerAssigned = null;
            System.out.println("This table has now been cleared for reservations");
            }
            public int getTableID() {
                return TableID;
            }
            public int getTableSize() {
                return TableSize;
            }
            public String getStatus() {
                return status;
            }
            public String getCustomerAssigned() {
                return customerAssigned;
            }
        }


