package mx.edu.utex.todolist.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CronJobs {

    private static final Logger log = LoggerFactory.getLogger(CronJobs.class);

    /*
    private final BusRepository busRepository;

    @Autowired
    public CronJobs(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    // El cron se ejecutará cada 5 minutos
    @Scheduled(cron = "0 5 * * * ?")
    public void printLastBus() {
        Optional<Bus> lastBus = busRepository.findLastBus();
        if (lastBus.isPresent()) {
            Bus bus = lastBus.get();
            log.info("Último autobús registrado: Marca - {}, Modelo - {}", bus.getBrand(), bus.getModel());
        } else {
            log.info("No hay autobuses registrados.");
        }
    }
    */
}