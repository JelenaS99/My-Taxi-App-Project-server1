package com.taxiapp.model.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RideDto {

    private Long id;

    private String username;

    @NotBlank(message = "Start point is required!")
    private String beginCity;

    @NotBlank(message = "End point is required!")
    private String endCity;

    @NotBlank(message = "Car type is required!")
    private String car;

    @NotBlank(message = "Payment method type is required!")
    private String paymentMethod;

    private LocalDateTime dateTime;


}
