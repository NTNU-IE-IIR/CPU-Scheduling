package edu.ntnu;

import java.util.*;

public class CPUScheduling {
  // First-Come-First-Served (FCFS) scheduling algorithm
  public static double[] firstComeFirstServed(LinkedList<Process> processes) {
    double[] time = new double[2];
    double avgWaitingTime;
    double avgTurnAroundTime;

    int currentTime = 0, totalWaitingTime = 0, totalTurnAroundTime = 0, n = processes.size();

    // Sort processes by arrival time, and by burst time if arrival times are equal
    processes.sort(Comparator.comparingInt((Process p) -> p.at).thenComparingInt(p -> p.bt));

    // Process each process in the sorted order
    while (!processes.isEmpty()) {
      Process process = processes.poll();
      // Determine the start time of the process
      int startTime = Math.max(currentTime, process.at);
      // Calculate the completion time of the process
      int completionTime = startTime + process.bt;
      // Calculate turnaround time (completion time - arrival time)
      int turnaroundTime = completionTime - process.at;
      // Calculate waiting time (turnaround time - burst time)
      int waitingTime = turnaroundTime - process.bt;

      totalWaitingTime += waitingTime;
      totalTurnAroundTime += turnaroundTime;
      // Update the current time to the completion time of the current process
      currentTime = completionTime;
    }

    // Calculate the average waiting time and turnaround time
    avgWaitingTime = (double) totalWaitingTime / n;
    avgTurnAroundTime = (double) totalTurnAroundTime / n;

    time[0] = avgWaitingTime;
    time[1] = avgTurnAroundTime;
    return time;
  }

  // Pre-Emptive Priority Scheduling algorithm
  public static double[] preEmptivePriorityScheduling(LinkedList<Process> processes) {
    double[] time = new double[2];
    double avgWaitingTime;
    double avgTurnAroundTime;

    int totalWaitingTime = 0, totalTurnAroundTime = 0, n = processes.size();

    // Map to store the completion times of processes
    HashMap<Integer, Integer> completionTimes = new HashMap<>();

    // Create a copy of the original processes list to preserve initial burst times
    ArrayList<Process> copy = new ArrayList<>();
    for (Process process : processes) {
      copy.add(new Process(process.id, process.at, process.bt, process.priority));
    }

    // Calculate the total time required for all processes
    int totalTime = calculateTime(processes);

    // Iterate through each time unit until all processes are completed
    for (int currentTime = 0; currentTime < totalTime; currentTime++) {
      Process prioritizedProcess = null;
      // Select the process with the highest priority that has arrived
      for (int i = 0; i < processes.size(); i++) {
        Process process = processes.get(i);
        if (process.at <= currentTime
                && (prioritizedProcess == null
                || prioritizedProcess.priority > process.priority)) {
          prioritizedProcess = process;
        }
      }

      // Decrement the burst time of the selected process
      assert prioritizedProcess != null;
      prioritizedProcess.bt -= 1;
      // If the process is completed, remove it from the list and record its completion time
      if (prioritizedProcess.bt <= 0) {
        processes.remove(prioritizedProcess);
        completionTimes.put(prioritizedProcess.id, currentTime + 1);
      }
    }

    // Calculate the total waiting time and turnaround time for each process
    for (Process process : copy) {
      int ct = completionTimes.get(process.id);
      int tat = ct - process.at;
      totalTurnAroundTime += tat;
      totalWaitingTime += tat - process.bt;
    }

    // Calculate the average waiting time and turnaround time
    avgWaitingTime = (double) totalWaitingTime / n;
    avgTurnAroundTime = (double) totalTurnAroundTime / n;

    time[0] = avgWaitingTime;
    time[1] = avgTurnAroundTime;
    return time;
  }

  // Calculate the total time required for all processes based on their burst times
  public static int calculateTime(LinkedList<Process> processes) {
    int totalTime = 0;
    for (Process process : processes) {
      totalTime += process.bt;
    }
    return totalTime;
  }
}