package rest.repository;

import org.springframework.data.repository.CrudRepository;

import rest.model.Prescription;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PrescriptionRepository extends CrudRepository<Prescription, Integer> {

}