package com.sadatmalik.fusionbanking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pojo class that models a single bank.
 *
 * @author sadatmalik
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bank {
    private String name;
    private String imageLocation;
}