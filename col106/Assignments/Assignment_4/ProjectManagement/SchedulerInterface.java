package ProjectManagement;

public interface SchedulerInterface {
    void run_to_completion();

    void handle_project(String[] cmd);

    void handle_job(String[] cmd);

    void handle_user(String name);

    void handle_query(String key);

    void handle_empty_line();

    void handle_add(String[] cmd);

    void print_stats();

    void schedule();
}
