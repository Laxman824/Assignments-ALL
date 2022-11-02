package ProjectManagement;

/**
 * DO NOT MODIFY
 */
public interface SchedulerInterface {
    /**
     * @param cmd Handles Project creation. Input is the command from INP1 file in array format (use space to split it)
     */
    void handle_project(String[] cmd);

    /**
     * @param cmd Handles Job creation. Input is the command from INP1 file in array format (use space to split it)
     */
    void handle_job(String[] cmd);

    /**
     * @param name Handles user creation
     */
    void handle_user(String name);

    /**
     * Returns status of a job
     *
     * @param key
     */
    void handle_query(String key);

    /**
     * Next cycle, is executed whenever an empty line is found.
     */
    void handle_empty_line();

    /**
     * Executed as a thread to server a job.
     */
    void schedule();

    /**
     * Add budget to a project Input is the command from INP1 file in array format (use space to split it)
     *
     * @param cmd
     */
    void handle_add(String[] cmd);

    /**
     * If there are no lines in the input commands, but there are jobs which can be executed, let the system run till there are no jobs left (which can be run).
     */
    void run_to_completion();

    /**
     * After execution is done, print the stats of teh system
     */
    void print_stats();
}
