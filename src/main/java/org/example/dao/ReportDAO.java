package org.example.dao;

public interface ReportDAO {
    public String TotalSale(String date);
    public String Reshelved(String date);
    public String LowLevel(String date);
    public String StockReport(String date);
    public String BillReport(String date);


}
