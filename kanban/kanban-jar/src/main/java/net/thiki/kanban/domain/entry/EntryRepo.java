package net.thiki.kanban.domain.entry;

public interface EntryRepo {

    void saveEntry(Entry newEntry);

    void saveTask(Task newTask);

}