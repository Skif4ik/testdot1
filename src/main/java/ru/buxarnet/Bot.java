package ru.buxarnet;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.buxarnet.question.*;

import java.util.concurrent.ConcurrentHashMap;

public class Bot extends TelegramLongPollingBot {

    private final AbstractQuestion[] questions; //инициализация переменной для массива вопросов
    private ConcurrentHashMap<Long, UserData> users; //переменная для хранения пользователя хашмап используем для неизменяемого хранения в многопоточной среде

    public Bot() {
        questions = new AbstractQuestion[4]; //создаем массив для 4 вопросов
        questions[0] = new JavaQuestion();
        questions[1] = new SQLQuestion();
        questions[2] = new HTTPQuestion();
        questions[3] = new GitQuestion();
        users = new ConcurrentHashMap<>();
    }


    @Override
    public String getBotUsername() {
        return "test13012024crypto_bot";
    }

    @Override
    public String getBotToken() {
        return "6332380129:AAF00zB0mytAClgzTsN9pwqGJ1uYVzcoKv4";
    }

    public void sendText(Long who, String what) { //метод для ответе пользователю
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                .text(what).build();    //Message content
        try {
            execute(sm);                        //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }


//    @Override
//    public void onUpdateReceived(Update update) {
//        Message message = update.getMessage(); //получаем всю информацию о пользователе написавшем в бота
//        long userId = message.getFrom().getId(); // берем idшник пользователя
//        var text = message.getText(); //получаем текс пользователя из чата
//       // System.out.println(message.getText()); //выводим только текст из полученой информации выше
//
//        if (text.equals("/start")){ //проверяем совпадения со /start
//            sendText(userId, "Напишите слово \"лошадь\"");
//        } else if (text.equals("лошадь")){ // проверяем что ввел пользователь
//            sendText(userId, "Верно! Спасибо! :)"); //даем ответ в чат
//        }else {
//            sendText(userId, "Не верно :(");
//        }
//    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        var text = message.getText();
        var userId = message.getFrom().getId();

        if (text.equals("/start")) {

            UserData data = new UserData();//создали новый обьект юзера после того как он написал в чат "/start"
            users.put(userId, data);
            sendText(userId, questions[0].getQuestion());//выводим текст превого вопроса
            return;
        }


        UserData data = users.get(userId);
        int current = data.getCurrent(); //номер текушего вопроса
        if (current == questions.length) {
            sendText(userId, "Вы завершили опрос. Перезапустите бота комантой /start если хотите пройти опрос снова");
            return;
        }

        boolean result = questions[current].checkAnswer(text);
        data.setScore(data.getScore() + (result ? 1 : 0)); //проверяем ответ тернарным оператором получаем 0 или 1
        data.setCurrent(current + 1);//задаем следующий вопрос

        if (current == questions.length - 1) {//если у пользователя последний вопрос выдаем ему его рейтинг
            String rating = data.getScore() + " из " + questions.length;
            sendText(userId, "Ваш рейтинг: " + rating);
            System.out.println(message.getFrom().getFirstName() + " " + message.getFrom().getLastName() + " - " + rating);
        } else {//если вопрос не последний задаем следующий вопрос
            sendText(userId, questions[current + 1].getQuestion());
        }
    }
}
