package com.etoko.etoko.services.abstraction;

import com.etoko.etoko.dtos.product.ProductGridDTO;
import com.etoko.etoko.dtos.product.UpsertProductDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

   public Page<ProductGridDTO> findAllProduct(Integer page);

     public List<ProductGridDTO> findProductById(String productId);

    public List<ProductGridDTO> insertProduct(UpsertProductDTO newProduct);

    boolean updateProduct(String productId, UpsertProductDTO productUpdateDTO);

    boolean deleteProduct(String productId);

    public Boolean checkExixtingProductId(String productId);
}
