package com.etoko.etoko.services.implementation;

import com.etoko.etoko.dao.ProductRepository;
import com.etoko.etoko.dtos.product.ProductGridDTO;
import com.etoko.etoko.dtos.product.UpsertProductDTO;
import com.etoko.etoko.entity.Product;
import com.etoko.etoko.services.abstraction.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<ProductGridDTO> findAllProduct(Integer page) {
        var pagination = PageRequest.of(page - 1, 10);
        Locale locale = new Locale("id", "ID");
        var formatDuit = NumberFormat.getCurrencyInstance(locale);
        var productList = productRepository.findAll();
        var productStream = productList.stream().skip(pagination.getOffset()).limit(pagination.getPageSize()).map(product -> {
            return new ProductGridDTO(
                    product.getProductId(),
                    product.getNameProduct(),
                    product.getCategory(),
                    formatDuit.format(product.getPrice()),
                    product.getDescription(),
                    product.getStock()
            );
        }).collect(Collectors.toList());

        if (productStream.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");
        }
        return new PageImpl<>(productStream, pagination, productStream.size());
    }

    @Override
    public List<ProductGridDTO> findProductById(String productId) {
        var productList = Optional.ofNullable(productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(" Data tidak ditemukan")));
        Locale locale = new Locale("id", "ID");
        var formatDuit = NumberFormat.getCurrencyInstance(locale);
        var productStream = productList.stream()
                .map(product -> new ProductGridDTO(
                        product.getProductId(),
                        product.getNameProduct(),
                        product.getCategory(),
                        formatDuit.format(product.getPrice()),
                        product.getDescription(),
                        product.getStock()
                )).collect(Collectors.toList());
        return productStream;
    }

    @Override
    public List<ProductGridDTO> insertProduct(UpsertProductDTO newProduct) {
        Product product = newProduct.convert();
        productRepository.save(product);
        return ProductGridDTO.toList(productRepository.findAll());
    }

    @Override
    public boolean updateProduct(String productId, UpsertProductDTO productUpdateDTO) {
        productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Tidak bisa update karena productID " + productId + " Tidak ada ! "));
        boolean result = false;
        var oldProduct = productRepository.findById(productId).orElse(null);

        oldProduct.setPrice(productUpdateDTO.getPrice() == null ? oldProduct.getPrice() : productUpdateDTO.getPrice());
        oldProduct.setDescription(productUpdateDTO.getDescription() == null ? oldProduct.getDescription() : productUpdateDTO.getDescription());
        oldProduct.setStock(productUpdateDTO.getStock() == null ? oldProduct.getStock() : productUpdateDTO.getStock());

        productRepository.save(oldProduct);
        return !result;
    }


    @Override
    public boolean deleteProduct(String productId) {
        productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product dengan " + productId + " Tidak ada di database"));
        productRepository.deleteById(productId);
        return true;
    }

    @Override
    public Boolean checkExixtingProductId(String productId) {
        var check = productRepository.findById(productId).isPresent();

        return check;
    }
}
