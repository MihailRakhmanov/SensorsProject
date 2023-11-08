package ru.rakhmanov.Project3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rakhmanov.Project3.models.Sensor;
import ru.rakhmanov.Project3.repositories.SensorsRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public Optional<Sensor> getSensorByName(String name){
        return sensorsRepository.findByName(name);
    }

    @Transactional
    public void register(Sensor sensor) {
        sensorsRepository.save(sensor);
    }

}
