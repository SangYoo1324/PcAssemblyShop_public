package com.example.pcAssemblyShop.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private Users shop_user;
    @Column
    private String invoice_id; //paypal payer_name

    @Column
    private String paypal_addr;
    @Column
    private String paypal_email;

    @Column
    private Float tax_total;

    @Column
    private Float item_total;

    @Column
    private Float total;


    @Column
    private Timestamp update_time;
    @Builder

    public Receipt(Long id, Users shop_user, String invoice_id, String paypal_addr, String paypal_email, Float tax_total, Float item_total, Float total, Timestamp update_time) {
        this.id = id;
        this.shop_user = shop_user;
        this.invoice_id = invoice_id;
        this.paypal_addr = paypal_addr;
        this.paypal_email = paypal_email;
        this.tax_total = tax_total;
        this.item_total = item_total;
        this.total = total;
        this.update_time = update_time;
    }
}
