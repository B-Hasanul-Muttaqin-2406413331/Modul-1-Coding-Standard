package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepositoryInterface;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepositoryInterface carRepository;

    @Override
    public Car create(Car car) {
        carRepository.create(car);
        return car;
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Car findById(UUID carId) {
        Car car = carRepository.findById(carId);
        return car;
    }

    @Override
    public Car Update(UUID carId, Car car) {
        carRepository.update(carId, car);
        return car;
    }

    @Override
    public void deleteByCarId(UUID carId) {
        carRepository.delete(carId);
    }

}
