package africa.semicom.ecommerceApp.repositories;

import africa.semicom.ecommerceApp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product ,Long> {
}
