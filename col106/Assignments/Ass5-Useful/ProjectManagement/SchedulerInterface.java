package ProjectManagement;

import java.util.ArrayList;

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
     * After execution is done, print the stats of the system
     */
    void print_stats();


    // Timed queries for the old queries. These are equivalent to their untimed parts.
    // Only they should not print anything so the real code is timed.
    default void timed_handle_user(String name){}
    default void timed_handle_job(String[] cmd){}
    default void timed_handle_project(String[] cmd){}
    default void timed_run_to_completion(){}

    //------------ New queries---------
    /*
     * In the format below, <> enclose parameter.
     * Format: PROJECT <PROJECT> <T1> <T2> =>
     *    Return list of all Jobs for project <PROJECT> arriving at <T1> or later and at <T2> or earlier
     *
     * Format: USER <USER> <T1> <T2>  =>
     *    Return list of all Jobs of user <USER> arriving at <T1> or later and at <T2> or earlier
     *
     * Format: PROJECTUSER <PROJECT> <USER> <T1> <T2>  =>
     *    Return list of all Jobs of user <USER> for project <PROJECT> arriving at <T1> or later and at <T2> or earlier. 
     *    The list must be sorted in the order of job completion. Unfinished Jobs come last, and in the order of their arrival.
     *
     
     * Format: PRIORITY <PRIORITY> =>
     *     Return the list of waiting (unfinished) Jobs with a priority higher than or equal to <PRIORITY>
     *
     */
    default ArrayList<JobReport_> timed_report(String[] cmd){ return null;}

   /*
    * Return the list of top <top> budget consuming users (cumulative usage)
    *    (sorted by consumption first, and then by the user's latest job's completion time)
    *    Note that budget is consumed when the job is finished, not when it is waiting.
    */
    default ArrayList<UserReport_>timed_top_consumer(int top){return null;}

   /*
    * "Prioritize" long waiting jobs: Execute all jobs waiting for <waittime> or longer,
    *    if there is sufficient budget, in the order of their relative priority 
    */
    default void timed_flush(int waittime){ }
}
