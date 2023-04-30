package pl.pjatk.FilLab.service;

import org.springframework.stereotype.Service;

@Service
public class SandboxService {

    public String throwException(boolean shouldThrowException){
        if (shouldThrowException){
            throw new RuntimeException();
        }
        return "Exception was not thrown";
    }
}
