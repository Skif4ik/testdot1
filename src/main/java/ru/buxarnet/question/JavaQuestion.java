package ru.buxarnet.question;

public class JavaQuestion extends AbstractQuestion {

    public JavaQuestion() {
        super("Сколько в языке програмирования JAva Существует примитивов");
    }

    @Override
    public boolean checkAnswer(String answer) {
        return answer.equals("8");

    }
}
