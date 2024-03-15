package com.alpha.lainovo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Promotional_gift")
public class PromotionalGift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotional_gift_id")
    private Integer promotionalGiftID;

    @Column(name = "promotional_gift_name", columnDefinition = "nvarchar(255)", nullable = false)
    private String promotionalGiftName;

    @Column(name = "promotional_gift_type", columnDefinition = "nvarchar(255)", nullable = false)
    private String promotionalGiftType;

    @ManyToMany(mappedBy = "gifts")
    private Set<Publications> publications = new HashSet<>();

}
