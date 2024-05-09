package net.hicham.appsspringang.web;

import net.hicham.appsspringang.entities.PayementStatus;
import net.hicham.appsspringang.entities.PayementType;
import net.hicham.appsspringang.entities.Payment;
import net.hicham.appsspringang.entities.Student;
import net.hicham.appsspringang.repository.PaymentRepository;
import net.hicham.appsspringang.repository.StudentRepository;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class PaymentRestController {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;

    public PaymentRestController(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    @GetMapping(path = "/payments")
    public List<Payment> allPayments() {
        return paymentRepository.findAll();
    }
    @GetMapping(path = "/students/{code}/payments")
    public List<Payment> paymentsByStudent(@PathVariable String code) {
        return paymentRepository.findByStudentCode(code);
    }
    @GetMapping(path = "/payments/byStatus")
    public List<Payment> paymentsByStatus(@RequestParam PayementStatus status) {
        return paymentRepository.findByStatus(status);
    }
    @GetMapping(path = "/payments/byType")
    public List<Payment> paymentsByType(@RequestParam PayementType type) {
        return paymentRepository.findByType(type);
    }

    @GetMapping(path = "payments/{id}")
    public Payment getPaymentById(Long id){
        return paymentRepository.findById(id).get();
    }

    @GetMapping("/students")
    public List<Student> allStudents(){
        return studentRepository.findAll();
    }
    @GetMapping("/students/{code}")
    public Student getStudentByCode(@PathVariable String code){
        return studentRepository.findByCode(code);
    }
    @GetMapping("/studentsByProgramId")
    public List<Student> getStudentsByProgramId(@RequestParam String programId){
        return studentRepository.findByProgramId(programId);
    }
    @PutMapping("/payments/{id}")
    public Payment updatePaymentStatus(@RequestParam PayementStatus status,@PathVariable Long id){

        Payment payment= paymentRepository.findById(id).get();
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }
    @PostMapping(path = "/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public Payment savePayment (@RequestParam MultipartFile file, LocalDate date, double amount, PayementType type,String studentCode)throws IOException {
        Path folderPath = Paths.get(System.getProperty("user.home"),"enset-data","payments");
        if (!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
        }
        String fileName = UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"),"enset-data","payments",fileName+".pdf");
        Files.copy(file.getInputStream(),filePath);
        Student student = studentRepository.findByCode(studentCode);
        Payment payment = Payment.builder()
                .date(date).type(type).student(student)
                .amount(amount)
                .file(filePath.toUri().toString())
                .status(PayementStatus.CREATED)
                .build();
        return paymentRepository.save(payment);
}
@GetMapping(value = "paymentFile/{paymentId}", produces = MediaType.APPLICATION_PDF_VALUE)
public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException{
        Payment payment = paymentRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }
}

