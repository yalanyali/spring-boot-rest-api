
package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.query.Param;
import rest.model.Medicine;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
    //@Query("FROM Medicine where name like LOWER(?0) + ':keyword%'")
    //Iterable<Medicine> search(@Param("keyword") String keyword);
    public Iterable<Medicine> findByNameContainingIgnoreCase(String name);
}