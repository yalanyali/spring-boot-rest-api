
package rest.repository;

import org.springframework.data.repository.CrudRepository;

import rest.model.Medicine;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface MedicineRepository extends CrudRepository<Medicine, Integer> {

}