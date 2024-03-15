package africa.semicom.ecommerceApp.services;

import africa.semicom.ecommerceApp.dtos.request.AddProductRequest;
import africa.semicom.ecommerceApp.dtos.response.AddProductResponse;
import africa.semicom.ecommerceApp.dtos.response.ProductResponse;
import org.springframework.context.annotation.Bean;

import java.util.List;

public interface ProductService {
    AddProductResponse addProduct(AddProductRequest request);

    List<ProductResponse> getProducts(int page, int size);

    ProductResponse getProductBy(long id);
}
