package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Car;
import java.util.List;
import java.util.UUID;

public interface CarService {
    Car create(Car car);
    List<Car> findAll();
    Car findById(UUID carId);
    Car Update(UUID carID, Car car);
    void deleteByCarId(UUID carId);
}
