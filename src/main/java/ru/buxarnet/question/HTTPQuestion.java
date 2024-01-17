package ru.buxarnet.question;

import java.util.Locale;

public class HTTPQuestion extends AbstractQuestion{

    private final int minimalCount = 4;
    private final String[] httpMethods = {"GET", "POST", "HEAD", "PUT", "PATCH", "DELETE", "OPTIONS", "CONNECT", "TRACE"};

    public HTTPQuestion() {
        super("Перечислите, пожалуйста, все известные вам методы HTTP-запросов");
    }

    @Override
    public boolean checkAnswer(String answer) {
        int count =0;
        for(String method: httpMethods){
            if(answer.toUpperCase(Locale.ROOT).contains(method)){
                count = count + 1;
            }
        }

        return count >= minimalCount;
    }
}
