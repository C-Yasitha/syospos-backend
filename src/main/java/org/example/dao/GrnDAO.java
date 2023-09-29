package org.example.dao;

import org.example.dto.GrnDTO;

import java.util.List;

public interface GrnDAO {
    public void saveGrn(GrnDTO grn);
    public List<GrnDTO> getAllGrns();
    public void moveGrn(GrnDTO grn);
}
