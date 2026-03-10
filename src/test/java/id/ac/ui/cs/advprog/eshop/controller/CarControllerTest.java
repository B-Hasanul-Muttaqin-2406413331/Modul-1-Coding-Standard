package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CarControllerTest {

    private CarService carService;
    private CarController carController;

    @BeforeEach
    void setUp() {
        carService = mock(CarService.class);
        carController = new CarController();
        ReflectionTestUtils.setField(carController, "carservice", carService);
    }

    @Test
    void createCarPageReturnsViewAndModelAttribute() {
        Model model = new ExtendedModelMap();

        String viewName = carController.createCarPage(model);

        assertEquals("CreateCar", viewName);
        assertNotNull(model.getAttribute("car"));
    }

    @Test
    void createCarPostCallsServiceAndRedirects() {
        Car car = createCar("Yaris", "Yellow", 2);
        Model model = new ExtendedModelMap();

        String viewName = carController.createCarPost(car, model);

        verify(carService).create(car);
        assertEquals("redirect:/car/listCar", viewName);
    }

    @Test
    void listCarPageAddsCarsAndReturnsView() {
        List<Car> cars = List.of(
                createCar("A", "Black", 1),
                createCar("B", "White", 3)
        );
        when(carService.findAll()).thenReturn(cars);
        Model model = new ExtendedModelMap();

        String viewName = carController.carListPage(model);

        verify(carService).findAll();
        assertEquals("CarList", viewName);
        assertSame(cars, model.getAttribute("cars"));
    }

    @Test
    void editCarPageAddsCarAndReturnsView() {
        UUID id = UUID.randomUUID();
        Car car = createCar("Corolla", "Gray", 4);
        when(carService.findById(id)).thenReturn(car);
        Model model = new ExtendedModelMap();

        String viewName = carController.editCarPage(id, model);

        verify(carService).findById(id);
        assertEquals("EditCar", viewName);
        assertSame(car, model.getAttribute("car"));
    }

    @Test
    void editCarPostCallsServiceAndRedirects() {
        Car car = createCar("C-HR", "Blue", 2);

        String viewName = carController.editCarPost(car);

        verify(carService).Update(car.getCarID(), car);
        assertEquals("redirect:/car/listCar", viewName);
    }

    @Test
    void deleteCarCallsServiceAndRedirects() {
        UUID id = UUID.randomUUID();

        String viewName = carController.deleteCar(id);

        verify(carService).deleteByCarId(id);
        assertEquals("redirect:/car/listCar", viewName);
    }

    private Car createCar(String name, String color, int quantity) {
        Car car = new Car();
        car.setCarID(UUID.randomUUID());
        car.setCarName(name);
        car.setCarColor(color);
        car.setCarQuantity(quantity);
        return car;
    }
}
