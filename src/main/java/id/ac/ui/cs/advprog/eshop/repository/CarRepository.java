package id.ac.ui.cs.advprog.eshop.repository;
import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.stereotype.Repository;

import  java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Repository
public class CarRepository implements CarRepositoryInterface {
    static int id = 0;
    private List<Car> carData = new ArrayList<>();
    public Car create(Car car) {
        if(car.getCarID() == null){
            car.setCarID(UUID.randomUUID());
        }
        carData.add(car);
        return car;
    }
    public List<Car> findAll() {
        return new ArrayList<>(carData);
    }
    public Car findById(UUID id) {
        for (Car car : carData) {
            if (car.getCarID().equals(id)) {
                return car;
            }
        }
        return null;
    }
    public Car update(UUID id, Car updatecar) {
        Car existingCar = findById(id);
        if (existingCar == null) {
            return null;
        }

        existingCar.setCarName(updatecar.getCarName());
        existingCar.setCarColor(updatecar.getCarColor());
        existingCar.setCarQuantity(updatecar.getCarQuantity());
        return existingCar;
    }

    public void delete(UUID id) {
        carData.removeIf(car -> car.getCarID().equals(id));
    }
}
