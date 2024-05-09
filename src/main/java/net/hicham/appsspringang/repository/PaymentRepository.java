package net.hicham.appsspringang.repository;

import net.hicham.appsspringang.entities.PayementStatus;
import net.hicham.appsspringang.entities.PayementType;
import net.hicham.appsspringang.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository <Payment,Long> {
    List <Payment> findByStudentCode (String code);

    List <Payment> findByStatus (PayementStatus status);

    List <Payment> findByType (PayementType type);
}
