package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Car;
import java.util.List;
import java.util.UUID;

public interface CarService {
    public Car create(Car car);
    public List<Car> findAll();
    Car findById(UUID carId);
    public Car Update(UUID carID, Car car);
    public void deleteByCarId(UUID carId);
}