package org.example.dto;

import java.util.Date;
import java.util.List;

public class GrnDTO {
    private int id;
    private Date grnDate;
    private String supplierName;
    private Float total;
    private boolean isShelf;
    private String shelfStatus;
    private List<GrnItemDTO> grnItems;

    public GrnDTO(String supplierName, Float total, boolean isShelf, List<GrnItemDTO> grnItems) {
        this.supplierName = supplierName;
        this.total = total;
        this.isShelf = isShelf;
        this.grnItems = grnItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public boolean isShelf() {
        return isShelf;
    }

    public void setShelf(boolean shelf) {
        isShelf = shelf;
    }

    public List<GrnItemDTO> getGrnItems() {
        return grnItems;
    }

    public void setGrnItems(List<GrnItemDTO> grnItems) {
        this.grnItems = grnItems;
    }

    public Date getGrnDate() {
        return grnDate;
    }

    public void setGrnDate(Date grnDate) {
        this.grnDate = grnDate;
    }

    public String getShelfStatus() {
        return shelfStatus;
    }

    public void setShelfStatus(String shelfStatus) {
        this.shelfStatus = shelfStatus;
    }
}
