package ru.buxarnet.question;

public class GitQuestion extends AbstractQuestion{
    public GitQuestion() {
        super("C помошью какой команды в Git можно посмотреть информацию об авторстве каждой строки кода в том или ином файле?");
    }

    @Override
    public boolean checkAnswer(String answer) {
        return answer.contains("blame"); //проверка через contains позволяет находить конкретную часть во всей строке
    }
}
