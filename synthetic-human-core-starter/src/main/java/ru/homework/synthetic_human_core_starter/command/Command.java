package ru.homework.synthetic_human_core_starter.command;

public class Command {
    private String description,author,time;
    private CommandLevel commandLevel;

    public Command() {
    }

    public Command(String description, String author, String time, CommandLevel commandLevel) {
        this.description = description;
        this.author = author;
        this.time = time;
        this.commandLevel = commandLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CommandLevel getCommandLevel() {
        return commandLevel;
    }

    public void setCommandLevel(CommandLevel commandLevel) {
        this.commandLevel = commandLevel;
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
