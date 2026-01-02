package com.example.servicecar.web;

import com.example.servicecar.entities.Car;
import com.example.servicecar.repositories.CarRepository;
import com.example.servicecar.services.ClientApi;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarRepository repo;
    private final ClientApi clientApi;

    public CarController(CarRepository repo, ClientApi clientApi) {
        this.repo = repo;
        this.clientApi = clientApi;
    }

    @PostMapping
    public Car save(@RequestBody Car car) {
        return repo.save(car);
    }

    // ✅ UPDATE pour corriger marque/modele null
    @PutMapping("/{id}")
    public Car update(@PathVariable Long id, @RequestBody Car newCar) {
        Car car = repo.findById(id).orElse(null);
        if (car == null) return null;

        car.setMarque(newCar.getMarque());
        car.setModele(newCar.getModele());
        car.setClientId(newCar.getClientId());

        return repo.save(car);
    }

    // ✅ DELETE (optionnel) pour supprimer les anciens enregistrements
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }

    @GetMapping
    public List<Car> findAll() {
        List<Car> cars = repo.findAll();

        for (Car car : cars) {
            if (car.getClientId() != null) {
                car.setClient(clientApi.findClientById(car.getClientId()));
            }
        }
        return cars;
    }

    @GetMapping("/byClient/{clientId}")
    public List<Car> findByClient(@PathVariable Long clientId) {
        List<Car> cars = repo.findByClientId(clientId);

        for (Car car : cars) {
            car.setClient(clientApi.findClientById(clientId));
        }
        return cars;
    }
}
