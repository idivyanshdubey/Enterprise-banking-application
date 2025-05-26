// package com.bankingApp.j.swaggerConfig;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.jdbc.core.JdbcTemplate;

// @Configuration
// public class DatabaseConfig {

//     @Autowired
//     private JdbcTemplate jdbcTemplate;

//     @Bean
//     public CommandLineRunner resetSequence() {
//         return args -> {
//             try {
//                 // Get the current max ID from the employees table
//                 Integer maxId = jdbcTemplate.queryForObject(
//                     "SELECT COALESCE(MAX(id), 0) FROM employees", Integer.class);
                
//                 // Get the sequence name for the id column in employees table
//                 String sequenceName = jdbcTemplate.queryForObject(
//                     "SELECT pg_get_serial_sequence('employees', 'id')", String.class);
                
//                 // Reset the sequence to start from max_id + 1
//                 if (maxId != null && sequenceName != null) {
//                     System.out.println("Resetting sequence " + sequenceName + " to " + (maxId + 1));
//                     jdbcTemplate.execute("ALTER SEQUENCE " + sequenceName + " RESTART WITH " + (maxId + 1));
//                 } else {
//                     System.out.println("Could not reset sequence. MaxId: " + maxId + ", SequenceName: " + sequenceName);
//                 }
//             } catch (Exception e) {
//                 System.err.println("Error resetting sequence: " + e.getMessage());
//                 e.printStackTrace();
//             }
//         };
//     }
// }
