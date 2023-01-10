package carDealer.repositories;

import carDealer.domain.cars.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(value = "SELECT * FROM car_dealer.cars ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<Car> getRandomCars(long count);

    List<Car> findAllByMakeOrderByModelAscTravelledDistanceDesc(String make);
}
