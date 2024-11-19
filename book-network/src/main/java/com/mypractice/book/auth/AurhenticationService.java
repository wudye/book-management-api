package com.mypractice.book.auth;

import com.mypractice.book.email.EmailService;
import com.mypractice.book.role.RoleRepository;
import com.mypractice.book.user.Token;
import com.mypractice.book.user.TokenRepository;
import com.mypractice.book.user.User;
import com.mypractice.book.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AurhenticationService {

        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;
        private final UserRepository userRepository;
        private final TokenRepository tokenRepository;
        private final EmailService emailService;


    public void register(RegistrationRequest request) {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(()->new RuntimeException("user no found"));
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();

        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) {
        var newToken = generateAndSaveActivationToken(user);

    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(6);
        var token  = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now())
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int i) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int j = 0; j < i; j++) {
            int randomIndext = random.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndext));

        }
        return codeBuilder.toString();
    }
}
