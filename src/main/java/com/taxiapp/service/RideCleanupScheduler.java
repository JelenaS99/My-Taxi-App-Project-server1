package com.taxiapp.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RideCleanupScheduler {

    private final UserClientService userClientService;

    public RideCleanupScheduler(UserClientService userClientService) {
        this.userClientService = userClientService;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void deleteOldRides() {
        try {
            userClientService.deleteRidesOlderThanMinutes(5);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}

