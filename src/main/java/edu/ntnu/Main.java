package edu.ntnu;

import java.util.LinkedList;

public class Main {
  public static void main(String[] args) {
    // Create an ArrayList to hold the processes
    LinkedList<Process> processes = new LinkedList<>();

    // Add processes to the list
    // BT: Burst Time, AT: Arrival Time
    processes.add(new Process(1, 0, 5, 2)); // ID=1, AT=0, BT=5, Priority=2
    processes.add(new Process(2, 2, 3, 1)); // ID=2, AT=2, BT=3, Priority=1
    processes.add(new Process(3, 3, 4, 3)); // ID=3, AT=3, BT=4, Priority=3
    processes.add(new Process(4, 5, 2, 2)); // ID=4, AT=5, BT=2, Priority=4
    processes.add(new Process(5, 6, 1, 4)); // ID=5, AT=6, BT=1, Priority=5

    // Print the list to verify
    for (Process p : processes) {
      System.out.println("Process ID: " + p.id +
              ", Arrival Time: " + p.at +
              ", Burst Time: " + p.bt +
              ", Priority: " + p.priority);
    }

    double[] preEmptivePriorityScheduling = CPUScheduling.preEmptivePriorityScheduling(processes);
    System.out.println("Average Waiting Time: " + preEmptivePriorityScheduling[0]);
    System.out.println("Average Turnaround Time: " + preEmptivePriorityScheduling[1]);
  }
}