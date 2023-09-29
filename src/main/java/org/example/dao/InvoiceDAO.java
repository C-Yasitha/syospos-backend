package org.example.dao;

import org.example.dto.InvoiceDTO;

import java.util.List;

public interface InvoiceDAO {
    public void saveInvoice(InvoiceDTO invoice);
    public List<InvoiceDTO> getAllInvoices();
}
