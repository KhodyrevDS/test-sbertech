package ru.kds.shoplist.service;

import java.time.LocalDate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Shedule item task
 */
@Component
public class SheduleItemTask {

    private SheduleItemCreationService sheduleItemCreationService;

    public SheduleItemTask(SheduleItemCreationService sheduleItemCreationService) {
        this.sheduleItemCreationService = sheduleItemCreationService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void createSheduledItem() {
        sheduleItemCreationService.createSheduledItem(LocalDate.now());
    }
}
