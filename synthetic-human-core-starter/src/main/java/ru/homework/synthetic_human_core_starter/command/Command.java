package ru.homework.synthetic_human_core_starter.command;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
public class Command {
    private String description, author, time;
    @Setter
    private CommandLevel commandLevel;

    public Command() {
    }

    public Command(String description, String author, String time, CommandLevel commandLevel) {
        this.description = description;
        this.author = author;
        this.time = time;
        this.commandLevel = commandLevel;
    }

    /**
     * <b>setDescription</b> - при присваивании проверяет длину, она не должна превышать 1000
     *
     * @param description
     */

    public void setDescription(String description) {
        if (description != null && description.length() > 1000)
            throw new IllegalArgumentException("Description length must be <= 1000");
        this.description = description;
    }

    /**
     * <b>setAuthor</b> - при присваивании проверяет длину, она не должна превышать 1000
     *
     * @param author
     */
    public void setAuthor(String author) {
        if (author != null && author.length() > 1000)
            throw new IllegalArgumentException("Author length must be <= 1000");
        this.author = author;
    }

    /**
     * <b>setTime</b> - при присваивании проверяет что время соответствует формату ISO8601
     *
     * @param time
     */
    public void setTime(String time) {
        if (time == null)
            throw new IllegalArgumentException("Time cannot be null!");
        try {
            OffsetDateTime.parse(time, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Time must be ISO8601 format!");
        }
        this.time = time;
    }

    @Override
    public String toString() {
        return "Command{" +
                "description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", time='" + time + '\'' +
                ", commandLevel=" + commandLevel +
                '}';
    }
}
