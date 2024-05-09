package net.hicham.appsspringang.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Entity @NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 private LocalDate date;
 private double amount;
 private PayementType type;
 private PayementStatus status;
 private String file;
 @ManyToOne
 private Student student;

}
