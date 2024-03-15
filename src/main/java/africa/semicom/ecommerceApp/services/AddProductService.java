package africa.semicom.ecommerceApp.services;

import africa.semicom.ecommerceApp.dtos.request.AddProductRequest;
import africa.semicom.ecommerceApp.dtos.response.AddProductResponse;
import africa.semicom.ecommerceApp.dtos.response.ProductResponse;
import africa.semicom.ecommerceApp.exceptions.ProductNotFoundException;
import africa.semicom.ecommerceApp.models.Product;
import africa.semicom.ecommerceApp.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.math.BigInteger.ONE;
@Slf4j
@Service
@AllArgsConstructor
public class AddProductService implements ProductService {
    private final ProductRepository productRepository;
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final ModelMapper mapper = new ModelMapper();

    @Override
    public AddProductResponse addProduct(AddProductRequest request) {
        Product product = mapper.map(request, Product.class);
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct, AddProductResponse.class);

    }


    @Override
        public List<ProductResponse> getProducts(int page, int size) {
            Page<Product> productPage = productRepository.findAll(createPageRequest(page, size));
            log.info("page--->{}", productPage);
            return productPage.getContent()
                    .stream()
                    .map(product -> mapper.map(product,ProductResponse.class))
                    .toList();
        }


    @Override
    public ProductResponse getProductBy(long id) {
        return mapper.map(findById(id), ProductResponse.class);
    }

    private static Pageable createPageRequest(int page, int size) {
        if (page < DEFAULT_PAGE_NUMBER) page = DEFAULT_PAGE_NUMBER;
        if (size < ONE.intValue()) size = DEFAULT_PAGE_SIZE;
        page = page - ONE.intValue();
        return PageRequest.of(page, size);
    }



    private  Product findById(Long id){
        return productRepository.findById(id).orElseThrow(

        );

    }

}