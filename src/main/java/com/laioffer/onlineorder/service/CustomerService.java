package com.laioffer.onlineorder.service;


import com.laioffer.onlineorder.entity.CartEntity;
import com.laioffer.onlineorder.entity.CustomerEntity;
import com.laioffer.onlineorder.repository.CartRepository;
import com.laioffer.onlineorder.repository.CustomerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CustomerService {


    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsManager userDetailsManager;


    public CustomerService(
            CartRepository cartRepository,
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder,
            UserDetailsManager userDetailsManager) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsManager = userDetailsManager;
    }

    // Why transactional? Because we are updating the DB and want to make sure the changes are committed (otherwise rollback)
    @Transactional
    public void signUp(String email, String password, String firstName, String lastName) {
        // Note, email does not concern casing, so we just stored them as all lower case in DB
        email = email.toLowerCase();
        // User.builder() is a convenience method to create a UserDetails object
            // use builder to specify username, password, and roles
            // if .builder() is not available, create manually using new User() constructor
            // build pattern prevents mixing up order of arguments
        UserDetails user = User.builder()
                .username(email)
                .password(passwordEncoder.encode(password))
                .roles("USER")
                .build();
        // createsUserSQL a SQL command from the provided in AppConfig
        userDetailsManager.createUser(user);
        customerRepository.updateNameByEmail(email, firstName, lastName);


        // create a new cart for the customer (1-1 relationship)
        CustomerEntity savedCustomer = customerRepository.findByEmail(email);
        CartEntity cart = new CartEntity(null, savedCustomer.id(), 0.0);
        cartRepository.save(cart);
    }


    public CustomerEntity getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
