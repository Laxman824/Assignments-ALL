package ProjectManagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Writes to nowhere
 */
class NullOutputStream extends OutputStream {
    @Override
    public void write(int b) throws IOException {
    }
}

public class Scheduler_Grader_Large {
    static PrintStream o;
    static PrintStream console;
    static Boolean overallsuccess = true;
    private static Scheduler_Driver scheduler_driver;
    private static ArrayList<Long> user_time, job_time, project_time, rtc_time, job_report_time, user_report_time, flush_time;
    private static Boolean debug = true;
    private static long total_queries = 0l;

    private static LinkedList<String> list_projects,list_users;
//    private static LinkedList<User> ;

    public static void main(String[] args) throws FileNotFoundException {

        list_projects  = new LinkedList<>();
        list_users = new LinkedList<>();

        user_time = new ArrayList<>();
        job_time = new ArrayList<>();
        project_time = new ArrayList<>();
        rtc_time = new ArrayList<>();
        job_report_time = new ArrayList<>();
        user_report_time = new ArrayList<>();
        flush_time = new ArrayList<>();

        // Creating a File object that represents the disk file.
//        o = new PrintStream(new File("tmp_output"));
        o = new PrintStream(new NullOutputStream());
        // Store current System.out before assigning a new value
        console = System.out;
        // Assign o to output stream
        System.out.println("Starting.. it might take a while to finish");
        debug = false;
        if (!debug)
            System.setOut(o);

        scheduler_driver = new Scheduler_Driver();
//        File file;
//        if (args.length == 0) {
//            URL url = Scheduler_Driver.class.getResource("INP");
//            file = new File(url.getPath());
//        } else {
//            file = new File(args[0]);
//        }

        try {
            execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!debug) {
            System.setOut(console);
            System.out.println();
            System.out.println("------------");
            System.out.println("Queries ran successfully");
            System.out.println("Total queries executed: " + total_queries);
            if (overallsuccess)
                System.out.println("Partial validation was successful.");
            else
                System.out.println("Partial validation was NOT successful.");

            System.out.println("(Validation only checks CORRECTNESS and NOT COMPLETENESS.)");

            System.out.println("Job report average time(ms): " + calculateAverage(job_report_time) + " Total: " + calculateSum(job_report_time));
            System.out.println("User report average time (ms): " + calculateAverage(user_report_time) + " Total: " + calculateSum(user_report_time));
            System.out.println("Flush report average time (ms): " + calculateAverage(flush_time) + " Total: " + calculateSum(flush_time));
            System.out.println();
        }
    }


    static String gen_user_query(String name) {
        list_users.add(name);
        return "USER " + name;
    }

    static String gen_project_query(String name, Integer priority, Integer budget) {
        list_projects.add(name);
        return "PROJECT " + name + " " + priority + " " + budget;

    }

    static String gen_job_query(String name, String project, String user, Integer cost) {
        return "JOB " + name + " " + project + " " + user + " " + cost;
    }

    static String gen_add_budget(String project_name, Integer budget) {
        return "ADD " + project_name + " " + budget;
    }

    static void execute() throws IOException {

        String[] cmd;
        Integer MAX_QUERIES = 250;

        /**
         * Users creation
         */
        for (int i = 0; i < MAX_QUERIES; i++) {
            cmd = gen_user_query("User" + i).split(" ");
            handle_cmd_query(cmd);
        }

        for (int i = 0; i < MAX_QUERIES; i++) {
            cmd = gen_project_query("Project" + i, i, (i + 1) * 100).split(" ");
            handle_cmd_query(cmd);
        }

        Integer jobid = 0;
        for (String user : list_users) {
            for (String project : list_projects) {
                cmd = gen_job_query("Job" + jobid, project, user, ++jobid).split(" ");
                handle_cmd_query(cmd);
            }
        }

        Integer budget = 1000;
        for (String project : list_projects) {
            cmd = gen_add_budget(project, budget++ * 100).split(" ");
            handle_cmd_query(cmd);
        }

        for (int i = 0; i < MAX_QUERIES; i++) {
            cmd = ("NEW_FLUSH 100").split(" ");
            handle_cmd_query(cmd);
        }
        for (String project : list_projects) {
            cmd = ("NEW_PROJECT " + project + " 1 100").split(" ");
            handle_cmd_query(cmd);
        }

        for (String user : list_users) {
            cmd = ("NEW_USER " + user + " 1 100").split(" ");
            handle_cmd_query(cmd);
        }

        for (String project : list_projects) {
            for (String user : list_users) {
                cmd = ("NEW_PROJECTUSER " + project + " " + user + " 1 100").split(" ");
                handle_cmd_query(cmd);
            }
        }
        for (int i = 0; i < MAX_QUERIES; i++) {
            cmd = ("NEW_PRIORITY " + i).split(" ");
            handle_cmd_query(cmd);
        }
        for (int i = 0; i < MAX_QUERIES; i++) {
            cmd = ("NEW_TOP " + i).split(" ");
            handle_cmd_query(cmd);
        }


        scheduler_driver.timed_run_to_completion();
        scheduler_driver.print_stats();


    }

    private static void handle_cmd_query(String[] cmd) {
        total_queries++;
        switch (cmd[0]) {
            case "PROJECT":
                scheduler_driver.timed_handle_project(cmd);
                break;
            case "JOB":
                scheduler_driver.timed_handle_job(cmd);
                break;
            case "USER":
                scheduler_driver.timed_handle_user(cmd[1]);
                break;
            case "QUERY":
                scheduler_driver.handle_query(cmd[1]);
                break;
            case "":
                scheduler_driver.handle_empty_line();
                break;
            case "ADD":
                scheduler_driver.handle_add(cmd);
                break;
            //--------- New Queries
            case "NEW_USER":
            case "NEW_PROJECT":
            case "NEW_PROJECTUSER":
            case "NEW_PRIORITY":
                long qstart_time = System.currentTimeMillis();
                ArrayList<JobReport_> out_user = scheduler_driver.timed_report(cmd);
                long qend_time = System.currentTimeMillis();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                job_report_time.add(qend_time - qstart_time);
                validate_timed_report(out_user, cmd);
                break;

            case "NEW_TOP":
                qstart_time = System.currentTimeMillis();
                ArrayList<UserReport_> out_topuser = scheduler_driver.timed_top_consumer(Integer.parseInt(cmd[1]));
                qend_time = System.currentTimeMillis();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                user_report_time.add(qend_time - qstart_time);

                break;
            case "NEW_FLUSH":
                qstart_time = System.currentTimeMillis();
                scheduler_driver.timed_flush(Integer.parseInt(cmd[1]));
                qend_time = System.currentTimeMillis();
                flush_time.add(qend_time - qstart_time);
                break;
            default:
                System.err.println("Unknown command: " + cmd[0]);
        }
    }

    private static void validate_timed_report(ArrayList<JobReport_> out, String[] cmd) {
//        if (!debug)
//            System.setOut(console);
        String project_name, user_name;
        Integer start_time, end_time;
        Boolean success = true;
        System.out.print("Validating: ");
        switch (cmd[0]) {
            case "NEW_PROJECT":
                System.out.print("Project query");
                project_name = cmd[1].trim();
                start_time = Integer.parseInt(cmd[2]);
                end_time = Integer.parseInt(cmd[3]);
                System.out.print(". Expecting Arrival time between: " + start_time + " and " + end_time + " and belongs to: " + project_name);
                for (JobReport_ jobReport_ : out) {
                    if (jobReport_.arrival_time() < start_time || jobReport_.arrival_time() > end_time ||
                            jobReport_.project_name().compareToIgnoreCase(project_name.trim()) != 0) {
                        System.out.println(" FAILED");
                        success = false;
                        break;
                    }

                }
                if (success)
                    System.out.println(" SUCCESS");
                break;
            case "NEW_USER":
                System.out.print("User query");
                user_name = cmd[1].trim();
                start_time = Integer.parseInt(cmd[2]);
                end_time = Integer.parseInt(cmd[3]);
                System.out.print(". Expecting Arrival time between: " + start_time + " and " + end_time + " and belongs to: " + user_name);

                for (JobReport_ jobReport_ : out) {
                    if (jobReport_.arrival_time() < start_time || jobReport_.arrival_time() > end_time ||
                            jobReport_.user().compareToIgnoreCase(user_name.trim()) != 0) {
                        System.out.println(" FAILED");
                        success = false;
                        break;
                    }

                }
                if (success)
                    System.out.println(" SUCCESS");
                break;
            case "NEW_PROJECTUSER":
                System.out.print("Project User query");
                project_name = cmd[1].trim();
                user_name = cmd[2].trim();
                start_time = Integer.parseInt(cmd[3]);
                end_time = Integer.parseInt(cmd[4]);
                System.out.print(". Expecting Arrival time between: " + start_time + " and " + end_time + " and belongs to: " + project_name + ", " + user_name);
                for (JobReport_ jobReport_ : out) {
                    if (jobReport_.arrival_time() < start_time || jobReport_.arrival_time() > end_time
                            || jobReport_.project_name().compareToIgnoreCase(project_name.trim()) != 0 || jobReport_.user().compareToIgnoreCase(user_name.trim()) != 0) {
                        System.out.println(" FAILED");
                        success = false;
                        break;
                    }

                }
                if (success)
                    System.out.println(" SUCCESS");
                break;
            case "NEW_PRIORITY":
                System.out.println();
//                System.out.print("Priority query");
//                Integer priority = Integer.parseInt(cmd[1]);
//                System.out.print(". Expecting Priority: " + priority + "  or more");
////                for (JobReport_ jobReport_ : out) {
////                    if (jobReport_.priority() < priority) {
////                        System.out.println(" FAILED");
////                        success = false;
////                        break;
////                    }
////
////                }
//                if (success)
//                    System.out.println(" NOT CHECKED");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + cmd[0]);
        }
//        if (!debug)
//            System.setOut(o);
    }


    private static long calculateSum(List<Long> longs) {
        Long sum = 0l;
        if (!longs.isEmpty()) {
            for (Long mark : longs) {
                sum += mark;
            }
        }
        return sum;
    }

    private static double calculateAverage(List<Long> longs) {
        Long sum = 0l;
        if (!longs.isEmpty()) {
            for (Long mark : longs) {
                sum += mark;
            }
            return sum.doubleValue() / longs.size();
        }
        return sum;
    }
}