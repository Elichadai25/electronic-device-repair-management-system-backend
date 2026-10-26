package auca.example.electronic_device_repair_management_syste.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import auca.example.electronic_device_repair_management_syste.model.Customer;
import auca.example.electronic_device_repair_management_syste.repository.CustomerRepository;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public String save(Customer customer) {
        customerRepository.save(customer);
        return "Customer saved successfully";
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(UUID id) {
        return customerRepository.findById(id);
    }

    public String update(UUID id, Customer updatedCustomer) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);

        if (existingCustomer.isEmpty()) {
            return "The customer with that id does not exist";
        }

        Customer customer = existingCustomer.get();
        customer.setFirstName(updatedCustomer.getFirstName());
        customer.setLastName(updatedCustomer.getLastName());
        customer.setCustomerCode(updatedCustomer.getCustomerCode());
        customer.setPhone(updatedCustomer.getPhone());

        customerRepository.save(customer);

        return "Customer updated successfully";
    }

    public String delete(UUID id) {
        if (customerRepository.findById(id).isEmpty()) {
            return "The customer with that id does not exist";
        }

        customerRepository.deleteById(id);

        return "Customer deleted successfully";
    }

    public List<Customer> findByLastName(String lastName) {
        return customerRepository
                .findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public List<Customer> findCustomersByTechnicianId(UUID technicianId) {
        return customerRepository.findCustomersByTechnicianId(technicianId);
    }
}
