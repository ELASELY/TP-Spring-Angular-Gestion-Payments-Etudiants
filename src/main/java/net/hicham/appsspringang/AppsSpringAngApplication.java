package net.hicham.appsspringang;

import net.hicham.appsspringang.entities.PayementStatus;
import net.hicham.appsspringang.entities.PayementType;
import net.hicham.appsspringang.entities.Payment;
import net.hicham.appsspringang.entities.Student;
import net.hicham.appsspringang.repository.PaymentRepository;
import net.hicham.appsspringang.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class AppsSpringAngApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppsSpringAngApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner (StudentRepository studentRepository
    ,PaymentRepository paymentRepository){
        return args -> {
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).
                    firstName("Mohammed").code("112233").programId("SDIA")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).
                    firstName("Imane").code("112244").programId("SDIA")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).
                    firstName("Yasmine").code("112255").programId("GLSID")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).
                    firstName("Najat").code("112266").programId("BDCC")
                    .build());

            PayementType [] payementTypes = PayementType.values();
            Random random = new Random();
            studentRepository.findAll().forEach(st -> {
                for (int i = 0; i < 10; i++) {
                    int index = random.nextInt(payementTypes.length);
                    Payment payment = Payment.builder().
                            amount(1000 + (int) (Math.random() + 20000))
                            .type(payementTypes[index])
                            .status(PayementStatus.CREATED)
                            .date(LocalDate.now())
                            .student(st)
                            .build();
                    paymentRepository.save(payment);

                }
            });

        };

        };
    }


