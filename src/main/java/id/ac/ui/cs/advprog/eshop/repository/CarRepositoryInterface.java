package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;

import java.util.Iterator;
import java.util.UUID;

public interface CarRepositoryInterface {
    Car create(Car car);
    Iterator<Car> findAll();
    Car findById(UUID id);
    Car update(UUID id, Car updateCar);
    void delete(UUID id);
}
