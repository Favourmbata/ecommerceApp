package africa.semicom.ecommerceApp.repositories;


import africa.semicom.ecommerceApp.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
