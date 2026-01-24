package com.taxiapp.service;

import com.taxiapp.model.dto.RegisterDto;
import com.taxiapp.model.dto.RideDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class UserClientService {

    private static final RestTemplate restTemplate = new RestTemplate();

    public boolean registerUser(RegisterDto dto) {
        String url = "http://localhost:8095/api/users/register";

        try {
            restTemplate.postForEntity(url, dto, Void.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public void createRide(RideDto rideDto, String username) {

        rideDto.setUsername(username);

        restTemplate.postForObject(
                "http://localhost:8095/api/rides/createRide",
                rideDto,
                Void.class
        );
    }

    public List<RideDto> getRides(String username) {

        ResponseEntity<RideDto[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8095/api/rides/getRides?username=" + username,
                        RideDto[].class);

        RideDto[] body = response.getBody();

        if (body == null) {
            return List.of();
        }

        return Arrays.asList(body);
    }


    public Integer getRideCount(String username) {

        return restTemplate.getForObject(
                "http://localhost:8095/api/users/count/{username}",
                Integer.class,
                username
        );
    }

    public String determineLevel(Integer rideCount) {

        if (rideCount == null || rideCount < 3) {
            return " -> STANDARD <- ";
        } else if (rideCount < 6) {
            return " -> SILVER <- ";
        } else {
            return " -> GOLD <- ";
        }
    }


    public void deleteRide(Long rideId) {
        String url = "http://localhost:8095/api/rides/{id}";
        restTemplate.delete(url, rideId);
    }


    public void deleteUserByUsername(String username) {
        String url = "http://localhost:8095/api/users/delete/{username}";
        restTemplate.delete(url, username);
    }

    public List<String> getAllUsernames() {
        ResponseEntity<String[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8095/api/users/allUsers",
                        String[].class
                );

        return Arrays.asList(response.getBody());
    }

    public void deleteRidesOlderThanMinutes(int minutes) {
        try {
            ResponseEntity<RideDto[]> response =
                    restTemplate.getForEntity(
                            "http://localhost:8095/api/rides/getAllRides",
                            RideDto[].class
                    );

            RideDto[] rides = response.getBody();

            if (rides != null && rides.length > 0) {

                LocalDateTime cutoff = LocalDateTime.now().minusMinutes(minutes);

                for (RideDto ride : rides) {

                    if (ride.getDateTime().isBefore(cutoff)) {
                        restTemplate.delete(
                                "http://localhost:8095/api/rides/{id}",
                                ride.getId()
                        );
                    }
                }
            }



        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }


}
