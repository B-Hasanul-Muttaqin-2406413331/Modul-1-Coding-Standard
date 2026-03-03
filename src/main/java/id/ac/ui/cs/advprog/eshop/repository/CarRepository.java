package id.ac.ui.cs.advprog.eshop.repository;
import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
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
    public Iterator<Car> findAll() {
        return carData.iterator();
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
        for (int i = 0; i< carData.size(); i++) {
            Car car = carData.get(i);
            if (car.getCarID().equals(id)) {
                car.setCarName(updatecar.getCarName());
                car.setCarColor(updatecar.getCarColor());
                car.setCarQuantity(updatecar.getCarQuantity());
                return car;
            }
        }
        return null;
    }

    public void delete(UUID id) {
        carData.removeIf(car -> car.getCarID().equals(id));
    }
}
