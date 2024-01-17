package ru.buxarnet;

public class UserData {
    private int score;//количество правельных ответов
    private int current;//номер текушего вопроса



    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}
