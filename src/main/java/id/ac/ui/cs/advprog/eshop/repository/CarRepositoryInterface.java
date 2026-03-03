package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;

import java.util.List;
import java.util.UUID;

public interface CarRepositoryInterface {
    Car create(Car car);
    List<Car> findAll();
    Car findById(UUID id);
    Car update(UUID id, Car updateCar);
    void delete(UUID id);
}
