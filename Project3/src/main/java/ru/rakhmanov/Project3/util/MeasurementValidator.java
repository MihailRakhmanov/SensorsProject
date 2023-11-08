package ru.rakhmanov.Project3.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.rakhmanov.Project3.models.Measurement;
import ru.rakhmanov.Project3.services.SensorsService;

@Component
public class MeasurementValidator implements Validator {
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        if (measurement.getSensor() == null){
            return;
        }

        if (sensorsService.getSensorByName(measurement.getSensor().getName()).isEmpty()){
            errors.rejectValue("sensor", "", "Нет зарегистрированного сенсора с таким именем");
        }
    }
}
