package org.example.dao;

import org.example.dto.ProductDTO;

import java.util.List;

public interface ProductDAO {
    public List<ProductDTO> getAllProducts();
    public ProductDTO getProductByCode(String productCode,boolean withStock);
    public void saveProduct(ProductDTO product);
    public void updateProduct(ProductDTO product);
    public void deleteProduct(String productCode);
}
