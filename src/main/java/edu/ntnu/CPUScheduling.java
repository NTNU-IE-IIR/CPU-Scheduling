package edu.ntnu;

import java.util.*;

public class CPUScheduling {
  public static double[] firstComeFirstServed(LinkedList<Process> processes) {
    double[] time = new double[2];
    double avgWaitingTime;
    double avgTurnAroundTime;

    int currentTime = 0, totalWaitingTime = 0, totalTurnAroundTime = 0, n = processes.size();

    processes.sort(Comparator.comparingInt(p -> p.at));

    while (!processes.isEmpty()) {
      Process process = processes.poll();
      int startTime = Math.max(currentTime, process.at);
      int completionTime = startTime + process.bt;
      int turnaroundTime = completionTime - process.at;
      int waitingTime = turnaroundTime - process.bt;

      totalWaitingTime += waitingTime;
      totalTurnAroundTime += turnaroundTime;
      currentTime = completionTime;
    }

    avgWaitingTime = (double) totalWaitingTime / n;
    avgTurnAroundTime = (double) totalTurnAroundTime / n;

    time[0] = avgWaitingTime;
    time[1] = avgTurnAroundTime;
    return time;
  }

  public static double[] preEmptivePriorityScheduling(LinkedList<Process> processes) {
    double[] time = new double[2];
    double avgWaitingTime;
    double avgTurnAroundTime;

    int totalWaitingTime = 0, totalTurnAroundTime = 0, n = processes.size();

    HashSet<Process> activeProcesses = new HashSet<>();
    HashMap<Process, Integer> completionTimes = new HashMap<>();

    int totalTime = calculateTime(processes);
    for (int currentTime = 0; currentTime < totalTime; currentTime++) {
      Process prioritizedProcess = null;
      for (Process process : processes) {
        if (process.bt <= 0) {
          completionTimes.put(process, currentTime);
          activeProcesses.remove(process);
        }
        if (process.at <= currentTime && (prioritizedProcess == null || prioritizedProcess.priority > process.priority)) {
          prioritizedProcess = process;
        }
      }

      activeProcesses.add(prioritizedProcess);

      System.out.println("Prioritized Process: " + prioritizedProcess.id);
      assert prioritizedProcess != null;
      prioritizedProcess.bt -= 1;
    }

    for (Process process : completionTimes.keySet()) {
      int ct = completionTimes.get(process);
      int tat = ct - process.at;
      totalTurnAroundTime += tat;
      totalWaitingTime += tat - process.bt;
    }

    avgWaitingTime = (double) totalWaitingTime / n;
    avgTurnAroundTime = (double) totalTurnAroundTime / n;

    time[0] = avgWaitingTime;
    time[1] = avgTurnAroundTime;
    return time;
  }

  public static int calculateTime(LinkedList<Process> processes) {
    int totalTime = 0;
    for (Process process : processes) {
      totalTime += process.bt;
    }
    return totalTime;
  }
}