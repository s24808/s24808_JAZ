package pl.pjatk.FilLab;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String handleException(String errorMessage) {
        throw new CustomException(errorMessage);
    }
}
