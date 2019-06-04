package rest.repository;

import org.springframework.data.repository.CrudRepository;

import rest.model.Patient;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PatientRepository extends CrudRepository<Patient, Integer> {
    List<Patient> findByEmail(String email);
    List<Patient> findByPhoneNumber(String phoneNumber);
    List<Patient> findByInsuranceNumber(String insuranceNumber);

}
