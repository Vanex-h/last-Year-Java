package rca.rw.vanessa.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rca.rw.vanessa.demo.models.Customer;
import rca.rw.vanessa.demo.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerDataService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerDataRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customerData= customerDataRepository.findByEmail(username);
        System.out.println("Here: "+username);
        return customerData.map(CustomerDataDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
    public Customer loadCurrentUser(String username) throws UsernameNotFoundException {
        Optional<Customer> customerData = customerDataRepository.findByEmail(username);
        return customerData
                .orElseThrow(() -> new UsernameNotFoundException("User not found " +
                        username));
    }
    public String addCustomer(Customer customerData) {
        customerData.setPassword(passwordEncoder.encode(customerData.getPassword()));
        customerDataRepository.save(customerData);
        return "User Added Successfully";
    }
    public List<Customer> listAll() {
        return customerDataRepository.findAll();
    }
}
