package ru.buxarnet.question;

public class SQLQuestion extends AbstractQuestion {


    public SQLQuestion() {
        super("Сколько основных типов связей между таблицами существует в реляционных базах данных?");
    }

    @Override
    public boolean checkAnswer(String answer) {
        return answer.equals("3");
    }
}
