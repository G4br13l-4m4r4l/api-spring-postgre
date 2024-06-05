package com.connection.pos_connection_jpa.controller;

import com.connection.pos_connection_jpa.dtos.ProductRecordDto;
import com.connection.pos_connection_jpa.model.ProductModel;
import com.connection.pos_connection_jpa.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        if(productRepository.findAll().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductModel>> getProductById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findById(id));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        System.out.println("Produto cadastrado: "+productModel.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }
}
