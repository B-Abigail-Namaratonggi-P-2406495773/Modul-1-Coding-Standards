package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class CarRepositoryImpl implements CarRepository {
    private final List<Car> carData = new ArrayList<>();

    @Override
    public Car create(Car car) {
        carData.add(car);
        return car;
    }

    @Override
    public Iterator<Car> findAll() {
        return carData.iterator();
    }

    @Override
    public Car findById(String id) {
        return carData.stream()
                .filter(car -> car.getCarId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Car update(String id, Car updatedCar) {
        Car car = findById(id);
        if (car != null) {
            car.setCarName(updatedCar.getCarName());
            car.setCarColor(updatedCar.getCarColor());
            car.setCarQuantity(updatedCar.getCarQuantity());
        }
        return car;
    }

    @Override
    public void delete(String id) {
        carData.removeIf(car -> car.getCarId().equals(id));
    }
}