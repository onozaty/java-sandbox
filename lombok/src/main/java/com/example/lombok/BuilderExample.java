package com.example.lombok;

import lombok.Builder;
import lombok.ToString;

public class BuilderExample {

    public static void main(String[] args) {

        Customer customer1 = new Customer("Taro", "Urashima", "ura@exmple.com", "111-111-1111");

        System.out.println(customer1);

        Customer customer2 = Customer.builder()
                .firstName("Taro")
                .lastName("Urashima")
                .mailAddress("ura@exmple.com")
                .telephone("111-111-1111")
                .build();

        System.out.println(customer2);
    }
}

@Builder
@ToString
class Customer {

    private String firstName;

    private String lastName;

    private String mailAddress;

    private String telephone;
}
