package com.youtube.ecommerce.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String brandName;
    @ManyToOne
    @JoinColumn(name = "c_id")
    private Category categories;

    @OneToMany(mappedBy = "productBrand")
    private List<Product> product;
}
